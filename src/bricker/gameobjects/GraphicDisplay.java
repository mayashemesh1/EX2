package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicDisplay extends GameObject {
    private static final float GAP_HEARTS = 5;
    private GameObjectCollection gameObjects;
    private GameObject[] heartsList;
    private int numLives;
    private Counter livesCounter;
    private final Renderable heartImage;
    private final Vector2 heartDimensions;

    public GraphicDisplay(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          GameObjectCollection gameObjects, Counter livesNum, int maxLives) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesNum;
        this.numLives =livesCounter.value();
        this.gameObjects = gameObjects;
        this.heartsList = new GameObject[maxLives];
        this.heartImage = renderable;
        this.heartDimensions = dimensions;
        for (int i = 0; i < numLives; i++) {
            float x = topLeftCorner.x() + (i * (dimensions.x() + GAP_HEARTS));
            Vector2 heartPosition = new Vector2(x, topLeftCorner.y());
            GameObject heart = new GameObject(heartPosition, dimensions, renderable);
            this.heartsList[i] = heart;
            this.gameObjects.addGameObject(heart, Layer.UI);
        }

    }

    /**
     * Adds or removes hearts so the number of hearts matches the lives counter.
     *
     * @param deltaTime time passed since the last frame, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        while (this.numLives < this.livesCounter.value()) {
            addHeart();
        }
        while (this.numLives > this.livesCounter.value()) {
            removeHeart();
        }
    }

    /*
     * Adds a heart at the end of the row.
     */
    private void addHeart() {
        float x = getTopLeftCorner().x() + (this.numLives * (this.heartDimensions.x() + GAP_HEARTS));
        Vector2 heartPosition = new Vector2(x, getTopLeftCorner().y());
        GameObject heart = new GameObject(heartPosition, this.heartDimensions, this.heartImage);
        this.heartsList[this.numLives] = heart;
        this.gameObjects.addGameObject(heart, Layer.UI);
        this.numLives++;
    }

    /*
     * Removes the last heart in the row.
     */
    private void removeHeart() {
        this.numLives--;
        this.gameObjects.removeGameObject(this.heartsList[this.numLives], Layer.UI);
        this.heartsList[this.numLives] = null;
    }
}
