package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaylistCreateReturn implements Parcelable {

    @SerializedName("id")
    public final String id;

    @SerializedName("name")
    public final String name;

    @SerializedName("uri")
    public final String uri;

    @SerializedName("external_urls")
    public final PlaylistSpotifyObject external_urls;


    protected PlaylistCreateReturn(Parcel in) {
        id = in.readString();
        name = in.readString();
        uri = in.readString();
        external_urls = in.readParcelable(PlaylistSpotifyObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(uri);
        dest.writeParcelable(external_urls, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaylistCreateReturn> CREATOR = new Creator<PlaylistCreateReturn>() {
        @Override
        public PlaylistCreateReturn createFromParcel(Parcel in) {
            return new PlaylistCreateReturn(in);
        }

        @Override
        public PlaylistCreateReturn[] newArray(int size) {
            return new PlaylistCreateReturn[size];
        }
    };
}
