package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A collision strategy that breaks the brick and drops a heart from its center.
 * Collecting the heart with the main paddle gives the player an extra life.
 */

public class ExtraLifeStrategy  implements CollisionStrategy {
	private static final float HEART_SPEED = 100;

	private final CollisionStrategy basicStrategy;
	private final GameObjectCollection gameObjects;
	private final Renderable heartImage;
	private final Vector2 heartSize;
	private final GameObject mainPaddle;
	private final Counter livesCounter;
	private final int maxLives;
	private final Vector2 windowDimensions;

	/**
	 * Constructs an extra life strategy.
	 *
	 * @param basicStrategy    the basic strategy, used to break the brick.
	 * @param gameObjects      the game's object collection, used to add the heart.
	 * @param heartImage       the image of the heart.
	 * @param heartSize        width and height of the heart.
	 * @param mainPaddle       the main paddle, the only object that can collect the heart.
	 * @param livesCounter     counter of the player's lives.
	 * @param maxLives         the maximal number of lives in the game.
	 * @param windowDimensions dimensions of the game window.
	 */
	public ExtraLifeStrategy(CollisionStrategy basicStrategy, GameObjectCollection gameObjects,
							 Renderable heartImage, Vector2 heartSize, GameObject mainPaddle,
							 Counter livesCounter, int maxLives, Vector2 windowDimensions) {
		this.basicStrategy = basicStrategy;
		this.gameObjects = gameObjects;
		this.heartImage = heartImage;
		this.heartSize = heartSize;
		this.mainPaddle = mainPaddle;
		this.livesCounter = livesCounter;
		this.maxLives = maxLives;
		this.windowDimensions = windowDimensions;
	}

	/**
	 * Breaks the brick and drops a heart from its center.
	 *
	 * @param brick    the brick that was hit.
	 * @param collider the object that hit the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject collider) {
		basicStrategy.onCollision(brick, collider);
		Heart heart = new Heart(Vector2.ZERO, heartSize, heartImage, mainPaddle, gameObjects,
				livesCounter, maxLives, windowDimensions);
		heart.setCenter(brick.getCenter());
		heart.setVelocity(Vector2.DOWN.mult(HEART_SPEED));
		gameObjects.addGameObject(heart);
	}


}
