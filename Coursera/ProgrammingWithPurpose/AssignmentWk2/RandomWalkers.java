public class RandomWalkers
{


	public static void main(String[] args)
	{

	   int r  = Integer.parseInt(args[0]);
	   int tr = Integer.parseInt(args[1]);
	   int x = 0;
	   int y = 0;
	   int steps = 0;
	   double Av_steps = 0.0;

	   for (int i =0; i<tr;i++)
	   {
		x = 0;
		y = 0;
		steps = 0; 
		while ( (Math.abs(x) + Math.abs(y)) != r)
	   	{
	
	     	     steps++;
	     
	             double d = Math.random();
	     
	             if 	( d <= 0.25) { y++; }// north
	             else if 	( d <= 0.50) { x++; }// east
	             else if 	( d <= 0.75) { y--; }// south
	             else                  { x--; }// west

	            //System.out.println("(" + x + ", " +y + ")");	
	        }

	        //   System.out.println("steps = "+steps);

		   Av_steps += steps;

	   }	

	 //  System.out.println(" total steps"+Av_steps);
	   Av_steps = Av_steps/tr;
	   System.out.println("average number of steps = "+Av_steps);	 
		  


	}

}
