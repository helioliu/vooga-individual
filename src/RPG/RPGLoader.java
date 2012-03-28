package RPG;

import java.awt.Graphics2D;

import title.LoseTitle;
import title.Title;
import title.WinTitle;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;

public class RPGLoader extends GameEngine{
	
	{ distribute = true; }

	public static final int	TITLE = 0, GAME = 1, WIN_TITLE = 2, LOSE_TITLE = 3;

	public void initResources() {
		nextGameID = TITLE;
	}

	public GameObject getGame(int GameID) {
		switch (GameID) {
			case TITLE : return new Title(this);
			case GAME : return new RPGGame(this);
			case WIN_TITLE : return new WinTitle(this);
			case LOSE_TITLE : return new LoseTitle(this);
		}

		return null;
	}

}
