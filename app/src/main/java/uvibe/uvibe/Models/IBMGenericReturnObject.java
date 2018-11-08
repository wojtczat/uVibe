package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class IBMGenericReturnObject implements Parcelable {

    @SerializedName("sentiment")
    public final Sentiment sentiment;

    @SerializedName("emotion")
    public  final EmotionO emotion;

    protected IBMGenericReturnObject(Parcel in) {
        sentiment = in.readParcelable(Sentiment.class.getClassLoader());
        emotion = in.readParcelable(EmotionO.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(sentiment, flags);
        dest.writeParcelable(emotion, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IBMGenericReturnObject> CREATOR = new Creator<IBMGenericReturnObject>() {
        @Override
        public IBMGenericReturnObject createFromParcel(Parcel in) {
            return new IBMGenericReturnObject(in);
        }

        @Override
        public IBMGenericReturnObject[] newArray(int size) {
            return new IBMGenericReturnObject[size];
        }
    };
}
