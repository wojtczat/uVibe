package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/14/2018.
 */

public class MessageObject implements Parcelable {

    @SerializedName("body")
    public final MusixMatchBodyObject body;

    protected MessageObject(Parcel in) {
        body = in.readParcelable(MusixMatchBodyObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(body, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageObject> CREATOR = new Creator<MessageObject>() {
        @Override
        public MessageObject createFromParcel(Parcel in) {
            return new MessageObject(in);
        }

        @Override
        public MessageObject[] newArray(int size) {
            return new MessageObject[size];
        }
    };
}
