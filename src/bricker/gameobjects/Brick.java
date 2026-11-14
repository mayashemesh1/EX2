package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A brick in the game. When the brick is hit, it delegates the behavior to its
 * collision strategy. A brick knows its position (row and column) in the bricks grid,
 * and can be broken only once.
 */
public class Brick extends GameObject {
    private final int row;
    private final int col;
    private final CollisionStrategy strategy;
    private boolean isBroken = false;

    /**
     * Constructs a new brick.
     *
     * @param topLeftCorner position of the brick's top-left corner, in window coordinates.
     * @param dimensions    width and height of the brick.
     * @param renderable    the image of the brick.
     * @param row           the row of the brick in the bricks grid.
     * @param col           the column of the brick in the bricks grid.
     * @param strategy      the behavior to perform when the brick is hit.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int row, int col,
                 CollisionStrategy strategy){
        super(topLeftCorner, dimensions, renderable);
        this.row = row;
        this.col = col;
        this.strategy = strategy;
    }

    /**
     * @return the row of the brick in the bricks grid.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column of the brick in the bricks grid.
     */
    public int getCol() {
        return col;
    }
    /**
     * Called by the engine when another object collides with this brick.
     *
     * @param other     the object that collided with the brick.
     * @param collision information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        breakBrick(other);
    }

    /**
     * Breaks the brick by performing its collision strategy. A brick can be broken only once;
     * any further call is ignored.
     *
     * @param collider the object that caused the brick to break.
     */
    public void breakBrick(GameObject collider) {
        if (isBroken) {
            return;
        }
        isBroken = true;
        strategy.onCollision(this, collider);
    }
}
