package elc102.ficct;

import com.badlogic.gdx.Game;

import elc102.ficct.props.Grid;
import elc102.ficct.screens.GameScreen;
import elc102.ficct.screens.StartMenuScreen;

public class MainGame extends Game {

  @Override
  public void create() {
      setScreen(new StartMenuScreen(this));
  }

  @Override
  public void resize(int width, int height) {

  }
}
