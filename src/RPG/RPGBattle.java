package RPG;

// JFC
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// GTGE
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.Timer;


public class RPGBattle {

	GameFont		font;
	BufferedImage	box;

	String[] 		dialog;
	int				frame;
	int				totalFrame;
	Timer			speed;

	boolean			endBattle;

	int				y;
	
	public long 	health;
	public int 		multiplier;
	BufferedImage	sprite;
	public long 	delay = (long)2000.0;
	public long		hAtk = (long) 0.0;
	public long		eAtk = (long) 0.0;
	BufferedImage	atk;


	public RPGBattle(GameFont font, BufferedImage box, BufferedImage sprite, BufferedImage atk, int mult) {
		this.font = font;
		this.box = box;
		this.sprite = sprite;
		this.atk = atk;
		multiplier = mult;
		health = (long)(20000*mult);
		if(mult > 4)
			health = (long)(50000*mult);

		speed = new Timer(20);
			
	}
	
	public void updateHealth(long time){
		health -= time;
		delay -= time;
		hAtk -= time;
		eAtk -= time;
	}
	public int getHealth(){
		return (int) health/1000;
	}

	public void update(long elapsedTime) {
		updateHealth(elapsedTime);
		if(health<0)
			endBattle = true;
	}


	public void render(Graphics2D g) {
		// render the box
		g.drawImage(box, 0, 0, null);
		g.drawImage(sprite, 440, 100, null);
		if(hAtk > 0){
			if(multiplier<5)
				g.drawImage(atk, 350, 50, null);
			else
				g.drawImage(atk, 380, 130, null);
		}
		if(eAtk > 0)
			g.drawImage(atk, 0, 200, null);
		font.drawString(g, "ENEMY TIME: "+getHealth(), 400, 0);
	}


}