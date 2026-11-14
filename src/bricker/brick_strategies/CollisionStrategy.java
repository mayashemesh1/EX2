package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;

/**
 * A behavior that is performed when an object collides with a brick.
 * Each brick holds one collision strategy, which decides what happens when the brick is hit.
 */

public interface CollisionStrategy {
    /**
     * Performs the behavior of the strategy when an object collides with a brick.
     *
     * @param brick    the brick that was hit.
     * @param collider the object that hit the brick.
     */
    void onCollision(Brick brick, GameObject collider);
}
