package com.example.cookinti;

import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeFeedView extends RecyclerView.Adapter<RecipeFeedView.ViewHolder> {

    private AppDatabase db;
    private List<Recipe> recipes;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView authorText;
        private final TextView descriptionText;
        private final ImageView imageView;
        private final ImageButton favButton;
        private final RatingBar recipeRating;
        private final Button addReviewButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.recipeText);
            authorText = (TextView) view.findViewById(R.id.authorText);
            descriptionText = (TextView) view.findViewById(R.id.descriptionText);
            imageView = (ImageView) view.findViewById(R.id.recipeImage);
            favButton = (ImageButton) view.findViewById(R.id.fav);
            recipeRating = (RatingBar) view.findViewById(R.id.recipeRating);
            addReviewButton = (Button) view.findViewById(R.id.addReviewButton);
        }

        public void SetUpRecipeFeedView(Recipe recipe, AppDatabase db)
        {
            Context cntxt = getImageView().getContext();

            getAuthorText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppActivity.CheckUserProfile(cntxt, recipe.getFk_userid());
                }
            });

            SetVisuals(cntxt, db, recipe.getId());

            getFavourite().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppActivity.FavouriteRecipe(AppActivity.currentSession.getId(), recipe.getId());
                    SetVisuals(cntxt, db, recipe.getId());
                }
            });
        }

        public void SetVisuals(Context cntxt, AppDatabase db, long recipeId)
        {
            Boolean notFavourite = db.favouriteDao().isFavourite(AppActivity.currentSession.getId(), recipeId).isEmpty();
            if (!notFavourite)
                getFavourite().setImageDrawable(cntxt.getDrawable(R.drawable.fav_filled));
            else
                getFavourite().setImageDrawable(cntxt.getDrawable(R.drawable.fav_empty));

            if (!notFavourite) {
                //getFavourite().setImageDrawable(getDrawable(cntxt, R.drawable.fav_filled));
                Anims.ScaleViewAnim(getFavourite()).start();
            }
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getAuthorText() {
            return authorText;
        }

        public TextView getDescriptionText() {
            return descriptionText;
        }
        public ImageView getImageView() {
            return imageView;
        }
        public ImageView getFavourite() {
            return favButton;
        }
        public RatingBar getRecipeRating() {
            return recipeRating;
        }

        public Button getAddReviewButton() {
            return addReviewButton;
        }
    }



    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public RecipeFeedView(List<Recipe> dataSet, AppDatabase database)
    {
        recipes = dataSet;
        db = database;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        Recipe rec = recipes.get(position);
        viewHolder.getTextView().setText(rec.getName());
        viewHolder.getDescriptionText().setText(rec.getDescription());

        Uri uri;
        if (rec.getImageLink() != null) {
            uri = Uri.parse(rec.getImageLink());
            if (uri != null)
                viewHolder.getImageView().setImageURI(uri);
            else
                viewHolder.getImageView().setImageResource(R.drawable.basically_burger_1);
        }

        String userName = db.userDao().getUser(rec.getFk_userid()).getUsername();
        viewHolder.getAuthorText().setText(userName);

        float review = db.reviewDao().getAverageRating(rec.getId());
        viewHolder.getRecipeRating().setRating(review);

        Context cntxt = viewHolder.getImageView().getContext();
        viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cntxt, RecipeActivity.class);
                intent.putExtra("RecipeId", rec.getId());
                cntxt.startActivity(intent);
            }
        });

        viewHolder.getAuthorText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppActivity.CheckUserProfile(cntxt, rec.getFk_userid());
            }
        });

        Boolean notFavourite = db.favouriteDao().isFavourite(AppActivity.currentSession.getId(), rec.getId()).isEmpty();
        if (!notFavourite)
            viewHolder.getFavourite().setImageDrawable(cntxt.getDrawable(R.drawable.fav_filled));
        else
            viewHolder.getFavourite().setImageDrawable(cntxt.getDrawable(R.drawable.fav_empty));

        viewHolder.getFavourite().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppActivity.FavouriteRecipe(AppActivity.currentSession.getId(), rec.getId());

                if (notFavourite) {
                    viewHolder.getFavourite().setImageDrawable(getDrawable(cntxt, R.drawable.fav_filled));
                    Anims.ScaleViewAnim(view).start();
                }

                notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }
}