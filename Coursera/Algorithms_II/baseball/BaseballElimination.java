/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;


public class BaseballElimination {

    private final int team_cnt;
    private final HashMap<String, Team> team_mp = new HashMap<String, Team>();
    private final HashMap<Integer, String> team_indx = new HashMap<Integer, String>();
    private Team[] t;
    private final int[][] g;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

        In in = new In(filename);
        /* read number of teams */
        team_cnt = Integer.parseInt(in.readLine());

        // System.out.println(team_cnt);

        t = new Team[team_cnt];
        g = new int[team_cnt][team_cnt];

        for (int i = 0; i < team_cnt; i++) {
            String team_name = in.readString();
            int wins = in.readInt();
            int loss = in.readInt();
            int rem = in.readInt();
            t[i] = new Team(i, team_name, wins, loss, rem);
            team_indx.put(i, team_name);
            team_mp.put(team_name, t[i]);
            for (int j = 0; j < team_cnt; j++) {
                g[i][j] = in.readInt();
            }
        }

        /* print for verification */
          /* for (int i = 0; i < team_cnt; i++) {
            String name = t[i].getName();
            Team k = team_mp.get(name);
            System.out
                    .printf("%-15s %d %-5d %d", k.getName(), k.getWins(), k.getLoss(), k.getRem());
            System.out.printf("\t");
            for (int j = 0; j < team_cnt; j++) {
                System.out.printf(" %d", g[i][j]);
            }
            System.out.println();
        }*/

        for (String t : team_mp.keySet()) {
            if (!isTrivialElimination(t)) {
                ComputeCertiOfElimination(t);
            }
            else {
                //System.out.println("Trivial  "+t);

            }
        }
    }

    private void ComputeCertiOfElimination(String team) {

        /* create a flow network first */
        int game_cnt = (((team_cnt - 1) * (team_cnt - 2)) / 2);
        int vex = game_cnt + team_cnt - 1 + 2;
        int lim_win = team_mp.get(team).getWins() + team_mp.get(team).getRem();

        HashMap<Integer, Integer> game_vex = new HashMap<Integer, Integer>();

        //System.out.printf(" Game count:%d", game_cnt);

        //System.out.printf(" Vertex count is:%d", vex);
        // System.out.println(team);
        int team_index = team_mp.get(team).getIndex();

        FlowNetwork f = new FlowNetwork(vex);

        int k = game_cnt + 1;
        int t = vex - 1;
        int[] gm_v = new int[team_cnt];
        for (int i = 0; i < team_cnt; i++) {
            if (team_index != i) {
                game_vex.put(k, i);
                gm_v[i] = k;
                /* flow from game to sink */
                String name = team_indx.get(i);
                int cap = lim_win - team_mp.get(name).getWins();
                f.addEdge(new FlowEdge(k, t, cap));
                k++;
            }
        }

        int s = 0;
        int v = 1;
        int gm_maxflow = 0;
        /* connect edges from source to game team */
        for (int i = 0; i < team_cnt; i++) {
            if (i == team_index) continue;
            for (int j = i + 1; j < team_cnt; j++) {
                if (i == j || j == team_index) continue;
                //System.out.println(i + " " + j);
                f.addEdge(new FlowEdge(s, v, g[i][j]));
                gm_maxflow += g[i][j];
                f.addEdge(new FlowEdge(v, gm_v[i], Double.POSITIVE_INFINITY));
                f.addEdge(new FlowEdge(v, gm_v[j], Double.POSITIVE_INFINITY));
                v++;
            }
        }

        //  System.out.println(team);
        // System.out.println(f.toString());
        //System.out.println("Game max flow   " + gm_maxflow);


        FordFulkerson maxflow = new FordFulkerson(f, s, t);
        //System.out.println(maxflow.value());

        if (maxflow.value() != gm_maxflow) {
            team_mp.get(team).setEliminated();
            for (int j = 0; j < f.V(); j++) {
                if (maxflow.inCut(j) && j > game_cnt) {
                    int indx = game_vex.get(j);
                    String tm = team_indx.get(indx);
                    team_mp.get(team).buildCertificateElimination(tm);
                    //StdOut.print(j + " ");
                }
            }
        }
    }

    private boolean isTrivialElimination(String team) {
        Team tx = team_mp.get(team);
        boolean isTrivialElimination = false;
        int max_win_x = tx.getWins() + tx.getRem();
        for (String t : team_mp.keySet()) {
            if (t == team) continue;
            if (max_win_x < team_mp.get(t).getWins()) {
                tx.setEliminated();
                tx.buildCertificateElimination(t);
                isTrivialElimination = true;
            }
        }
        return isTrivialElimination;
    }

    private static class Team {

        private final String name;
        private final int wins, loss, rem, index;
        private boolean isEliminated;
        private ArrayList<String> r = new ArrayList<String>();

        public Team(int index, String name, int wins, int loss, int rem) {
            this.name = name;
            this.index = index;
            this.wins = wins;
            this.loss = loss;
            this.rem = rem;
            isEliminated = false;
        }

        public String getName() {
            return name;
        }

        public int getWins() {
            return wins;
        }

        public int getLoss() {
            return loss;
        }

        public int getRem() {
            return rem;
        }

        public int getIndex() {
            return index;
        }

        public void setEliminated() {
            isEliminated = true;
        }

        public void buildCertificateElimination(String team) {
            r.add(team);
        }

        public boolean getEliminated() {
            return isEliminated;
        }

        public Iterable<String> certiOfElimination() {
            if (isEliminated) return r;
            else return null;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return team_cnt;
    }

    // all teams
    public Iterable<String> teams() {
        return team_mp.keySet();
    }

    private void isTeamvalid(String team) {
        if (!team_mp.containsKey(team))
            throw new IllegalArgumentException("Not a valid team");
    }

    // number of wins for given team
    public int wins(String team) {
        isTeamvalid(team);
        return team_mp.get(team).getWins();
    }

    // number of losses for given team
    public int losses(String team) {
        isTeamvalid(team);
        return team_mp.get(team).getLoss();
    }

    // number of remaining games for given team
    public int remaining(String team) {
        isTeamvalid(team);
        return team_mp.get(team).getRem();
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        isTeamvalid(team1);
        isTeamvalid(team2);
        int i = team_mp.get(team1).getIndex();
        int j = team_mp.get(team2).getIndex();
        return g[i][j];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        isTeamvalid(team);
        return team_mp.get(team).getEliminated();
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        isTeamvalid(team);
        return team_mp.get(team).certiOfElimination();
    }


    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
