package com.funstuff;

import java.util.ArrayList;

public class PlayerEntity {
	int x;
	int y;
	String UUID;
	String ip;
	ArrayList<Projectile> projectiles;
	
	public PlayerEntity(int x, int y,String UUID ,String ip){
		this.x=x;
		this.y=y;
		this.UUID = UUID;
		this.ip=ip;
		this.projectiles=null;
	}
	public PlayerEntity(){
		
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}
	
}
