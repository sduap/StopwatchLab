package com.example.stopwatchlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView stopwatch;
    private String lapInfo = "";
    private String currentTime = "";
    private int seconds = 0;
    private int lapsCount = 1;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("second");
            running = savedInstanceState.getBoolean("running");
            lapInfo = savedInstanceState.getString("lapInfo");
            lapsCount = savedInstanceState.getInt("lapsCount");
        }
        runTimer(); // Invocar el cronometro
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        saveInstanceState.putInt("seconds", seconds);
        saveInstanceState.putString("lapInfo", lapInfo);
        saveInstanceState.putBoolean("running", running);
        saveInstanceState.putInt("lapsCount", lapsCount);
        super.onSaveInstanceState(saveInstanceState);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        lapInfo = "";
    }

    public void onClickLap(View view) {
        TextView lapsInfo = findViewById(R.id.lapsInfo);

        lapInfo += "(" + lapsCount + ") " + currentTime + "\n"; // Guardar el tiempo de la vuelta

        lapsCount++; // Incrementar el numero de vueltas

        lapsInfo.setText(lapInfo); // Mostrar los registros
    }

    public void runTimer() {
        stopwatch = findViewById(R.id.time_view);
        Handler handler = new Handler();
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        int hours = seconds / 3600;
                        int minutes = (seconds % 3600) / 60;
                        int secs = seconds % 60;
                        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                        currentTime = time;
                        stopwatch.setText(time);
                        if (running)
                            seconds++;
                        handler.postDelayed(this, 1000);
                    }
                });
    }

}
