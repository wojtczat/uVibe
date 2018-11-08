package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class MusixMatchBodyObject implements Parcelable {

    @SerializedName("lyrics")
    public final LyricsObject lyrics;

    protected MusixMatchBodyObject(Parcel in) {
        lyrics = in.readParcelable(LyricsObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(lyrics, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusixMatchBodyObject> CREATOR = new Creator<MusixMatchBodyObject>() {
        @Override
        public MusixMatchBodyObject createFromParcel(Parcel in) {
            return new MusixMatchBodyObject(in);
        }

        @Override
        public MusixMatchBodyObject[] newArray(int size) {
            return new MusixMatchBodyObject[size];
        }
    };
}
