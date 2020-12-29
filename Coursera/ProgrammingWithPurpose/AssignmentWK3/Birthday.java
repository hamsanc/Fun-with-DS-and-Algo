public class Birthday
{

	public static void main(String[] args)
	{

		int days = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		

		int[] people = new int[days+2];
		
		 // repeat trials times
        	for (int t = 1; t <= trials; t++) {
			
			//  hasBirthday[d] = true if someone born on day d; false otherwise
          	 	boolean[] hasBirthday = new boolean[days];

			for(int i = 1;i <= (days+2); i++) {

				

			        int d = (int) (days * Math.random());   // integer between 0 and days-1
			  	if (hasBirthday[d]){
					
					people[i]++;				
					break;
			 	}
			 	else {

				       hasBirthday[d] = true;                  // update array	/
				}

			}            		

		}

		
		double fraction = 0.0;
		double sum = 0 ;

		for(int i = 1; i <= (days+2); i++)
		{
			sum += people[i];
			fraction = sum/trials;
			System.out.println(i+" "+people[i]+" "+fraction);

			if(fraction >= 0.500000) break;
			
		}
		

	}
       	 

}
