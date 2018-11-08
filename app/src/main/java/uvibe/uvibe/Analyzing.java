package uvibe.uvibe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import uvibe.uvibe.Controllers.SpotifyDataCollection;
import uvibe.uvibe.Experts.DanceabilityAnalysis;
import uvibe.uvibe.Experts.EnergyAnalysis;
import uvibe.uvibe.Experts.LyricSentimentAnalysis;
import uvibe.uvibe.Models.EmotionAnalysis;
import uvibe.uvibe.Models.Song;
import uvibe.uvibe.Models.User;

public class Analyzing extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String accessToken = intent.getStringExtra("access_token");

        Log.d("ACCESS TOKEN", accessToken);

        user = new User(null);

        // Retrieve Spotify data
        SpotifyDataCollection spotifyHandler = new SpotifyDataCollection(user, new InputListener<User>() {

            // Callback - Spotify Data
            @Override
            public void onInputReceived(User value) {
                user = value;

                // Get lyric sentiment results
                LyricSentimentAnalysis lyricSentiment = new LyricSentimentAnalysis(user, new InputListener<User>() {

                    // Callback - Lyrics sentiment
                    @Override
                    public void onInputReceived(User lyricsSentimentResponse) {
                        user = lyricsSentimentResponse;
                        final EnergyAnalysis energyAnalysis = new EnergyAnalysis(user, new InputListener<User>() {

                            // Callback - Energy
                            @Override
                            public void onInputReceived(User energyAnalysisResponse) {
                                user = energyAnalysisResponse;

                                // Danceability
                                DanceabilityAnalysis danceabilityAnalysis = new DanceabilityAnalysis(user, new InputListener<User>() {

                                    // Callback - Energy
                                    @Override
                                    public void onInputReceived(User danceAnalysisResponse) {
                                        user = danceAnalysisResponse;



                                        double numOfSongs = 0;
                                        for (Song song: user.songList){
                                            if (song.lyrics != null) {numOfSongs++;}
                                        }
                                        EmotionAnalysis avgLyricsAnalysis = new EmotionAnalysis(0.0, 0.0, 0.0, 0.0, 0.0);
                                        EmotionAnalysis avgEnergyAnalysis = new EmotionAnalysis(0.0, 0.0, 0.0, 0.0, 0.0);
                                        EmotionAnalysis avgBPMAnalysis = new EmotionAnalysis(0.0, 0.0, 0.0, 0.0, 0.0);
                                        EmotionAnalysis combinedAnalysis = new EmotionAnalysis(0.0, 0.0, 0.0, 0.0, 0.0);
                                        for (Song song : user.songList) {
                                            if (song.lyrics != null) {
                                                avgLyricsAnalysis.joy += song.lyricEmotions.getJoy()/numOfSongs;
                                                avgLyricsAnalysis.sadness += song.lyricEmotions.getSadness()/numOfSongs;
                                                avgLyricsAnalysis.fear += song.lyricEmotions.getFear()/numOfSongs;
                                                avgLyricsAnalysis.anger += song.lyricEmotions.getAnger()/numOfSongs;

                                                avgEnergyAnalysis.joy += song.energyEmotions.getJoy()/numOfSongs;
                                                avgEnergyAnalysis.sadness += song.energyEmotions.getSadness()/numOfSongs;
                                                avgEnergyAnalysis.fear += song.energyEmotions.getFear()/numOfSongs;
                                                avgEnergyAnalysis.anger += song.energyEmotions.getAnger()/numOfSongs;

                                                avgBPMAnalysis.joy += song.bpmEmotions.getJoy()/numOfSongs;
                                                avgBPMAnalysis.sadness += song.bpmEmotions.getSadness()/numOfSongs;
                                                avgBPMAnalysis.fear += song.bpmEmotions.getFear()/numOfSongs;
                                                avgBPMAnalysis.anger += song.bpmEmotions.getAnger()/numOfSongs;

                                                combinedAnalysis.joy += (song.energyEmotions.getJoy() + song.lyricEmotions.getJoy() + song.bpmEmotions.getJoy())/(numOfSongs*3);
                                                combinedAnalysis.sadness += (song.energyEmotions.getSadness() + song.lyricEmotions.getSadness() + song.bpmEmotions.getSadness())/(numOfSongs*3);
                                                combinedAnalysis.fear += (song.energyEmotions.getFear() + song.lyricEmotions.getFear() + song.bpmEmotions.getFear())/(numOfSongs*3);
                                                combinedAnalysis.anger += (song.energyEmotions.getAnger() + song.lyricEmotions.getAnger() + song.bpmEmotions.getAnger())/(numOfSongs*3);
                                            }
                                        }
                                        user.avgLyricsAnalysis = avgLyricsAnalysis;
                                        user.avgEnergyAnalysis = avgEnergyAnalysis;
                                        user.avgBPMAnalysis = avgBPMAnalysis;
                                        user.emotionAnalysis = combinedAnalysis;
                                        Log.d("joy", user.avgLyricsAnalysis.getJoy() + "");
                                        Log.d("sad", user.avgBPMAnalysis.getSadness() + "");
                                        Log.d("fear", user.avgEnergyAnalysis.getFear() + "");
                                        Log.d("anger", user.emotionAnalysis.getAnger() + "");

                                        Intent intent = new Intent(getBaseContext(), Analysis.class);
                                        intent.putExtra("user", user);
                                        intent.putExtra("access_token", accessToken);
                                        startActivity(intent);
                                    }
                                });

                                danceabilityAnalysis.analyzeDanceability();
//                                Intent intent = new Intent(getBaseContext(), Analysis.class);
//                                intent.putExtra("user", user);
//                                startActivity(intent);
                            }
                        });

                        energyAnalysis.analyzeEnergy();
                    }
                });
                lyricSentiment.makeIBMCall();
            }
        });
        spotifyHandler.makeSpotifyCall(accessToken);
    }

//    public void getEnergyEmotion(User user) {
//        EnergyAnalysis e = new EnergyAnalysis(user);
//        e.analyzeEnergy();
//        for (Song song : user.songList) {
//            Log.d("Emotion", song.energyEmotions.joy + "");
//        }
//    }

}
//public interface InputListener<T> {
//    void onInputReceieved(T model);
//}
