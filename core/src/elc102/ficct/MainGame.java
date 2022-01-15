package elc102.ficct;

import com.badlogic.gdx.Game;

import elc102.ficct.props.Grid;
import elc102.ficct.screens.GameScreen;
import elc102.ficct.screens.StartMenuScreen;

public class MainGame extends Game {

  @Override
  public void create() {

    Grid grid = new Grid(32, 18);


    grid.addToGrid(23, 13, Grid.OBSTACLE);
    grid.addToGrid(22, 13, Grid.OBSTACLE);
    grid.addToGrid(21, 13, Grid.OBSTACLE);
    grid.addToGrid(20, 13, Grid.OBSTACLE);

    
    // setScreen(new GameScreen(this, grid, 100));
    setScreen(new StartMenuScreen(this));
  }

  @Override
  public void resize(int width, int height) {

  }
}
