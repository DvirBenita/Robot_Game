package utils;

import algorithms.Game_Algo;
import gameClient.Game_Center;

public class Arena {

	private Game_Center game_center;
	private Game_Algo game_algo;
	private int change;
	
	public Arena() {
		game_center=null;
		
		
	}
	
	public Game_Center getGame_center() {
		return game_center;
	}
	public void setGame_center(Game_Center game_center, boolean manual) {
		this.game_center = game_center;
		this.game_center.setManual(manual);
		this.change=game_center.getGraph().getMC();
		
	}
	public Game_Algo getGame_algo() {
		return game_algo;
	}
	public void setGame_algo(Game_Algo game_algo) {
		this.game_algo = game_algo;
	}
	public int getChange() {
		return change;
	}
	public int getGraphChange() {
		return game_center.getGraph().getMC();
	}
	public void setChange(int change) {
		this.change = change;
	}
	
	
	
	
	

	
	

}
