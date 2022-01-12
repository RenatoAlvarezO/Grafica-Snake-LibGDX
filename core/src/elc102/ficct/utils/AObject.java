package elc102.ficct.utils;

import elc102.ficct.props.Grid;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AObject {

  private Texture texture;
  private int xGridPosition;
  private int yGridPosition;

  public AObject(Texture texture, int xGridPosition, int yGridPosition) {
    this.texture = texture;
    this.xGridPosition = xGridPosition;
    this.yGridPosition = yGridPosition;
  }

  public AObject(Texture texture) {
    this.texture = texture;
    this.xGridPosition = 0;
    this.yGridPosition = 0;
  }

  public Texture getTexture() {
    return texture;
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public int getxGridPosition() {
    return xGridPosition;
  }

  public void setxGridPosition(int xGridPosition) {
    this.xGridPosition = xGridPosition;
  }

  public int getyGridPosition() {
    return yGridPosition;
  }

  public void setyGridPosition(int yGridPosition) {
    this.yGridPosition = yGridPosition;
  }

  public void setGridPosition(int x, int y) {
    this.xGridPosition = x;
    this.yGridPosition = y;
  }

  public void draw(SpriteBatch batch, Grid grid) {
    int x = grid.getXPosition(this.xGridPosition);
    int y = grid.getYPosition(this.yGridPosition);

    int width = grid.getCellWidth();
    int height = grid.getCellHeight();

    batch.draw(texture, x, y, width, height);
  }

  public void dispose() {
    texture.dispose();
  }

  public static boolean areColliding(AObject first, AObject second) {
    return first.getxGridPosition() == second.getxGridPosition()
        && first.getyGridPosition() == second.getyGridPosition();
  }
}
