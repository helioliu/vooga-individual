package items;

import sprites.RPGSprite;

public abstract class Item {
	public long recharge;
	public String name;
	
	public abstract void doEffect();

}
