package elc102.ficct.utils;

import com.badlogic.gdx.graphics.Texture;

public class Food extends AObject {

  public Food(Texture texture, int xGridPosition, int yGridPosition) {
    super(texture, xGridPosition, yGridPosition);
  }

  public Food(Texture texture) {
    super(texture);
  }

  public Food(int xGridPosition, int yGridPosition){
    super(new Texture("apple.png"), xGridPosition, yGridPosition);
  }
}
