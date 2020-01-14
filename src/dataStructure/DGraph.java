package dataStructure;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import utils.edgeData;
import utils.fruit;
import utils.nodeData;



public class DGraph implements graph,Serializable {

	private LinkedHashMap<String, edge_data> edges ;
	private LinkedHashMap<Integer,node_data> vertex ;
	private LinkedHashMap<Integer,LinkedHashMap<Integer,edge_data>> E;
	private int change;
	private ArrayList<fruit> fruits;
	double EPSILON = 0.0001;
	public ArrayList<fruit> getFruits() {
		return fruits;
	}
	public void  setFruit(ArrayList<fruit> f) {
		
		fruits =f;
		for(edge_data n :edges.values()) {
			node_data src = vertex.get(n.getSrc());
			node_data dest = vertex.get(n.getDest());
			for(fruit f1 : fruits) {
				double withFriut =(Math.abs(distance(f1,src))+Math.abs(distance(f1,dest)));
				double withoutFruit = Math.abs(distance(src,dest));
				if((withFriut-withoutFruit)<EPSILON) {
					((edgeData)n).setF(f1);
					
				}
			}
		}
		change++;
	}
	private double distance(fruit f1 , node_data n) {
			double y= n.getLocation().y();
			double y1= f1.getPos()[1];
			double x=n.getLocation().x();
			double x1 = f1.getPos()[0];
			double dis = (y-y1)*(y-y1)+(x-x1)*(x-x1);
			return Math.sqrt(dis);
	}
	private double distance(node_data src,node_data dest) {
		double y = (dest.getLocation().y());
		double y1 = src.getLocation().y();
		double x = dest.getLocation().x();
		double x1 = src.getLocation().x();
		double dis = (y-y1)*(y-y1)+(x-x1)*(x-x1);
		return Math.sqrt(dis);
	}
	/**
	 * Constructors
	 */
	public DGraph() {
		this.edges = new LinkedHashMap<String, edge_data>();
		this.vertex = new LinkedHashMap<Integer, node_data>();
		this.E= new LinkedHashMap<Integer, LinkedHashMap<Integer,edge_data>>();
		this.change = Integer.MAX_VALUE;
	}
	
	public DGraph(int vert) {
		this();
		for (int i = 1; i <= vert; i++) {
			node_data n = new nodeData(i);
			this.addNode(n);
		}
	}
//	/**
//	 * Creates a shallow copied graph
//	 * @param edges2
//	 * @param vertex2
//	 * @param e2
//	 * @param change2
//	 */
//	public DGraph(LinkedHashMap<String, edge_data> edges2, LinkedHashMap<Integer, node_data> vertex2,
//			LinkedHashMap<Integer, LinkedHashMap<Integer, edge_data>> e2, int change2) {
//		this.edges=edges2;
//		this.vertex=vertex2;
//		this.E=e2;
//		this.change = change2;
//	}
	/**
	 * Deep copy constructor;
	 * @param edges
	 * @param vertex
	 * @param E
	 * @param change
	 */
		public  DGraph(LinkedHashMap<String, edge_data> edges , LinkedHashMap<Integer,node_data> vertex,LinkedHashMap<Integer,LinkedHashMap<Integer,edge_data>> E,int change) {
			
			this.edges = copyWithStringKey(edges);
			this.vertex = copyWithIntegerKey(vertex);
			this.E= copyWithIntegerHashInt(E);
			this.change = Integer.MAX_VALUE;
			
		}


	/**
	 * return deep copy of this graph
	 * @return
	 */
	public graph copy() {
		return new DGraph(this.getEdges(),this.getVertex(),this.getE(),this.getChange());
	}

