package reception;

import plot.Character;

import java.util.Arrays;

public class Response {
    private double viewersRating;
    private double criticsRating;
    private long viewersCount;
    private Review[] reviews;

    public Response() {
        this(0, 0, 0, new Review[0]);
    }

    public Response(double viewersRating, double criticsRating, long viewersCount, Review[] reviews) {
        this.viewersRating = viewersRating;
        this.criticsRating = criticsRating;
        this.viewersCount = viewersCount;
        this.reviews = reviews;
    }

    public double getViewersRating() {
        return viewersRating;
    }

    public double getCriticsRating() {
        return criticsRating;
    }

    public long getViewersCount() {
        return viewersCount;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void setViewersRating(double viewersRating) {
        this.viewersRating = viewersRating;
    }

    public void setCriticsRating(double criticsRating) {
        this.criticsRating = criticsRating;
    }

    public void setViewersCount(long viewersCount) {
        this.viewersCount = viewersCount;
    }

    public void addReview(Review review) {
        reviews = Arrays.copyOf(reviews,  reviews.length + 1);
        reviews[reviews.length - 1] = review;
    }

    public void deleteReview(Review review) {
        Review[] newReviews = new Review[reviews.length - 1];
        for (int i = 0, j = 0; i < reviews.length; i++) {
            if (reviews[i] != review) {
                newReviews[j++] = reviews[i];
            }
        }
        reviews = newReviews;
    }
}
