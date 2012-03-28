package title;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import RPG.RPGLoader;

import com.golden.gamedev.*;
import com.golden.gamedev.object.*;
import com.golden.gamedev.object.background.*;


public class LoseTitle extends GameObject {
	
	GameFont		font;

	BufferedImage	title;
	BufferedImage	arrow;

	int				option;

	//boolean			blink;
	//Timer			blinkTimer = new Timer(400);

	public LoseTitle(GameEngine ge) {
		super(ge);
	}

	@Override
	public void initResources() {
		title = getImage("gfx/Title.png", false);
		arrow = getImage("gfx/Arrow.png");

		font = fontManager.getFont(getImage("gfx/BitmapFont.png"));
		
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(title, 0, 0, null);
		font.drawString(g, "YOU DIED. WHAT DO?", 200, 100);
		font.drawString(g, "RESTART", 320, 200);
		font.drawString(g, "END", 320, 220);

		font.drawString(g, "\"Z\" FOR MAIN ACTION, \"X\" FOR SECONDARY ACTION", 10, 460);

		g.drawImage(arrow, 304, 195+(option*20), null);
		
	}

	@Override
	public void update(long elapsedTime) {

		switch (bsInput.getKeyPressed()) {
			case KeyEvent.VK_Z :
				if (option == 0) {
					// start
					parent.nextGameID = RPGLoader.GAME;
					finish();

				} else if (option == 1) {
					// end
					finish();

				}
			break;

			case KeyEvent.VK_UP :
				option--;
				if (option < 0) option = 1;
			break;

			case KeyEvent.VK_DOWN :
				option++;
				if (option > 1) option = 0;
			break;

			case KeyEvent.VK_ESCAPE :
				finish();
			break;
		}
		
	}


}
