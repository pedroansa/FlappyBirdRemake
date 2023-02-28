package com.pedrinhojogos.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrinhojogos.main.Game;

public class Cano extends Entity{
	
	public static BufferedImage CANO_UP = Game.spritesheet.getSprite(21, 5, 24, 143);
	public static BufferedImage CANO_DOWN = Game.spritesheet.getSprite(47, 16, 24, 143);
	private int espaco = 20;
	public boolean passado = false;
	
	public Cano(int x, int y) {
		super(x, y, 24, 143, CANO_UP);
		// TODO Auto-generated constructor stub
	}
	
	public boolean tick () {
		x--;
		if(x + 24 < 0)
			return true;
		return false;
		
	}
	
	public boolean Passou() {
		return passado;
	}
	
	public Rectangle createCollisionBox(boolean z) {
		Rectangle collisionBox;
		if(z)
			collisionBox = this.createCollisionBox(getX()+5, getY()+espaco+5, getWidth()-5, getHeight());
		else
			collisionBox = this.createCollisionBox(getX()+5,getY()-this.getHeight()-espaco, this.getWidth()-5, this.getHeight()-5);
		return collisionBox;
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(CANO_UP,(int) x ,(int) (y + espaco) , null);
		g.drawImage(CANO_DOWN,(int) x ,(int) (y - this.getHeight() - espaco) , null);
	}

}
