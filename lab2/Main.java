import java.io.*;
import java.util.*;

public class Main {

    public static final int GOOD_RATING = 4;

    public static void main(String[] args) throws IOException {

        FileHelper fileHelper = new FileHelper();
        List<Film> films = fileHelper.readFilms();

        List<String> filmsWithGoodRating = StatisticHelper.getFilmsWithRatingStarts(films, GOOD_RATING);
        List<User> users = fileHelper.readUsers(filmsWithGoodRating);

        System.out.println("Фильм, имеющий больше всех оценок: " + StatisticHelper.getFilmWithMaxCountRating(films).getTitle());
        System.out.println("Лучший фильм по мнению зрителей: " + StatisticHelper.getBestAndWorstFilms(films)[0].getTitle());
        System.out.println("Худший фильм по мнению зрителей: " + StatisticHelper.getBestAndWorstFilms(films)[1].getTitle());
        System.out.println("Пользователь, что поставил больше всех оценок: " + StatisticHelper.getUserGaveMostRatings(users).getId());
        System.out.println("Оценку, что ставят больше всего: " + StatisticHelper.getMostOftenRating(films));
        System.out.println("Злые пользователи:");
        for (User veryAngryUser : StatisticHelper.getAngryUsers(users)) {
            System.out.println(veryAngryUser.getId());
        }
    }
}