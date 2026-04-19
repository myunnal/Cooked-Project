package com.example.cookinti;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppActivity.getDatabase();

        View view = findViewById(R.id.recipeField);
        RecipeFeedView.ViewHolder recipeHolder = new RecipeFeedView.ViewHolder(view);

        Recipe rec = db.recipeDao().getRecipe(getIntent().getExtras().getLong("RecipeId"));
        recipeHolder.getTextView().setText(rec.getName());
        recipeHolder.getDescriptionText().setText(rec.getDescription());

        Uri uri;
        if (rec.getImageLink() != null) {
            uri = Uri.parse(rec.getImageLink());
            if (uri != null)
                recipeHolder.getImageView().setImageURI(uri);
            else
                recipeHolder.getImageView().setImageResource(R.drawable.basically_burger_1);
        }

        String userName = db.userDao().getUser(rec.getFk_userid()).getUsername();
        recipeHolder.getAuthorText().setText(userName);

        recipeHolder.SetUpRecipeFeedView(rec, db);


        //String[] ingredients = {"Salota", "Morka", "Bananas", "Cepelinai"};
        LinearLayout ingredientLayout = findViewById(R.id.ingredientLayout);
        //LinearLayout stepLayout = findViewById(R.id.stepsLayout);

        Log.d("JSON", rec.getIngredients());

        PopulateLayout(rec.getIngredients(), ingredientLayout);
        //PopulateLayout(rec.getSteps(), stepLayout);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button makeRecipe = findViewById(R.id.make_recipe);
        makeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RecipePagerActivity.class);
                intent.putExtra("RecipeId", getIntent().getExtras().getLong("RecipeId"));
                startActivity(intent);
            }
        });
    }

    void PopulateLayout(String jsonString, LinearLayout layout)
    {
//        JSONArray array;
//        try {
//            array = new JSONArray(jsonString);
//            array = new JSONArray(array.toString()); // galima automatizuoti
//
//            Log.d("JSON", array.toString());
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (int i = 0; i < array.length(); i++) {
//            Object row = null;
//            try {
//                row = array.get(i);
//
//                TextView newText = new TextView(this);
//                newText.setText(row.toString());
//                layout.addView(newText);
//
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }

        if (jsonString == null || jsonString.isEmpty()) return;

        try {
            // Clean the string if it was wrapped in extra quotes by a database export
            if (jsonString.startsWith("\"") && jsonString.endsWith("\"")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1).replace("\\\"", "\"");
            }

            JSONArray array = new JSONArray(jsonString);

            for (int i = 0; i < array.length(); i++) {
                // Use optString to avoid potential null pointer/json exceptions for specific indices
                String stepText = array.optString(i);

                TextView newText = new TextView(this);
                newText.setText(stepText);

                // Adding some padding for readability in long text
                newText.setPadding(0, 8, 0, 8);
                newText.setTextSize(20);
                layout.addView(newText);
            }
        } catch (JSONException e) {
            Log.e("JSON_ERROR", "Failed to parse steps at: " + jsonString);
            e.printStackTrace();
        }
    }
}