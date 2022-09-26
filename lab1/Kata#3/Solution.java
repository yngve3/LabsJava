import java.util.Arrays;
public class Solution {

    public static int solveSuperMarketQueue(int[] customers, int n) {
        int[] cashiers = new int[n];

        for (int customer : customers) {
            Arrays.sort(cashiers);
            cashiers[0] += customer;
        }

        Arrays.sort(cashiers);

        return cashiers[n - 1];

    }
    
}
