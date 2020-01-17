package utils;

import java.io.Serializable;

import dataStructure.edge_data;
import dataStructure.node_data;

public class edgeData implements edge_data,Serializable{
	/**
	 * 
	 */

	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
	
	/**
	 * Consrtuctors to edge data
	 * @param src
	 * @param dest
	 * @param w
	 */
	public edgeData(int src,int dest,double w) {
		if(w>=0) {
		this.src=src;
		this.dest=dest;
		this.weight=w;
		}else {
			System.out.println("cannot init negative weight");
		}
	}
	
	public edgeData(node_data n1,node_data dest,double w) {
		if(w>=0) {
		this.src=n1.getKey();
		this.dest=dest.getKey();
		this.weight=w;
		}else {
			System.out.println("cannot init negative weight");
		}
	}
	/**
	 * Init this edge with another edge
	 * @param edge
	 */
	public edgeData(edge_data edge) {
		this(edge.getSrc(),edge.getDest(),edge.getWeight());
	}
	/**
	 * getters and setters
	 */
	@Override
	public int getSrc() {
		
		return this.src;
	}

	@Override
	public int getDest() {
		
		return this.dest;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public String getInfo() {
		
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
		
	}

	@Override
	public int getTag() {
		
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;
		
	}

}
