package com.example.cookinti;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesFolderActivity extends AppCompatActivity {

    AppDatabase db;
    long folderId;
    TextView folderTitle;
    Button addRecipeButton;
    ImageButton backButton;
    RecyclerView folderRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites_folder);

        db = AppActivity.getDatabase();

        folderId = getIntent().getLongExtra("folderId", -1);

        folderTitle = findViewById(R.id.folderTitle);
        addRecipeButton = findViewById(R.id.addRecipeButton);
        backButton = (ImageButton) findViewById(R.id.backButton);

        folderRecipeList = findViewById(R.id.folderRecipeList);
        folderRecipeList.setLayoutManager(new LinearLayoutManager(this));

        FavoritesFolder folder = db.favoritesFolderDAO().getFolderById(folderId);
        folderTitle.setText(folder.name());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAddRecipeDialog();
            }
        });

        LoadFolderRecipes();
    }
    private void LoadFolderRecipes()
    {
        List<Recipe> recipes = db.folderRecipeDAO().getRecipesInFolder(folderId);

        RecipeFeedView recipeFeed = new RecipeFeedView(recipes, db);
        folderRecipeList.setAdapter(recipeFeed);
    }

    private void ShowAddRecipeDialog()
    {
        List<Recipe> savedRecipes = db.recipeDao()
                .getFavouriteRecipes(AppActivity.currentSession.getId());

        String[] recipeNames = new String[savedRecipes.size()];

        for (int i = 0; i < savedRecipes.size(); i++)
        {
            recipeNames[i] = savedRecipes.get(i).getName();
        }

        new AlertDialog.Builder(this)
                .setTitle("Add recipe to folder")
                .setItems(recipeNames, (dialog, which) -> {
                    Recipe selectedRecipe = savedRecipes.get(which);

                    List<FolderRecipe> alreadyAdded = db.folderRecipeDAO()
                            .isRecipeInFolder(folderId, selectedRecipe.getId());

                    if (alreadyAdded.isEmpty())
                    {
                        db.folderRecipeDAO().insert(
                                new FolderRecipe(folderId, selectedRecipe.getId())
                        );

                        LoadFolderRecipes();
                    }

                }).show();
    }

}