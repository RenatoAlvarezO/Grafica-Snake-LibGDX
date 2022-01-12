package elc102.ficct.utils;

import com.badlogic.gdx.graphics.Texture;

public class Obstacle extends AObject {

  public Obstacle(Texture texture, int xGridPosition, int yGridPosition) {
    super(texture, xGridPosition, yGridPosition);
  }

  public Obstacle(Texture texture) {
    super(texture);
  }

  public Obstacle(int xGridPosition, int yGridPosition){
    super(new Texture("gray.png"), xGridPosition, yGridPosition);
  }
}

