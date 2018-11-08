package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpotifyFeaturesReturn implements Parcelable{

    @SerializedName("audio_features")
    public final List<SpotifySongFeatureData> audio_features;

    protected SpotifyFeaturesReturn(Parcel in) {
        audio_features = in.createTypedArrayList(SpotifySongFeatureData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(audio_features);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpotifyFeaturesReturn> CREATOR = new Creator<SpotifyFeaturesReturn>() {
        @Override
        public SpotifyFeaturesReturn createFromParcel(Parcel in) {
            return new SpotifyFeaturesReturn(in);
        }

        @Override
        public SpotifyFeaturesReturn[] newArray(int size) {
            return new SpotifyFeaturesReturn[size];
        }
    };
}
