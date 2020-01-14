package utils;
import java.util.Comparator;

import dataStructure.node_data; 
public class compNode implements Comparator<node_data> {

	/**
	 * Compare between two nodes on their weight
	 */
	@Override
	public int compare(node_data o1, node_data o2) {
		return (int)(o1.getWeight()-o2.getWeight());
		
	}
}
