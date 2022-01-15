package elc102.ficct.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import elc102.ficct.MainGame;

public class DesktopLauncher {
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    config.title = "Snake";
    config.initialBackgroundColor = Color.TEAL;
    config.width = 1280;
    config.height = 720;
    new LwjglApplication(new MainGame(), config);
  }
}
