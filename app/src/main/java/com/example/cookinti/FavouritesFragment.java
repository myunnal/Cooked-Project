package com.example.cookinti;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesFragment extends Fragment {

    AppDatabase db;
    RecyclerView recyclerView;
    Button addFolder;
    LinearLayout folderList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        List<Recipe> recipes = db.recipeDao().getFavouriteRecipes(AppActivity.currentSession.getId());

        recyclerView = view.findViewById(R.id.recycler_view2);
        RecipeFeedView recipeFeed = new RecipeFeedView(recipes, db);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recipeFeed);
        addFolder = view.findViewById(R.id.addFolderButton);
        folderList = view.findViewById(R.id.folderList);

        addFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCreateFolderDialog();
            }
        });


        LoadFolders();
        // Inflate the layout for this fragment
        return view;
    }

    private void LoadFolders()
    {
        folderList.removeAllViews();

        List<FavoritesFolder> folders = db.favoritesFolderDAO()
                .getFoldersForUser(AppActivity.currentSession.getId());

        for (FavoritesFolder folder : folders)
        {
            Button folderButton = new Button(getContext());
            folderButton.setText(folder.name());

            folderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), FavoritesFolderActivity.class);
                    intent.putExtra("folderId", folder.getId());
                    startActivity(intent);
                }
            });

            folderList.addView(folderButton);
        }
    }

    private void ShowCreateFolderDialog()
    {
        EditText input = new EditText(getContext());
        input.setHint("Folder name");

        new AlertDialog.Builder(getContext())
                .setTitle("Create folder")
                .setView(input)
                .setPositiveButton("Create", (dialog, which) -> {
                    String folderName = input.getText().toString();

                    if (!folderName.isEmpty())
                    {
                        db.favoritesFolderDAO().insert(
                                new FavoritesFolder(AppActivity.currentSession.getId(), folderName)
                        );

                        LoadFolders();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}