package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
/**
 * A falling heart. When the main paddle collects it, the player gets an extra life
 * (up to a maximal number of lives). The heart collides only with the main paddle,
 * and is removed from the game if it leaves the window.
 */

public class Heart extends GameObject {
    private final GameObject mainPaddle;
    private final GameObjectCollection gameObjects;
    private final Counter livesCounter;
    private final int maxLives;
    private final float windowHeight;
    /**
     * Constructs a new falling heart.
     *
     * @param topLeftCorner    position of the heart's top-left corner, in window coordinates.
     * @param dimensions       width and height of the heart.
     * @param renderable       the image of the heart.
     * @param mainPaddle       the main paddle, the only object the heart can collide with.
     * @param gameObjects      the game's object collection, used to remove the heart.
     * @param livesCounter     counter of the player's lives.
     * @param maxLives         the maximal number of lives in the game.
     * @param windowDimensions dimensions of the game window.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObject mainPaddle, GameObjectCollection gameObjects, Counter livesCounter,
                 int maxLives, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.mainPaddle = mainPaddle;
        this.gameObjects = gameObjects;
        this.livesCounter = livesCounter;
        this.maxLives = maxLives;
        this.windowHeight = windowDimensions.y();
    }

    /**
     * The heart can collide only with the main paddle.
     *
     * @param other the object to check.
     * @return true only if the other object is the main paddle.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other == mainPaddle;
    }

    /**
     * Adds a life (if the maximum was not reached) and removes the heart from the game.
     *
     * @param other     the object that collided with the heart.
     * @param collision information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (livesCounter.value() < maxLives) {
            livesCounter.increment();
        }
        gameObjects.removeGameObject(this);
    }

    /**
     * Removes the heart from the game once it falls below the window.
     *
     * @param deltaTime time passed since the last frame, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getTopLeftCorner().y() > windowHeight) {
            gameObjects.removeGameObject(this);
        }
    }





}
