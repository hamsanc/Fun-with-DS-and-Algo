public class RevesPuzzle {
    private static void hanoi3(int n, String from, String temp, String to, int k) {
        if (n == 0) return;
        hanoi3(n - 1, from, to, temp, k);
        StdOut.println("Move disc " + (n + k) + " from " + from + " to " + to);
        hanoi3(n - 1, temp, from, to, k);
    }


    private static void hanoi4(int n, String from, String temp1, String temp2, String to, int x) {
            if (n == 0) return;
            if (x == 0) return;
            hanoi4(n - 1, from, temp2, to, temp1, x - 1);
            StdOut.println("Move disc " + (n) + " from " + from + " to " + to);
            hanoi4(n - 1, temp1, temp2, from, to, x - 2);
    }


    private static void hanoik(int n, String A, String B, String C, String D) {
        if(n <= 3){
            hanoi4(n,A,B,C,D,3);
            return;
        }
        int k = n + 1 - (int) Math.round((Math.sqrt(2 * n + 1)));
        hanoik(k,A,C,D,B);
        hanoi3(n-k,A,C,D,k);
        hanoik(k,B,A,C,D);
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        hanoik(n, "A", "B", "C", "D");
    }
}
