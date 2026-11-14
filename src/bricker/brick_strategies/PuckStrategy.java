package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class PuckStrategy implements CollisionStrategy{
	private static final int NUM_PUCKS = 2;

	private final CollisionStrategy basicStrategy;
	private final GameObjectCollection gameObjects;
	private final Renderable puckImage;
	private final Sound collisionSound;
	private final Vector2 puckSize;
	private final float puckSpeed;
	private final Vector2 windowDimensions;
	private final Random random = new Random();

	/**
	 * Constructs a puck strategy.
	 *
	 * @param basicStrategy    the basic strategy, used to break the brick.
	 * @param gameObjects      the game's object collection, used to add the pucks.
	 * @param puckImage        the image of the pucks.
	 * @param collisionSound   the sound played when a puck collides.
	 * @param puckSize         width and height of each puck.
	 * @param puckSpeed        the speed of each puck.
	 * @param windowDimensions dimensions of the game window.
	 */
	public PuckStrategy(CollisionStrategy basicStrategy, GameObjectCollection gameObjects,
						Renderable puckImage, Sound collisionSound, Vector2 puckSize,
						float puckSpeed, Vector2 windowDimensions) {
		this.basicStrategy = basicStrategy;
		this.gameObjects = gameObjects;
		this.puckImage = puckImage;
		this.collisionSound = collisionSound;
		this.puckSize = puckSize;
		this.puckSpeed = puckSpeed;
		this.windowDimensions = windowDimensions;
	}

	/**
	 * Breaks the brick and creates the pucks at its center.
	 *
	 * @param brick    the brick that was hit.
	 * @param collider the object that hit the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject collider) {
		basicStrategy.onCollision(brick, collider);
		for (int i = 0; i < NUM_PUCKS; i++) {
			Puck puck = new Puck(Vector2.ZERO, puckSize, puckImage, collisionSound,
					gameObjects, windowDimensions);
			puck.setCenter(brick.getCenter());
			puck.setVelocity(randomVelocity());
			gameObjects.addGameObject(puck);
		}
	}

	/*
	 * Returns a velocity in a random direction on the upper half of the unit circle.
	 */
	private Vector2 randomVelocity() {
		double angle = random.nextDouble() * Math.PI;
		float velocityX = (float) Math.cos(angle) * puckSpeed;
		float velocityY = (float) Math.sin(angle) * puckSpeed;
		return new Vector2(velocityX, velocityY);
	}




}
