package com.example.cookinti;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.room.Room;

import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AppActivity extends Application {
    static AppDatabase db;
    static User currentSession;

    @Override
    public void onCreate(){
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my_app_db")
                .allowMainThreadQueries().build();
        //SetUpDatabase(); //uncommented for testing
    }

    public static AppDatabase getDatabase() {
        return db;
    }

    private void SetUpDatabase()
    {
        db.userDao().deleteAll();

        // refactored to use constructor
        db.userDao().insert(new User(
                "admin",
                "admin",
                "He/Him",
                "who up jav'ing their kotlin",
                null
        ));

        db.userDao().insert(new User(
                "345",
                "345",
                "She/Her",
                "am 3:45",
                null
        ));

        long uid = db.userDao().getAllUsers().get(0).getId();

        // refactored to use constructor
        db.recipeDao().insert(new Recipe(
                uid,
                "Tradiciniai kotletai",
                "0",
                "Klasikinis patiekalas, kuris niekada nenuvilia! Tobulai subalansuotas skonis, auksinė plutelė ir gardus aromatas sugrąžins į vaikystės pietus. Puikiai tinka su bulvių koše, daržovėmis ar mėgstamu padažu. Išbandykite šį laiko patikrintą receptą ir mėgaukitės naminiu jaukumu! Ugnė",
                "[\"400 gramų kiaulienos faršo \"," +
                        "\"400 gramų maltos kalakutienos (arba vištienos) \"," +
                        "\"2 vienetai kiaušinių \"," +
                        "\"0.5 vieneto svogūnųi\"]",
                "[\"Susmulkinkite česnako skilteles bei svogūną. Pamerkite vandenyje (galima ir piene) baltos duonos riekeles.\"," +
                        "\"Į dubenį sudėkite kiaulienos bei kalakutienos faršą, suberkite prieskonius, dėkite majonezą, įmuškite kiaušinius, suberkite česnaką, svogūną, sudėkite nuspaustas baltos duonos riekeles, viską gerai išminkykite.\","+
                        "\"Kepkite keptuvėje iš abiejų pusių, kol gražiai parus.\"]",
                ""
        ));

        db.recipeDao().insert(new Recipe(
                uid,
                "Pyragas",
                "0",
                "Sveiti i orkaite.",
                "[\"Salota\",\"Morka\",\"Bananas\",\"Cepelinai\"]",
                "[\"Pjauti\",\"Kepti\"]",
                ""
        ));

        db.recipeDao().insert(new Recipe(
                uid,
                "Pjaustyti pomidorai",
                "0",
                "Pirma reikia paimti i ranka peili",
                "[\"Salota\",\"Morka\",\"Bananas\",\"Cepelinai\"]",
                "[\"Pjauti\",\"Kepti\"]",
                "kandy"
        ));
    }

    /*public static String EncryptPass(String password)
    {
        SecretKeyFactory keyFactory = null;
        try
        {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] salt = {0, 0, 0};
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 4096, 256);
            keyFactory.generateSecret(spec);
        }
        catch (NoSuchAlgorithmException e)
        {

        }
    }*/

    public static void FavouriteRecipe(long userid, long recipeid)
    {
        Boolean notFavourite = db.favouriteDao().isFavourite(userid, recipeid).isEmpty();
        if (notFavourite)
        {
            db.favouriteDao().insert(new Favourite(userid, recipeid));
        }
        else
        {
            db.favouriteDao().removeFavourite(userid, recipeid);
        }
    }

    public static void FollowUser(long userid, long targetid)
    {
        Boolean notFollowing = db.followDao().isFollowing(userid, targetid).isEmpty();
        if (notFollowing)
        {
            db.followDao().insert(new Follow(userid, targetid));
        }
        else
        {
            db.followDao().removeFollow(userid, targetid);
        }
    }

    public static void CheckUserProfile(Context cntxt, long userid)
    {
        Intent intent = new Intent(cntxt, UserRecipes.class);
        intent.putExtra("userid", userid);
        cntxt.startActivity(intent);
    }
}
