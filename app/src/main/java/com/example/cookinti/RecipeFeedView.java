package com.example.cookinti;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.recipeText);
            authorText = (TextView) view.findViewById(R.id.authorText);
            descriptionText = (TextView) view.findViewById(R.id.descriptionText);
            imageView = (ImageView) view.findViewById(R.id.recipeImage);
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
        viewHolder.getImageView().setImageResource(R.drawable.basically_burger_1);

        String userName = db.userDao().getUser(rec.getFk_userid()).getUsername();
        viewHolder.getAuthorText().setText(userName);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }
}