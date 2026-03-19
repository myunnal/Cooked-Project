package com.example.cookinti;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookinti.databinding.ActivityRecipeCreationBinding;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class RecipeCreationActivity extends AppCompatActivity {

    private ActivityRecipeCreationBinding binding;
    ImageButton backButton;
    Button uploadRecipeButton;
    AppDatabase db;
    EditText recipeNameEditText;
    private LinearLayout ingredientContainer;
    private LinearLayout recipeStepContainer;

    private TextView addIngredient;
    private TextView addRecipeStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AppActivity.getDatabase();
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recipeNameEditText = (EditText) findViewById(R.id.RecipeNameEditText);
        uploadRecipeButton = (Button) findViewById(R.id.uploadRecipeButton);
        uploadRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = new Recipe();
                String recipeName = recipeNameEditText.getText().toString().trim();

                List<String> list = IngredientList();
                JSONArray ingredientsArray = new JSONArray(list);
                String ingredients = ingredientsArray.toString();

                List<String> list2 = RecipeStepList();
                JSONArray stepsArray = new JSONArray(list2);
                String steps = stepsArray.toString();

                long userId = db.userDao().getAllUsers().get(0).getId();

                if(TextUtils.isEmpty(steps) || list.isEmpty() || list2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "There are empty fields", Toast.LENGTH_SHORT).show();
                } else {
                    recipe.setName(recipeName);
                    recipe.setIngredients(ingredients);
                    recipe.setSteps(steps);
                    recipe.setDescription("Labai Skanu");
                    recipe.setImageLink("cia linkas");
                    recipe.setFk_userid(userId);

                    db.recipeDao().insert(recipe);
                    Toast.makeText(getApplicationContext(), "Recipe saved!", Toast.LENGTH_SHORT).show();
                    finish();
                }
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

    private List<String>IngredientList() {
        List<String> ingredientList = new ArrayList<>();
        for (int i = 0; i < ingredientContainer.getChildCount(); i++) {
            View row = ingredientContainer.getChildAt(i);

            EditText input = row.findViewById(R.id.ingredientName);
            String value = input.getText().toString().trim();

            if (!value.isEmpty()) {
                ingredientList.add(value);
            }
            else{
                Toast.makeText(getApplicationContext(), "Empty field(s)", Toast.LENGTH_SHORT).show();
            }
        }
        return ingredientList;
    }

    private List<String>RecipeStepList() {
        List<String> recipeStepList = new ArrayList<>();
        for (int i = 0; i < recipeStepContainer.getChildCount(); i++) {
            View row = recipeStepContainer.getChildAt(i);

            EditText input = row.findViewById(R.id.recipeStep);
            String value = input.getText().toString().trim();

            if (!value.isEmpty()) {
                recipeStepList.add(value);
            }
            else{
                Toast.makeText(getApplicationContext(), "Empty field(s)", Toast.LENGTH_SHORT).show();
            }
        }
        return recipeStepList;
    }
}