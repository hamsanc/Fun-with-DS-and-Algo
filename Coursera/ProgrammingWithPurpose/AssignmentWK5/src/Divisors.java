public class Divisors {

    public static int gcd(int a, int b){

       int l = Math.abs(a);
       int m  = Math.abs(b);

        if (l == 0 && m ==0) return 0;
        else {

            while(m != 0){

                int temp = l;
                l = m;
                m = temp % m;
           }

            return l;

        }
   }

   public static int lcm(int a, int b){

        if(a == 0 || b == 0) return 0;
        else{

            int k = Math.abs(a)/gcd(a,b);

            return (k * Math.abs(b));

        }

   }


   public static boolean areRelativelyPrime(int a, int b) {


        if(gcd(a,b) == 1) return true;
        else              return false;

   }


   public static int totient(int n){

        int count = 0;
        if(n <= 0 ) return 0;
        else {
            for(int i =1; i <= n; i++){
                if(areRelativelyPrime(i,n)) count++;
           }
            return count;
        }



   }


    public static void main(String[] args) {

        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        StdOut.printf("gcd(%d, %d) = ",a,b);
        System.out.println(gcd(a,b));

        StdOut.printf("lcm(%d, %d) = ",a,b);
        System.out.println(lcm(a,b));

        StdOut.printf("areRelativelyPrime(%d, %d) = ",a,b);
        System.out.println(areRelativelyPrime(a,b));

        StdOut.printf("totient(%d) = ",a);
        System.out.println(totient(a));

        StdOut.printf("totient(%d) = ",b);
        System.out.println(totient(b));










    }
}
