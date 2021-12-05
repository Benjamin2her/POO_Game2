package poo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import poo.game.PooGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title  = "Drop Game";
		config.width  = 840;
		config.height = 650;
		new LwjglApplication(new PooGame(), config);
	}
}
