package gameClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.fruit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import gui.Graph_GUI;
import utils.Point3D;
import utils.nodeData;

public class MyGameGUI extends JFrame implements ActionListener,MouseListener{

	
	game_service game;
	
	public MyGameGUI()  {
		initGui();
	}







	private void initGui()  {

		int scenar = scenario();
		if(scenar!=-1) {


			game = Game_Server.getServer(scenar);
			

			DGraph graph = new DGraph();

			jsonFileRead(game, graph);

			//ThreadFruit(graph,game);
			Graph_GUI window = new Graph_GUI(graph);
		


		}
	}
	private void ThreadFruit(DGraph graph,game_service game) {
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					

					ArrayList<fruit> f = new ArrayList<fruit>();
					for(int i=0;i<game.getFruits().size();i++) {
						JSONObject fruit = new JSONObject(game.getFruits().get(i));
						JSONObject fruitObject = (JSONObject) fruit.get("Fruit");
						String pos = (String) fruitObject.get("pos");
						String spl[] = pos.split(",");
						int type = (int) fruitObject.get("type");
						double value = (double) fruitObject.get("value");

						f.add(new fruit(Double.parseDouble(spl[0]),Double.parseDouble(spl[1]),type,value));


					}
					graph.setFruit(f);

				}catch(Exception e) {
					e.printStackTrace();
				}





			}
		});
		t2.start();


	}

	private static void jsonFileRead(game_service game,DGraph graph)
	{
		try {

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


		}catch(Exception e) {
			e.printStackTrace();
		}

	}


	private int scenario() {
		String scenario = JOptionPane.showInputDialog("Please insert scenario [0,23]");
		try {
			int sce = Integer.parseInt(scenario);
			if(sce>23||sce<0) {
				JOptionPane.showMessageDialog(null, "Invalild input");
				return -1;
			}

			return sce;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Invalild input");
			return -1;
		}

	}





	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}




}
