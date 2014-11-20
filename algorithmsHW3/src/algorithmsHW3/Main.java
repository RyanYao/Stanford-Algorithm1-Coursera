package algorithmsHW3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
	public static void main(String []args){
		String fileName = "C:\\Users\\yaoyun\\workspace\\algorithmsHW3\\src\\algorithmsHW3\\kargerMinCut.txt";
		HashMap<Integer, ArrayList<Integer>> originGraph = readGraph(fileName);
		int minEdge = Integer.MAX_VALUE;
		for(int i=0; i<10; i++){
			HashMap<Integer, ArrayList<Integer>> graph = copyGraph(originGraph);
			//printGraph(graph);
			while(graph.size()>2){
				ArrayList<Integer> randomEdge = getRandomEdge(graph);
				//System.out.print("node 1 = "+Integer.toString(randomEdge.get(0).intValue()));
				//System.out.println("   node 2 = "+Integer.toString(randomEdge.get(1).intValue()));
				mergeTwoNodes(graph, randomEdge.get(0), randomEdge.get(1));
				//printGraph(graph);
			}
			//now there are only two nodes left, count the number of edges
			int min = countEdges(graph);
			minEdge = Math.min(min, minEdge);
			System.out.println("min value = "+Integer.toString(min));
		}
		System.out.println("minimul edges number = "+Integer.toString(minEdge));
	}
	
	private static HashMap<Integer, ArrayList<Integer>> copyGraph(HashMap<Integer, ArrayList<Integer>> graph)
    {
        HashMap<Integer, ArrayList<Integer>> newGraph = new HashMap<Integer, ArrayList<Integer>>();        
        for(Integer i : graph.keySet())
        {
            ArrayList<Integer> currentItemList = graph.get(i);
            newGraph.put(i, new ArrayList<Integer>(currentItemList));
        }
        return newGraph;
    }
	private static void printGraph(HashMap<Integer, ArrayList<Integer>> graph){
		for(Integer key : graph.keySet()){
			System.out.print(Integer.toString(key.intValue())+" ");
			for(Integer i : graph.get(key)){
				System.out.print(Integer.toString(i.intValue())+" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	private static int countEdges(HashMap<Integer, ArrayList<Integer>> graph){
		return graph.get(graph.keySet().toArray()[0]).size();
	}
	
	private static void mergeTwoNodes(HashMap<Integer, ArrayList<Integer>> graph, Integer first, Integer second){
		ArrayList<Integer> newArray = new ArrayList<Integer>(graph.get(first));
		newArray.addAll(graph.get(second));
		graph.remove(second);
		graph.put(first, newArray);
		renameSecondToFirst(graph, first, second);//now all edges connected with second node is connect with the first node
		removeSelfLoop(graph, first); // remove self loop for first node
	}
	
	private static void renameSecondToFirst(HashMap<Integer, ArrayList<Integer>> graph, Integer first, Integer second){
		for(ArrayList<Integer> array : graph.values()){
			for(Integer i : array){
				if(i.intValue() == second.intValue())
					array.set(array.indexOf(i), first);
			}
		}
	}
	
	private static void removeSelfLoop(HashMap<Integer, ArrayList<Integer>> graph, Integer first){
		ArrayList<Integer> firstArray = graph.get(first);
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for(Integer i : firstArray){
			if (i.intValue() == first.intValue())
				toRemove.add(i);
		}
		firstArray.removeAll(toRemove);
	}
	
	private static ArrayList<Integer> getRandomEdge( HashMap<Integer, ArrayList<Integer>> graph ){
		ArrayList<Integer> randomEdge = new ArrayList<Integer>();
		int randkey = (int)(graph.keySet().size() * Math.random());
		Integer firstNode = (Integer)graph.keySet().toArray()[randkey];
		
		int randIndex = (int)(graph.get(firstNode).size() * Math.random());
		Integer secondNode = graph.get(firstNode).get(randIndex);
		
		randomEdge.add(firstNode);
		randomEdge.add(secondNode);
		return randomEdge;
	}
	
	private static HashMap<Integer, ArrayList<Integer>> readGraph(String fileName){
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
		BufferedReader textFile = null;
		try {
			textFile = new BufferedReader(new FileReader(fileName));
		}catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		try {
			String str;
			while ( (str=textFile.readLine() ) != null) {
				StringTokenizer token = new StringTokenizer(str);
				ArrayList<Integer> edges = new ArrayList<Integer>();
				Integer node = Integer.parseInt(token.nextToken());
				while(token.hasMoreTokens())
					edges.add(Integer.parseInt(token.nextToken()));
				graph.put(node, edges);
			}
		} catch (IOException e) {
			System.out.println("io exception");
		}finally{
			 try {
				textFile.close();
			} catch (IOException e) {
				System.out.println("not able to close file");
			}
		}
		return graph;
	}
}
