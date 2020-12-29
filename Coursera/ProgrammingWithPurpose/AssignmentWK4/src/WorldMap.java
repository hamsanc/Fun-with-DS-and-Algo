public class WorldMap {
    public static void main(String[] args) {

        int w = StdIn.readInt();
        int h = StdIn.readInt();

        // Set Canvas sise
        StdDraw.setCanvasSize(w , h);

        // Set X and Y scale
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();


        while(!StdIn.isEmpty()){

            // read the name of the region
            StdIn.readString();
            int v = StdIn.readInt();

            double[] x = new double[v];
            double[] y = new double[v];

            //read all co-ordinates
            for(int i = 0; i < v; i++){

                x[i] = StdIn.readDouble();
                y[i] = StdIn.readDouble();

            }

           // System.out.println("OK done 1 round");

            StdDraw.polygon(x, y);

        }
        StdDraw.show();






    }
}
