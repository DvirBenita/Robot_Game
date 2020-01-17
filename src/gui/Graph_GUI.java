package gui;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;

import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.nodeData;

public class Graph_GUI extends JFrame implements ActionListener,MouseListener{

	private graph Graph;
	private  Graph_Algo graphAlgo;
	private boolean[] algo = new boolean[7];
	private JTextField t1;
	private JTextField t2; 
	private String[] text = new String[2];
	private JFrame f;
	private Graphics g;
	private int mc;


	/**
	 * Constractor to gui
	 */
	public Graph_GUI(graph gr) {
		if(gr==null) {
			throw new RuntimeException("Cannot init null graph sorry!");
		}
		Graph =gr;
		graphAlgo = new Graph_Algo(gr);
		initGui();


	}

	/**
	 * Init the gui
	 */
	private void initGui() {
		setTitle("Graph");
		this.setSize(900, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MenuBar menu = new MenuBar();
		Menu m = new Menu("Menu");
		Menu algo = new Menu("Algorithms");

		menu.add(m);
		menu.add(algo);
		
		this.setMenuBar(menu);
		RandomL(800, 650);
		MenuItem save = new MenuItem("Save");
		MenuItem open = new MenuItem("Open File");
		MenuItem isConnect= new MenuItem("isConnected");
		addMouseListener(this);
		
		MenuItem ShotDist = new MenuItem("shortestPathDist");
		MenuItem shortPath = new MenuItem("shortestPath");
		MenuItem TSP = new MenuItem("TSP");

		save.addActionListener(this);
		open.addActionListener(this);
		isConnect.addActionListener(this);
		ShotDist.addActionListener(this);
		shortPath.addActionListener(this);
		TSP.addActionListener(this);
		m.add(save);
		m.add(open);
		algo.add(TSP);
		algo.add(shortPath);
		algo.add(ShotDist);
		algo.add(isConnect);
		setVisible(true);
		initThread();
	}
	/**
	 * Function to draw the graph on the JFrame
	 */
	public void paint(Graphics g) {
		try {
			super.paint(g);
			this.g=g;
			for(node_data n : this.Graph.getV()) {
				g.setColor(Color.BLUE);
				int x =n.getLocation().ix();
				int y = n.getLocation().iy();
				g.fillOval(x,y, 10, 10);

				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 13));
				g.drawString(n.getKey()+"", x+10, y+10);

				if(Graph.getE(n.getKey())!=null) {
					for(edge_data edge: Graph.getE(n.getKey())) {
						if(this.Graph.getNode(edge.getDest())!=null) {
							Point3D p = this.Graph.getNode(edge.getDest()).getLocation();

							int x1 = p.ix();
							int y1 = p .iy();

							g.setColor(Color.GREEN);
							g.setFont(new Font("Arial", Font.BOLD, 10));
							g.drawLine(x+5, y+5,x1+5, y1+5);
							g.setColor(Color.RED);
							g.drawString(""+edge.getWeight(), (int)(x+x1)/2-3,(int)((y+y1)/2-3));
							g.setColor(Color.RED);
							x1=(int)((((x+x1)/2+x1)/2+x1)/2+x1)/2;
							y1=(int)((((y+y1)/2+y1)/2+y1)/2+y1)/2;
							g.fillOval(x1, y1, 7, 7);
						}
					}
				}
			}











			if(this.algo[0]) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 16));
				g.drawString("Connect status :"+this.graphAlgo.isConnected(),30,620);
				algo[0]=false;
			}

			if(algo[1]) {
				BottonFun("Enter", this);
				algo[1]=false;
			}
			if(algo[2]) {
				BottonFun("Insert", this);
				algo[2]=false;
			}
			if(algo[3]) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 16));

				g.setColor(Color.RED);

				f = new JFrame("textfield"); 
				JLabel    src = new JLabel("Enter keys you want to visit like this : 1,3,5..  "); 

				JButton botton = new JButton("OK");
				botton.addActionListener(this); 
				t1 = new JTextField(15); 


				JPanel p = new JPanel(); 
				p.add(src);
				p.add(t1); 



				p.add(botton);
				f.add(p); 
				f.setSize(300, 150); 
				f.setVisible(true);
				algo[3]=false;
			}

			if(algo[4]) {
				algo[4]=false;
				try {
					int src = Integer.parseInt(text[0]);
					int dst = Integer.parseInt(text[1]);
					double dis = this.graphAlgo.shortestPathDist(src, dst);
					g.setFont(new Font("Arial", Font.BOLD, 16));
					g.setColor(Color.GREEN);
					g.drawString("Shortest path distance is :"+dis,30,620);
				}catch(Exception e) {

				}
				algo[4]=false;
			}
			if(algo[5]) {
				algo[5]=false;
				try {

					int src = Integer.parseInt(text[0]);
					int dst = Integer.parseInt(text[1]);
					g.setFont(new Font("Arial", Font.BOLD, 13));
					g.setColor(Color.RED);
					double dis = this.graphAlgo.shortestPathDist(src, dst);
					if(dis==Double.POSITIVE_INFINITY) {
						g.drawString("There is no path to "+dst,30,620);
					}
					g.drawString("Shortest path is :"+toS(this.graphAlgo.shortestPath(src, dst)),30,620);
				}catch(Exception e) {

				}

				algo[5]=false;
			}
			if(algo[6]) {
				algo[6]=false;
				boolean f=true;
				try {
					String arr[] = text[0].split(",");
					g.setFont(new Font("Arial", Font.BOLD, 13));
					g.setColor(Color.RED);
					List<Integer> list = new ArrayList<Integer>();
					for(int i=0;i<arr.length;i++) {
						try {

							int current = Integer.parseInt(arr[i]);
							list.add(current);

						}catch (Exception E) {
							f=false;
							g.drawString("Invaild input",30,620);
						}
					}
					if(f)
						g.drawString("TSP :"+toS(this.graphAlgo.TSP(list)),30,620);

				}catch(Exception e) {

				}
				algo[6]=false;
			}
		}catch(Exception e) {
		}

	}
	/**
	 * Button to Jfarme
	 * @param s
	 * @param t
	 */
	private  void BottonFun(String s,Graph_GUI t) {

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 16));

		g.setColor(Color.RED);

		f = new JFrame("textfield"); 
		JLabel    src = new JLabel("       Source    "); 
		JLabel    dst = new JLabel("  Destination         "); 
		JButton botton = new JButton(s);
		botton.addActionListener(t); 
		t1 = new JTextField(13); 

		t2 = new JTextField(12);
		JPanel p = new JPanel(); 
		p.add(t1); 
		p.add(src);
		p.add(t2);
		p.add(dst);
		p.add(botton);
		f.add(p); 
		f.setSize(250, 150); 
		f.setVisible(true);
	}
	/**
	 * Function to return string of list with -> between the elements
	 * @param list
	 * @return
	 */
	private static String toS(List<node_data> list) {

		if(list==null) {
			JFrame frame = new JFrame("Messege");
			JOptionPane.showMessageDialog(frame, "there is no path", "messge",
					JOptionPane.INFORMATION_MESSAGE);
		}
		String s ="";
		for(int i =0;i<list.size();i++) {
			if(i!=list.size()-1) {
				s+=list.get(i).getKey()+"->";
			}else {
				s+=list.get(i).getKey();
			}
		}


		return s;

	}
	/**
	 * Random location to vertex
	 * @param height
	 * @param width
	 */
	public void RandomL(int height, int width) {

		for (node_data n : Graph.getV()) {
			double x = Math.random() * (width+100)+100;
			double y = (Math.random() * (height)-40);
			if((x>60)&&(y>100&&y<height-200)) {
				n.setLocation(new Point3D(x, y));
			}else {
				if(!(x>60)) {
					x+=60;
				}
				if(!(y>100)) {
					y+=100;
				}
				if(!(y<height-200)) {
					y=y-250;
				}

				n.setLocation(new Point3D(x, y));
			}

		}
	}
	/**
	 * Override action listener function , to listen the menu clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		if(s.compareTo("Save")==0) {
			FileDialog saver = new FileDialog(this, "Save", FileDialog.SAVE);
			saver.setVisible(true);
			String nameFile = saver.getDirectory()+saver.getFile();
			this.graphAlgo.save(nameFile);
		}else if(s.compareTo("isConnected")==0){

			algo[0]= true;
			repaint();	 
		}else if(s.compareTo("shortestPathDist")==0) {
			algo[1] = true;
			repaint();
		}else if(s.compareTo("shortestPath")==0) {
			algo[2] = true;
			repaint();
		}else if(s.compareTo("TSP")==0) {
			algo[3]=true;
			repaint();
		}else if(s.equals("Enter")){
			try {
				stringBotton(4);
			}catch( Exception e12){

			}

			repaint();

		}else if(s.equals("Open File")) {
			FileDialog opener = new FileDialog(this, "Save", FileDialog.LOAD);
			opener.setVisible(true);
			String nameFile = opener.getDirectory()+opener.getFile();
			this.graphAlgo.init(nameFile);
			this.Graph = this.graphAlgo.getGraph();
			repaint();
		}else if(s.equals("Insert")){
			try {
				stringBotton(5);
			}catch( Exception e12){

			}

			repaint();

		}else if(s.equals("OK")){
			try {

				text[0]=t1.getText();
				t1.setText(" ");
				algo[6]=true;
				f.setVisible(false);
				repaint();
			}catch( Exception e12){

			}

			repaint();

		}


	}

	/**
	 * This function reboots a button
	 * @param i
	 */
	private void stringBotton(int i) {
		text[0]=t1.getText();
		t1.setText(" ");
		text[1]=t2.getText();
		t2.setText(" ");
		algo[i]=true;
		f.setVisible(false);
		repaint();	
	}
	public int getMc() {
		return mc;
	}

	public void setMc(int mc) {
		this.mc = mc;
	}
	public void addEdge() {
		try {
			JFrame frame = new JFrame("Add Edge");
			JLabel src_lbl = new JLabel("src:  ");
			JLabel dest_lbl = new JLabel("dest: ");
			JLabel w_lbl = new JLabel("weight: ");
			JTextField src_text = new JTextField(13);
			JTextField dest_text = new JTextField(13);
			JTextField w_text = new JTextField(11);
			JButton btn = new JButton("Add Edge");

			frame.setVisible(true);
			frame.setLayout(new FlowLayout());
			frame.setBounds(200, 0, 200, 200);
			frame.add(src_lbl);
			frame.add(src_text);
			frame.add(dest_lbl);
			frame.add(dest_text);
			frame.add(w_lbl);
			frame.add(w_text);
			frame.add(btn);

			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int src = Integer.parseInt(src_text.getText());
						int dest = Integer.parseInt(dest_text.getText());
						double w = Double.parseDouble(w_text.getText());
						Graph.connect(src, dest, w);
						repaint();
						frame.setVisible(false);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frame, e1.getMessage(), "Add Edge", JOptionPane.ERROR_MESSAGE);
					}
				}

			});
		}catch(Exception e) {
			g.drawString("Invaild input",30,620);
		}
	}
	


	private void initThread() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {

				while(true) {

					synchronized(Graph) {

						if(Graph.getMC()!=getMc()) {
							setMc(Graph.getMC());
							repaint();
						}

					}

				}


			}
		}) ;
		t1.start();
	}


	@Override
	public void mouseClicked(MouseEvent e) {


	}

	@Override
	public void mouseReleased(MouseEvent e) {


	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {


	}
	@Override
	public void mousePressed(MouseEvent e) {

	}

}

