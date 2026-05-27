package com.example.cookinti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
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
    EditText recipeDescriptionEditText;

    private LinearLayout ingredientContainer;
    private LinearLayout recipeStepContainer;
    private LinearLayout tagContainer;

    private TextView addIngredient;
    private TextView addRecipeStep;
    private TextView addTag;

    ImageView imageView;
    Uri imageLink;
    int SELECT_PICTURE = 200;


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
        recipeDescriptionEditText = (EditText) findViewById(R.id.recipeDescriptionEditText);
        uploadRecipeButton = (Button) findViewById(R.id.uploadRecipeButton);
        uploadRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = new Recipe();
                String recipeName = recipeNameEditText.getText().toString().trim();
                String recipeDescription = recipeDescriptionEditText.getText().toString().trim();

                List<String> list = IngredientList();
                JSONArray ingredientsArray = new JSONArray(list);
                String ingredients = ingredientsArray.toString();

                List<String> list2 = RecipeStepList();
                JSONArray stepsArray = new JSONArray(list2);
                String steps = stepsArray.toString();

                long userId = AppActivity.currentSession.getId();

                if(TextUtils.isEmpty(steps) || list.isEmpty() || list2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "There are empty fields", Toast.LENGTH_SHORT).show();
                } else {
                    recipe.setName(recipeName);
                    recipe.setIngredients(ingredients);
                    recipe.setSteps(steps);
                    recipe.setDescription(recipeDescription);
                    recipe.setImageLink(imageLink != null ? imageLink.toString() : "");
                    recipe.setFk_userid(userId);
                    recipe.setTags(tagString());

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

        tagContainer = findViewById(R.id.tagListContainer);
        addTag = findViewById(R.id.addTag);

        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTagRow();
            }
        });



        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        getContentResolver().takePersistableUriPermission(uri, flag);

                        imageLink = uri;
                        imageView.setImageURI(uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        ActivityResultLauncher<Intent> cameraActivityLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String uriString = result.getData().getStringExtra("photo_uri");
                        if (uriString != null) {
                            Uri cameraUri = Uri.parse(uriString);

                            imageLink = cameraUri;
                            imageView.setImageURI(cameraUri);
                        }
                    } else {
                    }
                });

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(view.getContext());
                builder.setTitle("Select Image Source");
                builder.setItems(options, (dialog, item) -> {
                    if (options[item].equals("Take Photo")) {
                        // Launch your custom CameraActivity
                        Intent intent = new Intent(view.getContext(), CameraActivity.class);
                        cameraActivityLauncher.launch(intent);

                    } else if (options[item].equals("Choose from Gallery")) {
                        // Launch your existing standard Photo Picker
                        pickMedia.launch(new PickVisualMediaRequest.Builder()
                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                .build());

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                });
                builder.show();
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

    private void addTagRow() {

        View tagRow = getLayoutInflater().inflate(R.layout.tag_list, null);
        ImageView butttonRemove = tagRow.findViewById(R.id.butttonRemoveTag);

        butttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagContainer.removeView(tagRow);
            }
        });

        tagContainer.addView(tagRow);

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

    private List<String>TagList() {
        List<String> tagList = new ArrayList<>();
        for (int i = 0; i < recipeStepContainer.getChildCount(); i++) {
            View row = recipeStepContainer.getChildAt(i);

            EditText input = row.findViewById(R.id.recipeStep);
            String value = input.getText().toString().trim();

            if (!value.isEmpty()) {
                tagList.add(value);
            } // check needed for one word
        }
        return tagList;
    }

    private String tagString(){
        StringBuilder sb = new StringBuilder();
        List<String> tagList = TagList();
        for(String s : tagList){
            sb.append(s.toLowerCase());
        }
        return sb.toString();
    }

}