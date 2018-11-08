package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SpotifySongFeatureData implements Parcelable {

    @SerializedName("danceability")
    public final double danceability;

    @SerializedName("energy")
    public final double energy;

    @SerializedName("acousticness")
    public final double acousticness;

    @SerializedName("valence")
    public final double valence;

    protected SpotifySongFeatureData(Parcel in) {
        danceability = in.readDouble();
        energy = in.readDouble();
        acousticness = in.readDouble();
        valence = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(danceability);
        dest.writeDouble(energy);
        dest.writeDouble(acousticness);
        dest.writeDouble(valence);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpotifySongFeatureData> CREATOR = new Creator<SpotifySongFeatureData>() {
        @Override
        public SpotifySongFeatureData createFromParcel(Parcel in) {
            return new SpotifySongFeatureData(in);
        }

        @Override
        public SpotifySongFeatureData[] newArray(int size) {
            return new SpotifySongFeatureData[size];
        }
    };
}
