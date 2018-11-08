package uvibe.uvibe.Controllers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import uvibe.uvibe.InputListener;
import uvibe.uvibe.Models.*;

/**
 * Created by Mevin on 3/12/2018.
 */

public class SpotifyDataCollection {
    private final User user;
    private List<Song> tracks;
    private Stack<Boolean> musixmatchPromises;

    public UserTopTracksObject topTracks;
    public final InputListener<User> callback;

    public SpotifyDataCollection(User user, InputListener<User> callback) {
        this.user = user;
        this.callback = callback;
    }

    public void makeSpotifyUserCall(final String accessToken) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                topTracks = gson.fromJson(response.body().string(), UserTopTracksObject.class);
                user.setName(topTracks.display_name);
                user.setEmail(topTracks.email);
                //Log.d("email", topTracks.email);
                user.setuID(topTracks.id);
                Log.d("email", topTracks.email);

            }
        });
    }

    public void makeSpotifyCall(final String accessToken) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?limit=50")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Parse JSON
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                topTracks = gson.fromJson(response.body().string(), UserTopTracksObject.class);

                //Log.d("# of tracks", topTracks.items.size() + "");

                tracks = new ArrayList<>();
                musixmatchPromises = new Stack<>();
                Song a;
                for (int i = 0; i < topTracks.items.size(); i++) {
                    a = new Song();
                    a.artistName = topTracks.items.get(i).artists.get(0).name;
                    a.spotifyID = topTracks.items.get(i).id;
                    a.trackName = topTracks.items.get(i).name;
                    a.uri = topTracks.items.get(i).uri;
                    tracks.add(a);

                    musixmatchPromises.push(true);
                }

                // Grab lyrics for all songs
                getLyricsForSongs(tracks);
                makeSpotifyUserCall(accessToken);
                makeSpotifyFeaturesCall(tracks, accessToken);

                // Generate recommended tracks
                makeSpotifyRecommendedCall(tracks, accessToken);

                // Set user's songs
                user.setSongs(tracks);
            }
        });


    }

    public String featureParamBuilder(List<Song> songList) {
        String ids = "";
        for (Song song : songList) {
            if (ids == "") {
                ids += song.spotifyID;
            } else {
                ids += "," + song.spotifyID;
            }
        }
        return ids;
    }

    public void makeSpotifyFeaturesCall(final List<Song> songList, String accessToken) {
        String ids = featureParamBuilder(songList);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/audio-features/?ids=" + ids)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                SpotifyFeaturesReturn spotifyFeaturesReturn = gson.fromJson(response.body().string(), SpotifyFeaturesReturn.class);

                for (int i = 0; i < songList.size(); i++) {
                    songList.get(i).setAcousticness(spotifyFeaturesReturn.audio_features.get(i).acousticness);

                    songList.get(i).setEnergy(spotifyFeaturesReturn.audio_features.get(i).energy);
                    //Log.d("energy", spotifyFeaturesReturn.audio_features.get(i).energy + "");
                    songList.get(i).setDanceability(spotifyFeaturesReturn.audio_features.get(i).danceability);
                    songList.get(i).setValence(spotifyFeaturesReturn.audio_features.get(i).valence);
                    //Log.d("EnergyValue", spotifyFeaturesReturn.audio_features.get(i).energy + "");
                }

                callback.onInputReceived(user);


            }
        });

    }

    public String featureRecommendedParamBuilder(List<Song> songList) {
        String ids = "";
        int count = 0;
        for (Song song : songList) {
            if (count == 5){ break; }

            if (ids == "") {
                ids += song.spotifyID;
            } else {
                ids += "," + song.spotifyID;
            }
            count ++;
        }
        return ids;
    }

    public void makeSpotifyRecommendedCall(final List<Song> songList, String accessToken) {
        double avgAcousticness = 0;
        double avgEnergy = 0;
        double avgValence = 0;
        double avgDanceability = 0;

        for (int i = 0; i < songList.size(); i++) {
            avgAcousticness = songList.get(i).acousticness;
            avgEnergy = songList.get(i).energy;
            avgValence = songList.get(i).valence;
            avgDanceability = songList.get(i).danceability;
        }

        avgAcousticness = avgAcousticness / songList.size();
        avgEnergy = avgEnergy / songList.size();
        avgValence = avgValence / songList.size();
        avgDanceability = avgDanceability / songList.size();

        String ids = featureRecommendedParamBuilder(songList);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/recommendations?limit=100&seed_tracks=" + ids
                        + "&min_energy=" + avgEnergy
                        + "&min_danceability=" + avgDanceability
                        + "&min_acousticness=" + avgAcousticness
                        + "&min_valence=" + avgValence)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                SpotifyRecommendedReturn recommendedSongs = gson.fromJson(response.body().string(), SpotifyRecommendedReturn.class);

                tracks = new ArrayList<>();
                musixmatchPromises = new Stack<>();
                Song a;
                for (int i = 0; i < recommendedSongs.tracks.size(); i++) {
                    a = new Song();
                    a.artistName = recommendedSongs.tracks.get(i).artists.get(0).name;
                    a.spotifyID = recommendedSongs.tracks.get(i).id;
                    a.trackName = recommendedSongs.tracks.get(i).name;
                    a.uri = recommendedSongs.tracks.get(i).uri;
                    tracks.add(a);

                    musixmatchPromises.push(true);
                }

                user.setRecommended(tracks);
                Log.d("RECOMMENDED", "done");

            }
        });

    }


    public void getLyricsForSongs(List<Song> songList) {
        for (int i = 0; i < songList.size(); i++) {
            makeMusixmatchCall(songList.get(i));
        }
    }

    public String MusixParamBuilder(String trackName, String artistName) {
        return "matcher.lyrics.get?q_track=" + trackName + "&q_artist=" + artistName + "&apikey=9a25a7e009350138582ed036e8ba5646";
    }

    public void makeMusixmatchCall(final Song song) {

        OkHttpClient client = new OkHttpClient();

        String url = "http://api.musixmatch.com/ws/1.1/" + MusixParamBuilder(song.getTrackName(), song.getArtistName());

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GsonBuilder GsonBuilder = new GsonBuilder();
                GsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson Gson = GsonBuilder.create();

                try {
                    MusixMatchReturnObject lyricsReturn = Gson.fromJson(response.body().string(), MusixMatchReturnObject.class); //Now use a for each statment to go through each topTrack.item and get name and first artist of song.
                    //Log.d("trackNewObject", lyricsReturn.message.body.lyrics.lyrics_body);

                    song.setLyrics(lyricsReturn.message.body.lyrics.lyrics_body.replace("...\n" +
                            "\n" +
                            "******* This Lyrics is NOT for Commercial use *******\n(1409617597603)", ""));
                } catch (Exception e) {

                }
                musixmatchPromises.pop();

                if (musixmatchPromises.size() == 0) {
                    user.setSongs(tracks);
                    callback.onInputReceived(user);
                }
            }
        });
    }
}
