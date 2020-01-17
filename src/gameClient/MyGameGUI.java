package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import gui.Graph_GUI;
import utils.*;

public class MyGameGUI extends JFrame implements ActionListener,MouseListener{




	private Arena Arena;
	private boolean manual;
	private static boolean chooseRobot=true;
	private Robot tempRobot=null;


	public MyGameGUI()  {
		Arena = new Arena();
		initGui();
	}







	private void initGui()  {

		this.setTitle("Robot Game");
		this.setSize(900, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		MenuBar menu = new MenuBar();
		Menu m = new Menu("Menu");
		menu.add(m);

		MenuItem scenario = new MenuItem("Scenario");
		m.add(scenario);
		scenario.addActionListener(this);
		this.addMouseListener(this);
		this.setMenuBar(menu);

	}


	public void paint(Graphics g) {
		try {
			if (this.Arena.getGame_center() != null) {

				BufferedImage bufferedImage = new BufferedImage(900, 650, BufferedImage.TYPE_INT_ARGB);
				Graphics2D grr = bufferedImage.createGraphics();
				grr.setBackground(new Color(240, 240, 240));
				grr.clearRect(0, 0, 900, 650);


				paintInfo(grr);
				if(Arena.getGame_center().getGame().isRunning()) {
					

					paintGraph(grr,800,550);
					paintRobots(grr,800,550);
					paintFruits(grr,800,550);


				}
				changeThread();

				Graphics2D Comp = (Graphics2D) g;
				Comp.drawImage(bufferedImage, null, 0, 0);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}



	}
	private void paintFruits(Graphics2D g,int i ,int j) {

		Range ra[] =range();
		synchronized (Arena.getGame_center().getGame_algo().getFruits()) {
			for(Fruit fruit: Arena.getGame_center().getGame_algo().getFruits()) {

				g.setColor(Color.GREEN);
				if(fruit.getType()==-1) {
					g.setColor(Color.YELLOW);
				}


				int x1 = (int) scale(fruit.getPos()[0].x(),ra[0].get_min(),ra[0].get_max(),50,i-50);
				int y1 = (int) scale(fruit.getPos()[0].y(),ra[1].get_min(),ra[1].get_max(),70,j-70);

				g.fillOval(x1 , y1 , 13, 13);
				g.setColor(Color.DARK_GRAY);
				g.drawString("" + fruit.getVal(), x1+15, y1+15);


			}

		}
	}







	private void paintRobots(Graphics2D grr,int i ,int j) {
		if(!Arena.getGame_center().getGame_algo().getRobots().isEmpty()) {
			synchronized (Arena.getGame_center().getGame_algo().getRobots()) {
				Range ra[] =range();
				if(ra!=null) {
					for (Robot robot : Arena.getGame_center().getGame_algo().getRobots()) {

						try {
							BufferedImage im = ImageIO.read(new File("robot.png"));
							int x1 = (int) scale(robot.getPos()[0].x(),ra[0].get_min(),ra[0].get_max(),50,i-50);
							int y1 = (int) scale(robot.getPos()[0].y(),ra[1].get_min(),ra[1].get_max(),70,j-70);
							grr.drawImage(im, x1 - 15, y1 - 15, null);
						} catch (Exception e) {

							e.printStackTrace();
						}

					}
				}
			}
		}
	}







	private void paintInfo(Graphics2D g) {
		if(Arena.getGame_center().getGame().isRunning()) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("Time: " + Arena.getGame_center().getGame().timeToEnd()/ 1000,50,70);

		}else {
			String results = Arena.getGame_center().getGame().toString();
			JSONObject line;
			String grade = "0";
			try {
				line = new JSONObject(results);
				JSONObject gs = line.getJSONObject("GameServer");
				grade = "" + gs.getInt("grade");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("Score: " + grade,450,225);
		}
	}





	/**
	 * 
	 * @param data  denote some data to be scaled
	 * @param r_min the minimum of the range of your data
	 * @param r_max the maximum of the range of your data
	 * @param t_min the minimum of the range of your desired target scaling
	 * @param t_max the maximum of the range of your desired target scaling
	 * @return
	 */
	private double scale(double data, double r_min, double r_max, double t_min, double t_max) {

		double res = ((data - r_min) / (r_max - r_min)) * (t_max - t_min) + t_min;
		return res;
	}


	private void paintGraph(Graphics2D g,int i,int j) {

		if(Arena.getGame_center().getGraph()!=null) {
			for(node_data n : Arena.getGame_center().getGraph().getV()) {
				Range[] ra = range();

				g.setColor(Color.BLUE);



				int x = (int) scale(n.getLocation().x(),ra[0].get_min(),ra[0].get_max(),50,i-50);
				int y = (int) scale(n.getLocation().y(),ra[1].get_min(),ra[1].get_max(),70,j-70);


				g.fillOval(x,y, 10, 10);

				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 13));
				g.drawString(n.getKey()+"", x+10, y+10);

				if(Arena.getGame_center().getGraph().getE(n.getKey())!=null) {
					for(edge_data edge: Arena.getGame_center().getGraph().getE(n.getKey())) {


						if(Arena.getGame_center().getGraph().getNode(edge.getDest())!=null) {
							Point3D p = Arena.getGame_center().getGraph().getNode(edge.getDest()).getLocation();



							int x1 = (int) scale(p.x(),ra[0].get_min(),ra[0].get_max(),50,i-50);
							int y1 = (int) scale(p.y(),ra[1].get_min(),ra[1].get_max(),70,j-70);


							g.setColor(Color.GREEN);
							g.setFont(new Font("Arial", Font.BOLD, 10));
							g.drawLine(x+5, y+5,x1+5, y1+5);
							g.setColor(Color.RED);
							x1=(int)((((x+x1)/2+x1)/2+x1)/2+x1)/2;
							y1=(int)((((y+y1)/2+y1)/2+y1)/2+y1)/2;
							g.fillOval(x1, y1, 7, 7);
						}
					}
				}
			}
		}

	}



