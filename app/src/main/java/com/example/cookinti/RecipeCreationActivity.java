package com.example.cookinti;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cookinti.databinding.ActivityRecipeCreationBinding;

public class RecipeCreationActivity extends AppCompatActivity {

    private ActivityRecipeCreationBinding binding;
    ImageButton backButton;
    Button uploadRecipeButton;
    private LinearLayout ingredientContainer;
    private LinearLayout recipeStepContainer;

    private TextView addIngredient;
    private TextView addRecipeStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uploadRecipeButton = (Button) findViewById(R.id.uploadRecipeButton);
        uploadRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //this is just a placeholder function, later this will upload the created recipe
                // to the database and add it to the profile
            }
        });



        ingredientContainer = findViewById(R.id.ingredientListContainer);
        addIngredient = findViewById(R.id.addIngredient);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientRow();
            }
        });

        recipeStepContainer = findViewById(R.id.recipeStepListContainer);
        addRecipeStep = findViewById(R.id.addRecipeStep);

        addRecipeStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipeStepRow();
            }
        });
    }

    private void addIngredientRow() {
        // This create the row view from the ingredient list layout file
        View ingredientRow = getLayoutInflater().inflate(R.layout.ingredient_list, null);
        ImageView butttonRemove = ingredientRow.findViewById(R.id.butttonRemoveIngredient);

        //removes the row when the button is pressed
        butttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientContainer.removeView(ingredientRow);
            }
        });

        ingredientContainer.addView(ingredientRow);

    }

    private void addRecipeStepRow() {

        View recipeStepRow = getLayoutInflater().inflate(R.layout.recipe_step_list, null);
        ImageView butttonRemove = recipeStepRow.findViewById(R.id.butttonRemoveRecipeStep);

        butttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeStepContainer.removeView(recipeStepRow);
            }
        });

        recipeStepContainer.addView(recipeStepRow);

    }
}