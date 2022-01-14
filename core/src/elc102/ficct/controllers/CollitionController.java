package elc102.ficct.controllers;

import java.util.ArrayList;
import java.util.List;

import elc102.ficct.Game;

public class CollitionController {

  private boolean isRunning = false;

  public interface CollitionProcessor {
    void onCollition(String event);
  }

  private final List<CollitionProcessor> listeners = new ArrayList<>();

  private void notifyListeners(String event) {
    for (CollitionProcessor collitionProcessor : listeners)
      collitionProcessor.onCollition(event);
  }

  public void addListentener(CollitionProcessor listener) {
    listeners.add(listener);
  }

  public void initialize(Game game) {
    this.isRunning = true;
   
    addListentener(game);

    Thread listenerThead = new Thread() {
      long startTime;
      long updateTime = 100;
      long lastTime = startTime;

      @Override
      public void run() {
        setName("Collition");
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
  // public boolean foodCollitions() {
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
  // public boolean obstacleCollition() {
  //
  // SnakePart snakeHead = snake.getSnakeHead();
  //
  // for (int index = 0; index < obstacleList.size(); index++)
  // if (AObject.areColliding(snakeHead, obstacleList.get(index)))
  // return true;
  // return false;
  // }
  //
  // public boolean snakeSelfCollition() {
  //
  // SnakePart snakeHead = snake.getSnakeHead();
  // List<SnakePart> snakeBody = snake.getSnakeBody();
  // for (int index = 1 ; index < snakeBody.size(); index++)
  // if (AObject.areColliding(snakeHead, snakeBody.get(index)))
  // return true;
  // return false;
  // }
}
