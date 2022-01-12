package elc102.ficct.utils;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import elc102.ficct.controllers.InputController;
import elc102.ficct.props.Grid;

public class Snake {

  List<SnakePart> snakeBodyList;

  int currentDirection = 0;

  Grid grid;

  public Snake(int xGridPosition, int yGridPosition, Grid grid) {
    this.snakeBodyList = new LinkedList<>();
    this.snakeBodyList.add(new SnakePart(xGridPosition, yGridPosition));
    this.grid = grid;
  }

  public void addToGridPosition(int x, int y) {

    SnakePart snakeHead = this.snakeBodyList.get(0);

    x = (snakeHead.getxGridPosition() + x);
    y = (snakeHead.getyGridPosition() + y);

    this.updateBody(x, y);
  }

  private void updateBody(int x, int y) {
    SnakePart snakeHead = this.snakeBodyList.get(0);

    for (int index = this.snakeBodyList.size() - 1; index > 0; index--) {
      SnakePart currentPart = this.snakeBodyList.get(index);
      SnakePart nextPart = this.snakeBodyList.get(index - 1);

      currentPart.setGridPosition(nextPart.getxGridPosition(), nextPart.getyGridPosition());
    }
    snakeHead.setGridPosition(x, y);
  }

  public void draw(SpriteBatch batch) {
    for (SnakePart snakePart : snakeBodyList)
      snakePart.draw(batch, grid);
  }

  public SnakePart getSnakeHead() {
    return this.snakeBodyList.get(0);
  }

  public void addToTail() {
    SnakePart currentTail = this.snakeBodyList.get(this.snakeBodyList.size() - 1);

    if (currentDirection == InputController.UP)
      this.snakeBodyList.add(
          new SnakePart(new Texture("body.png"), currentTail.getxGridPosition(), currentTail.getyGridPosition() - 1));
    if (currentDirection == InputController.DOWN)
      this.snakeBodyList.add(
          new SnakePart(new Texture("body.png"), currentTail.getxGridPosition(), currentTail.getyGridPosition() + 1));
    if (currentDirection == InputController.LEFT)
      this.snakeBodyList.add(
          new SnakePart(new Texture("body.png"), currentTail.getxGridPosition() + 1, currentTail.getyGridPosition()));
    if (currentDirection == InputController.RIGHT)
      this.snakeBodyList.add(
          new SnakePart(new Texture("body.png"), currentTail.getxGridPosition() - 1, currentTail.getyGridPosition()));

  }

  public void updatePosition() {

    SnakePart snakeHead = snakeBodyList.get(0);
    if (currentDirection == InputController.UP) {
      this.addToGridPosition(0, 1);
      if (snakeHead.getyGridPosition() > grid.getRowCount() - 1)
        snakeHead.setyGridPosition(0);
      return;
    }
    if (currentDirection == InputController.DOWN) {
      this.addToGridPosition(0, -1);
      if (snakeHead.getyGridPosition() < 0)
        snakeHead.setyGridPosition(grid.getRowCount() - 1);
      return;
    }
    if (currentDirection == InputController.LEFT) {
      this.addToGridPosition(-1, 0);
      if (snakeHead.getxGridPosition() < 0)
        snakeHead.setxGridPosition(grid.getColumnCount() - 1);
      return;
    }
    if (currentDirection == InputController.RIGHT) {
      this.addToGridPosition(1, 0);
      if (snakeHead.getxGridPosition() > grid.getColumnCount() - 1)
        snakeHead.setxGridPosition(0);
      return;
    }

  }

  public void setCurrentDirection(int newDirection) {
    if (validateDirection(newDirection))
      this.currentDirection = newDirection;
  }

  public boolean validateDirection(int newDirection) {
    return !(currentDirection == InputController.UP && newDirection == InputController.DOWN) &&
        !(currentDirection == InputController.DOWN && newDirection == InputController.UP) &&
        !(currentDirection == InputController.RIGHT && newDirection == InputController.LEFT) &&
        !(currentDirection == InputController.LEFT && newDirection == InputController.RIGHT);

  }

  public List<SnakePart> getSnakeBody() {
    return this.snakeBodyList;
  }
}
