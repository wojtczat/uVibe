package uvibe.uvibe.Experts;

import uvibe.uvibe.InputListener;
import uvibe.uvibe.Models.EmotionAnalysis;
import uvibe.uvibe.Models.Song;
import uvibe.uvibe.Models.User;

/**
 * Created by Eliad on 4/7/2018.
 */

public class DanceabilityAnalysis {
    private final User user;
    public final InputListener<User> callback;

    public DanceabilityAnalysis(User user, InputListener<User> callback) {
        this.user = user;
        this.callback = callback;


    }

    public void analyzeDanceability() {
        for (final Song song: this.user.songList) {
            EmotionAnalysis bpmEmotions = new EmotionAnalysis();
            bpmEmotions.setJoy(0.5);

            song.bpmEmotions = bpmEmotions;

            song.bpmEmotions.setJoy(song.getValence());
            song.bpmEmotions.setSadness(1.0 - song.getValence());
            if (song.getAcousticness() >= 0.5) { // if the song is acoustic
                if (song.getValence() >= 0.5 && song.getDanceability() >= 0.5) {
                    song.bpmEmotions.setAnger((1.0 - song.getValence()) * (1.0 - song.getEnergy()));
                    song.bpmEmotions.setFear(1.0 - song.getEnergy());
                } else if (song.getValence() >= 0.5 && song.getDanceability() <= 0.5) {
                    song.bpmEmotions.setAnger((1.0 - song.getValence()) * (song.getDanceability()));
                    song.bpmEmotions.setFear(1.0 - song.getDanceability());
                } else if (song.getValence() <= 0.5 && song.getDanceability() >= 0.5) {
                    song.bpmEmotions.setAnger(song.getDanceability());
                    song.bpmEmotions.setFear(1.0 - song.getDanceability());
                } else {
                    song.bpmEmotions.setAnger(song.getDanceability() / 2.0);
                    song.bpmEmotions.setFear(1.0 - song.getDanceability());
                }
            } else { // if the song is not acoustic
                if (song.getValence() >= 0.5 && song.getDanceability() >= 0.5) {
                    song.bpmEmotions.setAnger((1.0 - song.getValence()) * (1.0 - song.getDanceability()));
                    song.bpmEmotions.setFear(1.0 - song.getDanceability());
                } else if (song.getValence() >= 0.5 && song.getDanceability() <= 0.5) {
                    song.bpmEmotions.setAnger((1.0 - song.getValence()) / 2.0);
                    song.bpmEmotions.setFear(song.getDanceability() / 2.0);
                } else if (song.getValence() <= 0.5 && song.getDanceability() >= 0.5) {
                    song.bpmEmotions.setAnger(song.getDanceability());
                    song.bpmEmotions.setFear(song.getDanceability() / 2.0);
                } else {
                    song.bpmEmotions.setAnger(1.0 - song.getDanceability());
                    song.bpmEmotions.setFear((1.0 - song.getDanceability()) / 2.0);
                }
            }
        }

        callback.onInputReceived(user);
    }
}