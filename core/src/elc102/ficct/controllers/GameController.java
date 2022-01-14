package elc102.ficct.controllers;

import java.util.ArrayList;
import java.util.List;

import elc102.ficct.Game;
import elc102.ficct.props.Grid;
import elc102.ficct.utils.Coordinates;
import elc102.ficct.utils.Snake;

public class GameController {

  private boolean isRunning = false;

  Game game;

  public interface GameProcessor {
    void onCollition(String event);

    void updateSnake();
  }

  private final List<GameProcessor> listeners = new ArrayList<>();

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

  public void initialize(Game game) {
    this.isRunning = true;

    this.game = game;

    addListentener(this.game);

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
            notifyUpdateSnake();
            lastTime = startTime;
          }
        }
      }
    };

    listenerThead.start();
  }

  void checkForCollitions() {
    Coordinates headPosition = game.snake.getHeadPosition();
    int snakeDirection = game.snake.getDirection();

    int cellValue = Grid.EMPTY;

    if (snakeDirection == Snake.RIGHT) {
      cellValue = game.grid.getCellValue(headPosition.x + 1, headPosition.y);
      if (cellValue == Grid.EMPTY)
        return;
    }

    if (snakeDirection == Snake.UP) {
      cellValue = game.grid.getCellValue(headPosition.x, headPosition.y + 1);
      if (cellValue == Grid.EMPTY)
        return;
    }

    if (snakeDirection == Snake.LEFT) {
      cellValue = game.grid.getCellValue(headPosition.x - 1, headPosition.y);
      if (cellValue == Grid.EMPTY)
        return;
    }

    if (snakeDirection == Snake.DOWN) {
      cellValue = game.grid.getCellValue(headPosition.x, headPosition.y - 1);
      if (cellValue == Grid.EMPTY)
        return;
    }

    if (cellValue != Grid.EMPTY) {
      if (cellValue == Grid.FOOD)
        notifyCollition("Food");
      else {
        isRunning = false;
        notifyCollition("Death");
      }
    }
  }
}
