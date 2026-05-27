package com.example.cookinti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipePagerActivity extends AppCompatActivity implements SensorEventListener {

    AppDatabase db;

    SensorManager sensorManager;
    Sensor accelerometer;
    ViewPager2 viewPager;
    StepAdapter adapter;
    long lastTiltTime = 0;

    boolean handsFreeMode;

    TextView debugTextView;

    private static final int RECORD_AUDIO_REQUEST_CODE = 101;
    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppActivity.getDatabase();
        Recipe rec = db.recipeDao().getRecipe(getIntent().getExtras().getLong("RecipeId"));
        handsFreeMode = getIntent().getExtras().getBoolean("HandsFree");

        if(handsFreeMode){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
            } else {
                initSpeechRecognizer();
            }

            speechRecognizer.startListening(recognizerIntent);
        }

        Button btnNext = findViewById(R.id.btnNext);
        Button btnPrev = findViewById(R.id.btnPrev);
        viewPager = findViewById(R.id.stepsViewPager);

        TextView recipeName = findViewById(R.id.tvRecipeName);
        recipeName.setText(rec.getName());

        String jsonSteps = rec.getSteps();
        List<String> stepsList = convertJsonToList(jsonSteps);
        adapter = new StepAdapter(stepsList);
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(new FlipPageTransformer()); // page flip anim
        viewPager.setOffscreenPageLimit(1);

        btnNext.setOnClickListener(v -> {
            pageNext();
            Anims.ScaleViewAnim(v, 1.1f).start();
        });

        btnPrev.setOnClickListener(v -> {
            pagePrev();
            Anims.ScaleViewAnim(v, 1.1f).start();
        });

        ImageButton buttonBack= findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void pageNext(){
        int current = viewPager.getCurrentItem();
        if (current < adapter.getItemCount() - 1) {
            viewPager.setCurrentItem(current + 1);
        }
    }

    private void pagePrev(){
        int current = viewPager.getCurrentItem();
        if (current > 0) {
            viewPager.setCurrentItem(current - 1);
        }
    }

    private List<String> convertJsonToList(String json) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accelerometer != null)
        {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
        {
            return;
        }

        float x = sensorEvent.values[0];

        if (System.currentTimeMillis() - lastTiltTime < 900)
        {
            return;
        }

        if (x < (-5))
        {
            pageNext();

            lastTiltTime = System.currentTimeMillis();
        }

        if (x > 5)
        {
            pagePrev();

            lastTiltTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nereikalingas metodas
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initSpeechRecognizer();
        } else {
            Toast.makeText(this, "Permission Denied. Voice commands won't work.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSpeechRecognizer() {
        Log.d("SpeechDebug", "initSpeechRecognizer() STARTED");

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition is not available on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        speechRecognizer.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {
                speechRecognizer.startListening(recognizerIntent);
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String spokenText = matches.get(0).toLowerCase(Locale.getDefault());

                    if (spokenText.contains("next") || spokenText.contains("forward")) {
                        pageNext();
                    }
                    else if (spokenText.contains("back") || spokenText.contains("previous")){
                        pagePrev();
                    }
                }

                speechRecognizer.startListening(recognizerIntent);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }


}