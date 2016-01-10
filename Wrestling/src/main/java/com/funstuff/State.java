package com.funstuff;

import java.util.ArrayList;

import com.owlike.genson.Genson;

public class State extends Thread{
	
	public ArrayList<PlayerEntity> players = new ArrayList<PlayerEntity>();
	Genson gen = new Genson();
	public void run(){
		while(true){
			try{
				Thread.sleep(1000);
				System.out.println(gen.serialize(players));
			}catch(Exception e){
				
			}
		}
	}
	public ArrayList<PlayerEntity> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<PlayerEntity> players) {
		this.players = players;
	}
	

}
