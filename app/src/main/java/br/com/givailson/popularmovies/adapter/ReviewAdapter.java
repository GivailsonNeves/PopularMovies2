package br.com.givailson.popularmovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import br.com.givailson.popularmovies.R;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.givailson.popularmovies.model.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review_item, viewGroup, false);

        ReviewViewHolder vh = new ReviewViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        reviewViewHolder.position = i;
        reviewViewHolder.prepare(this.reviews.get(i));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvAuthor;
        private final TextView tvReview;
        public int position;

        public ReviewViewHolder(View view) {
            super(view);
            this.tvAuthor = view.findViewById(R.id.tvAuthor);
            this.tvReview = view.findViewById(R.id.tvReview);
        }

        public void prepare(Review review) {

            this.tvAuthor.setText(review.author);
            this.tvReview.setText(review.content);
        }
    }
}
