public class RandomWalker
{


	public static void main(String[] args)
	{

	   int r = Integer.parseInt(args[0]);
	   int x = 0;
	   int y = 0;
	   int steps =0;

	   int n=0, s=0, e=0, w=0;

	   while ( (Math.abs(x) + Math.abs(y)) != r)
	   {
	
	     System.out.println("(" + x + ", " +y + ")");
	     steps++;
	     
	     double d = Math.random();
	     
	     if 	( d <= 0.25) { y++; n++;}// north
	     else if 	( d <= 0.50) { x++; e++;}// east
	     else if 	( d <= 0.75) { y--; s++;}// south
	     else                  { x--; w++;}// west

	     	
	   }
	
	   System.out.println("(" + x + ", " +y + ")");

	   //System.out.println(n+" "+e+" "+s+" "+w);
  
	   System.out.println("steps = "+steps);

	}

}
