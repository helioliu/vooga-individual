package RPG;

// JFC
import items.Sword2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.Scanner;
import java.util.StringTokenizer;

import sprites.NPC;
import sprites.RPGSprite;

import maps.Map;

// GTGE
import com.golden.gamedev.GameObject;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.util.FileUtil;


public class RPGGame extends GameObject {

 /***************************** GAME STATE **********************************/

	public static final int PLAYING = 0, TALKING = 1, BATTLING = 2;
	public int gameState = PLAYING;

	GameFont		font;
	PlayField		playfield;
	public Map		map;
	RPGSprite		hero;
	SpriteGroup sg1 = new SpriteGroup("npc1");
	RPGSprite npc1, npc2;

	RPGDialogue		dialog;
	RPGBattle		battle;

	NPC				talkToNPC;			// the NPC we talk to
	int				talkToNPCDirection;	// old NPC direction before
										// we talk to him/her


 /****************************************************************************/
 /******************************* CONSTRUCTOR ********************************/
 /****************************************************************************/

	public RPGGame(GameEngine parent) {
		super(parent);
	}


	public void initResources() {
		playMusic("sound/music.mid");
		font = fontManager.getFont(getImage("gfx/BitmapFont.png"));
		map = new Map(bsLoader, bsIO, "gfx/floor0.lwr", "gfx/floor0.upr");
		hero = new RPGSprite(this, getImages("/gfx/hero.png",3 ,4), 10, 10, 3, RPGSprite.DOWN);
		playfield = new PlayField(map);
		playfield.setComparator(new Comparator() {
			public int compare(Object o1, Object o2) {
				// sort based on y-order
				return (int) (((Sprite) o1).getY()-((Sprite) o2).getY());
			}
		} );

		playfield.add(hero);

		dialog = new RPGDialogue(fontManager.getFont(getImage("gfx/BitmapFont.png")),
							   getImage("gfx/DialogBox.png", false));
		
		String[] npc1d = {"I'M NOT LONG FOR THIS WORLD... YOU'VE A SWORD AND",
				          "TIME BALL. DEFEAT THE MONSTER AND SAVE US FROM",
				          "THIS DISEASE BEFORE YOU TOO SUCCUMB."};
		npc1 = new NPC(this, getImages("gfx/npc1.png", 3, 4), 15, 5, 3, RPGSprite.DOWN,
				1, npc1d, 1);
		sg1.add(npc1);
		playfield.addGroup(sg1);
		RPGSprite lad1 = new NPC(this, getImages("gfx/ladder.png", 3, 4), 2, 12, 3, RPGSprite.DOWN, 1, null, 3);
		playfield.add(lad1);
	}


