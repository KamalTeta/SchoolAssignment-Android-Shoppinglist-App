package org.projects.shoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EAK on 12/05/16.
 */
public class Product implements Parcelable {

        private String name;
        private int quantity;
        private String unit;




        public Product(String name, int quantity, String unit)
        {
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
        }



// get and set method

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public String toString() {return quantity + " " + unit + " " + name;}

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(quantity);
            dest.writeString(unit);
        }

        // Creator
        public static final Parcelable.Creator CREATOR
                = new Parcelable.Creator() {
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

        // "De-parcel object
        public Product(Parcel in) {
            name = in.readString();
            quantity = in.readInt();
            unit = in.readString();
        }
}