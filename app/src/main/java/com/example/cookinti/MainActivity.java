package com.example.cookinti;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppActivity.getDatabase();
        SetUpDatabase();


        /*String[] dataset = {"holy", "shit", "fellas", "holy", "shit","holy",
                "shit","holy", "shit","holy", "shit","holy", "shit","holy",
                "shit","holy", "shit","holy", "shit","holy", "shit","holy",
                "shit","holy", "shit","holy", "shit"};*/
        RecipeFeedView recipeFeed = new RecipeFeedView(db.recipeDao().getAllRecipes(), db);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeFeed);
    }

    private void SetUpDatabase()
    {
        db.userDao().deleteAll();

        User user = new User();
        user.setUsername("špygelis");
        user.setBio("who up jav'ing their kotlin");
        db.userDao().insert(user);

        long uid = db.userDao().getAllUsers().get(0).getId();

        Recipe recipe = new Recipe();
        recipe.setName("Kotletas");
        recipe.setDescription("Lorem ipsum. Pasikepi ant keptuves.");
        recipe.setFk_userid(uid);
        recipe.setIngredients("0");
        recipe.setImageLink("0");
        recipe.setSteps("0");
        db.recipeDao().insert(recipe);

        recipe.setName("Pyragas");
        recipe.setDescription("Sveiti i orkaite.");
        recipe.setFk_userid(uid);
        recipe.setIngredients("0");
        recipe.setImageLink("0");
        recipe.setSteps("0");
        db.recipeDao().insert(recipe);

        recipe.setName("Pjaustyti pomidorai");
        recipe.setDescription("Pirma reikia paimti i ranka peili");
        recipe.setFk_userid(uid);
        recipe.setIngredients("0");
        recipe.setImageLink("0");
        recipe.setSteps("0");
        db.recipeDao().insert(recipe);
    }
}