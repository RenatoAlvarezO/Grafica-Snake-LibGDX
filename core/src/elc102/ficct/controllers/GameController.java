package elc102.ficct.controllers;

import java.util.ArrayList;
import java.util.List;

import elc102.ficct.Game;

public class GameController {

  private boolean isRunning = false;

  public interface GameProcessor {
    void onCollition(String event);
    void updateSnake();
  }

  private final List<GameProcessor> listeners = new ArrayList<>();

  private void notifyListeners(String event) {
    for (GameProcessor gameProcessor : listeners){
      gameProcessor.onCollition(event);
      gameProcessor.updateSnake();  
    }
  }

  public void addListentener(GameProcessor listener) {
    listeners.add(listener);
  }

  public void initialize(Game game) {
    this.isRunning = true;

    addListentener(game);

    Thread listenerThead = new Thread() {
      long startTime;
      long updateTime = 50;
      long lastTime = startTime;

      @Override
      public void run() {
        setName("GameController");
        setPriority(2);

        while (isRunning) {
          startTime = System.currentTimeMillis();

          if (startTime - lastTime >= updateTime) {
            checkForCollitions();
            notifyListeners(String.valueOf(startTime));
            lastTime = startTime;
          }
        }
      }
    };

    listenerThead.start();
  }

  void checkForCollitions() {

  }



  
  // public boolean foodtarts() {
  // SnakePart snakeHead = snake.getSnakeHead();
  //
  // for (int index = 0; index < foodList.size(); index++) {
  // if (AObject.areColliding(snakeHead, foodList.get(index))) {
  // foodList.remove(index);
  // snake.addToTail();
  // return true;
  // }
  // }
  // return false;
  // }
  //
  // public boolean obstacletart() {
  //
  // SnakePart snakeHead = snake.getSnakeHead();
  //
  // for (int index = 0; index < obstacleList.size(); index++)
  // if (AObject.areColliding(snakeHead, obstacleList.get(index)))
  // return true;
  // return false;
  // }
  //
  // public boolean snakeSelftart() {
  //
  // SnakePart snakeHead = snake.getSnakeHead();
  // List<SnakePart> snakeBody = snake.getSnakeBody();
  // for (int index = 1 ; index < snakeBody.size(); index++)
  // if (AObject.areColliding(snakeHead, snakeBody.get(index)))
  // return true;
  // return false;
  // }
}
