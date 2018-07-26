package edu.ptit.vhlee.contactsplus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PhoneFunction{
    public static final int REQUEST_CODE = 1;
    public static final String TEL_PHONE = "tel:";
    private RecyclerView mRecycler;
    private ContactAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkPermissionRuntime();
    }

    private void checkPermissionRuntime() {
        String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE};
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            for(String permission : permissions) {
                if (checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, REQUEST_CODE);
                } else {
                    readContacts();
                }
            }
        } else {
            readContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== REQUEST_CODE) {
            for (int result : grantResults) {
                if (result==PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else if (result==PackageManager.PERMISSION_DENIED){
                    checkPermissionRuntime();
                }
            }

        }
    }

    private void init() {
        mContacts = new ArrayList<>();
        mRecycler = findViewById(R.id.recycler_contacts);
        mLayoutManager = new LinearLayoutManager(this);
    }

    private void readContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver()
                .query(uri, null, null,null,null);
        while (cursor.moveToNext()) {
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact(displayName, phone);
            mContacts.add(contact);
        }
        showList();
    }

    @SuppressLint("MissingPermission")
    public void makeCall(int position) {
        Contact contact = mContacts.get(position);
        String uri = TEL_PHONE + contact.getPhone();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public void makeFavorite(int position) {
        Contact contact = mContacts.get(position);
        if (contact.isFavorite()) {
            contact.setFavorite(false);
        } else {
            contact.setFavorite(true);
        }
        mContacts.set(position,contact);
        mAdapter.notifyDataSetChanged();
    }

    private void showList() {
        mAdapter = new ContactAdapter(mContacts, this);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }
}
