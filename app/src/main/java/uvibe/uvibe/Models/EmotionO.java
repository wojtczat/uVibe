package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class EmotionO implements Parcelable {

    @SerializedName("document")
    public final Document document;

    protected EmotionO(Parcel in) {
        document = in.readParcelable(Document.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(document, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EmotionO> CREATOR = new Creator<EmotionO>() {
        @Override
        public EmotionO createFromParcel(Parcel in) {
            return new EmotionO(in);
        }

        @Override
        public EmotionO[] newArray(int size) {
            return new EmotionO[size];
        }
    };
}
