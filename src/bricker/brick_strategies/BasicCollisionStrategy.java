package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import danogl.collisions.Layer;

public class BasicCollisionStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObject;
    private final Counter counterBricks;

    public BasicCollisionStrategy(GameObjectCollection gameObject, Counter counterBricks){
        this.gameObject = gameObject;
        this.counterBricks = counterBricks;
    }


    @Override
    public void onCollision(Brick brick, GameObject collider) {
        if (this.gameObject.removeGameObject(brick, Layer.STATIC_OBJECTS)) {
            this.counterBricks.decrement();
        }
    }
}
