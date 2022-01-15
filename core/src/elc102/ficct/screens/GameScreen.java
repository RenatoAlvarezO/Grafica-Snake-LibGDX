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

  Texture background;
  public Grid grid;

  private Grid originalGrid;

  public Snake snake;

  GameController gameController;

  BitmapFont scoreFont;
  BitmapFont lifesFont;

  long gameSpeed;
  long baseSpeed;

  int score;
  int lifes;

  boolean gameState;

  int snakeDirection = Snake.RIGHT;
  MainGame mainGame;

  public GameScreen(MainGame mainGame, Grid grid, long gameSpeed) {

    this.mainGame = mainGame;

    this.grid = grid;

    background = new Texture("background.png");

    this.originalGrid = grid.copyGrid();
    batch = new SpriteBatch();

    lifes = 3;

    snake = new Snake(grid.getColumnCount() / 2, grid.getRowCount() / 2, grid);

    score = 0;
    gameState = true;
    this.gameSpeed = gameSpeed;
    this.baseSpeed = gameSpeed;

    gameOverTexture = new Texture("game_over.png");

    scoreFont = new BitmapFont();
    lifesFont = new BitmapFont();

    addNewFood();
    Gdx.input.setInputProcessor(this);
    gameController = new GameController(this.gameSpeed);
    gameController.initialize(this);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

    ScreenUtils.clear(Color.GRAY);

    batch.begin();

    batch.draw(background, 0, 0);

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
      gameSpeed -= 5;
      gameController.setSpeed(gameSpeed);
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
    grid.setSnakeTexture(snakeDirection);
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
    if (!gameState)
      if (keycode == Input.Keys.ESCAPE)
        mainGame.setScreen(new StartMenuScreen(mainGame));
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
    gameController.stopThread();

    grid.copy(originalGrid);
    snakeDirection = Snake.RIGHT;
    snake.restart(grid.getColumnCount() / 2, grid.getRowCount() / 2);
     
    gameState = true;

    gameSpeed = baseSpeed;
     
    gameController = new GameController(baseSpeed);
    gameController.initialize(this);

    addNewFood();
  }
}
