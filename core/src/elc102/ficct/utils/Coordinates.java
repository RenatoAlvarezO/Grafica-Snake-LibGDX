package elc102.ficct.utils;

public class Coordinates {

  public int x;
  public int y;

  public Coordinates(int x, int y) {
    this.x = x;
    this.y = y;

  }

  // THIS CONSTRUCTOR COPIES!
  public Coordinates(Coordinates coordinates) {
    this.x = coordinates.x;
    this.y = coordinates.y;
  }

  public void setCoordinates(int x, int y) {
    this.x = x;
    this.y = y;

  }

  @Override
  public String toString() {
    return "(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
  }

}
