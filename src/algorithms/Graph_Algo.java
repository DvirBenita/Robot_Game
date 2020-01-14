package algorithms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.dijkstras;
import utils.nodeData;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author Dvir Benita
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable{

	
	
	
	graph Graph;
	public Graph_Algo() {
		
	}
	public Graph_Algo(graph g) {
		init(g);
	}

	/**
	 * Init the graph
	 */
	@Override
	public void init(graph g) {
		if(g==null) {
			System.out.println("cannot init null graph");
		}
		Graph = g;
	}
	/**
	 * Function to init the graph by file
	 */
	@Override
	public void init(String file_name)  {
		try {
			FileInputStream f = new FileInputStream(file_name);
			ObjectInputStream o = new ObjectInputStream(f);

			this.Graph = (graph) o.readObject();
			o.close();
			f.close();
			System.out.println("the graph has init from file");

		}catch (IOException e) {
			System.out.println("IOException is caught");
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException is caught");
			e.printStackTrace();
		}

	}
	/**
	 * Function to save the graph object
	 */
	@Override
	public void save(String file_name) {
		try {
			FileOutputStream f = new FileOutputStream(file_name);
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(this.Graph);

			o.close();
			f.close();
		}catch(IOException e) {
			System.out.println("IOException is caught");
			e.printStackTrace();
		}
	}
	/**
	 * Function to chek if the graph is connected.
	 */
	@Override
	public boolean isConnected() {
		try {
		setFalse();
		
		for(node_data n : (Graph).getV()) {

			setFalse();

			DFS(n,(DGraph)Graph);

			for(node_data b : ((DGraph)Graph).getVertex().values()) {
				if(b.getTag()!=1)
					return false;
			}
		}
		return true;
		}catch(Exception e) {
		return false;
			}
			
		}

	/**
	 * Depth-first search (DFS) is an algorithm for traversing or searching tree or graph data structures.
	 * @param n
	 * @param Graph
	 */
	private static void DFS(node_data n,DGraph Graph) {

		n.setTag(1);

		for(edge_data edge: Graph.getE(n.getKey())) {
			if((Graph).getVertex().get(edge.getDest()).getTag()!=1) {
				DFS((Graph).getVertex().get(edge.getDest()), Graph);
			}

		}

	}


	/**
	 * Algorithm to find the shortes path distance between src and dest
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		return new dijkstras((DGraph) Graph).dijkstra(src, dest);
	}
	/**
	 * Algorithm to find the shortes path between src and dest
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		return new dijkstras((DGraph) Graph).dijkstra1(src, dest);
	}
	/**
	 * Algorithm that computes a relatively short path which visit each node in the
	 * targets List
	 */
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		if(!this.isConnected()) {
			return null;
		}
		ArrayList<node_data> ts = new ArrayList<node_data>();
		List<node_data> lastArr = null;
		
		for(int i=1;i<targets.size();i++) {
		int src =	targets.get(i-1);
		int dst = targets.get(i);
		if(this.Graph.getNode(dst)!=null) {
		if(((nodeData)(((DGraph)this.Graph).getVertex().get(dst))).getCounter()>0) 
			continue;
			
		}else {
			return null;
		}
		List<node_data> arr= this.shortestPath(src, dst);
		lastArr=arr;
		if(i!=1) {
		arr.remove(0);
		}
		ts.addAll(arr);	
		}
		
		for(node_data node : this.Graph.getV()) {
			((nodeData)node).setCounter(0);
		}
		return ts;
	}

	

	/**
	 * Deep copy
	 */
	@Override
	public graph copy() {
		if(this.Graph instanceof DGraph) {
		return ((DGraph)Graph).copy();
		}
		return null;
	}
	/**
	 * Set all tags 0.
	 */
	private void setFalse() {
		if(this.Graph instanceof DGraph) {
		for(node_data n : ((DGraph)Graph).getVertex().values()) {
			n.setTag(0);
		}
		}
	}
	/**
	 * getter to graph
	 * @return
	 */
	public graph getGraph() {

		return this.Graph;
	}





}
