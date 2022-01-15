package elc102.ficct.props;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid {
  private int cellWidth;
  private int cellHeight;
  private int rowCount;
  private int columnCount;

  public static final int EMPTY = 0;
  public static final int FOOD = 1;
  public static final int OBSTACLE = 2;
  public static final int BODY = 3;
  public static final int HEAD = 4;

  public int[][] matrixGrid;
  private Texture[] textures = {
      null,
      new Texture("apple.png"),
      new Texture("gray.png"),
      new Texture("body.png"),
      new Texture("snake.png"),
  };

  public Grid(int columnCount, int rowCount) {
    this.rowCount = rowCount;
    this.columnCount = columnCount;

    this.matrixGrid = new int[columnCount][rowCount];
    clearGrid();
    recalculateCellDimentions();
  }

  private void clearGrid() {

    for (int i = 0; i < matrixGrid.length; i++)
      for (int j = 0; j < matrixGrid[0].length; j++)
        matrixGrid[i][j] = 0;
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
  }

  public int getXPosition(int x) {
    return cellWidth * x;
  }

  public int getYPosition(int y) {
    return cellHeight * y;
  }

  public int getCellValue(int x, int y) {
    if (isxOutOfBounds(x))
      x = x < 0 ? this.columnCount - 1 : 0;
    if (isyOutOfBounds(y))
      y = y < 0 ? this.rowCount - 1 : 0;
    return this.matrixGrid[x][y];
  }

  public boolean isxOutOfBounds(int x) {
    return x < 0 || x > (this.columnCount - 1);
  }

  public boolean isyOutOfBounds(int y) {
    return y < 0 || y > (this.rowCount - 1);
  }

  public boolean isOutOfBounds(int x, int y) {
    return isxOutOfBounds(x) || isyOutOfBounds(y);
  }

  public int addToGrid(int column, int row, int type) {

    if (isOutOfBounds(column, row))
      System.out.println("(" + String.valueOf(column) + "," + String.valueOf(row) + ")");
    int previousValue = matrixGrid[column][row];
    if(column == 2 && row == 3 && previousValue == FOOD )
      System.out.println("ACÁ");
    matrixGrid[column][row] = type;
    return previousValue;
  }

  public void render(SpriteBatch batch) {
    for (int i = 0; i < matrixGrid.length; i++)
      for (int j = 0; j < matrixGrid[0].length; j++) {
        int cellValue = matrixGrid[i][j];
        if (cellValue != 0)
          batch.draw(textures[cellValue], getXPosition(i), getYPosition(j), getCellWidth(), getCellHeight());
      }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < matrixGrid.length; i++) {
      builder.append('[');
      for (int j = 0; j < matrixGrid[0].length - 1; j++)
        builder.append(String.valueOf(matrixGrid[i][j]) + '\t');
      builder.append(String.valueOf(matrixGrid[i][matrixGrid[i].length - 1]) + "]\n");
    }

    return builder.toString();
  }
}
