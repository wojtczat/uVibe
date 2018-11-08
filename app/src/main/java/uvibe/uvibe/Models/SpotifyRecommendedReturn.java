package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyRecommendedReturn implements Parcelable {

    @SerializedName("tracks")
    public List<Items> tracks;

    protected SpotifyRecommendedReturn(Parcel in) {
        tracks = in.createTypedArrayList(Items.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tracks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpotifyRecommendedReturn> CREATOR = new Creator<SpotifyRecommendedReturn>() {
        @Override
        public SpotifyRecommendedReturn createFromParcel(Parcel in) {
            return new SpotifyRecommendedReturn(in);
        }

        @Override
        public SpotifyRecommendedReturn[] newArray(int size) {
            return new SpotifyRecommendedReturn[size];
        }
    };
}
