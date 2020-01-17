package gameClient;

import org.json.JSONObject;

import utils.Point3D;

public class Robot {


	private double value,speed;
	private int src,dest,id;
	private Point3D pos[];


	public Robot() {

	}

	public Robot(String jsonOb) {
		this();
		try {
			this.pos = new Point3D[2];
			JSONObject rb = new JSONObject(jsonOb).getJSONObject("Robot");
			int dest = rb.getInt("dest");
			this.dest = dest;
			double val = rb.getDouble("value");
			this.value = val;
			int src = rb.getInt("src");
			this.src = src;
			double speed = rb.getDouble("speed");
			this.speed = speed;
			int id = rb.getInt("id");
			this.id = id;
			String pos = rb.getString("pos");
			this.pos[0] = new Point3D(pos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point3D[] getPos() {
		return pos;
	}

	public void setPos(Point3D[] pos) {
		this.pos = pos;
	}


}
