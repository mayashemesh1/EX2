package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private final int row;
    private final int col;
    private final CollisionStrategy strategy;
    private boolean isBroken = false;

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
