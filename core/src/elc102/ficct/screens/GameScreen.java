package elc102.ficct.screens;

import com.badlogic.gdx.Screen;

import elc102.ficct.MainGame;

import java.security.SecureRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import elc102.ficct.controllers.GameController;
import elc102.ficct.controllers.GameController.GameProcessor;
import elc102.ficct.props.Grid;
import elc102.ficct.utils.Snake;

/**
 * GameScreen
 */
public class GameScreen implements Screen, InputProcessor, GameProcessor {

  SpriteBatch batch;
  Texture gameOverTexture;
  public Grid grid;

  private Grid originalGrid;

  public Snake snake;

  GameController gameController;

  BitmapFont scoreFont;
  BitmapFont lifesFont;

  int score;
  int lifes;

  boolean gameState;

  int snakeDirection = Snake.RIGHT;
  MainGame mainGame;

  public GameScreen(MainGame mainGame, Grid grid) {

    this.mainGame = mainGame;

    this.grid = grid;

    this.originalGrid = grid.copyGrid();
    batch = new SpriteBatch();

    lifes = 3;

    // grid.addToGrid(23, 13, Grid.OBSTACLE);
    // grid.addToGrid(22, 13, Grid.OBSTACLE);
    // grid.addToGrid(21, 13, Grid.OBSTACLE);
    // grid.addToGrid(20, 13, Grid.OBSTACLE);

    snake = new Snake(grid.getColumnCount() / 2, grid.getRowCount() / 2, grid);

    score = 0;
    gameState = true;

    gameOverTexture = new Texture("game_over.png");

    scoreFont = new BitmapFont();
    lifesFont = new BitmapFont();

    grid.addToGrid(20, 11, Grid.FOOD);
    // addNewFood();
    Gdx.input.setInputProcessor(this);
    gameController = new GameController();
    gameController.initialize(this);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

    ScreenUtils.clear(Color.TEAL);

    batch.begin();

    scoreFont.draw(batch, "Score: " + String.valueOf(score), 10, Gdx.graphics.getHeight() - 10);
    lifesFont.draw(batch, "Lifes: " + String.valueOf(lifes), 10, Gdx.graphics.getHeight() - 30);

    if (gameState)
      grid.render(batch);

    if (!gameState && lifes == 0)
      batch.draw(gameOverTexture, Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 150, 500, 300);

    batch.end();
  }

  @Override
  public void resize(int width, int height) {
    mainGame.resize(width, height);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

    batch.dispose();
    gameOverTexture.dispose();
  }

  @Override
  public void onCollition(String event) {

    if (event == "Food") {
      gameState = snake.grow();
      score++;
      addNewFood();
    } else {
      gameState = false;
      lifes--;
    }

    if (!gameState) {
      if (lifes <= 0)
        gameController.stopThread();
      else
        restart();
    }
  }

  @Override
  public void updateSnake() {
    snake.setCurrentDirection(snakeDirection);
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

  private void addNewFood() {

    boolean foundPlace = false;

    SecureRandom random = new SecureRandom();
    while (!foundPlace) {
      int xRandom = -1;
      int yRandom = -1;

      random = new SecureRandom();
      while (grid.isxOutOfBounds(xRandom)) {
        random = new SecureRandom();
        xRandom = random.nextInt(grid.getColumnCount() - 1);
      }
      while (grid.isyOutOfBounds(yRandom)) {
        random = new SecureRandom();
        yRandom = random.nextInt(grid.getRowCount() - 1);
      }

      int randomPlace = grid.getCellValue(xRandom, yRandom);
      if (randomPlace == Grid.EMPTY) {
        grid.addToGrid(xRandom, yRandom, Grid.FOOD);
        foundPlace = true;
      }
    }
  }

  private void restart() {
    grid.copy(originalGrid);

    snake.restart(grid.getColumnCount() / 2, grid.getRowCount() / 2);
    gameState = true;
    gameController.stopThread();
    gameController = new GameController();
    gameController.initialize(this);

    addNewFood();
  }
}
