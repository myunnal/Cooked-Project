package com.example.cookinti;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    }
}