package edu.ptit.vhlee.contactsplus;

public class Contact {
    private String mName;
    private String mPhone;
    private boolean mFavorite;

    public Contact(String Name, String Phone) {
        this.mName = Name;
        this.mPhone = Phone;
        this.mFavorite = false;
    }

    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String Phone) {
        this.mPhone = Phone;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean Favorite) {
        this.mFavorite = Favorite;
    }
}
