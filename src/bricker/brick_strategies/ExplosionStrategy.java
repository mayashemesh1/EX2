package bricker.brick_strategies;
import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.gui.Sound;

/**
 * A collision strategy that breaks the brick, plays an explosion sound,
 * and breaks the bricks next to it (not including diagonals).
 */

public class ExplosionStrategy implements CollisionStrategy{
	private static final int[][] NEIGHBOR_OFFSETS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	private final CollisionStrategy basicStrategy;
	private final Brick[][] bricks;
	private final Sound explosionSound;

	/**
	 * Constructs an explosion strategy.
	 *
	 * @param basicStrategy  the basic strategy, used to break the brick.
	 * @param bricks         grid of all the bricks in the game, by row and column.
	 * @param explosionSound the sound played when a brick explodes.
	 */
	public ExplosionStrategy(CollisionStrategy basicStrategy, Brick[][] bricks, Sound explosionSound) {
		this.basicStrategy = basicStrategy;
		this.bricks = bricks;
		this.explosionSound = explosionSound;
	}

	/**
	 * Breaks the brick, plays the explosion sound and breaks its neighbors.
	 *
	 * @param brick    the brick that was hit.
	 * @param collider the object that hit the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject collider) {
		basicStrategy.onCollision(brick, collider);
		explosionSound.play();
		for (int[] offset : NEIGHBOR_OFFSETS) {
			int row = brick.getRow() + offset[0];
			int col = brick.getCol() + offset[1];
			if (isInGrid(row, col)) {
				bricks[row][col].breakBrick(brick);
			}
		}
	}

	/*
	 * Checks whether the given position is inside the bricks grid.
	 */
	private boolean isInGrid(int row, int col) {
		return row >= 0 && row < bricks.length && col >= 0 && col < bricks[row].length;
	}
}
