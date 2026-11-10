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

    public GraphicDisplay(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          GameObjectCollection gameObjects, Counter livesNum) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesNum;
        this.numLives =livesCounter.value();
        this.gameObjects = gameObjects;
        this.heartsList = new GameObject[numLives];
        for (int i = 0; i < numLives; i++) {
            float x = topLeftCorner.x() + (i * (dimensions.x() + GAP_HEARTS));
            Vector2 heartPosition = new Vector2(x, topLeftCorner.y());
            GameObject heart = new GameObject(heartPosition, dimensions, renderable);
            this.heartsList[i] = heart;
            this.gameObjects.addGameObject(heart, Layer.UI);
        }

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value()<this.numLives){
            GameObject lastHeart = this.heartsList[numLives-1];
            this.gameObjects.removeGameObject(lastHeart, danogl.collisions.Layer.UI);
            this.heartsList[numLives-1] = null;
            this.numLives--;
        }
    }
}
