/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNet {
    private int synsetcnt;
    private HashMap<String, List<Integer>> synset;
    private List<String> nounlist;
    private SAP sap;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("argument is null");

        nounlist = new ArrayList<String>();
        synset = new HashMap<String, List<Integer>>(1000, 0.75f);

        In n = new In(synsets);
        while (!n.isEmpty()) {
            // Increment the synset count
            synsetcnt++;
            // Read each line from the file
            String s = n.readLine();
            String[] fields = s.split(",");
            // Extract the synset id
            int synsetid = Integer.parseInt(fields[0]);
            // Extract the nouns
            String[] nouns = fields[1].split("\\s");
            nounlist.add(fields[1]);
            // Insert noun in Hashtable
            for (String noun : nouns) {
                List<Integer> lst = synset.getOrDefault(noun, new ArrayList<>());
                lst.add(synsetid);
                synset.put(noun, lst);
            }
        }

        Createdigraph(hypernyms);
    }

    private void Createdigraph(String hypernyms) {
        // Create a empty digraph with synset count as no of vertices
        Digraph G = new Digraph(nounlist.size());
        In n = new In(hypernyms);
        while (!n.isEmpty()) {
            String s = n.readLine();
            String[] fields = s.split(",");
            for (int i = 1; i < fields.length; i++)
                G.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
        }

        // Check if the digraph has cycles
        hasCycle(G);
        isRootedDAG(G);
        sap = new SAP(G);
    }


    private void hasCycle(Digraph g) {
        DirectedCycle gc = new DirectedCycle(g);
        if (gc.hasCycle())
            throw new IllegalArgumentException("The graph has cycles - sorry");
    }

    private void isRootedDAG(Digraph g) {
        int rootcnt = 0;
        for (int i = 0; i < g.V(); i++) {
            // root vertex has zero out degree
            if (g.outdegree(i) == 0) {
                if (rootcnt == 0) rootcnt++;
                else throw new IllegalArgumentException("The graph has more than one root");
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synset.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("argument is null");
        return synset.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException(" Passed noun not found in the wordnet");

        List<Integer> lstA = synset.get(nounA);
        List<Integer> lstB = synset.get(nounB);

        if (lstA.size() == 1 && lstB.size() == 1)
            return sap.length(lstA.get(0), lstB.get(0));
        else
            return sap.length(lstA, lstB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException(" Passed noun not found in the wordnet");

        List<Integer> lstA = synset.get(nounA);
        List<Integer> lstB = synset.get(nounB);


        if (lstA.size() == 1 && lstB.size() == 1)
            return nounlist.get(sap.ancestor(lstA.get(0), lstB.get(0)));
        else
            return nounlist.get(sap.ancestor(lstA, lstB));
    }


    public static void main(String[] args) {
        WordNet w = new WordNet(args[0], args[1]);
        //System.out.println(w.isNoun("k"));
        //System.out.println(w.nouns());
        System.out.println(w.distance("scleroprotein", "matter"));
        System.out.println(w.sap("scleroprotein", "ricin"));
    }
}
