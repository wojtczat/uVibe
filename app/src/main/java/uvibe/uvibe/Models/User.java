package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Mevin on 3/27/2018.
 */

public class User implements Parcelable{
    public List<Song> songList;
    public EmotionAnalysis emotionAnalysis;
    public EmotionAnalysis avgLyricsAnalysis;
    public EmotionAnalysis avgEnergyAnalysis;
    public EmotionAnalysis avgBPMAnalysis;
    public String name;
    public String email;
    public List<Song> recommendedList;
    public String uID;

    public User(@Nullable List<Song> songs) {
        this.songList = songs;
        this.name = null;
        this.email = null;
        this.uID = null;
    }

    protected User(Parcel in) {
        songList = in.createTypedArrayList(Song.CREATOR);
        emotionAnalysis = in.readParcelable(EmotionAnalysis.class.getClassLoader());
        avgLyricsAnalysis = in.readParcelable(EmotionAnalysis.class.getClassLoader());
        avgEnergyAnalysis = in.readParcelable(EmotionAnalysis.class.getClassLoader());
        avgBPMAnalysis = in.readParcelable(EmotionAnalysis.class.getClassLoader());
        name = in.readString();
        email = in.readString();
        recommendedList = in.createTypedArrayList(Song.CREATOR);
        uID = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {return this.name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public List<Song> getSongList() { return this.songList; }

    public void setSongs(List<Song> songs) {
        this.songList = songs;
    }

    public List<Song> getRecommended() { return this.recommendedList; }

    public void setRecommended(List<Song> songs) { this.recommendedList = songs; }

    public String getuID() { return this.uID; }

    public void setuID(String id) { this.uID = id; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(songList);
        parcel.writeParcelable(emotionAnalysis, i);
        parcel.writeParcelable(avgLyricsAnalysis, i);
        parcel.writeParcelable(avgBPMAnalysis, i);
        parcel.writeParcelable(avgEnergyAnalysis, i);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeTypedList(recommendedList);
        parcel.writeString(uID);
    }
}
