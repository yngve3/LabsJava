import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для получения списков пользователей и фильмов.
 * Так как файлы, представленные в задании довольно большие,
 * то для экономии времени, вся информация один раз читается и записывается
 * в отдельные файлы FILMS_FILE (список фильмов) и USERS_FILE (список пользователей).
 * Каждый элемент в этих файлах соответсвтует классу (Film или User).
 * При последующем запросе списков данные читаются уже из новых файлов, что проиходит куда быстрее.
 */
public class FileHelper {

    private final String DIR_PATH_SOURCE = "D:\\archive";
    private final String FILMS_FILE = "films.txt";
    private final String USERS_FILE = "users.txt";
    private final String FILMS_TITLES_FILE = "movie_titles.csv";
    private final String ID_PATTERN = "[0-9]*:";

    public List<Film> readFilms() throws IOException {
        File filmsFile = new File(FILMS_FILE);
        if (!filmsFile.exists()) {
            readFilmsToFile(filmsFile);
        }

        return readFilmsFromFile(filmsFile);
    }

    public List<User> readUsers(List<String> filmsWithGoodRating) throws IOException {
        File usersFile = new File(USERS_FILE);
        if (!usersFile.exists()) {
            readUsersToFile(usersFile, filmsWithGoodRating);
        }

        return readUsersFromFile(usersFile);
    }

    private List<Film> readFilmsFromFile(File file) throws IOException {
        List<Film> films = new ArrayList<>();
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            Scanner scanner = new Scanner(fr);

            while (scanner.hasNextLine()) {
                Film film = new Film(scanner.nextLine());
                String[] dataOfFilm = scanner.nextLine().split(",");
                film.setTitle(dataOfFilm[0]);

                film.setRatings(
                        Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray()
                );
                films.add(film);
            }
            fr.close();
        }

        return films;
    }
    private List<User> readUsersFromFile(File file) throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            Scanner scanner = new Scanner(fr);

            while (scanner.hasNextLine()) {
                User user = new User(scanner.nextLine());
                String[] dataOfUser = scanner.nextLine().split(",");
                user.setCountBadRatingGoodFilms(Integer.parseInt(dataOfUser[0]));
                user.setCountRatingNotGoodFilms(Integer.parseInt(dataOfUser[1]));

                user.setRatings(
                        Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray()
                );
                users.add(user);
            }
        }

        return users;
    }

    private void readFilmsToFile(File file) throws IOException {
        File dir = new File(DIR_PATH_SOURCE);
        String[] fileNames = dir.list((dir1, name) -> name.startsWith("combined_data_"));

        List<Film> films = new ArrayList<>();

        if (fileNames != null) {
            for (String fileName : fileNames) {
                FileReader fileReaderFilmsData = new FileReader(DIR_PATH_SOURCE + "\\" + fileName);
                Scanner scannerFilmsData = new Scanner(fileReaderFilmsData);

                FileReader fileReaderFilmsTitles = new FileReader(DIR_PATH_SOURCE + "\\" + FILMS_TITLES_FILE);
                Scanner scannerFilmsTitles = new Scanner(fileReaderFilmsTitles);

                Pattern patternId = Pattern.compile(ID_PATTERN);

                Film film = null;
                while (scannerFilmsData.hasNextLine()) {
                    String str = scannerFilmsData.nextLine();
                    Matcher matcher = patternId.matcher(str);
                    if (matcher.find()) {
                        String id = matcher.group();
                        if (film != null) films.add(film);
                        film = new Film(id.substring(0, id.length() - 1), scannerFilmsTitles.nextLine().split(",")[2]);
                    } else if (film != null) {
                        int rating = Integer.parseInt(str.split(",")[1]);
                        film.addRating(rating);
                    }
                }
                scannerFilmsData.close();
                fileReaderFilmsData.close();
                scannerFilmsTitles.close();
                fileReaderFilmsTitles.close();
            }

            FileWriter fw = new FileWriter(file);

            for (Film film : films) {
                fw.write(film.toString());
            }
            fw.close();
        }
    }

    private void readUsersToFile(File file, List<String> filmsWithGoodRating) throws IOException {
        File dir = new File(DIR_PATH_SOURCE);
        String[] fileNames = dir.list((dir1, name) -> name.startsWith("combined_data_"));

        Map<String, User> users = new HashMap<>();

        if (fileNames != null) {
            for (String fileName : fileNames) {
                FileReader fl = new FileReader(DIR_PATH_SOURCE + "\\" + fileName);
                Scanner scanner = new Scanner(fl);
                Pattern patternId = Pattern.compile("[0-9]*:");

                String filmId = null;

                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                    Matcher matcher = patternId.matcher(str);
                    if (matcher.find()) {
                        filmId = str.substring(0, str.length() - 1);
                    } else {
                        int rating = Integer.parseInt(str.split(",")[1]);
                        String userId = str.split(",")[0];

                        if (users.containsKey(userId)) {
                            addRating(users.get(userId), rating, filmId, filmsWithGoodRating);
                        } else {
                            User user = new User(userId);
                            addRating(user, rating, filmId, filmsWithGoodRating);
                            users.put(userId, user);
                        }
                    }
                }
                scanner.close();
                fl.close();
            }

            FileWriter fw = new FileWriter(file);

            for (User user : users.values()) {
                fw.write(user.toString());
            }
            fw.close();
        }
    }

    private void addRating(User user, int rating, String filmId, List<String> films) {
        if (films.contains(filmId)) {
            user.addRating(rating, User.QualityFilms.GOOD_FILM);
        } else {
            user.addRating(rating, User.QualityFilms.NOT_GOOD_FILM);
        }
    }
}
