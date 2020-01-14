package utils;
import java.util.*;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data; 
public class dijkstras {
	private DGraph Grpah;
	private Set<Integer> settled; 
	private PriorityQueue<node_data> pq; 


	/**
	 * Constructor to dijkstras algoristhms
	 * @param Grpah
	 */
	public dijkstras( DGraph Grpah) 
	{ 
		try {
			this.Grpah=Grpah;
			settled = new HashSet<Integer>(); 
			pq = new PriorityQueue<node_data>(new compNode());
		}catch(Exception e) {

		}
	} 


	/**
	 *  Function to find shortes distance.
	 */
	public double dijkstra(int src,int dest) 
	{ 
		try {

			for (Map.Entry<Integer, node_data> entry : this.Grpah.getVertex().entrySet())
			{
				entry.getValue().setWeight(Double.POSITIVE_INFINITY);
				entry.getValue().setInfo("-1");
				entry.getValue().setTag(-1);
				pq.add(entry.getValue());
			}

			this.Grpah.getVertex().get(src).setInfo("-10");
			pq.remove(this.Grpah.getVertex().get(src));
			this.Grpah.getVertex().get(src).setWeight(0);
			pq.add(this.Grpah.getVertex().get(src)); 

			while (settled.size() != this.Grpah.getVertex().size()) { 
				PriorityQueue<node_data> arr = new PriorityQueue<node_data>(new compNode()); 
			
				while(!pq.isEmpty()) {
					arr.add(pq.remove());
				}
				while(!arr.isEmpty()) {
					pq.add(arr.remove());
				}


				int u = pq.remove().getKey();

				this.Grpah.getVertex().get(u).setTag(1);
				settled.add(u); 
				Neigh(u); 

			}


			node_data temp =  this.Grpah.getVertex().get(dest);

			return temp.getWeight();
		}catch(Exception e) {
			return -1;
		}
	} 



	/**
	 * Auxiliary function to tag the Neighbors
	 * @param u
	 */
	private void Neigh(int u) 
	{ 
		double edgeDistance = -1; 
		double newDistance = -1; 
		for(edge_data edge : this.Grpah.getE().get(u).values() ) {
			if(this.Grpah.getVertex().get(edge.getDest()).getTag()!=1) {
				edgeDistance = this.Grpah.getVertex().get(edge.getSrc()).getWeight();
				newDistance = edgeDistance +  edge.getWeight();
				if(newDistance<this.Grpah.getVertex().get(edge.getDest()).getWeight()) {
					this.Grpah.getVertex().get(edge.getDest()).setWeight(newDistance);
					this.Grpah.getVertex().get(edge.getDest()).setInfo(""+edge.getSrc());

				}
				pq.remove(this.Grpah.getVertex().get(edge.getDest()));
				node_data curr = this.Grpah.getVertex().get(edge.getDest());
				pq.add(curr);
			}
		}
	}
	/**
	 *  Function for Dijkstra's Algorithm use to find shortest path.
	 */
	public List<node_data> dijkstra1(int src, int dest) {
		try {
			for (Map.Entry<Integer, node_data> entry : this.Grpah.getVertex().entrySet())
			{
				entry.getValue().setWeight(Double.POSITIVE_INFINITY);
				entry.getValue().setInfo("-1");
				entry.getValue().setTag(-1);
				pq.add(entry.getValue());
			}
			this.Grpah.getVertex().get(src).setInfo("-10");
			pq.remove(this.Grpah.getVertex().get(src));
			this.Grpah.getVertex().get(src).setWeight(0);
			pq.add(this.Grpah.getVertex().get(src)); 

			while (settled.size() != this.Grpah.getVertex().size()) { 
				PriorityQueue<node_data> arr = new PriorityQueue<node_data>(new compNode()); 
				
				while(!pq.isEmpty()) {
					arr.add(pq.remove());
				}
				while(!arr.isEmpty()) {
					pq.add(arr.remove());
				}


				


				int u = pq.remove().getKey();

				this.Grpah.getVertex().get(u).setTag(1);
				settled.add(u); 
				Neigh(u); 

			}

			ArrayList<node_data> path = new ArrayList<node_data>();
			node_data temp =  this.Grpah.getVertex().get(dest);
			path.add(temp);
			((nodeData)temp).setCounter(((nodeData)temp).getCounter()+1);
			while(temp.getInfo().compareTo("-10")!=0) {

				temp = this.Grpah.getVertex().get(Integer.parseInt(temp.getInfo()));
				((nodeData)temp).setCounter(((nodeData)temp).getCounter()+1);
				path.add(temp);
			}


			return reverseArrayList(path);
		}catch(Exception e) {
			return null;
		}
	}
	/**
	 * Return reverse of arraylist.
	 * @param alist
	 * @return
	 */
	private static ArrayList<node_data> reverseArrayList(ArrayList<node_data> alist) 
	{ 

		ArrayList<node_data> revArrayList = new ArrayList<node_data>(); 
		for (int i = alist.size() - 1; i >= 0; i--) { 

			revArrayList.add(alist.get(i)); 
		}

		return revArrayList; 
	} 



}
