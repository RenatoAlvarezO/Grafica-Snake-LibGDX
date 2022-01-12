package elc102.ficct;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import elc102.ficct.controllers.CollitionController;
import elc102.ficct.controllers.InputController;
import elc102.ficct.props.Grid;
import elc102.ficct.utils.AObject;
import elc102.ficct.utils.Food;
import elc102.ficct.utils.Obstacle;
import elc102.ficct.utils.Snake;
import elc102.ficct.utils.TimeCounter;

public class Game extends ApplicationAdapter {
  SpriteBatch batch;
  Texture gameOverTexture;
  Grid grid;

  List<Food> foodList;
  List<Obstacle> obstacleList;

  Snake snake;
  int counter = 0;

  InputController inputController;
  CollitionController collitionController;
  TimeCounter gameSpeed;

  BitmapFont scoreFont;
  int score;
  boolean gameState;

  @Override
  public void create() {
    batch = new SpriteBatch();

    grid = new Grid(16 * 2, 9 * 2);

    foodList = new LinkedList<>();
    obstacleList = new LinkedList<>();

    foodList.add(new Food(0, 0));

    obstacleList.add(new Obstacle(17, 10));
    obstacleList.add(new Obstacle(18, 10));
    obstacleList.add(new Obstacle(19, 10));

    snake = new Snake(grid.getColumnCount() / 2, grid.getRowCount() / 2, grid);

    inputController = new InputController(snake);
    inputController.start();
    gameSpeed = new TimeCounter(50);

    collitionController = new CollitionController(snake, foodList, obstacleList);

    score = 0;
    gameState = true;

    gameOverTexture = new Texture("game_over.png");
    scoreFont = new BitmapFont();
  }

  @Override
  public void render() {

    ScreenUtils.clear(Color.TEAL);

    if (collitionController.foodCollitions()) {
      addNewFood(foodList, obstacleList, grid);
      score++;
    }

    if (collitionController.obstacleCollition() || collitionController.snakeSelfCollition()) {
      inputController.stop();
      gameSpeed.stop();
      gameState = false;
    }
    if (gameSpeed.hasPassed()) {
      snake.updatePosition();
      gameSpeed.reset();
    }

    batch.begin();

    snake.draw(batch);
    for (Food food : foodList)
      food.draw(batch, grid);

    for (Obstacle obstacle : obstacleList)
      obstacle.draw(batch, grid);
    scoreFont.draw(batch, String.valueOf(score), 10, Gdx.graphics.getHeight() - 10);

    if (!gameState) {
      batch.draw(gameOverTexture, Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 150, 500, 300);
    }
    batch.end();
  }

  @Override
  public void dispose() {
    batch.dispose();
    gameOverTexture.dispose();
  }

  private void addNewFood(List<Food> foodList, List<Obstacle> obstacleList, Grid grid) {
    Random random = new Random();

    int xRandom = -1;
    int yRandom = -1;
    do {
      while (grid.isxOutOfBounds(xRandom))
        xRandom = random.nextInt(grid.getColumnCount() - 1);
      while (grid.isyOutOfBounds(yRandom))
        yRandom = random.nextInt(grid.getRowCount() - 1);

      foodList.add(new Food(xRandom, yRandom));
    } while (contains(xRandom, yRandom, obstacleList));
    System.out.println("( " + xRandom + " , " + yRandom + " )");
  }

  private boolean contains(int x, int y, List<Obstacle> objectList) {

    for (AObject aObject : objectList)
      if (x == aObject.getxGridPosition() && y == aObject.getyGridPosition())
        return true;
    return false;
  }
}
