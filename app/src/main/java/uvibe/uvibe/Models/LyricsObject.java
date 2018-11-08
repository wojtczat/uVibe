package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class LyricsObject implements Parcelable {

    @SerializedName("lyrics_id")
    public final int lyrics_id;

    @SerializedName("lyrics_body")
    public final String lyrics_body;

    protected LyricsObject(Parcel in) {
        lyrics_id = in.readInt();
        lyrics_body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lyrics_id);
        dest.writeString(lyrics_body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LyricsObject> CREATOR = new Creator<LyricsObject>() {
        @Override
        public LyricsObject createFromParcel(Parcel in) {
            return new LyricsObject(in);
        }

        @Override
        public LyricsObject[] newArray(int size) {
            return new LyricsObject[size];
        }
    };
}
