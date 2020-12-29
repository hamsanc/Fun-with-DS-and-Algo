public class ActivationFunction {

    // returns heaviside function of x
    public static double heaviside(double x){

        double ret = -1.0;

        if (Double.isNaN(x)) ret = Double.NaN;
        else {

            if(x < 0.0)        ret = 0.0;
            else if(x == 0.0)  ret = 0.5;
            else if(x > 0)     ret = 1;

        }

        return ret;
    }


    // returns sigmoid of x
    public static double sigmoid(double x){

        if (Double.isNaN(x) ) return Double.NaN;
        else return (1/(1+Math.exp(-x)));

    }

    // returns the hyperbolic tangent of x
    public static double tanh(double x){

        if (Double.isNaN(x) ) return Double.NaN;
        else if(x >= 20.0) return 1.0;
        else if(x <= -20.0) return -1.0;
        else {

              return ((Math.exp(x) - Math.exp(-x))/(Math.exp(x) + Math.exp(-x)));
        }

    }

    // return softsign function of x
    public static double softsign(double x){

        if (Double.isNaN(x)) return Double.NaN;
        else if (x == Double.POSITIVE_INFINITY) return 1.0;
        else if (x == Double.NEGATIVE_INFINITY) return -1.0;
        else return (x/(1+Math.abs(x)));

    }


   public static double sqnl(double x){

       if (Double.isNaN(x)) return Double.NaN;
       else if (x <= -2.0) return -1.0;
       else if (x > -2.0 && x < 0.0) return (x + (x * x)/4);
       else if (x >= 0.0 && x < 2.0) return (x - (x * x)/4);
       else return 1;
  }


    public static void main(String[] args) {

        double x = Double.parseDouble(args[0]);


        StdOut.printf("heaviside(%.1f) = ",x);
        System.out.println(heaviside(x));

        StdOut.printf("  sigmoid(%.1f) = ",x);
        System.out.println(sigmoid(x));

        StdOut.printf("     tanh(%.1f) = ",x);
        System.out.println(tanh(x));

        StdOut.printf(" softsign(%.1f) = ",x);
        System.out.println(softsign(x));

        StdOut.printf("     sqnl(%.1f) = ",x);
        System.out.println(sqnl(x));

  }
}
