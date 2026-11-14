package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * An extra paddle that moves like the regular paddle, and disappears
 * after being hit a fixed number of times.
 */
public class ExtraPaddle extends Paddle {
	private static final int MAX_HITS = 4;

	private final GameObjectCollection gameObjects;
	private int hitCount = 0;

	/**
	 * Constructs a new extra paddle.
	 *
	 * @param topLeftCorner    position of the paddle's top-left corner, in window coordinates.
	 * @param dimensions       width and height of the paddle.
	 * @param renderable       the image of the paddle.
	 * @param inputListener    listener for the player's keyboard input.
	 * @param windowDimensions dimensions of the game window.
	 * @param borderWidth      width of the walls at the window's edges.
	 * @param gameObjects      the game's object collection, used to remove the paddle.
	 */
	public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
					   UserInputListener inputListener, Vector2 windowDimensions, float borderWidth,
					   GameObjectCollection gameObjects) {
		super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, borderWidth);
		this.gameObjects = gameObjects;
	}

	/**
	 * Counts the hit, and removes the paddle from the game after the maximal number of hits.
	 *
	 * @param other     the object that collided with the paddle.
	 * @param collision information about the collision.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		hitCount++;
		if (hitCount == MAX_HITS) {
			gameObjects.removeGameObject(this);
		}
	}

	/**
	 * @return true if the paddle is still in the game, false if it was already removed.
	 */
	public boolean isActive() {
		return hitCount < MAX_HITS;
	}


}
