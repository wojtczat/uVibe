package uvibe.uvibe.Experts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Stack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uvibe.uvibe.InputListener;
import uvibe.uvibe.Models.Document;
import uvibe.uvibe.Models.EmotionAnalysis;
import uvibe.uvibe.Models.EmotionRequestObject;
import uvibe.uvibe.Models.IBMFeaturesObject;
import uvibe.uvibe.Models.IBMGenericReturnObject;
import uvibe.uvibe.Models.IBMRequestObject;
import uvibe.uvibe.Models.Song;
import uvibe.uvibe.Models.User;

/**
 * Created by Mevin on 3/13/2018.
 */

public class LyricSentimentAnalysis {
    private final User user;
    public final InputListener<User> callback;

    private Stack<Boolean> lyricsSentimentPromises;

    public LyricSentimentAnalysis(User user, InputListener<User> callback) {
        this.user = user;
        this.callback = callback;

        lyricsSentimentPromises = new Stack<>();
    }

    private void updateSentimentForSong(Song song, IBMGenericReturnObject returnObject) {
        if (returnObject.emotion != null && returnObject.sentiment != null) {
            song.lyricEmotions = new EmotionAnalysis(returnObject);
        } else {
            song.lyricEmotions = new EmotionAnalysis();

        }
    }

    public void makeIBMCall() {
        OkHttpClient client = new OkHttpClient();

        EmotionRequestObject ero = new EmotionRequestObject();
        IBMFeaturesObject featuresObject = new IBMFeaturesObject(ero, ero);

        for (final Song song : this.user.songList) {
            if (song.lyrics != null) {
                lyricsSentimentPromises.push(true);

                IBMRequestObject requestObject = new IBMRequestObject(song.lyrics, featuresObject);

                GsonBuilder GsonBuilder = new GsonBuilder();
                GsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson Gson = GsonBuilder.create();
                String jsonStr = Gson.toJson(requestObject);

                RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonStr);

                Request request = new Request.Builder()
                        .url("https://gateway.watsonplatform.net/natural-language-understanding/api/v1/analyze?version=2017-02-27")
                        .addHeader("Authorization", Credentials.basic("d40d9248-0bf7-49dc-87cb-edd3818b7cc9", "LZi2lpZqnJ1L"))
                        .addHeader("Content-Type", "application/json")
                        .post(body)
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

                        IBMGenericReturnObject analysis = gson.fromJson(response.body().string(), IBMGenericReturnObject.class); //Now use a for each statment to go through each topTrack.item and get name and first artist of song.

                        updateSentimentForSong(song, analysis);

                        lyricsSentimentPromises.pop();

                        if (lyricsSentimentPromises.size() == 0) {
                            callback.onInputReceived(user);
                        }
                    }
                });
            }
        }
    }
}
