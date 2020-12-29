public class AudioCollage {

    // Returns a new array the rescales a[] by a multiplication factor alpha
    public static double[] amplify(double[] a, double alpha){

            double[] amp_a = new double[a.length];


            for(int i = 0; i < a.length; i++){

                amp_a[i] = a[i] * alpha;

            }

            return amp_a;

    }

   // Returns a new array that is the reverse of a[]
    public static double[] reverse(double[] a){

        double[] rev_a = new double[a.length];


        int j = a.length-1;
        for(int i = 0; i < a.length; i++){

            rev_a[i] = a[j];
            j--;

        }
        return rev_a;


    }

    public static double[] merge(double[] a, double[] b){

        int m = a.length;
        int n = b.length;

        double[] merge_ab = new double[m+n];


        for(int i = 0; i < a.length; i++) merge_ab[i] = a[i];
        int j = a.length;
        for(int i = 0; i < b.length; i++){
            merge_ab[j] = b[i];
            j++;

        }
        return merge_ab;


    }

    // returns a new array that is sum of a[] and b[]
    public static double[] mix(double[] a, double[] b){

        int m = a.length;
        int n = b.length;

        int k =0;

        if(m != n){
            if(m > n)  k = m;
            else       k = n;
        }
        else k = m;

        double[] mix_ab = new double[k];


        // Check if array a needs padding
        if (m == n){

            for(int i = 0; i < k; i++) mix_ab[i] = a[i] + b [i];
         }
        else if(m < k){

            for(int i = 0; i < m; i++) mix_ab[i] = a[i];
            for(int i = m; i < k; i++) mix_ab[i] = 0;
            for(int i = 0; i < k; i++) mix_ab[i] += b[i];
        }
        else {

            for(int i = 0; i < n; i++) mix_ab[i] = b[i];
            for(int i = n; i < k; i++) mix_ab[i] = 0;
            for(int i = 0; i < k; i++) mix_ab[i] += a[i];

        }

        return mix_ab;

    }

    // Returns a new array that changes the speed by given factor
    public static double[] changeSpeed(double[] a, double alpha){

       if(alpha == 1)  return a;

        int m = a.length;
        int k = (int)(m/alpha);
        double[] cs = new double[k];

        for (int i = 0; i < k;i++){

            int j = (int)(i*alpha);
            cs[i] = a[j];
        }

        return cs;

    }


    public static void main(String[] args) {


        double[] a = StdAudio.read("singer.wav");
        double[] b = StdAudio.read("exposure.wav");
        double[] c = StdAudio.read("buzzer.wav");
        double[] d = StdAudio.read("chimes.wav");
        double[] e = StdAudio.read("silence.wav");
        double[] f = StdAudio.read("piano.wav");
        double[] g = StdAudio.read("beatbox.wav");


        double alpha = 0.5;

        StdAudio.play(amplify(f,alpha));

        alpha = 1.5;
        StdAudio.play(amplify(f,alpha));


        StdAudio.play(reverse(f));

        StdAudio.play(merge(b,c));


        StdAudio.play(mix(d,e));

        alpha = 1.5;


       StdAudio.play(changeSpeed(b,alpha));
        alpha = 0.6;


        StdAudio.play(changeSpeed(g,alpha));


    }
}
