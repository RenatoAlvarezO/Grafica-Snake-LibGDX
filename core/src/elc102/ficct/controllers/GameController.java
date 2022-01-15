package elc102.ficct.controllers;

import java.util.ArrayList;
import java.util.List;

import elc102.ficct.props.Grid;
import elc102.ficct.screens.GameScreen;

public class GameController {

  private boolean isRunning = false;

  GameScreen game;

  long updateTime;

  public interface GameProcessor {
    void onCollition(String event);

    void updateSnake();
  }

  private final List<GameProcessor> listeners = new ArrayList<>();

  private Thread listenerThead;

  public GameController(long speed) {
    this.updateTime = speed;
  }

  private void notifyListeners(String event) {
    for (GameProcessor gameProcessor : listeners) {
      gameProcessor.onCollition(event);
      gameProcessor.updateSnake();
    }
  }

  private void notifyUpdateSnake() {
    for (GameProcessor gameProcessor : listeners)
      gameProcessor.updateSnake();
  }

  private void notifyCollition(String event) {
    for (GameProcessor gameProcessor : listeners)
      gameProcessor.onCollition(event);
  }

  public void addListentener(GameProcessor listener) {
    listeners.add(listener);
  }

  public void initialize(GameScreen game) {

    this.game = game;

    addListentener(this.game);

    isRunning = true;
    listenerThead = new Thread() {
      long startTime;

      long lastTime = startTime;

      @Override
      public void run() {
        setName("GameController");
        setPriority(2);

        while (isRunning) {
          startTime = System.currentTimeMillis();

          if (startTime - lastTime >= updateTime) {
            notifyUpdateSnake();
            checkForCollitions();
            lastTime = startTime;
          }
        }
      }
    };

    listenerThead.start();
  }

  void checkForCollitions() {

    int cellValue = game.snake.updatePosition();
    if (cellValue != Grid.EMPTY) {
      if (cellValue == Grid.FOOD) {
        notifyCollition("Food");
      } else {
        stopThread();
        notifyCollition("Death");
      }
    }
  }

  public void stopThread() {
    isRunning = false;
  }

  public void setSpeed(long speed){
    this.updateTime = speed;
  }

}
