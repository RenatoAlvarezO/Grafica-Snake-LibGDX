package elc102.ficct.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import elc102.ficct.MainGame;
import elc102.ficct.props.Grid;
import elc102.ficct.utils.Coordinates;

/**
 * StartMenuScreen
 */
public class StartMenuScreen implements Screen, InputProcessor {

  List<Coordinates> levelPositions;
  List<Texture> levelTextures;
  Texture background;
  Texture selector;
  SpriteBatch batch;

  int currentLevel;
  OrthographicCamera camera;
  Viewport viewport;

  List<Grid> levels;
  List<Long> speeds;

  MainGame mainGame;

  public StartMenuScreen(MainGame mainGame) {
    this.mainGame = mainGame;
  }

  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Input.Keys.D) {
      currentLevel++;
      if (currentLevel > 2)
        currentLevel = 2;
    }
    if (keycode == Input.Keys.A) {
      currentLevel--;
      if (currentLevel < 0)
        currentLevel = 0;
    }

    if (keycode == Input.Keys.ENTER) {
      this.mainGame.setScreen(new GameScreen(mainGame, levels.get(currentLevel), speeds.get(currentLevel)));
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

  @Override
  public void show() {

    camera = new OrthographicCamera();

    viewport = new StretchViewport(1, 1, camera);

    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

    batch = new SpriteBatch();
    selector = new Texture("selector.png");
    levelPositions = new ArrayList<Coordinates>();

    levelPositions.add(new Coordinates(18, 170));
    levelPositions.add(new Coordinates(226, 170));
    levelPositions.add(new Coordinates(435, 170));

    levelTextures = new ArrayList<>();
    levelTextures.add(new Texture("Nivel 1.png"));
    levelTextures.add(new Texture("Nivel 2.png"));
    levelTextures.add(new Texture("Nivel 3.png"));

    createLevels();
    background = new Texture("selectmodeofplayHD.png");
    currentLevel = 0;
    Gdx.input.setInputProcessor(this);

  }

  @Override
  public void render(float delta) {

    ScreenUtils.clear(Color.BLACK);

    batch.setTransformMatrix(camera.combined);
    batch.begin();
    batch.draw(background, 0, 0, viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2);
    batch.draw(levelTextures.get(0), 38, 215, 150, 50);
    batch.draw(levelTextures.get(1), 250, 215, 150, 50);
    batch.draw(levelTextures.get(2), 455, 215, 150, 50);

    batch.draw(selector, levelPositions.get(currentLevel).x, levelPositions.get(currentLevel).y, 185, 145);
    batch.end();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }

  void createLevels() {
    levels = new ArrayList<>();
    speeds = new ArrayList<>();
    Grid level1 = new Grid(32, 18);

    level1.addToGrid(23, 13, Grid.OBSTACLE);
    level1.addToGrid(22, 13, Grid.OBSTACLE);
    level1.addToGrid(21, 13, Grid.OBSTACLE);
    level1.addToGrid(20, 13, Grid.OBSTACLE);

    speeds.add(100l);

    Grid level2 = new Grid(32, 18);

    level2.addToGrid(23, 13, Grid.OBSTACLE);
    level2.addToGrid(22, 13, Grid.OBSTACLE);
    level2.addToGrid(14, 12, Grid.OBSTACLE);
    level2.addToGrid(16, 12, Grid.OBSTACLE);
    level2.addToGrid(22, 2, Grid.OBSTACLE);

    level2.addToGrid(22, 1, Grid.OBSTACLE);

    speeds.add(90l);

    Grid level3 = new Grid(32, 18);
    level3.addToGrid(0, 0, Grid.OBSTACLE);
    level3.addToGrid(0, 1, Grid.OBSTACLE);
    level3.addToGrid(0, 2, Grid.OBSTACLE);
    level3.addToGrid(0, 3, Grid.OBSTACLE);
    level3.addToGrid(0, 4, Grid.OBSTACLE);
    level3.addToGrid(0, 5, Grid.OBSTACLE);
    level3.addToGrid(0, 6, Grid.OBSTACLE);
    level3.addToGrid(0, 7, Grid.OBSTACLE);
    level3.addToGrid(0, 8, Grid.OBSTACLE);
    level3.addToGrid(0, 13, Grid.OBSTACLE);
    level3.addToGrid(0, 14, Grid.OBSTACLE);
    level3.addToGrid(0, 15, Grid.OBSTACLE);

    level3.addToGrid(31, 0, Grid.OBSTACLE);
    level3.addToGrid(31, 1, Grid.OBSTACLE);
    level3.addToGrid(31, 2, Grid.OBSTACLE);
    level3.addToGrid(31, 3, Grid.OBSTACLE);
    level3.addToGrid(31, 4, Grid.OBSTACLE);
    level3.addToGrid(31, 5, Grid.OBSTACLE);
    level3.addToGrid(31, 6, Grid.OBSTACLE);
    level3.addToGrid(31, 7, Grid.OBSTACLE);
    level3.addToGrid(31, 8, Grid.OBSTACLE);
    level3.addToGrid(31, 12, Grid.OBSTACLE);
    level3.addToGrid(31, 13, Grid.OBSTACLE);
    level3.addToGrid(31, 14, Grid.OBSTACLE);
    level3.addToGrid(31, 15, Grid.OBSTACLE);
    speeds.add(85l);

    levels.add(level1);
    levels.add(level2);
    levels.add(level3);
  }

}
