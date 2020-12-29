public class ThueMorse
{

	public static void main(String[] args)
	{

		int n = Integer.parseInt(args[0]);
	

		/*// Generate Thue-Morse string
		String Thue = "0";
		String Morse = "1";
		
	
		for (int i = 1; Thue.length() < n; i++){

			String t = Thue;
			String m = Morse;

			Thue += m;
			Morse += t;

		}*/

		

		int[] thue = new int[n];

		for(int i =0; i < n; i++)
		{
			if (i == 0)
			{

				thue[i] = 0;

			}
			else if (i%2 == 0)
			{
				thue[i] = thue[i/2];

			}
			else
			{

				thue[i] = 1-thue[i-1];


			}
		
		}
		

		for(int i = 0; i < n; i++){ 
			for (int j = 0; j < n; j++){

				if( i != j){

					if(thue[i] == thue[j]) System.out.print("+  ");
					else System.out.print("-  ");
				}else System.out.print("+  ");

			}

			System.out.println();
			
		}
		

	}

}
