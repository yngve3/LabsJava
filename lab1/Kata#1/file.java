public class SurrealCheck {

  public static boolean isSurreal(String l, String r) {
        if (!l.isEmpty() && !r.isEmpty()) {
            for (String i : l.split(" ")) {
                for (String j : r.split(" ")) {
                    if (parse(i) >= parse(j)) return false;
                }
            }
        }

        return true;
    }

    public static float parse(String num) {
        if (num.contains("/")) {
            return Float.parseFloat(num.split("/")[0]) / Float.parseFloat(num.split("/")[1]);
        } else {
            return Float.parseFloat(num);
        }
    }
}
