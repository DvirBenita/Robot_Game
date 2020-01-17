package gameClient;

import java.util.Comparator;

public class Fruit_Comp implements Comparator<Fruit> {

	
	public Fruit_Comp() {
		
	}
	/**
	 * compare between 2 fruits by value
	 */
	@Override
	public int compare(Fruit o1, Fruit o2) {
		
		if( o1.getVal()-o2.getVal()<0) {
			return (int) (o1.getVal()-o2.getVal()-1);
		}else if(o1.getVal()-o2.getVal()>0) {
			return (int) (o1.getVal()-o2.getVal()+1);
		}
		return 0;
		
		
	}

}
