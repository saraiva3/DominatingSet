
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GraphLoader{

    public static void loadGraph(graph.Graph g, String filename) {
        
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }      
        while (sc.hasNext()) 
        {
        	if (sc.hasNextInt())
        	{
	            int v1 = sc.nextInt();
	            int v2 = sc.nextInt();
	            if (!seen.contains(v1)) {
	                g.addNode(v1);
	                seen.add(v1);
	            }
	            if (!seen.contains(v2)) {
	                g.addNode(v2);
	                seen.add(v2);
	            }
	            g.addEdge(v1, v2);
	        }
	        else
	        {
	        	sc.nextLine();
	        }
        }
        
        sc.close();
    }
}
