package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObject;
    private final Counter counterBricks;

    public BasicCollisionStrategy(GameObjectCollection gameObject, Counter counterBricks){
        this.gameObject = gameObject;
        this.counterBricks = counterBricks;
    }


    @Override
    public void onCollision(Brick brick, GameObject collider) {
        this.gameObject.removeGameObject(brick, danogl.collisions.Layer.STATIC_OBJECTS);
        this.counterBricks.decrement();

    }
}
