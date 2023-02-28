package com.pedrinhojogos.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.pedrinhojogos.main.Game;

public class UI {
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,9));
		g.drawString(""+Game.score, 55, 25);
	}
}
