import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.jgrapht.alg.*;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.flow.MaximumFlowAlgorithmBase;
//import org.jgrapht.alg.matching.EdmondsMaximumCardinalityMatching;
import org.jgrapht.alg.matching.MaximumWeightBipartiteMatching;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.*;
import org.jgrapht.alg.util.extension.*;


/*
 * Uttam Rao (ur6yr)
 * I thought of this assignment like the checker board question. I first converted half of the #'s to o's
 * appropriately to mirror a checker board style. Then I created a graph using JgraphT with each of the #'s
 * and o's as nodes. Then I made an edge from each # to an adjacent o. I made a source node and made
 * an edge from it to each of the #'s and a sink node which had an edge coming in from all the o's. Then
 * I ran a max flow on it and got the edges that are part of the max flow and got rid of the edges
 * coming out of the source node and the edges going into the sink node. If the number of remaining edges
 * was equal to half of the total number of nodes in the graph (not including source and sink) then
 * it is possible to tile the image and I returned a list of the remaining edges. If the number of 
 * remaining edges was not exactly half of the total number of nodes (not including source and sink) I 
 * returned a list containing the String "impossible."
 * 
 * Side note: I initially implemented it using a directed graph and the method above but then I started
 * looking to see if there was a better function to use from JgraphT to solve it. JgraphT has a matching
 * algorithm (calls max flow inside it) which, when I looked it up, does exactly what I did above but instead of setting the source
 * and sink nodes myself all I have to do is give it an undirected graph and specify which nodes are #'s
 * and o's and it does the above process and gives me the same result. This sped up my program very
 * slightly so this is the implementation I'm submitting. I've left the other parts with 
 * the original max flow implementation in comments. The homework description didn't say we had to do
 * max flow, it just said JgraphT was added to the class path. And this homework seems like it's about 
 * setting up the graph and know what's happening and how max flow solves it, not implementing max flow,
 * that the extra credit.
 * 
 * I looked up JGraphT documentation and some string manipulation regex things
 */

public class TilingDino1 {
	
	private Set<String> dots = new HashSet<>();
	private Set<String> hashtags = new HashSet<>();
	
	public String[][] convert(List<String> fileLines){
		String[][] converted = new String[fileLines.size()][fileLines.get(0).length()];
		for(int i = 0; i < fileLines.size(); i++) 
		{
			for(int j = 0; j < fileLines.get(0).length(); j++) 
			{
				if(fileLines.get(i).charAt(j) == '.') {
					converted[i][j] = ".";
					
				}
				else if(fileLines.get(i).charAt(j) == '#') {
					converted[i][j] = "#";
				}
				
			}
		}
		
		return converted;
	}
	
	
	public Graph<String, DefaultEdge> createGraph(String[][] lines) {
		// if using max flow then need this to be directed
		Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		
		
		
		
		for(int i = 0; i < lines.length; i++) 
		{
			
			for(int j = 0; j < lines[0].length; j++) 
			{
				
				if(lines[i][j].equals("#"))
				{
					hashtags.add(Integer.toString(j) + " " + Integer.toString(i));
					graph.addVertex(Integer.toString(j) + " " + Integer.toString(i));
				}
//				else if (lines[i][j].equals("."))
//				{
//					dots.add(Integer.toString(j) + " " + Integer.toString(i));
//					graph.addVertex(Integer.toString(j) + " " + Integer.toString(i));
//				}
			}
		}
		
//		
//		for(int i = 0; i < lines.length; i++) 
//		{
//			for(int j = 0; j < lines[0].length; j++) 
//			{
//				
//				if (i+1 < lines.length)
//				{
//					if(lines[i][j].equals("#") && lines[i+1][j].equals(".")) 
//					{
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i+1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					
//				}
//				
//			
//				
//				if (j+1 < lines[0].length)
//				{
//					if(lines[i][j].equals("#") && lines[i][j+1].equals(".")) 
//					{
//						graph.addEdge(Integer.toString(j+1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					
//				}
//				
//			}
//	
//		}	
//		
		
		for(int i = 0; i < lines.length; i++) 
		{
			for(int j = 0; j < lines[0].length; j++) 
			{
				
				if (i+1 < lines.length)
				{
					if(lines[i][j].equals("#") && lines[i+1][j].equals("#")) 
					{
						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i+1));
					}
				}
				
			
				if (j+1 < lines[0].length)
				{
					if(lines[i][j].equals("#") && lines[i][j+1].equals("#")) 
					{
						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i), Integer.toString(j+1) + " " + Integer.toString(i));
					}
					
				}
		
				
				
				
			}
						
		}
		
		
				
