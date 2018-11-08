package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaylistSpotifyObject implements Parcelable {

    @SerializedName("spotify")
    public final String spotify;

    protected PlaylistSpotifyObject(Parcel in) {
        spotify = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spotify);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaylistSpotifyObject> CREATOR = new Creator<PlaylistSpotifyObject>() {
        @Override
        public PlaylistSpotifyObject createFromParcel(Parcel in) {
            return new PlaylistSpotifyObject(in);
        }

        @Override
        public PlaylistSpotifyObject[] newArray(int size) {
            return new PlaylistSpotifyObject[size];
        }
    };
}
