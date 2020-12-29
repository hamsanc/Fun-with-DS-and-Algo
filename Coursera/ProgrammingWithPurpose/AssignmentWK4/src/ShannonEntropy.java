public class ShannonEntropy {
    public static void main(String[] args) {

        int m = Integer.parseInt(args[0]);

        double count = 0.0;
        double[] freq = new double[m+1];

        //Read the input
        // Parallelly also calculate the frequency
        while(!StdIn.isEmpty()){

            int x = StdIn.readInt();
            freq[x]++;
            count++;
        }


        // Calculate Shannon Entropy
        double h = 0.0;
        for(int i = 1; i <= m; i++){

            double p = freq[i]/count;

            if(p != 0){

                h = h + (p * (Math.log(p)/Math.log(2.0)));

            }

        }
            h = -h;
            StdOut.printf("%.4f\n",h);


    }
}
