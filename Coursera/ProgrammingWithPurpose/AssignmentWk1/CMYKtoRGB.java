public class CMYKtoRGB
{

	public static void main(String[] args)
	{

		double cyan = Double.parseDouble(args[0]);
		double magneta = Double.parseDouble(args[1]);
		double yellow = Double.parseDouble(args[2]);
		double black = Double.parseDouble(args[3]);

		double white = 1-black;
		double red = 255 * ( white * (1 - cyan));
		double green = 255 * (white * (1 - magneta));
		double blue = 255 * (white * (1-yellow));

		int r = (int)Math.round(red);
		int g = (int)Math.round(green);
		int b = (int)Math.round(blue);

		System.out.println("red   = "+r);
		System.out.println("green = "+g);
		System.out.println("blue  = "+b);


	}


} 
