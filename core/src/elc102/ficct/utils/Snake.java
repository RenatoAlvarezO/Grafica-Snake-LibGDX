package elc102.ficct.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

  private Coordinates headPosition;
  private Coordinates tailPosition;

  List<Coordinates> snakePath;

  public Snake(int xGridPosition, int yGridPosition, Grid grid) {

    this.headPosition = new Coordinates(xGridPosition, yGridPosition);
    this.tailPosition = new Coordinates(xGridPosition - 1, yGridPosition);

    this.snakePath = new LinkedList<Coordinates>();

    this.snakePath.add(headPosition);
    this.snakePath.add(tailPosition);

    this.grid = grid;
    grid.addToGrid(headPosition.x, headPosition.y, Grid.HEAD);
    grid.addToGrid(tailPosition.x, tailPosition.y, Grid.BODY);
  }

  public boolean addToGridPosition(int x, int y) {

    if (grid.isOutOfBounds(this.headPosition.x + x, this.headPosition.y + y))
      return false;

    // addToHead();
    grid.addToGrid(this.headPosition.x, this.headPosition.y, Grid.BODY);
    this.headPosition.x += x;
    this.headPosition.y += y;

    return true;
  }

  public void addToTail(int x, int y) {
    Coordinates previousTail = new Coordinates(this.tailPosition);
    this.tailPosition.setCoordinates(x, y);
    int previousTailIndex = snakePath.size() - 2;
    snakePath.add(previousTailIndex, previousTail);
  }

  public void addToHead() {
    Coordinates previousHead = new Coordinates(headPosition);
    snakePath.add(1, previousHead);
    System.out.println(snakePath.size());
  }

  public void updateBody() {
    grid.addToGrid(this.headPosition.x, this.headPosition.y, Grid.HEAD);
    grid.addToGrid(this.tailPosition.x, this.tailPosition.y, Grid.EMPTY);
    findNextTailPosition();
    grid.addToGrid(this.tailPosition.x, this.tailPosition.y, Grid.BODY);
  }

  public void setCurrentDirection(int newDirection) {
    if (validateDirection(newDirection))
      this.currentDirection = newDirection;
  }

  public Coordinates getHeadPosition() {
    return headPosition;
  }

  public Coordinates getTailPosition() {
    return tailPosition;
  }

  public int getDirection() {
    return this.currentDirection;
  }

  public boolean validateDirection(int newDirection) {
    return !(currentDirection == UP && newDirection == DOWN) &&
        !(currentDirection == DOWN && newDirection == UP) &&
        !(currentDirection == RIGHT && newDirection == LEFT) &&
        !(currentDirection == LEFT && newDirection == RIGHT);
  }

  private void findNextTailPosition() {
    if (tailDirection == RIGHT) {
      if (testRight(tailPosition, Grid.BODY) || testRight(tailPosition, Grid.HEAD))
        return;
      if (testUp(tailPosition, Grid.BODY) || testUp(tailPosition, Grid.HEAD))
        return;
      if (testDown(tailPosition, Grid.BODY) || testDown(tailPosition, Grid.HEAD))
        return;
    }
    if (tailDirection == UP) {
      if (testUp(tailPosition, Grid.BODY) || testUp(tailPosition, Grid.HEAD))
        return;
      if (testLeft(tailPosition, Grid.BODY) || testLeft(tailPosition, Grid.HEAD))
        return;
      if (testRight(tailPosition, Grid.BODY) || testRight(tailPosition, Grid.HEAD))
        return;
    }
    if (tailDirection == LEFT) {
      if (testLeft(tailPosition, Grid.BODY) || testLeft(tailPosition, Grid.HEAD))
        return;
      if (testDown(tailPosition, Grid.BODY) || testDown(tailPosition, Grid.HEAD))
        return;
      if (testUp(tailPosition, Grid.BODY) || testUp(tailPosition, Grid.HEAD))
        return;
    }

    if (tailDirection == DOWN) {
      if (testDown(tailPosition, Grid.BODY) || testDown(tailPosition, Grid.HEAD))
        return;
      if (testRight(tailPosition, Grid.BODY) || testRight(tailPosition, Grid.HEAD))
        return;
      if (testLeft(tailPosition, Grid.BODY) || testLeft(tailPosition, Grid.HEAD))
        return;
    }

    try {
      throw new Exception("Tail cannot find new position \n\tCurrent Direction=> " + String.valueOf(currentDirection)
          + "\n\tTail's Direction=> " + String.valueOf(tailDirection) + "\n\tCurrent Position=> "
          + String.valueOf(tailPosition.x) + " : " + String.valueOf(tailPosition.y));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean testRight(Coordinates testPosition, int value) {

    int newPosition = findRight(testPosition, value);
    if (newPosition >= 0) {
      tailPosition.x = newPosition;
      tailDirection = RIGHT;
      return true;
    }
    return false;
  }

  private boolean testUp(Coordinates testPosition, int value) {
    int newPosition = findUp(testPosition, value);
    if (newPosition >= 0) {
      tailPosition.y = newPosition;
      tailDirection = UP;
      return true;
    }
    return false;
  }

  private boolean testLeft(Coordinates testPosition, int value) {
    int newPosition = findLeft(testPosition, value);
    if (newPosition >= 0) {
      tailPosition.x = newPosition;
      tailDirection = LEFT;
      return true;
    }
    return false;
  }

  private boolean testDown(Coordinates testPosition, int value) {
    int newPosition = findDown(testPosition, value);
    if (newPosition >= 0) {
      tailPosition.y = newPosition;
      tailDirection = DOWN;
      return true;
    }
    return false;
  }

  private int findRight(Coordinates testPosition, int value) {
    int newPosition = testPosition.x + 1;
    if (grid.isxOutOfBounds(newPosition))
      newPosition = 0;
    return grid.getCellValue(newPosition, testPosition.y) == value ? newPosition : -1;
  }

  private int findLeft(Coordinates testPosition, int value) {
    int newPosition = testPosition.x - 1;
    if (grid.isxOutOfBounds(newPosition))
      newPosition = grid.getColumnCount() - 1;
    return grid.getCellValue(newPosition, testPosition.y) == value ? newPosition : -1;
  }

  private int findUp(Coordinates testPosition, int value) {
    int newPosition = testPosition.y + 1;
    if (grid.isyOutOfBounds(newPosition))
      newPosition = 0;
    return grid.getCellValue(testPosition.x, newPosition) == value ? newPosition : -1;
  }

  private int findDown(Coordinates testPosition, int value) {
    int newPosition = testPosition.y - 1;
    if (grid.isyOutOfBounds(newPosition))
      newPosition = grid.getRowCount() - 1;
    return grid.getCellValue(testPosition.x, newPosition) == value ? newPosition : -1;
  }

  public void updatePosition() {
    if (currentDirection == UP) {
      if (headPosition.y > grid.getRowCount() - 1 || !this.addToGridPosition(0, 1))
        headPosition.y = (0);
      updateBody();
      return;
    }
    if (currentDirection == DOWN) {
      if (headPosition.y < 0 || !this.addToGridPosition(0, -1))
        headPosition.y = (grid.getRowCount() - 1);
      updateBody();
      return;
    }
    if (currentDirection == LEFT) {
      if (headPosition.x < 0 || !this.addToGridPosition(-1, 0))
        headPosition.x = (grid.getColumnCount() - 1);
      updateBody();
      return;
    }
    if (currentDirection == RIGHT) {
      if (headPosition.x > grid.getColumnCount() - 1 || !this.addToGridPosition(1, 0))
        headPosition.x = (0);
      updateBody();
    }

  }

  public void grow() {

    if (currentDirection == RIGHT)
      addToTail(tailPosition.x - 1, tailPosition.y);
    else if (currentDirection == UP)
      addToTail(tailPosition.x, tailPosition.y - 1);
    else if (currentDirection == LEFT)
      addToTail(tailPosition.x + 1, tailPosition.y);
    else if (currentDirection == DOWN)
      addToTail(tailPosition.x, tailPosition.y + 1);

    System.out.println(snakePath);
  }

}
