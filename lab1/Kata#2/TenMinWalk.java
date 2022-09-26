public class TenMinWalk {
  public static boolean isValid(char[] walk) {
        if (walk.length != 10) return false;
        
        int n = 0, s = 0, e = 0, w = 0;

        for (char dir : walk) {
            switch (dir) {
                case 'n' -> n++;
                case 's' -> s++;
                case 'e' -> e++;
                case 'w' -> w++;
            }
        }

        return ((n - s) == 0) && ((e - w) == 0);
    }
}
