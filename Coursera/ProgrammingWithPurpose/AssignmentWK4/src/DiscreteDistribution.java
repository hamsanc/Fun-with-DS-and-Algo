public class DiscreteDistribution
{

	public static void main(String[] args)
	{

		 // Number of command line arguments
		  int k = args.length;
	
		 // Number positive integer sequence
	 	 int n = k-1; 
	
	 	 // Number of random indices
	 	 int m = Integer.parseInt(args[0]);


	 	 // Read the sequence   
	 	 int[] a = new int[n];
	  
	 	 for(int i = 1; i <= n; i++)
			a[i-1] = Integer.parseInt(args[i]);

	 	 // Cummulative sums
	 	 int cumm_sum = 0 ;
	 	 for (int i = 0; i < n; i++){
			cumm_sum += a[i];
	 	 }

	 		

	 	 // Random integer r 
	 	 
	
	 	 for ( int i = 0 ; i < m ; i++) {

		 	int sum = 0;
	 		int event = -1;
			
			int r = (int) (cumm_sum * Math.random());
			for( int j = 1; j <= n && sum <= r; j++){
		
		  	 sum += a[j-1];
		  	 event = j;
			}

			System.out.print(event+" ");
		}

		

	  }

	

}

