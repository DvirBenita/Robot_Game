package gameClient;

import java.util.Comparator;

public class Fruit_Comp implements Comparator<Fruit> {

	Robot r;
	public Fruit_Comp(Robot r) {
		this.r=r;
	}
	/**
	 * compare between 2 fruits by value
	 */
	@Override
	public int compare(Fruit o1, Fruit o2) {

		
		if(r.getPos()[0].distance2D(o1.getPos()[0])<r.getPos()[0].distance2D(o2.getPos()[0])){
			return -1;
		}else if(r.getPos()[0].distance2D(o1.getPos()[0])>r.getPos()[0].distance2D(o2.getPos()[0])) {
			return 1;
		}
		return 0;
	
		
		
		
	}

}
