import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс, в котором вычисляются непосредственно ответы на вопросы задания
 */
public class StatisticHelper {
    public static Film getFilmWithMaxCountRating(List<Film> films) {
        return films.stream().max(Comparator.comparingInt(Film::getCountRatings)).get();
    }

    public static Film[] getBestAndWorstFilms(List<Film> films) {
        films.sort(Comparator.comparingDouble(Film::getScore).thenComparingInt(Film::getCountRatings));
        return new Film[] {films.get(films.size() - 1), films.get(0)};
    }

    public static int getMostOftenRating(List<Film> films) {
        int[] sum = new int[5];
        for (Film film : films) {
            int[] t = film.getRatings();
            for (int i = 0; i < 5; i++) {
                sum[i] += t[i];
            }
        }
        int maxEl = Integer.MIN_VALUE;
        int maxInd = -1;
        for (int i = 0; i < 5; i++) {
            if (sum[i] > maxEl) {
                maxEl = sum[i];
                maxInd = i + 1;
            }
        }

        return maxInd;
    }

    public static User getUserGaveMostRatings(List<User> users) {
        return users.stream().max(Comparator.comparingInt(User::getCountRatings)).get();
    }

    public static List<User> getAngryUsers(List<User> users) {
        users.sort(Comparator.comparingInt(User::getCountBadRatingGoodFilms));
        List<User> veryAngryUsers = new ArrayList<>();
        for (int i = users.size() - 1; i > users.size() - 11; i--) {
            veryAngryUsers.add(users.get(i));
        }

        return veryAngryUsers;
    }

    public static List<String> getFilmsWithRatingStarts(List<Film> films, int rating) {
        List<String> res = new ArrayList<>();
        for (Film film : films) {
            if (film.getScore() >= rating) res.add(film.getId());
        }
        return res;
    }
}
