package gameClient;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import utils.Point3D;

public class KML_Logger {

	private StringBuilder KML;
	int scenario;


	public KML_Logger(int scenario) {
		KML = new StringBuilder();
		KML();
		this.scenario=scenario;
	}

	public void KML() {

		KML.append( 	"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
				"  <Document>\r\n" + 
				"    <name> "+  "Hw3 scenario:"+scenario+ "</name>" +
				"	 <Style id=\"node\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/pal3/icon49.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>" +
				"	 <Style id=\"fruit-banana\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/pal4/icon41.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>" +
				"	 <Style id=\"fruit-apple\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/pal4/icon40.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>" +
				"	 <Style id=\"robot\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/shapes/play.png</href>\r\n" + 
				"        </Icon>\r\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>"
				);


	}



	public void add(java.util.Date date, String pos, String typ) {

		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'z'");


		KML.append("<Placemark>\r\n" + 
				"      <TimeStamp>\r\n" + 
				"        <when>"+d.format(date)+"</when>\r\n" + 
				"      </TimeStamp>\r\n" + 
				"      <styleUrl>#"+typ+"</styleUrl>\r\n" + 
				"      <Point>\r\n" + 
				"        <coordinates>"+pos+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>"
				);

	}

	public void done() {
		KML.append(
				"  \r\n</Document>\r\n" + 
						"</kml>"
				);
		try {
			PrintWriter pw = new PrintWriter(new File("data/" + scenario + ".kml"));
			pw.write(KML.toString());
			pw.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	

}
