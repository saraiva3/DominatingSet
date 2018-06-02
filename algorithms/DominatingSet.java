package algorithms;

import graph.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DominatingSet{   
    static int i = 0;
	
    public static List<Node> findMinimumDominatingSet(Graph graph){   
        List<Node> mds = recursiveSearchTree(graph);
        return mds;
    }
	
    public static Graph resetGraph(Graph graph) {
	    for(int i =0; i<graph.getNumNodes() ; i++) {
    		graph.getNodes().get(i).setDominated(false,graph.getNodes().get(i));
    		graph.getNodes().get(i).setDominator(false);
    	}
    	return graph;
    }    
    
    // HEURISTIC
    public static List<Node> heuristicDominatingSet(Graph graph) {   	
    	List<Node> nodes=  new ArrayList<Node>(graph.getNodes());    	
    	Collections.sort(nodes, new Comparator<Node>() {
    	    @Override
    	    public int compare(Node o1, Node o2) {
    	    	if(o1.getNeighbors().size() > o2.getNeighbors().size()) {
    				return 1;
    			}else if(o1.getNeighbors().size() < o2.getNeighbors().size()) {
    				return -1;
    			}else {
    				return 0;
    			}
    	    }
    	});
    	
    	Collections.reverse(nodes);
    	int numberOfRounds = nodes.size();
    	List<Node> dominantSet = new ArrayList<Node>();
    	int index =0;
    	for(int i=0; i< numberOfRounds; i++)	{
        	int domCheck = neighOnDominant(nodes.get(i));    
    		if(!dominantSet.contains(nodes.get(i)) && domCheck == 2) {
    			nodes.get(i).setDominator(true);
    			nodes.get(i).setDominated(true, nodes.get(i));
    			
    			dominantSet.add(nodes.get(i));
    			dominateNeighs(nodes.get(i));
    		}else if(!dominantSet.contains(nodes.get(i)) && domCheck == 1) {
    			
    			List<Node> neighs = new ArrayList<Node>(nodes.get(i).getNeighbors());
    				
    			for(int j=0; j < neighs.size(); j++) { 
    				if(!neighs.get(j).isDominated())
    					index = nodes.indexOf(neighs.get(j));
        		}
    			nodes.get(index).setDominator(true);
    			nodes.get(index).setDominated(true, nodes.get(index));
    			
    			dominantSet.add(nodes.get(index));	
    			dominateNeighs(nodes.get(index));
    		} else if (!dominantSet.contains(nodes.get(i)) && !nodes.get(i).isDominated() && domCheck ==0) {
    			nodes.get(i).setDominator(true);
    			nodes.get(i).setDominated(true, nodes.get(i));
    			
    			dominantSet.add(nodes.get(i));	
    		}	 
    	}

    	for(int i=0; i< numberOfRounds; i++){
    		List<Node> dominatorsList = new ArrayList<Node>(nodes.get(i).getDominators());

    		if(dominatorsList.size() >=2) {
    			List<Integer> flags = new ArrayList<Integer>();
    			
    			for(int j=0; j< dominatorsList.size(); j++) {
    				List<Node> dominatorNeigh = new ArrayList<Node>(dominatorsList.get(j).getNeighbors());
    				int number = 0;
    				for(int z = 0; z<dominatorNeigh.size(); z++) {
    					if(dominatorNeigh.get(z).getDominators().size() > 1)     					
    						number++;
    					else if(dominatorNeigh.get(z).getDominators().size() == 1 && dominatorNeigh.get(z).getNeighbors().size() == 1)
    						number++;    					
    				}    			
    				if(number == dominatorNeigh.size())    					
    				    flags.add(1);
    				else 
    				    flags.add(0);    			     			    	
    			}
    			if(!flags.contains(0)) {    			
    				nodes.get(i).setDominator(true);
    				dominantSet.add(nodes.get(i));
        			for(int j=0; j< dominatorsList.size(); j++) {
        				dominantSet.remove(dominatorsList.get(j));
        				dominatorsList.get(j).setDominator(false);
        				dominatorsList.get(j).setDominated(true,  nodes.get(i));
        			}    				
    			}
    	 	}    		    
    	}
    	for(int i = 0; i < dominantSet.size();i++) {
    		List<Node> neighs = new ArrayList<Node>(dominantSet.get(i).getNeighbors());
    		 
    		for(int j =0; j < neighs.size(); j++) {
    			if(dominantSet.contains(neighs.get(j))) {
    				
    				if(neighs.get(j).getNeighbors().size() == 1) {
    					dominantSet.remove(dominantSet.indexOf(neighs.get(j)));
    					neighs.get(j).setDominator(false);
    				}else if(dominantSet.get(i).getNeighbors().size() == 1 ) {
    					dominantSet.remove(dominantSet.indexOf(dominantSet.get(i)));
    					dominantSet.get(i).setDominator(false);
    				}
    			}
    		}   		
    	}
    	return dominantSet;    	
    }
    
    public static void dominateNeighs(Node current) {  
    	List<Node> neighs = new ArrayList<Node>(current.getNeighbors());    
    	for(int i=0; i < neighs.size(); i++) {    		
    		if(!neighs.get(i).isDominator()){
    		  neighs.get(i).setDominator(false);
    		  neighs.get(i).setDominated(true, current);
    	  	}    		 
    }   
    
    public static int neighOnDominant(Node current) {	  
	    List<Node> neighs = new ArrayList<Node>(current.getNeighbors());
	    int flag = 0;	  
	    for(int i=0; i < neighs.size(); i++) {
			if(!neighs.get(i).isDominated())
				flag++;		    
			if(flag >= 2)
				return 2;		    
			else if(flag == 1) 
				 return 1;				  
			else 
				 return 0;
			
    }
	
    private static List<Node> getNumUnassignedNeighbors(Node node)  {
      List<Node> unAssigned = new ArrayList<Node>();
      for (Node neighbor : node.getNeighbors())
          if (neighbor.isAssigned() == -1)
              unAssigned.add(neighbor);
      return unAssigned;
    }
    /**
     * Fomin, et. al. algorithm simplified 
     */
    private static List<Node> recursiveSearchTree(Graph graph){   
       
        Node node = null;
        Node neigh1 = null;
        Node neigh2 = null;
        Node aux_vertex  = null;

        List<Node> wResult  = null;
        List<Node> neigh1Result = null;
        List<Node> neigh2Result = null;
        List<Node> vResult  = null;
        
        int wSize  = Integer.MAX_VALUE;
        int neigh1Size = Integer.MAX_VALUE;
        int neigh2Size = Integer.MAX_VALUE;
        int vSize  = Integer.MAX_VALUE;
       
         for (Node n : graph.getNodes() ){
            if (n.isAssigned() == -1){
                List<Node> unassigned = getNumUnassignedNeighbors(n);
                if (unassigned.size() == 1){   
                    node = unassigned.get(0);
                    aux_vertex = n;
                }
                else if (unassigned.size() == 2){
                    neigh1 = unassigned.get(0);
                    neigh2 = unassigned.get(1);
                    aux_vertex = n;
                }
            }
        }
        if (node==null && neigh1==null && neigh2==null && aux_vertex==null){   
            int numNodesAssigned = 0;
            for (Node nodes : graph.getNodes())
                if (nodes.isAssigned() == 1)  {
                 numNodesAssigned++; 
             }     
            return bruteForce(graph, graph.getNumNodes() - numNodesAssigned, numNodesAssigned);
        }  

        if (node != null){
            node.setAssignment(1);
            aux_vertex.setAssignment(0);
            wResult = recursiveSearchTree(graph);
            wSize = wResult.size();
            node.setAssignment(-1);
            aux_vertex.setAssignment(-1);
        }     
        if (neigh1 != null){             
            neigh1.setAssignment(1);
            neigh2.setAssignment(-1);
            aux_vertex.setAssignment(0);
            neigh1Result = recursiveSearchTree(graph);
            neigh1Size = neigh1Result.size();            
            neigh1.setAssignment(0);
            neigh2.setAssignment(0);
            aux_vertex.setAssignment(1);
            vResult = recursiveSearchTree(graph);
            vSize = vResult.size();
            neigh1.setAssignment(0);
            neigh2.setAssignment(1);
            aux_vertex.setAssignment(0);
            neigh2Result = recursiveSearchTree(graph);
            neigh2Size = neigh2Result.size();            
            neigh1.setAssignment(-1);
            neigh2.setAssignment(-1);
            aux_vertex.setAssignment(-1);
        } 
        int minNum = Math.min( wSize, Math.min(neigh1Size, Math.min(neigh2Size, vSize)));
        if (minNum == wSize ) 
           return wResult;
        else if (minNum == neigh1Size)
            return neigh1Result; 
        else if (minNum == neigh2Size)
            return neigh2Result; 
        else 
            return vResult;  
    }


    private static List<Node> bruteForce(Graph graph, int sizeOfSubgraph, int numAlreadyAssigned){   
        
        Node u = null; 
        int totalNumAssignedToOne = 0;
         for (Node node : graph.getNodes() ){ 

            if (node.isAssigned() == 0){
                boolean allNeighborsSetToZero = true;
                
                for (Node neighbor : node.getNeighbors() ) {   
                    if (neighbor.isAssigned() != 0)
                        allNeighborsSetToZero = false;
                }
                
                if (allNeighborsSetToZero){
                    return (ArrayList<Node>) graph.getNodes();
                }  
            }
            else if (node.isAssigned() == 1)
                totalNumAssignedToOne++;
            else
                u = node;       
        }

        if (u == null){   
            List<Node> ds = new ArrayList<Node>();
            for (Node node : graph.getNodes()){
                if (node.isAssigned() == 1)
                    ds.add(node);
            }
            return ds;
        }
        if (totalNumAssignedToOne - numAlreadyAssigned >= 3*sizeOfSubgraph/8) {   
        	return (ArrayList<Node>) graph.getNodes();            
        }  

        u.setAssignment(0);
        List<Node> u0 = bruteForce(graph, sizeOfSubgraph, numAlreadyAssigned);
        u.setAssignment(1);
        List<Node> neigh1 = bruteForce(graph, sizeOfSubgraph, numAlreadyAssigned);
        u.setAssignment(-1);

        if ( u0.size() < neigh1.size() )   {
        		return u0; 
     	}
        else
            return neigh1; 
    }


}
