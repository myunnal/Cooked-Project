package com.example.cookinti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FeedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedFragment() {
        // Required empty public constructor
    }

    AppDatabase db;
    RecyclerView recyclerView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        db = AppActivity.getDatabase();
        SetUpDatabase();


        /*String[] dataset = {"holy", "shit", "fellas", "holy", "shit","holy",
                "shit","holy", "shit","holy", "shit","holy", "shit","holy",
                "shit","holy", "shit","holy", "shit","holy", "shit","holy",
                "shit","holy", "shit","holy", "shit"};*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        RecipeFeedView recipeFeed = new RecipeFeedView(db.recipeDao().getAllRecipes(), db);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recipeFeed);

        // Inflate the layout for this fragment
        return view;
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