package elc102.ficct.utils;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import elc102.ficct.controllers.InputController;
import elc102.ficct.props.Grid;

public class Snake {

  public static final int RIGHT = 0;
  public static final int DOWN = 1;
  public static final int LEFT = 2;
  public static final int UP = 3;

  public int currentDirection = RIGHT;
  public int tailDirection = RIGHT;

  public int tailAvoidDirection = LEFT;
  Grid grid;

  public int xHeadPosition;
  public int yHeadPosition;
  public int xTailPosition;
  public int yTailPosition;

  public Snake(int xGridPosition, int yGridPosition, Grid grid) {
    this.xHeadPosition = xGridPosition;
    this.yHeadPosition = yGridPosition;

    this.xTailPosition = xHeadPosition - 1;
    this.yTailPosition = yHeadPosition;

    this.grid = grid;
    grid.addToGrid(xHeadPosition, yHeadPosition, Grid.HEAD);
    grid.addToGrid(xTailPosition, yTailPosition, Grid.BODY);
  }

  public boolean addToGridPosition(int x, int y) {

    if (grid.isOutOfBounds(this.xHeadPosition + x, this.yHeadPosition + y))
      return false;

    grid.addToGrid(this.xHeadPosition, this.yHeadPosition, Grid.BODY);
    this.xHeadPosition += x;
    this.yHeadPosition += y;

    return true;
  }

  private void findNextTailPosition() {
    if (tailDirection == RIGHT) {
      if (testRight())
        return;
      if (testUp())
        return;
      if (testDown())
        return;
    }
    if (tailDirection == UP) {
      if (testUp())
        return;
      if (testLeft())
        return;
      if (testRight())
        return;
    }
    if (tailDirection == LEFT) {
      if (testLeft())
        return;
      if (testDown())
        return;
      if (testUp())
        return;
    }

    if (tailDirection == DOWN) {
      if (testDown())
        return;
      if (testRight())
        return;
      if (testLeft())
        return;
    }

    try {
      throw new Exception("Tail cannot find new position \n\tCurrent Direction=> " + String.valueOf(currentDirection)
          + "\n\tTail's Direction=> " + String.valueOf(tailDirection) + "\n\tCurrent Position=> "
          + String.valueOf(xTailPosition) + " : " + String.valueOf(yTailPosition));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean testRight() {
    int testPosition = findRight();
    if (testPosition >= 0) {
      xTailPosition = testPosition;
      tailDirection = RIGHT;
      return true;
    }
    return false;
  }

  private boolean testUp() {
    int testPosition = findUp();
    if (testPosition >= 0) {
      yTailPosition = testPosition;
      tailDirection = UP;
      return true;
    }
    return false;
  }

  private boolean testLeft() {
    int testPosition = findLeft();
    if (testPosition >= 0) {
      xTailPosition = testPosition;
      tailDirection = LEFT;
      return true;
    }
    return false;
  }

  private boolean testDown() {
    int testPosition = findDown();
    if (testPosition >= 0) {
      yTailPosition = testPosition;
      tailDirection = DOWN;
      return true;
    }
    return false;
  }

  private int findRight() {
    int testPosition = xTailPosition + 1;
    if (grid.isxOutOfBounds(testPosition))
      testPosition = 0;
    return grid.getCellValue(testPosition, yTailPosition) >= 3 ? testPosition : -1;
  }

  private int findLeft() {
    int testPosition = xTailPosition - 1;
    if (grid.isxOutOfBounds(testPosition))
      testPosition = grid.getColumnCount() - 1;
    return grid.getCellValue(testPosition, yTailPosition) >= 3 ? testPosition : -1;
  }

  private int findUp() {
    int testPosition = yTailPosition + 1;
    if (grid.isyOutOfBounds(testPosition))
      testPosition = 0;
    return grid.getCellValue(xTailPosition, testPosition) >= 3 ? testPosition : -1;
  }

  private int findDown() {
    int testPosition = yTailPosition - 1;
    if (grid.isyOutOfBounds(testPosition))
      testPosition = grid.getRowCount() - 1;
    return grid.getCellValue(xTailPosition, testPosition) >= 3 ? testPosition : -1;
  }

  public void updatePosition() {
    if (currentDirection == InputController.UP) {
      if (yHeadPosition > grid.getRowCount() - 1 || !this.addToGridPosition(0, 1))
        yHeadPosition = (0);
      updateBody();
      return;
    }
    if (currentDirection == InputController.DOWN) {
      if (yHeadPosition < 0 || !this.addToGridPosition(0, -1))
        yHeadPosition = (grid.getRowCount() - 1);
      updateBody();
      return;
    }
    if (currentDirection == InputController.LEFT) {
      if (xHeadPosition < 0 || !this.addToGridPosition(-1, 0))
        xHeadPosition = (grid.getColumnCount() - 1);
      updateBody();
      return;
    }
    if (currentDirection == InputController.RIGHT) {
      if (xHeadPosition > grid.getColumnCount() - 1 || !this.addToGridPosition(1, 0))
        xHeadPosition = (0);
      updateBody();
    }
  }

  void updateBody() {
    grid.addToGrid(this.xHeadPosition, this.yHeadPosition, Grid.HEAD);
    grid.addToGrid(this.xTailPosition, this.yTailPosition, Grid.EMPTY);
    findNextTailPosition();
    grid.addToGrid(this.xTailPosition, this.yTailPosition, Grid.BODY);
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

}
