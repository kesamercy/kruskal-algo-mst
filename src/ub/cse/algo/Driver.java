package ub.cse.algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args){
        if(args.length != 1){
            System.err.println("Please provide the input filepath as the first argument");
            return;
        }

        //Get the input filename from command line arguments and parse it
        String inputFile = args[0];
        ArrayList<ArrayList<Integer>> adj_list = readFile(inputFile);

        //Create an instance of student class
        Solution student = new Solution(adj_list);

        //Get the tree from the student class
        int[] tree = student.outputEdges();
        //System.out.println("Your Solution:");
        //System.out.println("===========================================================");
        System.out.print("[");
        if(tree != null) {
            for (int i = 0; i < tree.length; i++) {
                System.out.print(tree[i]);
                if (i != tree.length - 1) System.out.print(", ");
            }
        }
        System.out.println("]");
        //System.out.println("===========================================================");
    }

    /**
     * This function will read a file and return the corresponding adjacency matrix.
     *
     * @param inputFile the file to be read
     * @return the adjacency matrix. A value of -1 means the two nodes are not connected.
     */
    private static ArrayList<ArrayList<Integer>> readFile(String inputFile){

        ArrayList<ArrayList<Integer>> adj_matrix = new ArrayList<>();

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

            String line = bufferedReader.readLine();

            while (line != null) {
                String[] split = line.split(" ");
                ArrayList<Integer> l = new ArrayList<>();
                for(String s : split) {
                    l.add(Integer.parseInt(s));
                }
                adj_matrix.add(l);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return adj_matrix;
    }
}
