package com.example.cookinti;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

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
    Button deleteFolderButton;
    boolean deleteFolderMode = false;
    LinearLayout folderList;
    public ImageView poof;
    public AnimationDrawable poofAnimation;

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
                createFolderDialog();
            }
        });

        deleteFolderButton = view.findViewById(R.id.deleteFolderButton);

        deleteFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFolderMode = !deleteFolderMode;

                if (deleteFolderMode) {
                    deleteFolderButton.setText("Cancel Delete");
                } else {
                    deleteFolderButton.setText("Delete Folder");
                }

                loadFolders();
            }
        });


        loadFolders();
        // Inflate the layout for this fragment
        return view;
    }

    private void loadFolders()
    {
        folderList.removeAllViews();

        List<FavoritesFolder> folders = db.favoritesFolderDAO()
                .getFoldersForUser(AppActivity.currentSession.getId());

        for (FavoritesFolder folder : folders)
        {
            MaterialButton folderButton = new MaterialButton(requireContext());

            if (deleteFolderMode) {
                folderButton.setText("X  " + folder.name());

                ObjectAnimator shake = ObjectAnimator.ofFloat(folderButton, "rotation", -1f, 1f);
                shake.setDuration(90);
                shake.setRepeatCount(ValueAnimator.INFINITE);
                shake.setRepeatMode(ValueAnimator.REVERSE);
                shake.setInterpolator(new LinearInterpolator());
                shake.start();

                ObjectAnimator move = ObjectAnimator.ofFloat(folderButton, "translationX", -2f, 2f);

                move.setDuration(90);
                move.setRepeatCount(ValueAnimator.INFINITE);
                move.setRepeatMode(ValueAnimator.REVERSE);
                move.setInterpolator(new LinearInterpolator());
                move.start();


            } else {
                folderButton.setText(folder.name());
                folderButton.setRotation(0f);
                folderButton.setTranslationX(0f);
            }

            folderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!deleteFolderMode) {
                        Intent intent = new Intent(getContext(), FavoritesFolderActivity.class);
                        intent.putExtra("folderId", folder.getId());
                        startActivity(intent);
                        return;
                    }

                    new AlertDialog.Builder(getContext())
                            .setTitle("Delete folder")
                            .setMessage("Delete \"" + folder.name() + "\"?")
                            .setPositiveButton("Delete", (dialog, which) -> {

                                folderButton.clearAnimation();

                                ObjectAnimator scaleXUp = ObjectAnimator.ofFloat(folderButton, "scaleX", 1f, 2.5f);
                                ObjectAnimator scaleYUp = ObjectAnimator.ofFloat(folderButton, "scaleY", 1f, 2.5f);
                                scaleXUp.setInterpolator(new DecelerateInterpolator());
                                scaleYUp.setInterpolator(new DecelerateInterpolator());
                                ObjectAnimator scaleXDown = ObjectAnimator.ofFloat(folderButton, "scaleX", 2.5f, -2f);
                                ObjectAnimator scaleYDown = ObjectAnimator.ofFloat(folderButton, "scaleY", 2.5f, -2f);
                                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(folderButton, "alpha", 1f, 0f);

                                AnimatorSet expand = new AnimatorSet();
                                expand.playTogether(scaleXUp, scaleYUp);
                                expand.setDuration(120);

                                AnimatorSet shrink = new AnimatorSet();
                                shrink.playTogether(scaleXDown, scaleYDown, fadeOut);
                                shrink.setDuration(200);

                                AnimatorSet animatorSet = new AnimatorSet();
                                animatorSet.playSequentially(expand, shrink);
                                animatorSet.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        db.favoritesFolderDAO().deleteFolder(folder.getId());
                                        deleteFolderMode = false;
                                        deleteFolderButton.setText("Delete Folder");

                                        poof = new ImageView(requireContext());
                                        poof.setLayoutParams(new ViewGroup.LayoutParams(folderButton.getWidth(), folderButton.getHeight()));
                                        poof.setX(folderButton.getX());
                                        poof.setY(folderButton.getY()-120f);
                                        poof.setImageResource(R.drawable.poof_animation);
                                        folderList.addView(poof);

                                        poofAnimation = (AnimationDrawable) poof.getDrawable();
                                        poof.post(() -> poofAnimation.start());

                                        int totalDuration = 0;
                                        for (int i = 0; i < poofAnimation.getNumberOfFrames(); i++) {
                                            totalDuration += poofAnimation.getDuration(i);
                                        }

                                        poof.postDelayed(() -> {
                                            folderList.removeView(poof);
                                            loadFolders();
                                        }, totalDuration);

                                    }
                                });
                                animatorSet.start();
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            });

            folderList.addView(folderButton);
        }
    }

    private void createFolderDialog()
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

                        loadFolders();
                    }else
                    {
                        Toast.makeText(getContext(), "Folder name can't be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}