package uvibe.uvibe.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mevin on 3/13/2018.
 */

public class IBMRequestObject {

    @SerializedName("text")
    public String text;

    @SerializedName("features")
    public IBMFeaturesObject features;

    public IBMRequestObject(String text, IBMFeaturesObject features) {
        this.text = text;
        this.features = features;
    }
}
