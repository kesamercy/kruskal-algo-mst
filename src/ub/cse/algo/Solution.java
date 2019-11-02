package ub.cse.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * For use in CSE 331
 */
public class Solution {

    private ArrayList<ArrayList<Integer>> adj_matrix;

    public Solution(ArrayList<ArrayList<Integer>> adj_matrix) {
        this.adj_matrix = adj_matrix;
    }

    class Union implements Comparable<Union> {
        private int edgeWeight;
        private int parentNode;
        private int childNode;

        public Union(int edgeWeight, int parentNode, int childNode) {
            this.edgeWeight = edgeWeight;
            this.parentNode = parentNode;
            this.childNode = childNode;
        }
        public int getWeight() {
            return edgeWeight;
        }

        public void setWeight(String name) {
            this.edgeWeight = edgeWeight;
        }

        @Override
        public String toString() {
            return "Union{" +
                    "weight='" + edgeWeight + '\'' +
                    ", parentnode=" + parentNode +
                    ", childNode=" + childNode;
        }

        @Override
        public int compareTo(Union edge) {
            if (this.getWeight() > this.getWeight()) {
                return 1;
            } else if (this.getWeight() < edge.getWeight()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public int[] outputEdges() {
        /*
         * Output the node ids of the smallest weighted path.
         */
        int sizeOfAdjMatrix = adj_matrix.size();
        boolean[] visitedNodes = new boolean[sizeOfAdjMatrix];

        int[] output = new int[sizeOfAdjMatrix];
        PriorityQueue<Union> pq = new PriorityQueue<>();

        for (int i = 0; i < output.length; i++) {
            output[i] = Integer.MAX_VALUE;
        }

        for (int parent = 0; parent < sizeOfAdjMatrix; parent++) {
            for (int child = 0; child < sizeOfAdjMatrix; child++) {
                int edgeWeight = adj_matrix.get(parent).get(child);
                if (edgeWeight != -1) {
                    pq.add(new Union(edgeWeight, parent, child));
                }
            }
        }//end for the adj list

        System.out.println(pq);
        System.exit(-1);
        while (!pq.isEmpty()){
            int parent = pq.poll().parentNode;
            int child = pq.poll().childNode;

            //check if the parent is viisted
            if (visitedNodes[parent] == true) {
                //check if the child is visited
                if (visitedNodes[child] == true) {
                    //do nothing
                }
                else {
                    visitedNodes[child] = true;
                    output[child] = parent;
                }
            }
            else {
                if (visitedNodes[child] == false) {
                    visitedNodes[parent] = true;
                    visitedNodes[child] = true;
                    output[child] = parent;
                }
                else {
                    visitedNodes[parent] = true;
                    output[child] = parent;
                }
            }

        }

        output[0] = -1;


        return output;
    }
}
