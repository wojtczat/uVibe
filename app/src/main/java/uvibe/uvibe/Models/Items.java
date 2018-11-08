package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mevin on 3/12/2018.
 */

public class Items implements Parcelable{

    @SerializedName("album")
    public final Album album;

    @SerializedName("artists")
    public final List<Artists> artists;

    @SerializedName("id")
    public final String id;

    @SerializedName("name")
    public final String name;

    @SerializedName("uri")
    public final String uri;


    protected Items(Parcel in) {
        album = in.readParcelable(Album.class.getClassLoader());
        artists = in.createTypedArrayList(Artists.CREATOR);
        id = in.readString();
        name = in.readString();
        uri = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(album, flags);
        dest.writeTypedList(artists);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(uri);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };
}
