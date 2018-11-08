package uvibe.uvibe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uvibe.uvibe.Models.Emotion;
import uvibe.uvibe.Models.EmotionRequestObject;
import uvibe.uvibe.Models.PlaylistCreateReturn;
import uvibe.uvibe.Models.PlaylistObject;
import uvibe.uvibe.Models.Song;
import uvibe.uvibe.Models.SpotifyRecommendedReturn;
import uvibe.uvibe.Models.User;


public class RecommendedSongs extends AppCompatActivity {

    static boolean playlistGen = false;

    private String playlistID;
    private static String playlistURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recommended_songs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final User user = intent.getParcelableExtra("user");
        final String accessToken = intent.getStringExtra("access_token");

        final Button button = findViewById(R.id.anaButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_analysis);

                Intent intent = new Intent(getBaseContext(), Analysis.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (playlistGen == true){
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(playlistURL));
            startActivity(intent);
        }
        else{
            builder.setTitle("Confirm");
            builder.setMessage("Would you like to save the playlist?");

            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    dialog.dismiss();
                    setContentView(R.layout.activity_analysis);

                    Intent intent = new Intent(getBaseContext(), Analysis.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Create and add playlist

                    playlistGen = true;

                    dialog.dismiss();

                    Intent intent = getIntent();
                    final User user = intent.getParcelableExtra("user");
                    final String accessToken = intent.getStringExtra("access_token");

                    final String playlistName = "uVibe";
                    PlaylistObject pObj = new PlaylistObject(playlistName);

                    GsonBuilder GsonBuilder = new GsonBuilder();
                    GsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson Gson = GsonBuilder.create();
                    String jsonStr = Gson.toJson(pObj);

                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonStr);

                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url("https://api.spotify.com/v1/users/" + user.uID + "/playlists")
                            .addHeader("Authorization", "Bearer " + accessToken)
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
                            PlaylistCreateReturn playlistCreateReturn = gson.fromJson(response.body().string(), PlaylistCreateReturn.class);
                            playlistID = playlistCreateReturn.id;
                            playlistURL = playlistCreateReturn.external_urls.spotify;

//                        String uris = paramBuilder(user.recommendedList);



                            Log.d("PLAYLIST: " , response.code() + "");
                            addSongsToPlaylist(accessToken,playlistID, user, playlistURL);
                        }
                    });
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void addSongsToPlaylist (String accessToken, String playlistID, User user, final String playlistURL) {
        String uris = paramBuilder(user.recommendedList);

        GsonBuilder GsonBuilder = new GsonBuilder();
        GsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson Gson = GsonBuilder.create();
        EmotionRequestObject pObj = new EmotionRequestObject();
        String jsonStr = Gson.toJson(pObj);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonStr);

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/users/" + user.uID + "/playlists/" + playlistID + "/tracks?uris=" + uris)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                displayPlaylist(playlistURL);

            }
        });

    }

    public void displayPlaylist(String playlistURL){
        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(playlistURL));
        startActivity(intent);

    }
    public String paramBuilder(List<Song> songList) {
        String ids = "";
        for (Song song : songList) {
            if (ids == "") {
                ids += song.uri;
            } else {
                ids += "," + song.uri;
            }
        }
        return ids;
    }

}
