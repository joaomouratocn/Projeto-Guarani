package br.com.devjmcn.projetoguarani.model.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleObject implements Parcelable {
    String name;

    public SimpleObject(String name) {
        this.name = name;
    }

    protected SimpleObject(Parcel in) {
        name = in.readString();
    }

    public static final Creator<SimpleObject> CREATOR = new Creator<SimpleObject>() {
        @Override
        public SimpleObject createFromParcel(Parcel in) {
            return new SimpleObject(in);
        }

        @Override
        public SimpleObject[] newArray(int size) {
            return new SimpleObject[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "SimpleObject{name='" + name + "'}";
    }
}
