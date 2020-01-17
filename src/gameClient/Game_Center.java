package gameClient;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Game_Algo;
import dataStructure.*;
import utils.Point3D;
import utils.nodeData;
import utils.Arena;
public class Game_Center  implements Runnable {



	private graph graph;
	private game_service game;
	private Game_Algo game_algo;
	private boolean manual;
	private KML_Logger km;

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	public Game_Center() {

	}

	public Game_Center(int scenario) {
		km = new KML_Logger(scenario);
		graph= new DGraph();
		game = Game_Server.getServer(scenario);
		this.jsonFileRead(graph,game);
		game_algo = new Game_Algo();
		game_algo.fillFruit(game, graph,km);
		game_algo.fillRobots(game, graph,km);



	}

	private  void jsonFileRead(graph graph,game_service game)
	{
		try {
			if(game.getGraph()!=null) {
				JSONObject empObj = new JSONObject();


				JSONObject jsonObject = new JSONObject(game.getGraph());

				JSONArray jsonArray= jsonObject.getJSONArray("Nodes");

				for (int j=0; j<jsonArray.length();j++)
				{
					empObj =jsonArray.getJSONObject(j);
					String pos = (String) empObj.get("pos");
					String spl[] = pos.split(",");


					Point3D p = new Point3D(Double.parseDouble(spl[0]),Double.parseDouble(spl[1]));

					int key = (int) empObj.get("id");
					nodeData n = new nodeData(key,p);

					graph.addNode(n);

				}
				jsonArray = jsonObject.getJSONArray("Edges");
				for (int j=0; j<jsonArray.length();j++)
				{
					empObj =jsonArray.getJSONObject(j);
					int src = (int) empObj.get("src");
					int dest = (int) empObj.get("dest");
					double w = (double) empObj.get("w");

					graph.connect(src, dest, w);

				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	public graph getGraph() {
		return graph;
	}

	public void setGraph(graph graph) {
		this.graph = graph;
	}

	public game_service getGame() {
		return game;
	}

	public void setGame(game_service game) {
		this.game = game;
	}

	public Game_Algo getGame_algo() {
		return game_algo;
	}

	public void setGame_algo(Game_Algo game_algo) {
		this.game_algo = game_algo;
	}

	@Override
	public void run() {
		try {
			game.startGame();

			while(game.isRunning()) {

				game_algo.update(game,this.graph,km);
				game.move();
				Thread.sleep(50);


			}
			km.done();





		}catch(Exception e) {

		}


	}



}
