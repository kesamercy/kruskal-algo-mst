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
        @Override
        public String toString() {
            return "Union{" +
                    "weight='" + edgeWeight + '\'' +
                    ", parentnode=" + parentNode +
                    ", childNode=" + childNode;
        }

        @Override
        public int compareTo(Union edge) {
            return (this.edgeWeight - edge.edgeWeight);
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
        int rootnode = 0;
        int initialUnion = 0;

        for (int i = 0; i < output.length; i++) {
            output[i] = -1;
        }

        for (int parent = 0; parent < sizeOfAdjMatrix; parent++) {
            for (int child = 0; child < sizeOfAdjMatrix; child++) {
                int edgeWeight = adj_matrix.get(parent).get(child);
                if (edgeWeight != -1) {
                    pq.add(new Union(edgeWeight, parent, child));
                }
            }
        }//end for the adj list


        while (!pq.isEmpty()){
            Union nodeComponent = pq.poll();
            int nodeOne = nodeComponent.parentNode;
            int nodeTwo = nodeComponent.childNode;
            //since we know that 0 will always be the root node... then we can say..

            if (output[nodeOne] == -1 && output[nodeTwo] == -1) {
                ++initialUnion;
                //this means that they are parents of themselves.... and they belong to 2 diff. sets.
                //define their parent and child relationship
                output[nodeTwo] = nodeOne;
                output[nodeOne] = output[rootnode];
                //root node changes when there is a union. if union, increment the counter
                if (initialUnion == 1) {
                    output[rootnode] = 0;
                    output[rootnode] = output[rootnode] - 2;
                }
            }
            else {
                //find the parents of node one and node 2, do this so we can find if there is a union
                int node1parent = findParent(nodeOne, output);
                int node2parent = findParent(nodeTwo, output);

                if ( node1parent != node2parent){
                    //a union can happen in this instance
                    //find the value at the index of the parent node
                    int rootvalue = output[node1parent];
                    int otherRootValue = output[node2parent];

                    if (rootvalue <= otherRootValue) {
                        output[node2parent] = node1parent;

                    }
                    else {
                        output[node1parent] = node2parent;
                    }

                    output[rootnode] = output[rootnode] - 2;

                }

            }

        }

        output[rootnode] = -1;
        return output;
    }

    public int findParent(int node, int[] output){

        if (output[node] <= 0) {
            return node;
        }

        return findParent(output[node], output);
    }
}
