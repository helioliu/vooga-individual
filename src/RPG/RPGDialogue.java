package RPG;

// JFC
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// GTGE
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.Timer;


public class RPGDialogue {

	GameFont		font;
	BufferedImage	box;

	String[] 		dialog;
	int				frame;
	int				totalFrame;
	Timer			speed;

	boolean			endDialog;

	int				y;


	public RPGDialogue(GameFont font, BufferedImage box) {
		this.font = font;
		this.box = box;

		speed = new Timer(20);
	}


	public void setDialog(String[] dialog, boolean bottom) {
		this.dialog = dialog;

		endDialog = false;
		frame = totalFrame = 0;
		for (int i=0;i < dialog.length;i++) {
			totalFrame += dialog[i].length();
		}

		y = (bottom == true) ? 320 : 0;
	}


	public void update(long elapsedTime) {
		if (!endDialog) {
			if (speed.action(elapsedTime)) {
				if (++frame >= totalFrame) {
					endDialog = true;
				}
			}

		}
	}


	public void render(Graphics2D g) {
		// render the box
		g.drawImage(box, 0, y, null);

		if (endDialog) {
			for (int i=0;i < dialog.length;i++) {
				font.drawString(g, dialog[i], 20, y+20+(i*20));
			}

		} else {
			// type dialog letter by letter
			int typing = frame;

			for (int i=0;i < dialog.length;i++) {
				if (typing > dialog[i].length()) {
					typing -= dialog[i].length();
					font.drawString(g, dialog[i], 20, y+20+(i*20));

				} else {
					// last dialog in current rendering
					font.drawString(g, dialog[i].substring(0, typing), 20, y+20+(i*20));
					break;
				}
			}
		}
	}


}