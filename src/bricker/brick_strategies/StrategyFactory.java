package bricker.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class StrategyFactory {
	private static final int NUM_SPECIAL_STRATEGIES = 4;
	private static final int PUCKS = 0;
	private static final int EXTRA_PADDLE = 1;
	private static final int EXPLOSION = 2;

	private final Random random = new Random();
	private final CollisionStrategy basicStrategy;
	private final CollisionStrategy puckStrategy;

	/**
	 * Constructs the factory and creates all the strategies of the game.
	 *
	 * @param gameObjects   the game's object collection.
	 * @param bricksCounter counter of the bricks left in the game.
	 * @param puckImage        the image of the pucks.
	 *  * @param collisionSound   the sound played when a ball or a puck collides.
	 *  * @param puckSize         width and height of each puck.
	 *  * @param ballSpeed        the speed of the balls in the game.
	 *  * @param windowDimensions dimensions of the game window.
	 */
	public StrategyFactory(GameObjectCollection gameObjects, Counter bricksCounter,
						   Renderable puckImage, Sound collisionSound, Vector2 puckSize,
						   float ballSpeed, Vector2 windowDimensions) {
		this.basicStrategy = new BasicCollisionStrategy(gameObjects, bricksCounter);
		this.puckStrategy = new PuckStrategy(basicStrategy, gameObjects, puckImage,
				collisionSound, puckSize, ballSpeed, windowDimensions);
	}

	/**
	 * Randomly chooses a collision strategy for a new brick.
	 *
	 * @return the chosen collision strategy.
	 */
	public CollisionStrategy getStrategy() {
		if (random.nextBoolean()) {
			return basicStrategy;
		}
		switch (random.nextInt(NUM_SPECIAL_STRATEGIES)) {
			case PUCKS:
				return puckStrategy;
			case EXTRA_PADDLE:
				return basicStrategy;        // TODO: extra paddle strategy
			case EXPLOSION:
				return basicStrategy;        // TODO: explosion strategy
			default:
				return basicStrategy;        // TODO: extra life strategy
		}
	}
}
