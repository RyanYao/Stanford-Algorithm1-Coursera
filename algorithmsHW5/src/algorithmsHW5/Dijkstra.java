package algorithmsHW5;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dijkstra {
	public static void main(String[] args){
		ArrayList<Vertex> vertices = loadGraphFromFile();
		HashSet<Integer> result = new HashSet<Integer>();
		result.add(7);
		result.add(37);
		result.add(59);
		result.add(82);
		result.add(99);
		result.add(115);
		result.add(133);
		result.add(165);
		result.add(188);
		result.add(197);
		
		minDistance(vertices.get(0), vertices);
		for(Vertex v : vertices){
			if(result.contains(v.id)){
				System.out.print(Integer.toString(v.distance)+",");
			}
		}
	}
	public static void minDistance(Vertex source, ArrayList<Vertex> vertices){
		source.distance = 0;
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		queue.add(source);
		
		while(!queue.isEmpty()){
			Vertex v = queue.poll();
			v.setExplored(true);
			for(Edge e : v.getEdges()){
				int mindis = v.distance + e.length;
				if(mindis < vertices.get(e.DestVertexID-1).distance){
					queue.remove(vertices.get(e.DestVertexID-1));
					vertices.get(e.DestVertexID-1).distance = mindis;
					queue.add(vertices.get(e.DestVertexID-1));
				}
			}
		}
	}
	
	private static ArrayList<Vertex> loadGraphFromFile()
    {
		ArrayList<Vertex> graph = new ArrayList<Vertex>(); 
        FileInputStream fstream = null;
        try 
        {
            fstream = new FileInputStream("dijkstraData.txt");
            
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            //FIRST READ THE EDGES
            String line;
            while ((line = br.readLine()) != null)
            {
                // process the line
                StringTokenizer tokens = new StringTokenizer(line);
                int id = Integer.parseInt(tokens.nextToken());
            	Vertex v = new Vertex(id, 100000);
                while(tokens.hasMoreTokens()){
                	String edgeStr = tokens.nextToken();
                	StringTokenizer edgeTokens = new StringTokenizer(edgeStr,",");
                	Edge edge = new Edge(Integer.parseInt(edgeTokens.nextToken()), Integer.parseInt(edgeTokens.nextToken()));
                	v.getEdges().add(edge);
                }
                graph.add(v);
            }
            br.close();
        }catch (FileNotFoundException ex) {
            Logger.getLogger(Dijkstra.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Dijkstra.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(Dijkstra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return graph;
    }
}

class Edge {
	int DestVertexID;
	int length;
	public Edge(int id, int len){
		this.DestVertexID = id;
		this.length = len;
	}
	int getDestId(){
		return this.DestVertexID;
	}
	int getLength(){
		return this.length;
	}
}

class Vertex implements Comparable<Vertex> {
	int id;
	boolean explored;
	int distance;
	ArrayList<Edge> edges;
	
	public Vertex(int id, int distance){
		this.id = id;
		this.distance = distance;
		this.explored = false;
		edges = new ArrayList<Edge>();
	}
	
	public int getID(){
		return id;
	}
	
	public boolean isExplored(){
		return explored;
	}
	
	public void setExplored(boolean explored){
		this.explored = explored;
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}

    public int compareTo(Vertex other)
    {
        return Double.compare(distance, other.distance);
    }
}