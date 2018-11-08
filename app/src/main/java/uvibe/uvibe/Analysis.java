package uvibe.uvibe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import uvibe.uvibe.Models.User;

public class Analysis extends AppCompatActivity {
    private PieChart pieChart_overall_average;
    private PieChart pieChart_lyric_average;
    private PieChart pieChart_energy_average;
    private PieChart pieChart_bpm_average;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final User user = intent.getParcelableExtra("user");
        final String accessToken = intent.getStringExtra("access_token");
        //Log.d("USERNAME", user.name+"");


        Button button = findViewById(R.id.recButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_recommended_songs);
                Intent intent = new Intent(getBaseContext(), RecommendedSongs.class);
                intent.putExtra("user", user);
                intent.putExtra("access_token", accessToken);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.logoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_home);
                Intent intent = new Intent(getBaseContext(), Home.class);
                startActivity(intent);
            }
        });

        // Grab all charts
        pieChart_overall_average = (PieChart) findViewById(R.id.piechart_overall_average);
        pieChart_lyric_average = (PieChart) findViewById(R.id.piechart_lyric_average);
        pieChart_energy_average = (PieChart) findViewById(R.id.piechart_energy_average);
        pieChart_bpm_average = (PieChart) findViewById(R.id.piechart_bpm_average);

        // Configure charts
        Description emptyDescription = new Description();
        emptyDescription.setText("");
        pieChart_overall_average.setUsePercentValues(true);
        pieChart_overall_average.setCenterText("Overall Emotion\nAnalysis");
        pieChart_overall_average.setCenterTextSize(18f);
        pieChart_overall_average.setDescription(emptyDescription);
        pieChart_overall_average.getLegend().setEnabled(false);
        pieChart_overall_average.setBackgroundColor(Color.TRANSPARENT);
        pieChart_overall_average.setEntryLabelColor(Color.BLACK);

        pieChart_lyric_average.setUsePercentValues(true);
        pieChart_lyric_average.setCenterText("Lyrics Emotion\nAnalysis");
        pieChart_lyric_average.setCenterTextSize(18f);
        pieChart_lyric_average.setDescription(emptyDescription);
        pieChart_lyric_average.getLegend().setEnabled(false);
        pieChart_lyric_average.setBackgroundColor(Color.TRANSPARENT);
        pieChart_lyric_average.setEntryLabelColor(Color.BLACK);

        pieChart_energy_average.setUsePercentValues(true);
        pieChart_energy_average.setCenterText("Energy Emotion\nAnalysis");
        pieChart_energy_average.setCenterTextSize(18f);
        pieChart_energy_average.setDescription(emptyDescription);
        pieChart_energy_average.getLegend().setEnabled(false);
        pieChart_energy_average.setBackgroundColor(Color.TRANSPARENT);
        pieChart_energy_average.setEntryLabelColor(Color.BLACK);

        pieChart_bpm_average.setUsePercentValues(true);
        pieChart_bpm_average.setCenterText("BPM Emotion\nAnalysis");
        pieChart_bpm_average.setCenterTextSize(18f);
        pieChart_bpm_average.setDescription(emptyDescription);
        pieChart_bpm_average.getLegend().setEnabled(false);
        pieChart_bpm_average.setBackgroundColor(Color.TRANSPARENT);
        pieChart_bpm_average.setEntryLabelColor(Color.BLACK);

        // Set average chart data
        ArrayList<PieEntry> entriesOverall = new ArrayList<PieEntry>();
        ArrayList<PieEntry> entriesLyric = new ArrayList<PieEntry>();
        ArrayList<PieEntry> entriesEnergy = new ArrayList<PieEntry>();
        ArrayList<PieEntry> entriesBPM = new ArrayList<PieEntry>();
        String[] pieLabels = {"Joy", "Anger", "Sadness", "Fear"};
        Double[] avgOverallValues = {user.emotionAnalysis.getJoy(), user.emotionAnalysis.getAnger(), user.emotionAnalysis.getSadness(), user.emotionAnalysis.getFear()};
        Double[] avgLyricsValues = {user.avgLyricsAnalysis.getJoy(), user.avgLyricsAnalysis.getAnger(), user.avgLyricsAnalysis.getSadness(), user.avgLyricsAnalysis.getFear()};
        Double[] avgEnergyValues = {user.avgEnergyAnalysis.getJoy(), user.avgEnergyAnalysis.getAnger(), user.avgEnergyAnalysis.getSadness(), user.avgEnergyAnalysis.getFear()};
        Double[] avgBpmValues = {user.avgBPMAnalysis.getJoy(), user.avgBPMAnalysis.getAnger(), user.avgBPMAnalysis.getSadness(), user.avgBPMAnalysis.getFear()};


        for (int i = 0; i < avgOverallValues.length; i++) {
            entriesOverall.add(new PieEntry(Float.parseFloat((avgOverallValues[i]) + ""), pieLabels[i]));
            Log.d("over", entriesOverall.get(i) + "");
        }

        for (int i = 0; i < avgLyricsValues.length; i++) {
            entriesLyric.add(new PieEntry(Float.parseFloat((avgLyricsValues[i]) + ""), pieLabels[i]));
            Log.d("lyric", entriesLyric.get(i) + "");
        }



        for (int i = 0; i < avgEnergyValues.length; i++) {
            entriesEnergy.add(new PieEntry(Float.parseFloat((avgEnergyValues[i]) + ""), pieLabels[i]));
            Log.d("energy", entriesEnergy.get(i) + "");
        }



        for (int i = 0; i < avgBpmValues.length; i++) {
            entriesBPM.add(new PieEntry(Float.parseFloat((avgBpmValues[i]) + ""), pieLabels[i]));
            Log.d("bpm", entriesBPM.get(i) + "");
        }



        PieDataSet dataSetOverall = new PieDataSet(entriesOverall, "Overall Emotion");
        PieDataSet dataSetLyric = new PieDataSet(entriesLyric, "Lyrics Emotion");
        PieDataSet dataSetEnergy = new PieDataSet(entriesEnergy, "Energy Emotion");
        PieDataSet dataSetBPM = new PieDataSet(entriesBPM, "BPM Emotion");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSetOverall.setColors(colors);
        dataSetLyric.setColors(colors);
        dataSetEnergy.setColors(colors);
        dataSetBPM.setColors(colors);

        PieData dataOverall = new PieData(dataSetOverall);
        dataOverall.setValueTextSize(15f);

        PieData dataLyric = new PieData(dataSetLyric);
        dataLyric.setValueTextSize(15f);

        PieData dataEnergy = new PieData(dataSetEnergy);
        dataEnergy.setValueTextSize(15f);

        PieData dataBPM = new PieData(dataSetBPM);
        dataBPM.setValueTextSize(15f);
        
        pieChart_overall_average.setData(dataOverall);
        pieChart_lyric_average.setData(dataLyric);
        pieChart_energy_average.setData(dataEnergy);
        pieChart_bpm_average.setData(dataBPM);
    }

}