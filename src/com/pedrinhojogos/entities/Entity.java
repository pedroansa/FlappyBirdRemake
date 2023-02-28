package com.pedrinhojogos.entities;

import java.awt.Graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.pedrinhojogos.main.Game;


public class Entity {
	protected double x;
	protected double y;
	public double speed = 1;
	private int width;
	private int height;

	private BufferedImage rightsprite;	

	public Entity(int x, int y, int width, int height, BufferedImage spriter) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rightsprite = spriter;
	}

	void Atacar(Entity en) {
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void render(Graphics g) {
		g.drawImage(rightsprite, this.getX() , this.getY() , null);
	}

	public Rectangle createCollisionBox(int x, int y) {
		Rectangle collisionBox = new Rectangle(x , y , width, height);
		return collisionBox;
	}
	
	public Rectangle createCollisionBox(int x, int y, int width, int height) {
		Rectangle collisionBox = new Rectangle(x , y , width, height);
		return collisionBox;
	}
	

	public static double Distance(Entity e1, Entity e2) {
		int x1 = e1.getX();
		int x2 = e2.getX();
		int y1 = e1.getY();
		int y2 = e2.getY();
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public Rectangle createCollisionBox() {
		Rectangle collisionBox = new Rectangle(getX(), getY(), width, height);
		return collisionBox;
	}
	
}
