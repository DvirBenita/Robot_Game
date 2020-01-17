package gameClient;

import org.json.JSONObject;

import dataStructure.edge_data;
import utils.Point3D;

public class Fruit {

	public Point3D[] getPos() {
		return pos;
	}

	public void setPos(Point3D[] pos) {
		this.pos = pos;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public edge_data getEdg() {
		return edg;
	}

	public void setEdg(edge_data edg) {
		this.edg = edg;
	}

	private Point3D pos[];
	private int type;
	private double val;
	private edge_data edg;

	public Fruit() {

	}

	public Fruit(String json) {
		try {
			this.pos=new Point3D[2];
			JSONObject fruit = new JSONObject(json);
			JSONObject fruitObject = fruit.getJSONObject("Fruit");
			String pos = (String) fruitObject.get("pos");
			String spl[] = pos.split(",");
			double x = Double.parseDouble(spl[0]);
			double y = Double.parseDouble(spl[1]);
			this.pos[0] = new Point3D(x,y);
			this.val  = fruitObject.getDouble("value");
			this.type = fruitObject.getInt("type");

		}catch(Exception e) {
			e.printStackTrace();
		}


	}


}
