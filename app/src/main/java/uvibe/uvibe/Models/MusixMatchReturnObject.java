package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class MusixMatchReturnObject implements Parcelable {

    @SerializedName("message")
    public final MessageObject message;

    protected MusixMatchReturnObject(Parcel in) {
        message = in.readParcelable(MessageObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(message, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusixMatchReturnObject> CREATOR = new Creator<MusixMatchReturnObject>() {
        @Override
        public MusixMatchReturnObject createFromParcel(Parcel in) {
            return new MusixMatchReturnObject(in);
        }

        @Override
        public MusixMatchReturnObject[] newArray(int size) {
            return new MusixMatchReturnObject[size];
        }
    };
}
