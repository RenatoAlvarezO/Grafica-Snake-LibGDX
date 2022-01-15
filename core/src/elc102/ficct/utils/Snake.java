package elc102.ficct.utils;

import java.util.LinkedList;
import java.util.List;

import elc102.ficct.props.Grid;

public class Snake {

  public static final int RIGHT = 0;
  public static final int DOWN = 1;
  public static final int LEFT = 2;
  public static final int UP = 3;

  public int currentDirection = RIGHT;
  public int tailDirection = RIGHT;

  Grid grid;


  private Coordinates headPosition;
  private Coordinates tailPosition;

  public List<Integer> snakePath;

  public Snake(int xGridPosition, int yGridPosition, Grid grid) {

    this.headPosition = new Coordinates(xGridPosition, yGridPosition);
    this.tailPosition = new Coordinates(xGridPosition - 1, yGridPosition);

    this.snakePath = new LinkedList<Integer>();

    this.snakePath.add(RIGHT);

    this.grid = grid;
    grid.addToGrid(headPosition.x, headPosition.y, Grid.HEAD);
    grid.addToGrid(tailPosition.x, tailPosition.y, Grid.BODY);
  }

  public int addToGridPosition(int x, int y) {
    int previousValue = grid.addToGrid(this.headPosition.x, this.headPosition.y, Grid.BODY);
    if(grid.isOutOfBounds(this.headPosition.x + x, this.headPosition.y + y))
       return Grid.OBSTACLE;
    
    this.headPosition.x += x;
    this.headPosition.y += y;

    
    // normalizePosition(headPosition);
    return previousValue;
  }

  public boolean addToTail(int x, int y) {
    if (grid.getCellValue(x, y) == Grid.OBSTACLE) {
      System.out.println("HEY1");
      return false;
    }
    this.tailPosition.setCoordinates(x, y);
    normalizePosition(this.tailPosition);
    int previousValue = snakePath.get(snakePath.size() - 1);
    snakePath.add(0, previousValue);
    return true;
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

    int tailNextDirection = snakePath.remove(0);
    snakePath.add(currentDirection);

    if (tailNextDirection == RIGHT) {
      tailPosition.setCoordinates(tailPosition.x + 1, tailPosition.y);
      normalizePosition(tailPosition);
    } else if (tailNextDirection == UP) {
      tailPosition.setCoordinates(tailPosition.x, tailPosition.y + 1);
      normalizePosition(tailPosition);
    } else if (tailNextDirection == LEFT) {
      tailPosition.setCoordinates(tailPosition.x - 1, tailPosition.y);
      normalizePosition(tailPosition);
    } else if (tailNextDirection == DOWN) {
      tailPosition.setCoordinates(tailPosition.x, tailPosition.y - 1);
      normalizePosition(tailPosition);
    } else {
      try {
        throw new Exception("Tail cannot find new position \n\tCurrent Direction=> " + String.valueOf(currentDirection)
            + "\n\tTail's Direction=> " + String.valueOf(tailDirection) + "\n\tCurrent Position=> "
            + String.valueOf(tailPosition.x) + " : " + String.valueOf(tailPosition.y));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public int updatePosition() {
    if (currentDirection == UP)
      this.addToGridPosition(0, 1);
    else if (currentDirection == DOWN)
      this.addToGridPosition(0, -1);
    else if (currentDirection == LEFT)
      this.addToGridPosition(-1, 0);
    else if (currentDirection == RIGHT)
      this.addToGridPosition(1, 0);

    return updateBody();
  }

  public int updateBody() {
    int previousValue = grid.addToGrid(this.headPosition.x, this.headPosition.y, Grid.HEAD);
    grid.addToGrid(this.tailPosition.x, this.tailPosition.y, Grid.EMPTY);
    findNextTailPosition();

    grid.addToGrid(this.tailPosition.x, this.tailPosition.y, Grid.BODY);

    return previousValue;
  }

  public boolean grow() {

    if (currentDirection == RIGHT)
      return addToTail(tailPosition.x - 1, tailPosition.y);
    if (currentDirection == UP)
      return addToTail(tailPosition.x, tailPosition.y - 1);
    if (currentDirection == LEFT)
      return addToTail(tailPosition.x + 1, tailPosition.y);
    if (currentDirection == DOWN)
      return addToTail(tailPosition.x, tailPosition.y + 1);

    return false;
  }

  void normalizePosition(Coordinates position) {
    if (position.y > grid.getRowCount() - 1)
      position.y = (0);

    if (position.y < 0)
      position.y = (grid.getRowCount() - 1);

    if (position.x < 0)
      position.x = (grid.getColumnCount() - 1);

    if (position.x > grid.getColumnCount() - 1)
      position.x = (0);
  }

  public void restart(int xGridPosition, int yGridPosition) {

    this.headPosition = new Coordinates(xGridPosition, yGridPosition);
    this.tailPosition = new Coordinates(xGridPosition - 1, yGridPosition);

    this.snakePath = new LinkedList<Integer>();

    this.snakePath.add(RIGHT);
    grid.addToGrid(headPosition.x, headPosition.y, Grid.HEAD);
    grid.addToGrid(tailPosition.x, tailPosition.y, Grid.BODY);
  }
}
