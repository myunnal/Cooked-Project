package com.example.cookinti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    ImageView profileImage;
    Uri profileLink;
    Button createProfileButton;
    TextView userName;
    TextView pronouns;
    TextView followers;
    TextView following;
    AppDatabase db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = rootView.findViewById(R.id.UserName);
        userName.setText(AppActivity.currentSession.getUsername());
        pronouns = rootView.findViewById(R.id.pronouns);
        pronouns.setText(AppActivity.currentSession.getPronouns());
        followers = rootView.findViewById(R.id.followers);
        followers.setText("Followers: " + db.followDao().getFollowers(AppActivity.currentSession.getId()).size());
        following = rootView.findViewById(R.id.following);
        followers.setText("Following: " + db.followDao().getFollowing(AppActivity.currentSession.getId()).size());



        createProfileButton = rootView.findViewById(R.id.createProfileButton);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openPage = new Intent(getContext(), RecipeCreationActivity.class);
                startActivity(openPage);
            }
        });

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        getContext().getContentResolver().takePersistableUriPermission(uri, flag);

                        profileLink = uri;
                        profileImage.setImageURI(uri);

                        AppActivity.currentSession.setPfpLink(uri.toString());
                        db.userDao().updateImage(AppActivity.currentSession.getId(), uri.toString());
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        profileImage = (ImageView) rootView.findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        Uri uri;
        if (AppActivity.currentSession.getPfpLink() != null) {
            uri = Uri.parse(AppActivity.currentSession.getPfpLink());
            if (uri != null)
                profileImage.setImageURI(uri);
            else
                profileImage.setImageResource(R.drawable.basically_burger_1);
        }

        return rootView;
    }
}