	private Range[] range() {
		double min_x=Double.POSITIVE_INFINITY;
		double max_x=Double.NEGATIVE_INFINITY;
		double min_y=Double.POSITIVE_INFINITY;
		double max_y=Double.NEGATIVE_INFINITY;
		if(Arena.getGame_center().getGraph().getV()!=null) {
			for(node_data n: Arena.getGame_center().getGraph().getV()) {
				Point3D p = n.getLocation();

				min_x = Math.min(min_x, p.x());
				max_x = Math.max(max_x, p.x());
				min_y = Math.min(min_y, p.y());
				max_y = Math.max(max_y, p.y());
			}
			Range ra[] = new Range[2];
			ra[0] = new Range(min_x,max_x);
			ra[1] = new Range(min_y,max_y);	
			return ra;
		}
		return null;
	}



	private int scenario() {
		String scenario = JOptionPane.showInputDialog("Please insert scenario [0,23]");
		try {
			int sce = Integer.parseInt(scenario);
			if(sce>23||sce<0) {
				JOptionPane.showMessageDialog(null, "Invalild input");
				return scenario();
			}

			return sce;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Invalild input");
			return scenario();
		}

	}





	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		Range[] ra = range();
		int id,src=-2,deviation=10;

		if(chooseRobot) {
			id=-1;
			src =-1;
			tempRobot = null;
		}
		if(chooseRobot) {
			if (e.getClickCount() == 2) {

				int x1 = e.getX();
				int y1 = e.getY();

				for(Robot r :Arena.getGame_center().getGame_algo().getRobots()) {
					int xR = (int) scale(r.getPos()[0].x(),ra[0].get_min(),ra[0].get_max(),50,750);
					int yR = (int) scale(r.getPos()[0].y(),ra[1].get_min(),ra[1].get_max(),70,550-70);
					if(Math.abs(x1-xR)<deviation&&Math.abs(y1-yR)<deviation){
						chooseRobot=false;
						id=r.getId();
						src=r.getSrc();
						tempRobot=r;


						break;
					}
				}
			}
		}

		if(src!=-1) {
			if (e.getClickCount()%2 == 0) {
				int x1 = e.getX();
				int y1 = e.getY();
				for(node_data n: Arena.getGame_center().getGraph().getV()) {
					int x = (int) scale(n.getLocation().x(),ra[0].get_min(),ra[0].get_max(),50,750);
					int y = (int) scale(n.getLocation().y(),ra[1].get_min(),ra[1].get_max(),70,550-70);

					if(Math.abs(x1-x)<deviation&&Math.abs(y1-y)<deviation){

						for(edge_data edge: Arena.getGame_center().getGraph().getE(tempRobot.getSrc())) {

							Point3D node_x	= Arena.getGame_center().getGraph().getNode(edge.getDest()).getLocation();
							Point3D node_y	= Arena.getGame_center().getGraph().getNode(edge.getDest()).getLocation();

							int xN = (int) scale(node_x.x(),ra[0].get_min(),ra[0].get_max(),50,750);
							int yN = (int) scale(node_y.y(),ra[1].get_min(),ra[1].get_max(),70,550-70);


							if(Math.abs(x-xN)<deviation&&Math.abs(y-yN)<deviation) {
								Arena.getGame_center().getGame().chooseNextEdge(tempRobot.getId(), edge.getDest());

								chooseRobot=true;

							}
						}


					}
				}


			}
		}


	}
	private void changeThread() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					synchronized (Arena.getGame_center()) {
						if (Arena.getGame_center().getGraph() != null && Arena.getGame_center().getGraph().getMC() != Arena.getChange()) {

							Arena.setChange(Arena.getGame_center().getGraph().getMC());
							repaint();
						}
					}
				}
			}
		});
		t.start();
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
		String s = e.getActionCommand();

		if(s.equals("Scenario")) {
			int sce =	scenario();


			String manual = JOptionPane.showInputDialog("Do you want play manual ? y/n");
			Thread t2 = new Thread(new Runnable() {
				public void run() {

					while(Arena.getGame_center().getGame().isRunning()) {

					}

					repaint();
				}
			});

			if(manual.equals("y")) {
				this.manual=true;
				this.Arena.setGame_center(new Game_Center(sce),this.manual);
				Thread t1 =new Thread(this.Arena.getGame_center());
				Arena.setChange(Arena.getGraphChange());
				t1.start();
				t2.start();
				repaint();
			}else if(manual.equals("n")) {
				this.manual=false;
				this.Arena.setGame_center(new Game_Center(sce),this.manual);
				Thread t1 =new Thread(this.Arena.getGame_center());
				Arena.setChange(Arena.getGraphChange());
				t1.start();
				t2.start();
				repaint();

			}else {
				JOptionPane.showMessageDialog(null, "Invalild input");
			}



		}




	}
}
