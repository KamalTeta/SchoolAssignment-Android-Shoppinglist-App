package org.projects.shoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kamal on 12/05/16.
 * Here we define the class for the list items
 */
public class Product implements Parcelable {

        private String name;
        private int quantity;
        private String unit;

         public String getName(){
        return name;
    }

        public int getQuantity(){
            return quantity;
        }

        public String getUnit(){
            return unit;
        }

        public void setName(String name){
            this.name = name;
        }

        public void setQuantity(int quantity){
            this.quantity = quantity;
        }

        public void setUnit(String unit){
            this.unit = unit;
        }

        public Product() {}

        public Product(String name, int quantity, String unit)
        {
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
        }


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


        public static final Parcelable.Creator CREATOR  = new Parcelable.Creator() {
            public Product createFromParcel(Parcel in) { return new Product(in); }

            public Object[] newArray(int size) { return new Object[0]; }
        };

        public Product(Parcel in) {
            name = in.readString();
            quantity = in.readInt();
            unit = in.readString();
        }
}
