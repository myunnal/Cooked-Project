package com.example.cookinti;

import static android.content.ContentValues.TAG;

import android.content.Intent;
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
        recipeHolder.getImageView().setImageResource(R.drawable.basically_burger_1);

        String userName = db.userDao().getUser(rec.getFk_userid()).getUsername();
        recipeHolder.getAuthorText().setText(userName);

        recipeHolder.SetUpRecipeFeedView(rec, db);


        //String[] ingredients = {"Salota", "Morka", "Bananas", "Cepelinai"};
        LinearLayout ingredientLayout = findViewById(R.id.ingredientLayout);
        LinearLayout stepLayout = findViewById(R.id.stepsLayout);

        Log.d("JSON", rec.getIngredients());

        PopulateLayout(rec.getIngredients(), ingredientLayout);
        PopulateLayout(rec.getSteps(), stepLayout);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void PopulateLayout(String jsonString, LinearLayout layout)
    {
        JSONArray array;
        try {
            array = new JSONArray(jsonString);
            array = new JSONArray(array.toString()); // galima automatizuoti

            Log.d("JSON", array.toString());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < array.length(); i++) {
            Object row = null;
            try {
                row = array.get(i);

                TextView newText = new TextView(this);
                newText.setText(row.toString());
                layout.addView(newText);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}