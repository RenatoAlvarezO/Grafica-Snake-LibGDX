package elc102.ficct.props;

import com.badlogic.gdx.Gdx;

public class Grid {
  private int cellWidth;
  private int cellHeight;
  private int rowCount;
  private int columnCount;

  public Grid(int columnCount, int rowCount) {
    this.rowCount = rowCount;
    this.columnCount = columnCount;
    recalculateCellDimentions();
  }

  public int getCellWidth() {
    return cellWidth;
  }

  public int getCellHeight() {
    return cellHeight;
  }

  public int getRowCount() {
    return rowCount;
  }

  public void setRowCount(int rowCount) {
    this.rowCount = rowCount;
  }

  public int getColumnCount() {
    return columnCount;
  }

  public void setColumnCount(int columnCount) {
    this.columnCount = columnCount;
  }

  public void recalculateCellDimentions() {
    this.cellWidth = Gdx.graphics.getWidth() / columnCount;
    this.cellHeight = Gdx.graphics.getHeight() / rowCount;

    System.out.println("Width => " + cellWidth);
    System.out.println("Height => " + cellHeight);
  }

  // Hay algo raro con esta logica
  public int getXPosition(int x) {

    return cellWidth * x;
  }

  public int getYPosition(int y) {
    return cellHeight * y;
  }

  public boolean isxOutOfBounds(int x) {
    return x < 0 || x > this.columnCount;
  }

  public boolean isyOutOfBounds(int y) {
    return y < 0 || y > this.rowCount;
  }

  public boolean isOutOfBounds(int x, int y) {
    return isxOutOfBounds(x) || isyOutOfBounds(y);
  }

}
