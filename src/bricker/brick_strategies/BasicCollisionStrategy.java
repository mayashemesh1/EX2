package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import danogl.collisions.Layer;

/**
 * The basic collision behavior: the brick is removed from the game
 * and the bricks counter is decremented.
 * The special strategies use this strategy to break the brick before performing
 * their own behavior.
 */

public class BasicCollisionStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObject;
    private final Counter counterBricks;

    /**
     * Constructs a basic collision strategy.
     *
     * @param gameObject    the game's object collection, used to remove the brick.
     * @param counterBricks counter of the bricks left in the game.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObject, Counter counterBricks){
        this.gameObject = gameObject;
        this.counterBricks = counterBricks;
    }


    /**
     * Removes the brick from the game. The counter is decremented only if the brick
     * was actually removed, so a brick that is hit twice is counted only once.
     *
     * @param brick    the brick that was hit.
     * @param collider the object that hit the brick.
     */
    @Override
    public void onCollision(Brick brick, GameObject collider) {
        if (this.gameObject.removeGameObject(brick, Layer.STATIC_OBJECTS)) {
            this.counterBricks.decrement();
        }
    }
}
