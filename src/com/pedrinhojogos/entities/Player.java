package com.pedrinhojogos.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.pedrinhojogos.main.Game;

public class Player extends Entity {

	public boolean morto = false;
	public boolean moved = false;
	private double force = 0;
	private static final double MAXFORCE = -2 ;
	
	public static BufferedImage PLAYER_FRONT = Game.spritesheet.getSprite(0, 0, 16, 16);
	
	
	public Player(int y, int width, int height) {
		super(Game.WIDGHT/2, y, width, height, PLAYER_FRONT);
		
	}

	public void tick() {
		CheckCollisionCano();
		if(moved) {
			force -= 4;
			if(force < MAXFORCE)
				force = MAXFORCE;
			moved = false;
		}
		else
			if (force < Game.GRAVITYTOTAL) {
				force += 0.1;
			}
		
		y += force;
		if(y >= Game.HEIGTH-15) {
			y = Game.HEIGTH-15;
			Game.gameState = "GAMEOVER";
			force = 0;
			this.morto = true;
		}

	}

	private void CheckCollisionCano() {
		// TODO Auto-generated method stub
			if(!morto && isColidding(this,Game.cano)) {
				Game.gameState = "GAMEOVER";
				force = 0;
				this.morto = true;
			}
		
	}
	
	private static boolean isColidding(Entity e1, Cano e2) {
		Rectangle c1 = e1.createCollisionBox();
		Rectangle c2 = e2.createCollisionBox(false);
		Rectangle c3 = e2.createCollisionBox(true);

		return c1.intersects(c2) || c1.intersects(c3);
	}

	@Override
	public void render(Graphics g) {

		g.drawImage(PLAYER_FRONT,(int) x ,(int) y , null);
		
	}
	
	

}
