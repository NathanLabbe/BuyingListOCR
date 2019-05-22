package com.example.buyinglistocr.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represent the List object
 */
public class List implements Parcelable {

    // Different attributes of List
    private long id;
    private String name;
    private double spent;

    /**
     * The constructor of the class
     * @param id - The id of the list
     * @param name - The name of the list
     * @param spent - The spent for the list
     */
    public List(long id, String name, double spent) {

        this.id = id;
        this.name = name;
        this.spent = spent;

    }

    protected List(Parcel in) {

        id = in.readLong();
        name = in.readString();
        spent = in.readDouble();

    }

    public static final Creator<List> CREATOR = new Creator<List>() {

        @Override
        public List createFromParcel(Parcel in) {

            return new List(in);

        }

        @Override
        public List[] newArray(int size) {

            return new List[size];

        }

    };

    /**
     * Getter of the id attribute
     * @return - The id
     */
    public long getId() {

        return this.id;

    }

    /**
     * Setter of the id attribute
     * @param id - The id
     */
    public void setId(long id) {

        this.id = id;

    }

    /**
     * Getter of the name attribute
     * @return - The name
     */
    public String getName() {

        return this.name;

    }

    /**
     * Setter of the name attribute
     * @param name - The name
     */
    public void setName(String name) {

        this.name = name;

    }

    /**
     * Getter of the spent attribute
     * @return - The spent
     */
    public double getSpent() {

        return this.spent;

    }

    /**
     * Setter of the spent attribute
     * @param spent - The spent
     */
    public void setSpent(double spent) {

        this.spent = spent;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeDouble(this.spent);

    }
}



