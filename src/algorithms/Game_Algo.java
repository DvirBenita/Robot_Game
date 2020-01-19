package algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONObject;



import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Fruit;
import gameClient.Fruit_Comp;
import gameClient.KML_Logger;
import gameClient.Robot;
import utils.Point3D;
import utils.nodeData;

public class Game_Algo {
	private ArrayList<Fruit> fruits;
	private ArrayList<Robot> robots;
	private Graph_Algo ga;
	public static final Fruit_Comp CMP = new Fruit_Comp();
	


	public Game_Algo(){
		fruits=new ArrayList<Fruit>();
		robots= new ArrayList<Robot>();
	}


	public ArrayList<Fruit> getFruits() {
		return fruits;
	}
	public void setFruits(ArrayList<Fruit> fruits) {
		this.fruits = fruits;
	}
	public ArrayList<Robot> getRobots() {
		return robots;
	}
	public void setRobots(ArrayList<Robot> robots) {
		this.robots = robots;
	}
	public Graph_Algo getGa() {
		return ga;
	}
	public void setGa(Graph_Algo ga) {
		this.ga = ga;
	}
	public void fillRobots(game_service game,graph graph,KML_Logger km) {

		this.robots=new ArrayList<Robot>();
		synchronized (robots) {
			try {

				
				for(int i=0;i<robotSum(game);i++) {
					if(fruits.size()>i) {
						
						game.addRobot(this.fruits.get(i).getEdg().getSrc());
						
					}else {
						game.addRobot(i%graph.nodeSize());
					}
				}

				updateRob(game,km);




			}catch(Exception e)
			{

			}

		}


	}

	public void updateRob(game_service game, KML_Logger km) {
		this.robots= new ArrayList<Robot>();
		synchronized (robots) {

			for(String robotJson:game.getRobots()) {
				Robot r = new Robot(robotJson);
				km.add(new Date(), r.getPos()[0].toString(), "robot");
				robots.add(r);
			}
		}

	}
	private int robotSum(game_service game) {
		try {
			JSONObject rb = new JSONObject(game.toString());

			JSONObject gameS = rb.getJSONObject("GameServer");
			return gameS.getInt("robots");



		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	public void fillFruit(game_service game,graph graph,KML_Logger km) {

		this.fruits=new ArrayList<Fruit>();

		synchronized (fruits) {
			boolean isIN=false;
			for(String fruitJson :game.getFruits()) {
				Fruit f = new Fruit(fruitJson);
				for(Fruit f1 : fruits) {
					if(f1.getPos()[0].distance2D(f.getPos()[0])<Point3D.EPS) {
						isIN=true;
						continue;
					}
				}
				if(isIN) {
					isIN=false;
					continue;
				}

				for(node_data vertex : graph.getV()) {
					for(edge_data edge: graph.getE(vertex.getKey())) {

						double distance1 =vertex.getLocation().distance2D(f.getPos()[0]);
						double distance2 = f.getPos()[0].distance2D(graph.getNode(edge.getDest()).getLocation());
						double srcDst = vertex.getLocation().distance2D(graph.getNode(edge.getDest()).getLocation());



						if(Math.abs(srcDst-(distance1+distance2))< Point3D.EPS) {

							if(vertex.getKey()>edge.getDest()) {
								if(f.getType()==-1) {
									km.add(new Date(), f.getPos()[0].toString(), "banana");
									f.setEdg(edge);

									fruits.add(f);
									break;
								}
							}
							else {
								if(f.getType()==1) {
									f.setEdg(edge);
									km.add(new Date(), f.getPos()[0].toString(), "apple");
									fruits.add(f);
									break;
								}
							}
						}

					}
				}

			}

			fruits.sort(CMP);
		}

	}
	
//	public void moveRobotsAuto(game_service game, graph g) {
//		ga.init(g);
//		synchronized (robots) {
//			for (Robot r : robots) {
//				int i = 0;
//				boolean found = false;
//				while (!found) {
//					Fruit f = fruits.get(i);
//					if (f.isOnTarget()) {
//						i++;
//						continue;
//					}
//					edge_data e = f.getEdg();
//					int src = r.getSrc();
//					int dest = -1;
//					int last = -1;
//					if (f.getType() == -1) {
//						dest = e.getDest() > e.getSrc() ? e.getSrc() : e.getDest();
//						last = e.getDest() > e.getSrc() ? e.getDest() : e.getSrc();
//					}
//					if (f.getType() == 1) {
//						dest = e.getDest() > e.getSrc() ? e.getDest() : e.getSrc();
//						last = e.getDest() > e.getSrc() ? e.getSrc() : e.getDest();
//					}
//					List<node_data> path = ga.shortestPath(src, dest);
//					path.add(new nodeData(last));
//					if (path != null && path.size() > 1) {
//						moveByPath(r.getId(), path, game);
//						f.setOnTarget(true);
//						found = true;
//						continue;
//					} else {
//						i++;
//					}
//					if (i == fruits.size()) {
//						found = true;
//						continue;
//					}
//				}
//				i++;
//			}
//		}
//	}
	public void update(game_service game,graph g,KML_Logger km) {

		this.robots= new ArrayList<Robot>();
		synchronized (robots) {

			for(String robotJson:game.getRobots()) {

				Robot r = new Robot(robotJson);
				km.add(new Date(), r.getPos()[0].toString(), "robot");
				robots.add(r);
			}


		}

		fillFruit(game, g,km);
		((DGraph)g).setChange(g.getMC()+1);
	}



}
