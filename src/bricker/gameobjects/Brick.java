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

    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int row, int col,
                 CollisionStrategy strategy){
        super(topLeftCorner, dimensions, renderable);
        this.row = row;
        this.col = col;
        this.strategy = strategy;
    }
    public void onCollisionEnter(GameObject other, Collision collision){
        strategy.onCollision(this, other);
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
}
