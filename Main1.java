import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;


public class Main1 {

	public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("test1.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
			    lines.add(line);
            }        

            TilingDino td = new TilingDino();
			List<String> result = td.tileImage(lines);
			
		/*
			String[][] test = td.convert(lines);
			for(int i = 0; i < test.length; i++) {
				for(int j = 0; j < test[1].length; j++) {
					System.out.print(test[i][j] + "  ");
				}
				System.out.println("   ");
			}*/
			
			/*
			Set<String> vertices =  td.createGraph(test).vertexSet();
			Object[] v = vertices.toArray();
			for(int i = 0; i < v.length; i++) {
				System.out.println(v[i]);
			}
			System.out.println(v.length);*/
			/*
			Set<DefaultEdge> edges =  td.createGraph(test).edgeSet();
			Object[] e = edges.toArray();
			for(int i = 0; i < e.length; i++) {
				System.out.println(e[i]);
			}*/
			//System.out.println(e.length);
			
//			for(int i = 0; i < lines.size(); i++) {
//				System.out.println(lines.get(i));
//			}
            for (String l : result)
                System.out.println(l);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred when reading file");
		}
	}

}