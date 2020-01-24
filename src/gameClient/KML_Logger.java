package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import utils.Point3D;

public class KML_Logger {

	public StringBuilder KML;
	int scenario;
	private static final String NODE_STYLE_ID = "node";
	private static final String BANANA_STYLE_ID = "fruit-banana";
	private static final String APPLE_STYLE_ID = "fruit-apple";
	private static final String ROBOT_STYLE_ID = "robot";

	public KML_Logger(int scenario) {
		KML = new StringBuilder();
		KML();
		this.scenario=scenario;
	}

	public void KML() {

		
		KML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		KML.append("<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n");
		KML.append("  <Document>\r\n");
		KML.append("    <name>stage: "+scenario+"</name>\r\n");
		KML.append("	 <Style id=\"" + NODE_STYLE_ID + "\">\r\n");
		KML.append("      <IconStyle>\r\n");
		KML.append("        <Icon>\r\n");
		KML.append(
				"          <href>http://maps.google.com/mapfiles/kml/shapes/placemark_circle_highlight.png</href>\r\n");
		KML.append("        </Icon>\r\n");
		KML.append("        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n");
		KML.append("      </IconStyle>\r\n");
		KML.append("    </Style>");
		KML.append("	 <Style id=\"" + BANANA_STYLE_ID + "\">\r\n");
		KML.append("      <IconStyle>\r\n");
		KML.append("        <Icon>\r\n");
		KML.append("          <href>http://maps.google.com/mapfiles/kml/paddle/ylw-circle.png</href>\r\n");
		KML.append("        </Icon>\r\n");
		KML.append("        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n");
		KML.append("      </IconStyle>\r\n");
		KML.append("    </Style>");
		KML.append("	 <Style id=\"" + APPLE_STYLE_ID + "\">\r\n");
		KML.append("      <IconStyle>\r\n");
		KML.append("        <Icon>\r\n");
		KML.append("          <href>http://maps.google.com/mapfiles/kml/paddle/red-circle.png</href>\r\n");
		KML.append("        </Icon>\r\n");
		KML.append("        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n");
		KML.append("      </IconStyle>\r\n");
		KML.append("    </Style>");
		KML.append("	 <Style id=\"" + ROBOT_STYLE_ID + "\">\r\n");
		KML.append("      <IconStyle>\r\n");
		KML.append("        <Icon>\r\n");
		KML.append("          <href>http://maps.google.com/mapfiles/kml/pal4/icon62.png</href>\r\n");
		KML.append("        </Icon>\r\n");
		KML.append("        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n");
		KML.append("      </IconStyle>\r\n");
		KML.append("    </Style>\r\n");


	}



	public void add(java.util.Date date, String pos, String typ) {

		LocalDateTime now = LocalDateTime.now();
		KML.append("    <Placemark>\r\n");
		KML.append("      <TimeStamp>\r\n");
		KML.append("        <when>" + now + "</when>\r\n");
		KML.append("      </TimeStamp>\r\n");
		KML.append("      <styleUrl>#" + typ + "</styleUrl>\r\n");
		KML.append("      <Point>\r\n");
		KML.append("        <coordinates>" + pos + "</coordinates>\r\n");
		KML.append("      </Point>\r\n");
		KML.append("    </Placemark>\r\n");

	}

	public void done() {
		KML.append("  </Document>\r\n");
		KML.append("</kml>");
		try {
			PrintWriter pw = new PrintWriter(new File("data/" + scenario + ".kml")); // change to save on data folder
																						// , and remove from git kmls
			pw.write(KML.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
	
	

}
