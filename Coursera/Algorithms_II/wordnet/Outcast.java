/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null)
            throw new IllegalArgumentException("the argument is null");
        for (int i = 0; i < nouns.length; i++) {
            if (!wordnet.isNoun(nouns[i]))
                throw new IllegalArgumentException("The word is not a noun");
        }

        int maxdist = -1;
        String outCastword = null;
        for (int i = 0; i < nouns.length; i++) {
            int sumdist = 0;
            for (int j = 0; j < nouns.length; j++) {
                sumdist += wordnet.distance(nouns[i], nouns[j]);
            }
            if (sumdist > maxdist) {
                maxdist = sumdist;
                outCastword = nouns[i];
            }
        }
        return outCastword;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}
