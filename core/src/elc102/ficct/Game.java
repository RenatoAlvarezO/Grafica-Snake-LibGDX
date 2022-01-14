package elc102.ficct;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import elc102.ficct.controllers.GameController;
import elc102.ficct.controllers.GameController.GameProcessor;
import elc102.ficct.controllers.InputController;
import elc102.ficct.props.Grid;
import elc102.ficct.utils.AObject;
import elc102.ficct.utils.Food;
import elc102.ficct.utils.Obstacle;
import elc102.ficct.utils.Snake;
import elc102.ficct.utils.TimeCounter;

public class Game extends ApplicationAdapter implements InputProcessor, GameProcessor {
  SpriteBatch batch;
  Texture gameOverTexture;
  public Grid grid;

  public Snake snake;
  int counter = 0;

  GameController gameController;
  TimeCounter gameSpeed;

  BitmapFont scoreFont;
  int score;
  boolean gameState;

  int snakeDirection = Snake.RIGHT;

  @Override
  public void create() {
    batch = new SpriteBatch();

    grid = new Grid(16 * 2, 9 * 2);

    grid.addToGrid(2, 3, Grid.FOOD);

    grid.addToGrid(2, 3, Grid.FOOD);
    grid.addToGrid(3, 12, Grid.FOOD);
    grid.addToGrid(21, 6, Grid.FOOD);
    grid.addToGrid(22, 7, Grid.FOOD);
    grid.addToGrid(23, 13, Grid.OBSTACLE);

    snake = new Snake(grid.getColumnCount() / 2, grid.getRowCount() / 2, grid);

    gameSpeed = new TimeCounter(50);

    score = 0;
    gameState = true;

    gameOverTexture = new Texture("game_over.png");
    scoreFont = new BitmapFont();

    Gdx.input.setInputProcessor(this);
    gameController = new GameController();
    gameController.initialize(this);

  }

  // LibGDX
  @Override
  public void render() {

    ScreenUtils.clear(Color.TEAL);

    batch.begin();

    scoreFont.draw(batch, String.valueOf(score), 10, Gdx.graphics.getHeight() - 10);

    if (!gameState)
      batch.draw(gameOverTexture, Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 150, 500, 300);

    grid.render(batch);
    batch.end();
  }

  @Override
  public void dispose() {
    batch.dispose();
    gameOverTexture.dispose();
  }

  private void addNewFood(List<Food> foodList, List<Obstacle> obstacleList, Grid grid) {
    Random random = new Random();

    int xRandom = -1;
    int yRandom = -1;
    do {
      while (grid.isxOutOfBounds(xRandom))
        xRandom = random.nextInt(grid.getColumnCount() - 1);
      while (grid.isyOutOfBounds(yRandom))
        yRandom = random.nextInt(grid.getRowCount() - 1);

      foodList.add(new Food(xRandom, yRandom));
    } while (contains(xRandom, yRandom, obstacleList));
  }

  private boolean contains(int x, int y, List<Obstacle> objectList) {

    for (AObject aObject : objectList)
      if (x == aObject.getxGridPosition() && y == aObject.getyGridPosition())
        return true;
    return false;
  }

  // InputProcessor
  @Override
  public boolean keyDown(int keycode) {

    if (keycode == (Input.Keys.W))
      snakeDirection = Snake.UP;
    else if (keycode == (Input.Keys.S))
      snakeDirection = Snake.DOWN;
    else if (keycode == (Input.Keys.D))
      snakeDirection = Snake.RIGHT;
    else if (keycode == (Input.Keys.A))
      snakeDirection = Snake.LEFT;
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

  // GameController
  @Override
  public void onCollition(String event) {

    if (event == "Food") {
      snake.grow();
      score++;
    } else {
      gameState = false;
      System.out.println(event);
    }
  }

  @Override
  public void updateSnake() {
    snake.setCurrentDirection(snakeDirection);
    snake.updatePosition();
  }
}
