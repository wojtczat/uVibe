package uvibe.uvibe.Experts;

import android.util.Log;

import uvibe.uvibe.InputListener;
import uvibe.uvibe.Models.EmotionAnalysis;
import uvibe.uvibe.Models.Song;
import uvibe.uvibe.Models.User;

/**
 * Created by Iliad on 2018-04-07.
 */

public class EnergyAnalysis {
    private final User user;
    public final InputListener<User> callback;

    public EnergyAnalysis(User user, InputListener<User> callback) {
        this.user = user;
        this.callback = callback;
    }

    public void analyzeEnergy() {
        for (Song song : user.songList) {
            EmotionAnalysis songEnergyEmotions = new EmotionAnalysis();
            songEnergyEmotions.setJoy(0.5);

            song.energyEmotions = songEnergyEmotions;

            song.energyEmotions.setJoy(song.getValence());
            song.energyEmotions.setSadness(1.0 - song.getValence());
            if (song.getAcousticness() >= 0.5) { // if the song is acoustic
                if (song.getValence() >= 0.5 && song.getEnergy() >= 0.5) {
                    song.energyEmotions.setAnger((1.0 - song.getValence()) * (1.0 - song.getEnergy()));
                    song.energyEmotions.setFear(1.0 - song.getEnergy());
                } else if (song.getValence() >= 0.5 && song.getEnergy() <= 0.5) {
                    song.energyEmotions.setAnger((1.0 - song.getValence()) * (song.getEnergy()));
                    song.energyEmotions.setFear(1.0 - song.getEnergy());
                } else if (song.getValence() <= 0.5 && song.getEnergy() >= 0.5) {
                    song.energyEmotions.setAnger(song.getEnergy());
                    song.energyEmotions.setFear(1.0 - song.getEnergy());
                } else {
                    song.energyEmotions.setAnger(song.getEnergy() / 2.0);
                    song.energyEmotions.setFear(1.0 - song.getEnergy());
                }
            } else { // if the song is not acoustic
                if (song.getValence() >= 0.5 && song.getEnergy() >= 0.5) {
                    song.energyEmotions.setAnger((1.0 - song.getValence()) * (1.0 - song.getEnergy()));
                    song.energyEmotions.setFear(1.0 - song.getEnergy());
                } else if (song.getValence() >= 0.5 && song.getEnergy() <= 0.5) {
                    song.energyEmotions.setAnger((1.0 - song.getValence()) / 2.0);
                    song.energyEmotions.setFear(song.getEnergy() / 2.0);
                } else if (song.getValence() <= 0.5 && song.getEnergy() >= 0.5) {
                    song.energyEmotions.setAnger(song.getEnergy());
                    song.energyEmotions.setFear(song.getEnergy() / 2.0);
                } else {
                    song.energyEmotions.setAnger(1.0 - song.getEnergy());
                    song.energyEmotions.setFear((1.0 - song.getEnergy()) / 2.0);
                }
            }
        }
        callback.onInputReceived(user);
    }
}