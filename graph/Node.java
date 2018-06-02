
package graph;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Node {
	

	private HashSet<Edge> nodesEdges;
	private int assignment;
	private boolean dominated;
	private boolean dominator;
	private List<Node> whoDominates;

	public Node(int name){
	
		this.nodesEdges = new HashSet<Edge>();
		this.assignment = -1;
		this.dominated = false;
		this.whoDominates = new ArrayList<Node>();
	}


	public List<Node> getNeighbors(){
		List<Node> neighbors = new ArrayList<Node>();
		for (Edge edge : nodesEdges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}


	public int numNeighbors() { 
		return nodesEdges.size(); 
	}

	public int isAssigned() { 
		return this.assignment; 
	}

	public void setAssignment(int newAssignment){  		
		this.assignment = newAssignment;	
	}
	public HashSet<Edge> getEdges() { return this.nodesEdges; }

	public void addEdge(Edge newEdge){ 
		if ( !nodesEdges.contains(newEdge) )
			nodesEdges.add(newEdge); 
	}
	
	public boolean isDominated(){		
		return this.dominated;
	}
	public boolean isDominator() {
		return this.dominator;
	}
	public List<Node> getDominators(){
		return this.whoDominates;
	}
	public void setDominated(boolean dom, Node dominator) {
		if(!this.isDominator())
			whoDominates.add(dominator);		
		this.dominated = dom;
	}	

	public void setDominator(boolean dom) {
		this.dominator = dom;
	}	
}