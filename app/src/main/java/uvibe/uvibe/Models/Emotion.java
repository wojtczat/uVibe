package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class Emotion implements Parcelable {

    @SerializedName("sadness")
    public final double sadness;

    @SerializedName("joy")
    public final double joy;

    @SerializedName("fear")
    public final double fear;

    @SerializedName("disgust")
    public final double disgust;

    @SerializedName("anger")
    public final double anger;

    protected Emotion(Parcel in) {
        sadness = in.readDouble();
        joy = in.readDouble();
        fear = in.readDouble();
        disgust = in.readDouble();
        anger = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(sadness);
        dest.writeDouble(joy);
        dest.writeDouble(fear);
        dest.writeDouble(disgust);
        dest.writeDouble(anger);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Emotion> CREATOR = new Creator<Emotion>() {
        @Override
        public Emotion createFromParcel(Parcel in) {
            return new Emotion(in);
        }

        @Override
        public Emotion[] newArray(int size) {
            return new Emotion[size];
        }
    };
}
