import java.awt.Dimension;
import RPG.RPGLoader;
import com.golden.gamedev.GameLoader;


public class Main
{
    public static void main (String[] args)
    {
        GameLoader loader = new GameLoader();
        loader.setup(new RPGLoader(), new Dimension(640, 480), false);
        loader.start();
    }
}
