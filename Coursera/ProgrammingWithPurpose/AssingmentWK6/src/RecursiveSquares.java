public class RecursiveSquares {

    // Draws a square centered on (x, y) of the given side length
    // with a light gray background and a black border.
    public static void drawSquare(double x, double y, double length){

        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(x,y,length/2);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x,y,length/2);


    }

    // Draws a recursive square pattern of order n, centered on (x, y)
    // of the given side length.
    public static void draw(int n, double x, double y, double len){

        if (n == 0) return;



        draw(n-1,x-len/2,y+len/2,len/2);
        draw(n-1,x+len/2,y+len/2,len/2);
        drawSquare(x,y,len);
        draw(n-1,x-len/2,y-len/2,len/2);
        draw(n-1,x+len/2,y-len/2,len/2);




    }

    // Takes an integer command-line argument n and draws a recursive
    // square pattern of order n, centered on (0.5, 0.5) with side length 0.5.

     public static void main(String[] args) {

         int n = Integer.parseInt(args[0]);

         draw(n, 0.5, 0.5, 0.5);


    }
}
