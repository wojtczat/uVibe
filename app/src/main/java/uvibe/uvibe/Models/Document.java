package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class Document implements Parcelable {

    @SerializedName("score")
    public final double score;

    @SerializedName("label")
    public final String label;

    @SerializedName("emotion")
    public final Emotion emotion;

    protected Document(Parcel in) {
        score = in.readDouble();
        label = in.readString();
        emotion = in.readParcelable(Emotion.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(score);
        dest.writeString(label);
        dest.writeParcelable(emotion, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };
}
