package sprites;

// JFC
import java.awt.image.BufferedImage;

// GTGE
import RPG.RPGGame;

import com.golden.gamedev.object.Timer;
import com.golden.gamedev.util.Utility;


public class NPC extends RPGSprite {

	Timer 			moveTimer;
	//LogicUpdater	logic;
	public String[]		dialog;
	public int ID;


	public NPC(RPGGame owner, BufferedImage[] images, int tileX, int tileY,
			int moveSpeed, int direction, int frequence, String[] dialog, int id) {
		super(owner,images,tileX,tileY,moveSpeed,direction);

		moveTimer = new Timer((8-frequence)*1500);
		if (moveTimer.getDelay() == 0) {
			// it's not good to set the timer delay to 0!
			moveTimer.setDelay(10);
		}

		//this.logic = logic;
		this.dialog = dialog;
		ID = id;
	}


	protected void updateLogic(long elapsedTime) {
		if (owner.gameState == RPGGame.PLAYING) {
			// if playing then
			// update the npc based on its logic updater
			if (moveTimer.action(elapsedTime)) {
				//logic.updateLogic(this, elapsedTime);
			}
		}
	}

}
