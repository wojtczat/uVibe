package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.*;

/**
 * Created by Mevin on 3/12/2018.
 */

public class UserTopTracksObject implements Parcelable {

    @SerializedName("items")
    public final List<Items> items;

    @SerializedName("total")
    public final int total;

    @SerializedName("display_name")
    public final String display_name;

    @SerializedName("email")
    public final String email;

    @SerializedName("id")
    public final String id;


    protected UserTopTracksObject(Parcel in) {
        items = in.createTypedArrayList(Items.CREATOR);
        total = in.readInt();
        display_name = in.readString();
        email = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
        dest.writeInt(total);
        dest.writeString(display_name);
        dest.writeString(email);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserTopTracksObject> CREATOR = new Creator<UserTopTracksObject>() {
        @Override
        public UserTopTracksObject createFromParcel(Parcel in) {
            return new UserTopTracksObject(in);
        }

        @Override
        public UserTopTracksObject[] newArray(int size) {
            return new UserTopTracksObject[size];
        }
    };
}

