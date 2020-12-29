/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class SeamCarver {

    private static final double MAX_ENERGY = 1000.0;
    // current  picture;
    private Picture pc;
    private int width, height;
    private double[][] energy;
    private int[][] colors;
    private boolean horizontal;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("The construtor argument is null");

        pc = new Picture(picture);
        width = pc.width();
        height = pc.height();
        getColor(pc);
        getEnergy(pc);
        horizontal = false;
    }


    private void getColor(Picture p) {
        colors = new int[width][height];
        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                colors[col][row] = p.getRGB(col, row);

    }

    private void getEnergy(Picture p) {
        energy = new double[width][height];
        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                energy[col][row] = CalEnergy(col, row);

    }

    // current picture
    public Picture picture() {
        if (horizontal == true) {
            transpose(width, height);
            horizontal = false;
        }
        Picture newPicture = new Picture(width, height);
        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                newPicture.setRGB(col, row, colors[col][row]);
        return newPicture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        //x - width y - height
        if (x < 0 || x > width - 1)
            throw new IllegalArgumentException("x- coordinate out of range");
        if (y < 0 || y > height - 1)
            throw new IllegalArgumentException("y- coordinate out of range");
        return energy[x][y];
    }

    // energy of pixel at column x and row y
    //the indices x and y are integers between 0 and width − 1 and between 0 and height − 1
    private double CalEnergy(int x, int y) {
        if (x == 0 || x == width - 1) return MAX_ENERGY;
        if (y == 0 || y == height - 1) return MAX_ENERGY;
        return Math.sqrt(energyX(x, y) + energyY(x, y));
    }

    //between pixel (x + 1, y) and pixel (x − 1, y), respectively
    private double energyX(int x, int y) {
        int rgb = colors[x + 1][y];
        int r1 = (rgb >> 16) & 0xFF;
        int g1 = (rgb >> 8) & 0xFF;
        int b1 = (rgb >> 0) & 0xFF;

        rgb = colors[x - 1][y];

        int r2 = (rgb >> 16) & 0xFF;
        int g2 = (rgb >> 8) & 0xFF;
        int b2 = (rgb >> 0) & 0xFF;

        double r = r1 - r2;
        double g = g1 - g2;
        double b = b1 - b2;

        return (r * r + g * g + b * b);

    }


    private double energyY(int x, int y) {
        int rgb = colors[x][y + 1];
        int r1 = (rgb >> 16) & 0xFF;
        int g1 = (rgb >> 8) & 0xFF;
        int b1 = (rgb >> 0) & 0xFF;

        rgb = colors[x][y - 1];

        int r2 = (rgb >> 16) & 0xFF;
        int g2 = (rgb >> 8) & 0xFF;
        int b2 = (rgb >> 0) & 0xFF;

        double r = r1 - r2;
        double g = g1 - g2;
        double b = b1 - b2;

        return (r * r + g * g + b * b);

    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (horizontal == false) {
            transpose(height, width);
            horizontal = true;
        }
        int[] horizontalSeam = findSeam();
       // transpose(width, height);
        return horizontalSeam;
    }

    private void transpose(int w, int h) {
        int[][] newColors = new int[height][width];
        double[][] newEnergy = new double[height][width];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                newColors[row][col] = colors[col][row];
                newEnergy[row][col] = energy[col][row];
            }
        }
        int temp = width;
        width = height;
        height = temp;
        colors = newColors;
        energy = newEnergy;
    }

    public int[] findVerticalSeam() {
        if (horizontal == true) {
            transpose(width, height);
            horizontal = false;
        }
        return findSeam();
    }

    // sequence of indices for vertical seam
    private int[] findSeam() {

        int[][] edgesTo = new int[width][height];
        double[][] distTo = new double[width][height];

        // initialize and set when row = 0
        for (int col = 0; col < width; col++) {
            Arrays.fill(distTo[col], Double.POSITIVE_INFINITY);
            distTo[col][0] = 1000;
        }

        for (int row = 1; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // relax edges
                if (distTo[col][row]    > distTo[col][row - 1] + energy[col][row]) {
                    distTo[col][row]    = distTo[col][row - 1] + energy[col][row];
                    edgesTo[col][row]   = col + (row - 1) * width;
                }
                if (col > 0 && distTo[col][row]
                        > distTo[col - 1][row - 1] + energy[col][row]) {
                    distTo[col][row] = distTo[col - 1][row - 1] + energy[col][row];
                    edgesTo[col][row] =  col - 1 + (row - 1) * width;
                }
                if (col + 1 < width && distTo[col][row]
                        > distTo[col + 1][row - 1] + energy[col][row]) {
                    distTo[col][row] = distTo[col + 1][row - 1] + energy[col][row];
                    edgesTo[col][row] = col + 1 + (row - 1) * width;
                }
            }
        }

        // find shorest path
        double minDist = distTo[0][height - 1];
        int minPath = 0;
        for (int col = 1; col < width; col++)
            if (distTo[col][height - 1] < minDist) {
                minDist = distTo[col][height - 1];
                minPath = col;
            }
        int [] seams = new int[height];
        seams[height - 1] = minPath;
        for (int row = height - 2; row >= 0; row--)
            seams[row] = edgesTo[seams[row + 1]][row + 1] - row * width;

        return seams;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if (horizontal == false) {
            if (isSeamValid(seam, width, height)) {
                transpose(height, width);
                horizontal = true;
                removeSeam(seam);
            }

        }else {
            if (isSeamValid(seam, height, width)) {
                removeSeam(seam);
            }
        }
    }


    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (horizontal == true) {
            transpose(width, height);
            horizontal = false;
        }
        if (isSeamValid(seam, height, width)) {
            removeSeam(seam);
        }
    }


    private void removeSeam(int[] seam) {
        /* check for seam data */

        int[][] newColors = new int[width - 1][height];
        double[][] newEnergys = new double[width - 1][height];
        for (int col = 0; col < width - 1; col++)
            for (int row = 0; row < height; row++)
                if (col < seam[row]) {
                    newColors[col][row] = colors[col][row];
                    newEnergys[col][row] = energy[col][row];
                }
                else {
                    newColors[col][row] = colors[col + 1][row];
                    newEnergys[col][row] = energy[col + 1][row];
                }
        colors = newColors;
        energy = newEnergys;
        width--;
    }


    private boolean isSeamValid(int[] seam, int len, int widthorht) {
        if (seam == null)
            throw new IllegalArgumentException("The removeSeam argument is null");
        if (widthorht <= 1)
            throw new IllegalArgumentException(
                    "The RemoveSeam called when the width/height of the picture is 1 or less");
        if (seam.length != len)
            throw new IllegalArgumentException(
                    "The removeSeam called with an array of the wrong length");

        int invalidrange = seam[0];
        for (int row = 0; row < len; row++) {
            if (seam[row] >= widthorht  || seam[row] < 0)
                throw new IllegalArgumentException("invalid seam");
            if(Math.abs(seam[row] - invalidrange) > 1)
                throw new IllegalArgumentException("invalid seam");
            invalidrange = seam[row];
        }

        return true;
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());
        picture.show();
        SeamCarver sc = new SeamCarver(picture);
        int[] verticalSeam = sc.findVerticalSeam();
        sc.removeVerticalSeam(verticalSeam);
        StdOut.printf("Displaying energy calculated for each pixel.\n");
        //SCUtility.showEnergy(sc);


    }
}
