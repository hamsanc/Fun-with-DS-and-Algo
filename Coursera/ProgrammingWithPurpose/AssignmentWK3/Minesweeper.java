public class Minesweeper
{

	public static void main(String[] args)
	{

		int m = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		int k = Integer.parseInt(args[2]);



		int[][] ms = new int[m+2][n+2];

		boolean[][] hasMines = new boolean[m][n];
		
	
		
		//randomly place the mines		
		int mine = k;
		
		while(mine > 0){

			int x = (int)(m * Math.random());
			int y = (int)(n * Math.random());

			if(!hasMines[x][y]){
				hasMines[x][y] = true;
				mine--;
			}

		}



		/*for(int i = 0; i < m; i++){
			for(int j = 0; j < n; j++){

			
			  System.out.print(hasMines[i][j]+" ");
			

			}
			
			System.out.println();
		
		}	*/

		
		// increment count

		for(int i = 1; i < m+1;i++){
			for(int j = 1; j < n+1;j++){

				if(hasMines[i-1][j-1]) {

					ms[i-1][j-1]++;
					ms[i+1][j+1]++;
					ms[i-1][j]++;
					ms[i+1][j]++;
					ms[i][j-1]++;
					ms[i][j+1]++;
					ms[i-1][j+1]++;
					ms[i+1][j-1]++;
					

				}
					
		
			}

			
		}



		// print Mines


		for(int i = 1; i < m+1;i++){
			for(int j = 1; j < n+1;j++){

				if(hasMines[i-1][j-1])  System.out.print("*  ");
				else 			System.out.print(ms[i][j]+"  ");


			}

			System.out.println();
		}




	}
}


