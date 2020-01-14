package utils;

public class fruit {
	
		public fruit(double parseDouble, double parseDouble2, int type2, double value2) {
				pos = new double[2];
				pos[0]=parseDouble;
				pos[1]=parseDouble2;
				type = type2;
				value = value2;
	}
		public double getValue() {
			return value;
		}
		public void setValue(double value) {
			this.value = value;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public double[] getPos() {
			return pos;
		}
		public void setPos(double[] pos) {
			this.pos = pos;
		}
		double value;
		int type;
		double pos[]; 
	
}
