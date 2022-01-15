package elc102.ficct.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import elc102.ficct.utils.Coordinates;

/**
 * StartMenuScreen
 */
public class StartMenuScreen implements Screen, InputProcessor {

  List<Coordinates> levelPositions;
  Texture background;
  SpriteBatch batch;
  TextButton button;

  OrthographicCamera camera;
  Viewport viewport;

  @Override
  public boolean keyDown(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void show() {

    camera = new OrthographicCamera();

    viewport = new StretchViewport(1, 1, camera);
     
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

    batch = new SpriteBatch();

    levelPositions = new ArrayList<Coordinates>();

    levelPositions.add(new Coordinates(200, 200));
    levelPositions.add(new Coordinates(500, 200));
    levelPositions.add(new Coordinates(800, 200));

    background = new Texture("selectmodeofplayHD.png");

    Gdx.input.setInputProcessor(this);
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.font = new BitmapFont();
    textButtonStyle.fontColor = Color.WHITE;

    button = new TextButton("INICIAR", textButtonStyle);
    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        System.out.println(System.currentTimeMillis());
      };
    });
    button.setPosition(0, 0);

  }

  @Override
  public void render(float delta) {

    ScreenUtils.clear(Color.BLACK);

    batch.setTransformMatrix(camera.combined);
    batch.begin();
    System.out.println(viewport.getScreenHeight());
    batch.draw(background, 0, 0, viewport.getScreenWidth()/2, viewport.getScreenHeight()/2);
    button.draw(batch, 1f);
    batch.end();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub

  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

}
