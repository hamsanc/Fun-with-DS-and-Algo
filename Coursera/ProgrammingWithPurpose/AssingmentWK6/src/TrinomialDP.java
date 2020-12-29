public class TrinomialDP {

    public static long trinomial(int n , int k){

        if(n == 0 && k == 0) return 1;
        else if(k < -n ||  k > n) return 0;
        long[][] t = new long[n+1][n+1];

        t[0][0] = 1;
        t[0][1] = 0;


        for(int i = 1; i <= n; i++){
            for(int j =0; j <=n; j++){

                t[i][j] = t_helper(t,i-1,j-1) + t_helper(t,i-1,j) + t_helper(t,i-1,j+1);

            } //  t[1][0] = t[0][-1]  + t[0][0] + t[0][1];
        }


        if(k < 0) return t[n][-k];
        else return t[n][k];

    }


    private static long t_helper(long [][] t, int i, int j){

        if(j < 0) {
            j = -j;
            return t[i][j];
        }
        else if(j > i) return 0;
        else return t[i][j];


    }



    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        System.out.println(trinomial(n,k));


    }
}
