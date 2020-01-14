package utils;

import java.io.Serializable;
import java.util.Comparator;

import dataStructure.node_data;

public class nodeData implements node_data,Serializable{
	private int key;
	private Point3D location;
	private double weight;
	private String info;
	private int tag;
	private int Counter=0;
	
	/**
	 * Empty constructor to node data
	 */
	public nodeData() {
		this.key=-1;
		this.location=new Point3D(0,0,0);
		this.weight=0;
		this.info=" ";
		this.tag=Integer.MIN_VALUE;
	}
	/**
	 * Constructor get paramater key
	 */
	public nodeData(int key) {
		this.key = key;
		this.init();
		Point3D p = new Point3D(key,key);
		setLocation(p);
		this.weight=0;
		this.info=" ";
		this.tag=Integer.MIN_VALUE;
	}
	/**
	 * Constructors that gets parameters
	 * @param key
	 * @param p
	 * @param weight
	 * @param info
	 * @param tag
	 */
	public nodeData(int key,Point3D p) {
		this.key=key;
		this.location=new Point3D(p);
		this.weight=0;
		this.info=" ";
		this.tag=Integer.MIN_VALUE;
	}
	
	
	public nodeData(int key,Point3D location , double weight, String info, int tag) {
		this.key=key;
		this.location=new Point3D(location);
		this.weight =weight;
		this.info=info;
		this.tag=tag;

	}
	
	/**
	 * Deep copy constructor.
	 * @param value
	 */
	public nodeData(node_data value) {
		this(value.getKey(),value.getLocation(),value.getWeight(),value.getInfo(),value.getTag());
	}
	/**
	 * getters and setters
	 */
	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public Point3D getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(Point3D p) {
		Point3D temp = new Point3D(p);
		this.location=temp;
	}

	@Override
	public double getWeight() {

		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		if(w>=0) {
		 this.weight=w;
		}
		else {
			System.out.println("cannot set negative weight");
		}
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
	public int getCounter() {
		return Counter;
	}
	public void setCounter(int counter) {
		Counter = counter;
	}
	public void init() {
		setTag(0);
		setInfo("");
		setWeight(Double.MAX_VALUE);
	}
}


