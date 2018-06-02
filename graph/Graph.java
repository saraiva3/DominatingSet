
package graph;

import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;


public class Graph
{
	private HashMap<Integer, Node> nodes;	
	private HashSet<Edge> edges;			


	public Graph()	{
		nodes = new HashMap<Integer, Node>();
		edges = new HashSet<Edge>();
	}



	public void addNode(int key){
		if ( nodes.get(key) == null){
			Node newNode = new Node(key);
			nodes.put(key, newNode);
		}
		return;
	}

	public void addEdge(int v1, int v2){	
		Node node1 = nodes.get(v1);
		Node node2 = nodes.get(v2);
		Edge newEdge = new Edge(node1, node2);
           for (Edge edge : node1.getEdges() ) {
            if (edge.isEqualTo(newEdge)) 
             return; 
        }	
        edges.add(newEdge);
        node1.addEdge(newEdge);
        node2.addEdge(newEdge);
        
	}

	public List<Node> getNodes(){ 	
		return new ArrayList<Node>( nodes.values() );	
	}

    public int getNumNodes(){   
    	return nodes.size(); 
    }

    public void resetGraph(){
        for (Node node : nodes.values() )
            node.setAssignment(-1);


        
        return;
    }
}