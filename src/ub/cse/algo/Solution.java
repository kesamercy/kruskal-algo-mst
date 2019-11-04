package ub.cse.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * For use in CSE 331
 */
public class Solution {

    private ArrayList<ArrayList<Integer>> adj_matrix;
    private ArrayList<ArrayList<Integer>> sets = new ArrayList<>();
    int[] output;

    public Solution(ArrayList<ArrayList<Integer>> adj_matrix) {
        this.adj_matrix = adj_matrix;
    }


    class Nodes implements Comparable<Nodes> {
        private int edgeWeight;
        private int parentNode;
        private int childNode;

        public Nodes(int edgeWeight, int parentNode, int childNode) {
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
        public int compareTo(Nodes node) {
            return (this.edgeWeight - node.edgeWeight);
        }
    }

    public int[] outputEdges() {
        /*
         * Output the node ids of the smallest weighted path.
         */
        int sizeOfAdjMatrix = adj_matrix.size();
        PriorityQueue<Nodes> pq = new PriorityQueue<>();
        output = new int[sizeOfAdjMatrix];


        for (int i = 0; i < output.length; i++) {
            output[i] = -1;
        }

        for (int parent = 0; parent < sizeOfAdjMatrix; parent++) {
            for (int child = 0; child < sizeOfAdjMatrix; child++) {
                int edgeWeight = adj_matrix.get(parent).get(child);
                if (edgeWeight != -1) {
                    pq.add(new Nodes(edgeWeight, parent, child));
                }
            }
        }//end for the adj list
        for (int i = 0; i < sizeOfAdjMatrix; i++) {
            ArrayList<Integer> setOfNodes = new ArrayList<>();
            setOfNodes.add(i);
            sets.add(setOfNodes);
        }

        while (!pq.isEmpty()) {
            Nodes removedNode = pq.poll();
            int childNode = removedNode.childNode;
            int parentNode = removedNode.parentNode;

            union(childNode, parentNode);
        }

        return output;
    }

    public void union(int childNode, int parentNode) {
        int setIndexOfChildNode = findSet(childNode);
        int setIndexOfParentNode = findSet(parentNode);

        if (setIndexOfParentNode != setIndexOfChildNode) {
            //CHECK TO SEE IF IT WORKS AS IT SHOULD
            for (int element : sets.get(setIndexOfChildNode)) {
                sets.get(setIndexOfParentNode).add(element);
            }
            //CHECK TO SEE IF IT WORKS AS IT SHOULD!!!!!!
            sets.remove(setIndexOfChildNode);
            if (output[childNode] == -1) {
                output[childNode] = parentNode;
            } else {
                System.out.println("You have a serious bug, the child node seems to have 2 parents" + childNode + " "+ parentNode);
            }
        }


    }

    public int findSet(int node) {
        int setIndex = 0;

        //TEST TO SEE IF IT WORKS AS IT SHOULD
        for (int i = 0; i < sets.size() ; i++) {
            setIndex = sets.get(i).indexOf(node);

            if (setIndex != -1) {
                return i;
            }

        }

        return setIndex;
    }

}
