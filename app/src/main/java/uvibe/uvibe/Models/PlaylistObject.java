package uvibe.uvibe.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MattPo on 2018-04-09.
 */

public class PlaylistObject {

    @SerializedName("name")
    public String name;

    @SerializedName("public")
    public boolean privacy;

    public PlaylistObject(String name) {
        this.name = name;
        this.privacy = false;
    }

}
