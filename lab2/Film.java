public class Film {
    private final int systemOfRating = 5;
    private final String id;
    private String title;
    private int[] ratings;

    public Film(String id) {
        this.id = id;
        ratings = new int[systemOfRating];
    }

    public Film(String id, String title) {
        this.id = id;
        this.title = title;
        ratings = new int[systemOfRating];
    }

    public void addRating(int rating) {
        ratings[rating - 1]++;
    }

    public void setRatings(int[] ratings) {
        this.ratings = ratings;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getScore() {
        int countRatings = 0;
        int sum = 0;
        for (int i = 0; i < ratings.length; i++) {
            sum += (i + 1) * ratings[i];
            countRatings += ratings[i];
        }

        return (double)sum / countRatings;
    }

    public int getCountRatings() {
        int countRatings = 0;
        for (int rating : ratings) {
            countRatings += rating;
        }
        return countRatings;
    }

    public int[] getRatings() {
        return ratings;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(id).append("\n").append(title).append("\n");
        for (int rating : ratings) {
            res.append(rating).append(",");
        }
        res.append("\n");
        return new String(res);
    }
}