	/**
	 * Getter to vertex in graph 
	 */
	@Override
	public node_data getNode(int key) {
		try {
			return vertex.get(key);
		}catch(Exception e) {

			return null;

		}

	}
	/**
	 * Getter to data of edge in the graph 
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		String key = ""+src+","+dest;
		try {
			return edges.get(key);
		}catch(Exception e) {
			return null;
		}
	}
	/**
	 * Add vertex to graph
	 */
	@Override
	public void addNode(node_data n) {
		try {
			if(!vertex.containsKey(n.getKey())) {
				vertex.put(n.getKey(), n);
				this.E.put(n.getKey(), new LinkedHashMap<Integer,edge_data>());
				this.change++;
			}
		}catch(Exception e) {
			System.out.println("cannot add this node");
		}
	}
	/**
	 * Linking 2 vertex
	 */
	@Override
	public void connect(int src, int dest, double w) {
		try {
			if(w>=0) {
				String key = ""+src+","+dest;
				if(vertex.get(src)==null||vertex.get(dest)==null) {
					System.out.println("Cannot connect bewtween src:"+src+" and dest:"+dest);
				}

				
					edge_data temp = new edgeData(src, dest, w);
					edges.put(key, temp);
					E.get(src).put(dest, temp);
					this.change++;
				
			}else {
				System.out.println("Cannot add negative weight");
			}
		}catch(Exception e) {

			System.out.println("Cannot connect bewtween src:"+src+" and dest:"+dest);
		}
	}
	/**
	 * Return collection of the vertex on grpah
	 */
	@Override
	public Collection<node_data> getV() {
		Collection<node_data> vertex = this.vertex.values();
		return  vertex;
	}
	/**
	 * Returns a collection of all neighbors coming from a certain vertex
	 * 
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		try {
			return	E.get(node_id).values();
		}catch(Exception e) {
			return null;
		}
	}
	/**
	 * Remove vertex from the graph 3,1
	 */
	@Override
	public node_data removeNode(int key) {
		try {
			String key1=""+key;
			node_data current = vertex.get(key);
			if(current==null)
				return null;
			vertex.remove(key);
			E.remove(key);
			int coun=0;
			int size = edges.size();
			boolean	flag=true;
			//3,11
			if(size!=0) {
				while(flag) {
					for(Map.Entry<String, edge_data> entry:edges.entrySet()) {
						coun++;
						if(entry.getKey().contains(""+key+",")) {
							if(entry.getKey().indexOf(key+",")==0) {
								
								edges.remove(entry.getKey());
								break;
							}
						}
						if(entry.getKey().contains(""+key)) {
							
							if(entry.getKey().indexOf(""+key)+key1.length()==entry.getKey().length()) {
								edges.remove(entry.getKey());
								break;
							}

						}
					}

					if(coun>=size-1)
						flag=false;
				}
			}
			this.setChange(this.change+1);;
			return current;
		}catch(Exception e) {
			return null;
		}
	}
	/**
	 * Remove edge from graph
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		try {
			edge_data current = this.edges.get(""+src+","+dest);
			if(current==null){
				return null;
			}
			edges.remove(""+src+","+dest);
			E.get(src).remove(dest);//O(1)
			this.change++;
			return current;
		}catch(Exception e) {
			return null;
		}
	}

	/**
	 * Return size of vertex in graph
	 */
	@Override
	public int nodeSize() {
		return this.vertex.values().size();
	}
	/**
	 * Return size of edges
	 */
	@Override
	public int edgeSize() {
		return edges.values().size();
	}
	/**
	 * Retrun the serial number of this commit 
	 */
	@Override
	public int getMC() {
		return this.change;
	}


	/**
	 * Copy HashMap with string key
	 * @param original
	 * @return
	 */
	public static LinkedHashMap<String, edge_data> copyWithStringKey(LinkedHashMap<String, edge_data> original)
	{
		String s="";
		LinkedHashMap<String, edge_data> copy = new LinkedHashMap<String, edge_data>();
		for (Map.Entry<String, edge_data> entry : original.entrySet())
		{
			s+=entry.getKey();
			copy.put(s,new edgeData(entry.getValue()));
			s="";
		}
		return copy;
	}
	/**
	 * Copy HashMap with Integer key.
	 * @param original
	 * @return
	 */
	private static LinkedHashMap<Integer, node_data> copyWithIntegerKey(LinkedHashMap<Integer, node_data> original)
	{
		LinkedHashMap<Integer, node_data> copy = new LinkedHashMap<Integer, node_data>();
		for (Map.Entry<Integer, node_data> entry : original.entrySet())
		{
			copy.put(entry.getKey(),new nodeData(entry.getValue()));
		}
		return copy;
	}
	/**
	 * Copy HashMap with Integer key and edge
	 * @param original
	 * @return
	 */
	private static LinkedHashMap<Integer, edge_data> copyWithIntegerKeyandEdge(LinkedHashMap<Integer, edge_data> original)
	{
		LinkedHashMap<Integer, edge_data> copy = new LinkedHashMap<Integer, edge_data>();
		for (Map.Entry<Integer, edge_data> entry : original.entrySet())
		{
			copy.put(entry.getKey(),new edgeData(entry.getValue()));
		}
		return copy;
	}
	/**
	 * Copy Hash map with Integer key and Hashmap value
	 * @param original
	 * @return
	 */
	private LinkedHashMap<Integer, LinkedHashMap<Integer, edge_data>> copyWithIntegerHashInt(
			LinkedHashMap<Integer, LinkedHashMap<Integer, edge_data>> original) {
		LinkedHashMap<Integer, LinkedHashMap<Integer, edge_data>> copy = new LinkedHashMap<Integer, LinkedHashMap<Integer,edge_data>>();

		for (Map.Entry<Integer, LinkedHashMap<Integer, edge_data>> entry : original.entrySet())
		{
			copy.put(entry.getKey(), copyWithIntegerKeyandEdge(entry.getValue()));

		}
		return copy;
	}
	/**
	 * getters and setters
	 * @return
	 */
	public LinkedHashMap<String, edge_data> getEdges() {
		return edges;
	}
	public void setEdges(LinkedHashMap<String, edge_data> edges) {

		this.edges = edges;
	}
	public LinkedHashMap<Integer, node_data> getVertex() {
		return vertex;
	}
	public void setVertex(LinkedHashMap<Integer, node_data> vertex) {
		this.vertex = vertex;
	}
	public LinkedHashMap<Integer, LinkedHashMap<Integer, edge_data>> getE() {
		return E;
	}
	public void setE(LinkedHashMap<Integer, LinkedHashMap<Integer, edge_data>> e) {
		E = e;
	}
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}
}
