package com.example.cookinti;

import android.app.Application;

import androidx.room.Room;

public class AppActivity extends Application {
    static AppDatabase db;

    @Override
    public void onCreate(){
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my_app_db")
                .allowMainThreadQueries().build();
        SetUpDatabase();
    }

    public static AppDatabase getDatabase() {
        return db;
    }

    private void SetUpDatabase()
    {
        db.userDao().deleteAll();

        User user = new User();
        user.setUsername("špygelis");
        user.setPronouns("He/Him");
        user.setBio("who up jav'ing their kotlin");
        db.userDao().insert(user);

        long uid = db.userDao().getAllUsers().get(0).getId();

        Recipe recipe = new Recipe();
        recipe.setName("Kotletas");
        recipe.setDescription("Lorem ipsum. Pasikepi ant keptuves.");
        recipe.setFk_userid(uid);
        recipe.setIngredients("[\"Salota\",\"Morka\",\"Bananas\",\"Cepelinai\"]");
        recipe.setImageLink("0");
        recipe.setSteps("[\"Pjauti\",\"Kepti\"]");
        db.recipeDao().insert(recipe);

        recipe.setName("Pyragas");
        recipe.setDescription("Sveiti i orkaite.");
        recipe.setFk_userid(uid);
        recipe.setIngredients("[\"Salota\",\"Morka\",\"Bananas\",\"Cepelinai\"]");
        recipe.setImageLink("0");
        recipe.setSteps("[\"Pjauti\",\"Kepti\"]");
        db.recipeDao().insert(recipe);

        recipe.setName("Pjaustyti pomidorai");
        recipe.setDescription("Pirma reikia paimti i ranka peili");
        recipe.setFk_userid(uid);
        recipe.setIngredients("[\"Salota\",\"Morka\",\"Bananas\",\"Cepelinai\"]");
        recipe.setImageLink("0");
        recipe.setSteps("[\"Pjauti\",\"Kepti\"]");
        db.recipeDao().insert(recipe);
    }
}
