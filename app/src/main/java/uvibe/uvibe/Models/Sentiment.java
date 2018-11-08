package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class Sentiment implements Parcelable {

    @SerializedName("document")
    public final Document document;

    protected Sentiment(Parcel in) {
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

    public static final Creator<Sentiment> CREATOR = new Creator<Sentiment>() {
        @Override
        public Sentiment createFromParcel(Parcel in) {
            return new Sentiment(in);
        }

        @Override
        public Sentiment[] newArray(int size) {
            return new Sentiment[size];
        }
    };
}
