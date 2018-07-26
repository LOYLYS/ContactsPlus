package edu.ptit.vhlee.contactsplus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<Contact> mContacts;
    private Context mContext;
    private LayoutInflater mInflater;
    private PhoneFunction phoneFunction;

    public ContactAdapter(ArrayList<Contact> mContacts, PhoneFunction phoneFunction) {
        this.mContacts = mContacts;
        this.phoneFunction = phoneFunction;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.item_contact,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Contact contact = mContacts.get(position);
        holder.mImageAvatar.setImageResource(R.drawable.ic_account);
        holder.mTextName.setText(contact.getName());
        holder.mTextPhone.setText(contact.getPhone());
        if (contact.isFavorite()) {
            holder.mImageFavorite.setImageResource(R.drawable.ic_liked);
        } else {
            holder.mImageFavorite.setImageResource(R.drawable.ic_like);
        }
        holder.mImageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneFunction.makeCall(position);
            }
        });
        holder.mImageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneFunction.makeFavorite(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageAvatar, mImageFavorite, mImageCall;
        private TextView mTextName;
        private TextView mTextPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mImageFavorite = itemView.findViewById(R.id.image_favorite);
            mImageCall = itemView.findViewById(R.id.image_call);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextPhone = itemView.findViewById(R.id.text_phone);
        }
    }
}
