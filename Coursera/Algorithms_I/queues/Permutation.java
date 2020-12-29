/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        int i = 0;
        if(k > 0) {
            for(;i<k;i++){
                rq.enqueue(StdIn.readString());
            }

            while(!StdIn.isEmpty()){
                String s = StdIn.readString();
                int l = StdRandom.uniform(i+1);
                if(l < k) {
                    rq.dequeue();
                    rq.enqueue(s);
                }
                i++;
            }

            Iterator<String> iterator1 = rq.iterator();

            for(int m = 0; m < k; m++) {
                if(iterator1.hasNext()) {
                    System.out.println(iterator1.next());
                }
            }
        }




    }
}

