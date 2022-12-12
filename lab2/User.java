public class User {

    public enum QualityFilms {
        GOOD_FILM,
        NOT_GOOD_FILM
    }
    private String id;

    /**
     * Количество оценок, что ставил пользователь
     * Индекс массива = соответсвующая оценка - 1
     */
    private int[] ratings;

    /**
     * Количество плохих оценок хорошим фильмам
     */
    private int countBadRatingGoodFilms;
    private int countGoodRatingNotGoodFilms;

    public User(String id) {
        this.id = id;
        ratings = new int[5];
    }

    /**
     * При добавлении оценки проверяется хорошему или плохому фильму ставится оценка,
     * Увеличиваются соответсвующие счетчики
     */
    public void addRating(int rating, QualityFilms qualityFilm) {
        ratings[rating - 1]++;

        switch (qualityFilm) {
            case GOOD_FILM -> {
                if (rating < Main.GOOD_RATING) countBadRatingGoodFilms++;
            }
            case NOT_GOOD_FILM -> {
                if (rating >= Main.GOOD_RATING) countGoodRatingNotGoodFilms++;
            }
        }
    }

    public void setCountBadRatingGoodFilms(int countBadRatingGoodFilms) {
        this.countBadRatingGoodFilms = countBadRatingGoodFilms;
    }

    public void setCountRatingNotGoodFilms(int countGoodRatingNotGoodFilms) {
        this.countGoodRatingNotGoodFilms = countGoodRatingNotGoodFilms;
    }

    public void setRatings(int[] ratings) {
        this.ratings = ratings;
    }

    public String getId() {
        return id;
    }

    public int getCountBadRatingGoodFilms() {
        return countBadRatingGoodFilms;
    }

    public int getCountGoodRatingNotGoodFilms() {
        return countGoodRatingNotGoodFilms;
    }

    public double getAverageRating() {
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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(id).append("\n").append(countBadRatingGoodFilms).append(",").append(countGoodRatingNotGoodFilms).append("\n");
        for (int rating : ratings) {
            res.append(rating).append(",");
        }
        res.append("\n");
        return new String(res);
    }
}
