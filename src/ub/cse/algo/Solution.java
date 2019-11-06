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
            return "Node{" +
                    "weight='" + edgeWeight + '\'' +
                    ", parentnode=" + parentNode +
                    ", childNode=" + childNode;
        }

        @Override
        public int compareTo(Nodes node) {
            return (this.edgeWeight - node.edgeWeight);
        }
    }

    class Node {
        int rank;
        int value;
    }
    Node[] tempOutput;

    public int[] outputEdges() {
        /*
         * Output the node ids of the smallest weighted path.
         */
        int sizeOfAdjMatrix = adj_matrix.size();
        PriorityQueue<Nodes> pq = new PriorityQueue<>();
        int[] output = new int[sizeOfAdjMatrix];
        int nodeWithmaxRank = 0;
        //used it as a temp array to compute the values for the class type Node that represnt the nodes in the graph
        tempOutput = new Node[sizeOfAdjMatrix];

        System.out.println("the original size " + adj_matrix);

        //find the nodes from the adj matirx and the weights affiliated with each node. store them in a priority queue to be sorted by the weights of the edges
        for (int parent = 0; parent < sizeOfAdjMatrix; parent++) {
            for (int child = 0; child < sizeOfAdjMatrix; child++) {
                int edgeWeight = adj_matrix.get(parent).get(child);
                if (edgeWeight != -1) {
                    pq.add(new Nodes(edgeWeight, parent, child));
                }
            }
        }//end for the adj list

        System.out.println(pq);

        //populate the tempOutput array with the nodes that exist in the graph... from {0,....n}
        //make them of the class node so that you can store a value and rank for each node.
        //rank is to be used when determining the parent child relationship in the union method
        for (int i = 0; i <tempOutput.length; i++) {
            Node newNode = new Node();
            newNode.rank = 0;
            newNode.value = i;
            tempOutput[i] = newNode;
        }

        //iterate through the pq and add edges to the MST based on their weight. the edge with the least weight comes first
        while (!pq.isEmpty()) {
            //remove every edge with the nodes affilated with that edge
            //the parent child relationship defined here is based on the adj matrix format. it has no meaning to the parent child relationship
            //..for the MST defined at the end of the function
            Nodes removedNode = pq.poll();
            int childNode = removedNode.childNode;
            int parentNode = removedNode.parentNode;

            //for each node added, create it based on the Node class time and assign a rank to it.
            Node nodeOne = new Node();
            Node nodeTwo = new Node();
            nodeOne.rank = 0;
            nodeTwo.rank = 0;
            nodeOne.value = childNode;
            nodeTwo.value = parentNode;

            //pass the nodes to the union function to detetrmine if they will be added to the MST or not.
            union(nodeOne, nodeTwo);
        }

        //when all the edges have been added to the MST, determine which node had the highest rank and make that the root node
        //highest rank means that all the nodes are pointing to it as the root value
        //also compy all the values of the temp array to the output array
        for (int i = 0; i < tempOutput.length ; i++) {
            output[i] = tempOutput[i].value;

            if (tempOutput[i].rank > nodeWithmaxRank) {
                nodeWithmaxRank = i;
            }
        }

        //find the root node of the value with the highest rank. the findParent method using recurrsion finds which index is the root value
        //if the index matches the value of the number, then that index value is the root node.
        Node rootNode = new Node();
        rootNode.value = nodeWithmaxRank;
        rootNode.rank = 0;
        rootNode = findParent(rootNode);

        //make the value of that index the root node value
        output[rootNode.value] = -1;

        System.out.println("the size of the output " + output.length);

        return output;
    }

    public void union(Node nodeOne, Node nodeTwo) {
        //find the parent nodes of the nodes of the edges
        Node parentofNodeOne = findParent(nodeOne);
        Node parentofNodeTwo = findParent(nodeTwo);

        //if the parents are different, then there is no cycle, they can be added to the MST
        if (parentofNodeOne.value != parentofNodeTwo.value) {
            //the parent child relationship is determined by the node with the highest rank.
            if (parentofNodeOne.rank >= parentofNodeTwo.rank) {
                ++parentofNodeOne.rank;
                //update the rank value of the parent of node 1
                tempOutput[parentofNodeOne.value] = parentofNodeOne;
                //assign the parent of node 1 as the root of parent of node 2
                tempOutput[parentofNodeTwo.value] = parentofNodeOne;
            }
            else if(parentofNodeOne.rank < parentofNodeTwo.rank){
                //the same applies for this function based on the comments of the above if function...just in opposite ordder
                ++parentofNodeTwo.rank;
                tempOutput[parentofNodeTwo.value] = parentofNodeTwo;
                tempOutput[parentofNodeOne.value] = parentofNodeTwo;
            } else{
                System.out.println("Some bullshit happened, temparray isnt't working as it should");
            }

        }
    }

    public Node findParent(Node node) {
        //recursively finds the root node of each node passed.
        //if the node is value matches the index, then we have found the parent of the node value
        int index = node.value;
        if (tempOutput[index].value == node.value) {
            return node;
        }
        return findParent(tempOutput[index]);
    }

}
