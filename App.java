
import graph.*;
import algorithms.*;


import java.util.List;

public class App {
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		String filename = "PATH";

		Graph g = new Graph();
		GraphLoader.loadGraph(g, filename);
		
		//System.out.println("The undirected graph has " + g.getNumNodes() + " nodes.");
		//System.out.println("The Undirected graph has " + g.getNumEdges() + " edges.");
		
		
		
	//	---------------------------------- HEURISTICA
		Runtime rt = Runtime.getRuntime();    
	    
		long start_mds;
		long end_mds;
		List<Node> mds;
		double seconds_mds;
		System.out.print("\n++ HEURISTIC ++\n");
	//	for(int i=0 ; i<4;i++) {
		 /*   long prevTotal = 0;
		    long prevFree = rt.freeMemory();

		  
		        long total = rt.totalMemory();
		        long free = rt.freeMemory();
		        if (total != prevTotal || free != prevFree) {
		            long used = total - free;
		            long prevUsed = (prevTotal - prevFree);
		            System.out.println(
		                "#" +
		                ", Total: " + total +
		                ", Used: " + used +
		                ", ∆Used: " + (used - prevUsed) +
		                ", Free: " + free +
		                ", ∆Free: " + (free - prevFree));
		            prevTotal = total;
		            prevFree = free;
		        }*/
			g = DominatingSet.resetGraph(g);
			start_mds = System.currentTimeMillis();
			mds =DominatingSet.heuristicDominatingSet(g);
			end_mds = System.currentTimeMillis();
			seconds_mds = end_mds / 1000.0 - start_mds / 1000.0;
			System.out.println(seconds_mds );
			
	//	}
		///System.out.println("\nThe program found a minimum dominating set in " + seconds_mds + " seconds.");
		System.out.println("The minimum dominating set found contains " + mds.size() + " nodes.");
		System.out.print("The minimum dominating set is: ");
		System.out.println(mds);
		System.out.println("++ HEURISTIC END ++");
		//---------------------------------- HEURISTICA	
		
		
	//	---------------------------------- EXATO	
		System.out.print("\n++ Fomin ++ \n");
		//for(int i=0 ; i<4;i++) {
			/*long prevTotal = 0;
		    long prevFree = rt.freeMemory();

		  
		        long total = rt.totalMemory();
		        long free = rt.freeMemory();
		        if (total != prevTotal || free != prevFree) {
		            long used = total - free;
		            long prevUsed = (prevTotal - prevFree);
		            System.out.println(
		                "#" +
		                ", Total: " + total +
		                ", Used: " + used +
		                ", ∆Used: " + (used - prevUsed) +
		                ", Free: " + free +
		                ", ∆Free: " + (free - prevFree));
		            prevTotal = total;
		            prevFree = free;
		 		}*/
		g = DominatingSet.resetGraph(g);
		start_mds = System.currentTimeMillis();
		mds = DominatingSet.findMinimumDominatingSet(g);
		end_mds = System.currentTimeMillis();
		seconds_mds = end_mds / 1000.0 - start_mds / 1000.0;
		System.out.println(seconds_mds );
	//	}
		//
		
		
		System.out.println("\nThe program found a minimum dominating set in " + seconds_mds + " seconds.");
		System.out.println("The minimum dominating set found contains " + mds.size() + " nodes.");
		System.out.print("The minimum dominating set is: ");
		System.out.println(mds);
		System.out.println("++ Fomin END ++");
		
	//	---------------------------------- EXATO
	}
	
}