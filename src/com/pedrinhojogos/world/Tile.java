package com.pedrinhojogos.world;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import com.pedrinhojogos.main.Game;

public class Tile {
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(48, 0, 112, 16);
	
	private BufferedImage sprite;
	private double x;
	private final double speed = 0.5;
	
	public Tile(double x) {
		this.x = x;
		this.sprite = TILE_FLOOR;
	}
	
	public boolean tick () {
		x--;
		if(x + 112 < 0)
			return true;
		return false;
		
	}
	
	public double getX() {
		return x;
	}
	
	public void render (Graphics g) {
		g.drawImage(sprite, (int) x, 185, null);
		
	}
}
