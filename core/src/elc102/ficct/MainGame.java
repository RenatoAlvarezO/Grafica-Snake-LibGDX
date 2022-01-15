package elc102.ficct;

import com.badlogic.gdx.Game;

import elc102.ficct.props.Grid;
import elc102.ficct.screens.GameScreen;

public class MainGame extends Game {

  @Override
  public void create() {

    Grid grid = new Grid(32, 18);

    grid.addToGrid(23, 13, Grid.OBSTACLE);
    grid.addToGrid(22, 13, Grid.OBSTACLE);
    grid.addToGrid(21, 13, Grid.OBSTACLE);
    grid.addToGrid(20, 13, Grid.OBSTACLE);

    // grid.addToGrid(0, 0, Grid.OBSTACLE);
    // grid.addToGrid(0, 1, Grid.OBSTACLE);
    // grid.addToGrid(0, 2, Grid.OBSTACLE);
    // grid.addToGrid(0, 3, Grid.OBSTACLE);
    // grid.addToGrid(0, 4, Grid.OBSTACLE);
    // grid.addToGrid(0, 5, Grid.OBSTACLE);
    // grid.addToGrid(0, 6, Grid.OBSTACLE);
    // grid.addToGrid(0, 7, Grid.OBSTACLE);
    // grid.addToGrid(0, 8, Grid.OBSTACLE);
    // grid.addToGrid(0, 9, Grid.OBSTACLE);
    // grid.addToGrid(0, 10, Grid.OBSTACLE);
    // grid.addToGrid(0, 11, Grid.OBSTACLE);
    // grid.addToGrid(0, 12, Grid.OBSTACLE);
    setScreen(new GameScreen(this, grid, 100));

  }

  @Override
  public void resize(int width, int height) {

  }
}
