package com.pedrinhojogos.main;

import java.awt.Canvas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javax.swing.JFrame;

import com.pedrinhojogos.entities.Cano;
import com.pedrinhojogos.entities.Player;
import com.pedrinhojogos.graficos.SpriteSheet;
import com.pedrinhojogos.graficos.UI;
import com.pedrinhojogos.world.Tile;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	public static final int WIDGHT = 120;
	public static final int HEIGTH = 200;
	public static final int SCALE = 5;

	private BufferedImage image;

	public static Player player;
	
	public static List<Tile> chao;
	public static Cano cano;
	
	public UI ui;

	public static String gameState = "NORMAL";

	public static SpriteSheet spritesheet;

	public static Random rand;
	
	public static final double GRAVITYTOTAL = 10;
	
	public static int score = 0;
	
	private boolean gameOverEnter = false;
	

	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDGHT * SCALE, HEIGTH * SCALE));
		initFrame();

		image = new BufferedImage(WIDGHT, HEIGTH, BufferedImage.TYPE_INT_RGB);
		spritesheet = new SpriteSheet("/spritesheet.png");
		ui = new UI();
		player = new Player(Game.HEIGTH/2, 16,16);
		chao = new ArrayList<Tile>();
		Tile chao1 = new Tile(0);
		Tile chao2 = new Tile(112);
		Tile chao3 = new Tile(224);
		chao.add(chao1);
		chao.add(chao2);
		chao.add(chao3);
		int y = rand.nextInt(HEIGTH-100);
		cano = new Cano(WIDGHT+100,y+50);
	}

	public static void main(String arg[]) {
		Game game = new Game();
		game.start();
	}

	public void initFrame() {

		frame = new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.run();
	}

	public synchronized void stop() {

	}

	public static void RestartGame() {
		gameState = "NORMAL";
		Game.spritesheet = new SpriteSheet("/spritesheet.png");
		chao.clear();
		player = new Player(Game.HEIGTH/2, 16,16);
		chao = new ArrayList<Tile>();
		Tile chao1 = new Tile(0);
		Tile chao2 = new Tile(112);
		Tile chao3 = new Tile(224);
		chao.add(chao1);
		chao.add(chao2);
		chao.add(chao3);
		int y = rand.nextInt(HEIGTH-100);
		cano = new Cano(WIDGHT+100,y+50);
		score = 0;
		return;
	}

	public void tick() {
		if (gameState == "NORMAL") {
			if(!cano.Passou() && player.getX() > cano.getX()) {
				cano.passado = true;
				Game.score++;
			}
			player.tick();
			for(int i = 0; i < chao.size(); i++) {
				if(chao.get(i).tick()) {
					Tile novochao = new Tile(chao.get(i).getX() + 3*112);
					chao.add(novochao);
					chao.remove(i);
					
				}
			}
				if(cano.tick()) {
					int y = rand.nextInt(HEIGTH-100);
					cano = new Cano(WIDGHT,y+50);
				}
			
		
			}
		else if(gameState == "GAMEOVER") {
			player.tick();
			if(gameOverEnter) {
				gameOverEnter = false;
				RestartGame();
			}
		}
		
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDGHT, HEIGTH);
		cano.render(g);
		ui.render(g);
		for(int i = 0; i < chao.size(); i++)
			chao.get(i).render(g);
		player.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, SCALE * WIDGHT, SCALE * HEIGTH, null);
		if (gameState == "GAMEOVER") {
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.white);
			g2.setFont(new Font("arial", Font.BOLD, 90));
			g2.drawString("GAMEOVER", (WIDGHT * SCALE) / 2 - 280 , (HEIGTH * SCALE) / 2);
			g2.setColor(Color.white);
			g2.setFont(new Font("arial", Font.BOLD, 20));
			
			g2.drawString("Clique Espaço  pra continuar", (WIDGHT * SCALE) / 2 - 150, (HEIGTH * SCALE) / 2 + 40);

		}
		
		
		bs.show();
		

	}

	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfticks = 60.0;
		double ns = 1000000000 / amountOfticks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				delta--;
				tick();
				render();
				frames++;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!Game.player.moved && e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(Game.gameState == "NORMAL")
				Game.player.moved  = true;
			else if(Game.gameState == "GAMEOVER")
				this.gameOverEnter = true;
		} 
		

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
