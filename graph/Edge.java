package graph;

public class Edge{
	private Node v1;
	private Node v2;

	public Edge (Node node1, Node node2){		
		this.v1 = node1;
		this.v2 = node2;
		
	}

	public Node getOtherNode(Node node){
		if (node.equals(v1)) 
			return this.v2;
		else if (node.equals(v2))
			return this.v1;
		return node;
	}

	public Node getNodev1(){ 
		return this.v1; 
	}
	public Node getNodev2(){ 
		return this.v2; 
	}

	public boolean isEqualTo(Edge edge)	{
		if ( this.v1.equals(edge.getNodev1()) && this.v2.equals(edge.getNodev2()) )
			return true;
		return false;
	}


}