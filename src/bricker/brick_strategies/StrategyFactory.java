package bricker.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.gui.UserInputListener;
import bricker.gameobjects.Brick;
import danogl.GameObject;

import java.util.Random;

public class StrategyFactory {
	private static final int NUM_SPECIAL_STRATEGIES = 4;
	private static final int PUCKS = 0;
	private static final int EXTRA_PADDLE = 1;
	private static final int EXPLOSION = 2;

	private final Random random = new Random();
	private final CollisionStrategy basicStrategy;
	private final CollisionStrategy puckStrategy;
	private final CollisionStrategy extraPaddleStrategy;
	private final CollisionStrategy explosionStrategy;
	private final CollisionStrategy extraLifeStrategy;

	/**
	 * Constructs the factory and creates all the strategies of the game.
	 *
	 * @param gameObjects      the game's object collection.
	 * @param bricksCounter    counter of the bricks left in the game.
	 * @param puckImage        the image of the pucks.
	 * @param collisionSound   the sound played when a ball or a puck collides.
	 * @param puckSize         width and height of each puck.
	 * @param ballSpeed        the speed of the balls in the game.
	 * @param windowDimensions dimensions of the game window.
	 * @param paddleImage      the image of the paddle.
	 * @param paddleSize       width and height of the paddle.
	 * @param inputListener    listener for the player's keyboard input.
	 * @param borderWidth      width of the walls at the window's edges.
	 * @param explosionSound   the sound played when a brick explodes.
	 * @param bricks           grid of all the bricks in the game, by row and column.
	 *
	 */
	public StrategyFactory(GameObjectCollection gameObjects, Counter bricksCounter,
						   Renderable puckImage, Sound collisionSound, Vector2 puckSize,
						   float ballSpeed, Vector2 windowDimensions,
						   Renderable paddleImage, Vector2 paddleSize,
						   UserInputListener inputListener, float borderWidth, Sound explosionSound, Brick[][] bricks,
						   Renderable heartImage, Vector2 heartSize, GameObject mainPaddle,
						   Counter livesCounter, int maxLives) {


		this.basicStrategy = new BasicCollisionStrategy(gameObjects, bricksCounter);
		this.puckStrategy = new PuckStrategy(basicStrategy, gameObjects, puckImage,
				collisionSound, puckSize, ballSpeed, windowDimensions);
		this.extraPaddleStrategy = new ExtraPaddleStrategy(basicStrategy, gameObjects, paddleImage,
				paddleSize, inputListener, windowDimensions, borderWidth);
		this.explosionStrategy = new ExplosionStrategy(basicStrategy, bricks, explosionSound);
		this.extraLifeStrategy = new ExtraLifeStrategy(basicStrategy, gameObjects, heartImage,
				heartSize, mainPaddle, livesCounter, maxLives, windowDimensions);
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
				return extraPaddleStrategy;
			case EXPLOSION:
				return explosionStrategy;
			default:
				return extraLifeStrategy;
		}
	}
}
