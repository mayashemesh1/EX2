package bricker.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends  Ball {
	private final GameObjectCollection gameObjects;
	private final float windowHeight;

	/**
	 * Constructs a new puck.
	 *
	 * @param topLeftCorner    position of the puck's top-left corner, in window coordinates.
	 * @param dimensions       width and height of the puck.
	 * @param renderable       the image of the puck.
	 * @param collisionSound   the sound played when the puck collides.
	 * @param gameObjects      the game's object collection, used to remove the puck.
	 * @param windowDimensions dimensions of the game window.
	 */
	public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
				GameObjectCollection gameObjects, Vector2 windowDimensions) {
		super(topLeftCorner, dimensions, renderable, collisionSound);
		this.gameObjects = gameObjects;
		this.windowHeight = windowDimensions.y();
	}

	/**
	 * Removes the puck from the game once it falls below the window.
	 *
	 * @param deltaTime time passed since the last frame, in seconds.
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (getTopLeftCorner().y() > windowHeight) {
			gameObjects.removeGameObject(this);
		}
	}


}
