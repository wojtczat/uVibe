package uvibe.uvibe.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class IBMFeaturesObject {

    @SerializedName("emotion")
    public EmotionRequestObject emotion;

    @SerializedName("sentiment")
    public EmotionRequestObject sentiment;

    public IBMFeaturesObject(EmotionRequestObject emotion, EmotionRequestObject sentiment) {
        this.emotion = emotion;
        this.sentiment = sentiment;
    }
}
