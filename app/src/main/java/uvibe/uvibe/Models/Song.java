package uvibe.uvibe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mevin on 3/27/2018.
 */

public class Song implements Parcelable{
    public String artistName;
    public String trackName;
    public String spotifyID;
    public String lyrics;
    public String uri;
    public double acousticness;
    public double energy;
    public double valence;
    public double danceability;
    public EmotionAnalysis lyricEmotions;
    public EmotionAnalysis energyEmotions;
    public EmotionAnalysis bpmEmotions;

    public Song() {
        this.artistName = null;
        this.trackName = null;
        this.spotifyID = null;
        this.lyrics = null;
        this.acousticness = 0.0;
        this.energy = 0.0;
        this.valence = 0.0;
        this.danceability = 0.0;
        this.lyricEmotions = null;
        this.energyEmotions = null;
        this.bpmEmotions = null;
    }


    protected Song(Parcel in) {
        artistName = in.readString();
        trackName = in.readString();
        spotifyID = in.readString();
        lyrics = in.readString();
        uri = in.readString();
        acousticness = in.readDouble();
        energy = in.readDouble();
        valence = in.readDouble();
        danceability = in.readDouble();
        lyricEmotions = in.readParcelable(EmotionAnalysis.class.getClassLoader());
        energyEmotions = in.readParcelable(EmotionAnalysis.class.getClassLoader());
        bpmEmotions = in.readParcelable(EmotionAnalysis.class.getClassLoader());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return this.trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getSpotifyID() {
        return this.spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public String getLyrics() {
        return this.lyrics;
    }

    public double getAcousticness() { return this.acousticness; }

    public void setAcousticness(double acousticness) { this.acousticness = acousticness; }

    public double getEnergy() { return this.energy; }

    public void setEnergy(double energy) { this.energy = energy; }

    public double getValence() { return this.valence; }

    public void setValence(double valence) { this.valence = valence; }

    public double getDanceability() { return this.danceability; }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public void setLyrics(String lyrics) { this.lyrics = lyrics; }

    public String getURI() { return this.uri; }

    public void setURI(String URI) { this.uri = URI; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artistName);
        parcel.writeString(trackName);
        parcel.writeString(spotifyID);
        parcel.writeString(lyrics);
        parcel.writeString(uri);
        parcel.writeDouble(acousticness);
        parcel.writeDouble(energy);
        parcel.writeDouble(valence);
        parcel.writeDouble(danceability);
        parcel.writeParcelable(lyricEmotions, i);
        parcel.writeParcelable(energyEmotions, i);
        parcel.writeParcelable(bpmEmotions, i);
    }
}