	public void update(long elapsedTime) {
		playfield.update(elapsedTime);
		hero.updateHealth(elapsedTime);


		switch (gameState) {
			// playing
			// moving hero : arrow key
			// talk to npc : Z
			case PLAYING:
				if (hero.getStatus() == RPGSprite.STANDING) {
					if (keyDown(KeyEvent.VK_LEFT)) {
						hero.walkTo(RPGSprite.LEFT, -1, 0);
						generateBattle();
					} else if (keyDown(KeyEvent.VK_RIGHT)) {
						hero.walkTo(RPGSprite.RIGHT, 1, 0);
						generateBattle();
					} else if (keyDown(KeyEvent.VK_UP)) {
						hero.walkTo(RPGSprite.UP, 0, -1);
						generateBattle();
					} else if (keyDown(KeyEvent.VK_DOWN)) {
						hero.walkTo(RPGSprite.DOWN, 0, 1);
						generateBattle();
					}


					// action key
					if (keyPressed(KeyEvent.VK_Z)) {
						int targetX = hero.tileX,
							targetY = hero.tileY;
						switch (hero.getDirection()) {
							case RPGSprite.LEFT:  targetX = hero.tileX - 1; break;
							case RPGSprite.RIGHT: targetX = hero.tileX + 1; break;
							case RPGSprite.UP:    targetY = hero.tileY - 1; break;
							case RPGSprite.DOWN:  targetY = hero.tileY + 1; break;
						}

						talkToNPC = (NPC) map.getLayer3(targetX, targetY);
						
						//code to check what the npc is
						

						if (talkToNPC != null && talkToNPC.dialog != null) {
							dialog.setDialog(talkToNPC.dialog,
								(hero.getScreenY()+hero.getHeight() < 320));

							// make NPC and hero, face to face!
							// we store the old NPC direction first
							talkToNPCDirection = talkToNPC.getDirection();
							switch (hero.getDirection()) {
								case RPGSprite.LEFT:  talkToNPC.setDirection(RPGSprite.RIGHT); break;
								case RPGSprite.RIGHT: talkToNPC.setDirection(RPGSprite.LEFT); break;
								case RPGSprite.UP:    talkToNPC.setDirection(RPGSprite.DOWN); break;
								case RPGSprite.DOWN:  talkToNPC.setDirection(RPGSprite.UP); break;
							}

							gameState = TALKING;
						}
						
						if(talkToNPC != null && talkToNPC.ID==3){
							map = new Map(bsLoader, bsIO, "gfx/floor1.lwr","gfx/floor1.upr");
							hero = new RPGSprite(hero, 2, 12);
							playfield = new PlayField(map);
							playfield.setComparator(new Comparator() {
								public int compare(Object o1, Object o2) {
									// sort based on y-order
									return (int) (((Sprite) o1).getY()-((Sprite) o2).getY());
								}
							} );
							playfield.add(hero);
							RPGSprite lad2 = new NPC(this, getImages("gfx/ladder.png", 3, 4), 17, 2, 1, RPGSprite.DOWN, 1, null, 4);
							playfield.add(lad2);
						}
						
						if(talkToNPC != null && talkToNPC.ID==4){
							map = new Map(bsLoader, bsIO, "gfx/floor1.lwr","gfx/floor2.upr");
							hero = new RPGSprite(hero, 17, 2);
							playfield = new PlayField(map);
							playfield.setComparator(new Comparator() {
								public int compare(Object o1, Object o2) {
									// sort based on y-order
									return (int) (((Sprite) o1).getY()-((Sprite) o2).getY());
								}
							} );
							playfield.add(hero);
							RPGSprite lad3 = new NPC(this, getImages("gfx/ladder.png", 3, 4), 14, 8, 8, RPGSprite.DOWN, 1, null, 5);
							playfield.add(lad3);
							String[] npc2d = {
									"YOU HAVE FOUND SWORD2! YOU QUICKLY DISCARD",
									"YOUR OLD SWORD FOR THIS NEW ONE. IT IS",
									"MARGINALLY MORE POWERFUL THAN YOUR OLD ONE",
									"BUT ALSO A BIT SLOWER."
									};
							npc2 = new NPC(this, getImages(
									"gfx/sword.png", 3, 4), 1, 13, 2,
									RPGSprite.DOWN, 1, npc2d, 2);
							sg1.remove(npc1);
							sg1.add(npc2);
							playfield.addGroup(sg1);
						}
						
						if(talkToNPC != null && talkToNPC.ID==5){
							map = new Map(bsLoader, bsIO, "gfx/floor1.lwr","gfx/floor3.upr");
							hero = new RPGSprite(hero, 14, 8);
							playfield = new PlayField(map);
							playfield.setComparator(new Comparator() {
								public int compare(Object o1, Object o2) {
									// sort based on y-order
									return (int) (((Sprite) o1).getY()-((Sprite) o2).getY());
								}
							} );
							playfield.add(hero);
							RPGSprite lad3 = new NPC(this, getImages("gfx/ladder.png", 3, 4), 10, 11, 1, RPGSprite.DOWN, 1, null, 6);
							playfield.add(lad3);
						}
						
						if(talkToNPC != null && talkToNPC.ID==6){
							map = new Map(bsLoader, bsIO, "gfx/floor1.lwr","gfx/floor4.upr");
							hero = new RPGSprite(hero, 10, 10);
							playfield = new PlayField(map);
							playfield.setComparator(new Comparator() {
								public int compare(Object o1, Object o2) {
									// sort based on y-order
									return (int) (((Sprite) o1).getY()-((Sprite) o2).getY());
								}
							} );
							playfield.add(hero);
							String[] text = {"RAWR"};
							RPGSprite boss = new NPC(this, getImages("gfx/bau5.png", 3, 4), 10, 3, 1, RPGSprite.DOWN, 1, text, 7);
							playfield.add(boss);
						}
						
					}


					// quit key
					if (keyPressed(KeyEvent.VK_ESCAPE)) {
						parent.nextGameID = RPGLoader.TITLE;
						finish();
					}
				}
			break;

			// talking to npc, end when Z or X or ESC is pressed
			case TALKING:
				if (dialog.endDialog) {
					if (keyPressed(KeyEvent.VK_Z) || keyPressed(KeyEvent.VK_X) || keyPressed(KeyEvent.VK_ESCAPE)) {
						// back to old direction
						talkToNPC.setDirection(talkToNPCDirection);
						gameState = PLAYING;
						
						talkToNPC.dialog=null;
						sg1.remove(npc1);
						sg1.remove(npc2);// = new SpriteGroup("derp");//break;
						if(talkToNPC.ID==2)
							hero.weapon = new Sword2();
						if(talkToNPC.ID==7){
							battle = new RPGBattle(fontManager.getFont(getImage("gfx/BitmapFont.png")),
									   getImage("gfx/battlebg.png", false), getImage("gfx/boss.png"), 
									   getImage("gfx/attack.png"), 15);
							gameState = BATTLING;
							
						}
					}
				}

				dialog.update(elapsedTime);
			break;
			
			// derp battle
			case BATTLING:
				if(battle.endBattle){
					hero.level++;
					gameState = PLAYING;
				}
				
				if(battle.delay<0){
					battle.eAtk = (long) 300.0;
					battle.delay = (long) 2000.0;
					hero.health -= (long)battle.multiplier*1000;
				}
				if(keyPressed(KeyEvent.VK_Z)){
					if(hero.delay<0){
						hero.delay = (long) hero.weapon.recharge*1000;
						battle.hAtk = (long) 300.0;
						battle.health -= (long) hero.weapon.power*1000*hero.level;
					}
				}
				if(keyPressed(KeyEvent.VK_X)){
					if(hero.delay<0){
						hero.delay = (long) hero.item.recharge*1000;
						playSound("sound/item.wav");
						battle.health -= (long) 5000.0;
						hero.health += (long) 5000.0;
					}
				}
				
				battle.update(elapsedTime);
			break;
				
		}

		map.setToCenter(hero);
	}
	
