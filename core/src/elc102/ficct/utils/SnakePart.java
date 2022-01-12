package elc102.ficct.utils;

import com.badlogic.gdx.graphics.Texture;

public class SnakePart extends AObject {

  public SnakePart(Texture texture, int xGridPosition, int yGridPosition) {
    super(texture, xGridPosition, yGridPosition);
  }

  public SnakePart(Texture texture) {
    super(texture);
  }

  public SnakePart(int xGridPosition, int yGridPosition) {
    super(new Texture("snake.png"), xGridPosition, yGridPosition);
  }

  public void addToGridPosition(int x, int y) {
    super.setxGridPosition(getxGridPosition() + x);
    super.setyGridPosition(getyGridPosition() + y);
  }
}

