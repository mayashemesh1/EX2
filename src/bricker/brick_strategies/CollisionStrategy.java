package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;

public interface CollisionStrategy {
    void onCollision(Brick brick, GameObject collider);
}