	private void generateBattle(){
		int x = (int) (100*Math.random());
		if(x<4){
			battle = new RPGBattle(fontManager.getFont(getImage("gfx/BitmapFont.png")),
					   getImage("gfx/battlebg.png", false), getImage("gfx/enemy1.png"), 
					   getImage("gfx/attack.png"), 1);
			gameState = BATTLING;
		}
		if(x>95){
			battle = new RPGBattle(fontManager.getFont(getImage("gfx/BitmapFont.png")),
					   getImage("gfx/battlebg.png", false), getImage("gfx/enemy2.png"), 
					   getImage("gfx/attack.png"), 2);
			gameState = BATTLING;
		}
	}


	public void render(Graphics2D g) {
		playfield.render(g);
		
		if(hero.getHealth()<=0){
			parent.nextGameID = RPGLoader.LOSE_TITLE;
			finish();
		}
		if(battle!=null && battle.multiplier>4 && battle.endBattle){
			parent.nextGameID = RPGLoader.WIN_TITLE;
			
			finish();
		}

		if (gameState == TALKING) {
			dialog.render(g);
		}
		
		if(gameState == BATTLING){
			battle.render(g);
			if(hero.delay<=0)
				font.drawString(g, "TIME UNTIL NEXT ACTION: READY!",0, 445);
			else
				font.drawString(g, "TIME UNTIL NEXT ACTION: "+hero.getDelay(),0, 445);
		}
		
		font.drawString(g, "TIME: "+hero.getHealth(), 0, 0);
		font.drawString(g, "LVL: "+hero.level, 150, 0);
		font.drawString(g, "Z: WEAPON: "+hero.weapon.name, 0, 465);
		font.drawString(g, "X: ITEM: "+hero.item.name, 320, 465);
	}


}