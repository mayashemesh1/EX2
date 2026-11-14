package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that breaks the brick and adds an extra paddle at the center
 * of the window, unless an extra paddle already exists.
 */

public class ExtraPaddleStrategy implements CollisionStrategy {
	private static final float HALF = 0.5f;

	private final CollisionStrategy basicStrategy;
	private final GameObjectCollection gameObjects;
	private final Renderable paddleImage;
	private final Vector2 paddleSize;
	private final UserInputListener inputListener;
	private final Vector2 windowDimensions;
	private final float borderWidth;
	private ExtraPaddle extraPaddle = null;

	/**
	 * Constructs an extra paddle strategy.
	 *
	 * @param basicStrategy    the basic strategy, used to break the brick.
	 * @param gameObjects      the game's object collection, used to add the extra paddle.
	 * @param paddleImage      the image of the paddle.
	 * @param paddleSize       width and height of the paddle.
	 * @param inputListener    listener for the player's keyboard input.
	 * @param windowDimensions dimensions of the game window.
	 * @param borderWidth      width of the walls at the window's edges.
	 */
	public ExtraPaddleStrategy(CollisionStrategy basicStrategy, GameObjectCollection gameObjects,
							   Renderable paddleImage, Vector2 paddleSize,
							   UserInputListener inputListener, Vector2 windowDimensions,
							   float borderWidth) {
		this.basicStrategy = basicStrategy;
		this.gameObjects = gameObjects;
		this.paddleImage = paddleImage;
		this.paddleSize = paddleSize;
		this.inputListener = inputListener;
		this.windowDimensions = windowDimensions;
		this.borderWidth = borderWidth;
	}
	/**
	 * Breaks the brick and adds an extra paddle if there is no active one.
	 *
	 * @param brick    the brick that was hit.
	 * @param collider the object that hit the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject collider) {
		basicStrategy.onCollision(brick, collider);
		if (extraPaddle != null && extraPaddle.isActive()) {
			return;
		}
		extraPaddle = new ExtraPaddle(Vector2.ZERO, paddleSize, paddleImage, inputListener,
				windowDimensions, borderWidth, gameObjects);
		extraPaddle.setCenter(windowDimensions.mult(HALF));
		gameObjects.addGameObject(extraPaddle);
	}

}
