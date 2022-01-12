package elc102.ficct.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import elc102.ficct.utils.Snake;

public class InputController {

  Thread inputControllerThread;

  public static final int RIGHT = 0;
  public static final int DOWN = 1;
  public static final int LEFT = 2;
  public static final int UP = 3;

  long lastMillis;
  final long updateTime = 10;

  private boolean isEnabled;

  int currentDirection = RIGHT;

  private Snake snake;

  public InputController(Snake snake) {
    this.snake = snake;
    this.inputControllerThread = new Thread() {
      @Override
      public void run() {
        try {
          inputHandler();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

  }

  public void start() {
    isEnabled = true;
    this.inputControllerThread.start();
  }

  public void stop() {
    isEnabled = false;
    this.inputControllerThread.stop();
  }

  private void inputHandler() throws InterruptedException {
    while (isEnabled) {

      long startTime = System.currentTimeMillis();

      inputRead();

      long usedTime = startTime - System.currentTimeMillis();

      Thread.sleep(updateTime - usedTime);
    }
  }

  private void inputRead() {
    if (Gdx.input.isKeyPressed(Input.Keys.W))
      currentDirection = UP;
    if (Gdx.input.isKeyPressed(Input.Keys.S))
      currentDirection = DOWN;
    if (Gdx.input.isKeyPressed(Input.Keys.D))
      currentDirection = RIGHT;
    if (Gdx.input.isKeyPressed(Input.Keys.A))
      currentDirection = LEFT;

    snake.setCurrentDirection(currentDirection);
    // String currentString = currentDirection == UP ? "UP"
    // : currentDirection == DOWN ? "DOWN" : currentDirection == RIGHT ? "RIGHT" :
    // "LEFT";
    // System.out.println(currentString);
  }

  public int getCurrentDirection() {
    return currentDirection;
  }
}