//	
		
		
//		for(int i = 0; i < lines.length; i++) 
//		{
//			for(int j = 0; j < lines[0].length; j++) 
//			{
//				
//				if(i == 0 && j == 0) {
//					if(lines[i][j].equals("#") && lines[i+1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i+1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j+1].equals(".")) {
//						graph.addEdge(Integer.toString(j+1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//				}
//				else if(i == 0 && j == lines[0].length-1) {
//					if(lines[i][j].equals("#") && lines[i+1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i+1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j-1].equals(".")) {
//						graph.addEdge(Integer.toString(j-1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//				}
//				else if(i == lines.length-1 && j == 0) {
//					if(lines[i][j].equals("#") && lines[i-1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i-1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j+1].equals(".")) {
//						graph.addEdge(Integer.toString(j+1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//				}
//				else if(i == lines.length-1 && j == lines[0].length-1) {
//					if(lines[i][j].equals("#") && lines[i-1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i-1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j-1].equals(".")) {
//						graph.addEdge(Integer.toString(j-1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i) );
//					}
//				}
//				else if(i == 0 && j !=0 && j != lines[0].length-1) {
//					if(lines[i][j].equals("#") && lines[i+1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i+1), Integer.toString(j) + " " + Integer.toString(i) );
//					}
//					if(lines[i][j].equals("#") && lines[i][j+1].equals(".")) {
//						graph.addEdge(Integer.toString(j+1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j-1].equals(".")) {
//						graph.addEdge(Integer.toString(j-1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//				}
//				else if(i == lines.length - 1 && j !=0 && j != lines[0].length-1) {
//					if(lines[i][j].equals("#") && lines[i-1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i-1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j+1].equals(".")) {
//						graph.addEdge(Integer.toString(j+1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j-1].equals(".")) {
//						graph.addEdge(Integer.toString(j-1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//				}
//				else if(j == 0 && i != 0 && i != lines.length-1) {
//					if(lines[i][j].equals("#") && lines[i+1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i+1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i-1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i-1), Integer.toString(j) + " " + Integer.toString(i));
//				
//					if(lines[i][j].equals("#") && lines[i][j+1].equals(".")) {
//						graph.addEdge(Integer.toString(j+1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}	
//				}
//				else if(j == lines[0].length-1 && i != 0 && i != lines.length-1) {
//					if(lines[i][j].equals("#") && lines[i+1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i+1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i-1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i-1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j-1].equals(".")) {
//						graph.addEdge(Integer.toString(j-1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//				}else {
//					if(lines[i][j].equals("#") && lines[i+1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i+1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i-1][j].equals(".")) {
//						graph.addEdge(Integer.toString(j) + " " + Integer.toString(i-1), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j+1].equals(".")) {
//						graph.addEdge(Integer.toString(j+1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//					if(lines[i][j].equals("#") && lines[i][j-1].equals(".")) {
//						graph.addEdge(Integer.toString(j-1) + " " + Integer.toString(i), Integer.toString(j) + " " + Integer.toString(i));
//					}
//				}
//			}
//		}
//	}
		
		
		
		// If using the max flow explicitly then need this!
		
		graph.addVertex("source");
		graph.addVertex("sink");
		for(int i = 0; i < lines.length; i++) 
		{
			for(int j = 0; j < lines[0].length; j++)
			{
				if(lines[i][j].equals("#")) {
					graph.addEdge("source", Integer.toString(j) + " " + Integer.toString(i));
				}
				if(lines[i][j].equals("#")) {
					graph.addEdge(Integer.toString(j) + " " + Integer.toString(i),"sink");
				}
			}
		}
		
		
		return graph;
	}

    public List<String> tileImage(List<String> fileLines) {
    	
    	String[][] checkered = convert(fileLines);
    	Graph<String, DefaultEdge> graph = createGraph(checkered);
    	
    	//Would need to comment this part out if explicitly using max flow
    	/*
    	Set<String> pounds = new HashSet<>();
    	Set<String> os = new HashSet<>();
    	Set<String> vertices =  graph.vertexSet();
    	String[] a = new String[vertices.size()];
    	String[] v = vertices.toArray(a);
    	for(int i = 0; i< v.length; i++) {
    		int x = splitStr(v[i])[0];
    		int y = splitStr(v[i])[1];
    		if(checkered[y][x].equals("#")) {
    			pounds.add(v[i]);
    		}
    		if(checkered[y][x].equals("o")) {
    			os.add(v[i]);
    		}
    	}
    	*/
    	
    	// comment out ends here
    	
    	
    	EdmondsKarpMFImpl max = new EdmondsKarpMFImpl<>(graph);
    	
    	Set<DefaultEdge> done = max.getMaximumFlow("source", "sink").getFlowMap().keySet();
    	
    	//MaximumWeightBipartiteMatching other = new MaximumWeightBipartiteMatching<>(graph, dots, hashtags);
    	
    	System.out.println(max.calculateMaximumFlow("source", "sink"));
    	
    	Object[] d = done.toArray();

    	String[] r = new String[d.length];
		for(int i = 0; i < d.length; i++) {
			r[i] = d[i].toString();
		}
		
		ArrayList<String> new1 = new ArrayList<String>();
		
		for(int i = 0; i < r.length; i++) 
		{
			if (!(r[i].contains("source") || r[i].contains("sink")))
			{
				new1.add(r[i]);
				System.out.println(r[i]);
				
			}
			
		}
		
		//Then we get rid of the edges with source or sink, I deleted this part but it's easy to do
    	
    	//MaximumWeightBipartiteMatching match = new MaximumWeightBipartiteMatching(graph);
    	
    	//Set<DefaultEdge> done = match.getMatching().getEdges();
		/*
    	Object[] d = done.toArray();
    	String[] r = new String[d.length];
		for(int i = 0; i < d.length; i++) {
			r[i] = d[i].toString();
		}
		*/

		
		
		boolean haspound = false;
		for(int i = 0; i < checkered.length; i++) 
		{
			if(haspound == true) 
			{
				break;
			}
			for(int j = 0; j < checkered[0].length; j++) 
			{
				if(checkered[i][j].equals("#")) 
				{
					haspound = true;
				}
			}
		}
    	
		ArrayList<String> result = new ArrayList<>();
        System.out.println();
        System.out.println(new1.size());
        System.out.println(graph.vertexSet().size()/2);
        
        if(!haspound) 
        {
        	result.add("impossible");
        	return result;
        }
        
        else if(new1.size()/2 == graph.vertexSet().size()/2 - 1) 
        {
			for(int i = 0; i < new1.size(); i++) 
			{
				result.add(new1.get(i));
			}
		}
        else 
		{
			result.add("impossible");
		}
        return result;
        
    }
    
    public int[] splitStr(String s) {
    	String[] splitted = s.split(" ");
    	int[] vals = new int[2];
    	vals[0] = Integer.parseInt(splitted[0]);
    	vals[1] = Integer.parseInt(splitted[1]);
    	return vals;
    }
    
    public String format(String s) {
    	String f = s.replaceAll("\\:","");
    	f = f.replaceAll("\\(","");
    	f = f.replaceAll("\\)","");
    	f = f.replaceAll("\\s+"," ");
    	return f;
    }
}