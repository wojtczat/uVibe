package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Mevin on 3/27/2018.
 */

public class EmotionAnalysis implements Parcelable {
    @Nullable
    public double joy;
    public double sadness;
    public double fear;
    public double disgust;
    public double anger;
    public double sentiment;

    public EmotionAnalysis(IBMGenericReturnObject returnObject) {
        this.sentiment = returnObject.sentiment.document.score;
        this.joy = returnObject.emotion.document.emotion.joy;
        this.sadness = returnObject.emotion.document.emotion.sadness;
        this.fear = returnObject.emotion.document.emotion.fear;
        this.disgust = returnObject.emotion.document.emotion.disgust;
        this.anger = returnObject.emotion.document.emotion.anger;
    }

    public EmotionAnalysis() {
        this.joy = 0;
        this.sadness = 0;
        this.fear = 0;
        this.disgust = 0;
        this.anger = 0;
        this.sentiment = 0;
    }

    public EmotionAnalysis(double joy, double sadness, double fear, double anger, double disgust) {
        this.joy = joy;
        this.sadness = sadness;
        this.fear = fear;
        this.disgust = disgust;
        this.anger = anger;
        this.sentiment = 0;
    }

    protected EmotionAnalysis(Parcel in) {
        joy = in.readDouble();
        sadness = in.readDouble();
        fear = in.readDouble();
        disgust = in.readDouble();
        anger = in.readDouble();
        sentiment = in.readDouble();
    }

    public static final Creator<EmotionAnalysis> CREATOR = new Creator<EmotionAnalysis>() {
        @Override
        public EmotionAnalysis createFromParcel(Parcel in) {
            return new EmotionAnalysis(in);
        }

        @Override
        public EmotionAnalysis[] newArray(int size) {
            return new EmotionAnalysis[size];
        }
    };

    @Nullable
    public double getJoy() {
        return this.joy;
    }

    public void setJoy(double joyValue) {
        this.joy = joyValue;
    }

    public double getSadness() {
        return this.sadness;
    }

    public void setSadness(double sadnessValue) {
        this.sadness = sadnessValue;
    }

    public double getFear() {
        return this.fear;
    }

    public void setFear(double fearValue) {
        this.fear = fearValue;
    }

    public double getDisgust() {
        return this.disgust;
    }

    public void setDisgust(double disgustValue) {
        this.disgust = disgustValue;
    }

    public double getAnger() {
        return this.anger;
    }

    public void setAnger(double angerValue) {
        this.anger = angerValue;
    }

    public double getSentiment() {
        return this.sentiment;
    }

    public void setSentiment(double sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(joy);
        parcel.writeDouble(sadness);
        parcel.writeDouble(fear);
        parcel.writeDouble(disgust);
        parcel.writeDouble(anger);
        parcel.writeDouble(sentiment);
    }
}
