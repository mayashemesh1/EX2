package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
/**
 * A numeric display of the player's lives. The color of the number changes according
 * to the number of lives, and the display updates itself according to the lives counter.
 */

public class NumericDisplay extends GameObject {
    private int numLives;
    private GameObjectCollection gameObjects;
    private final TextRenderable textRenderable;
    private Counter livesCounter;

    /**
     * Constructs the numeric lives display.
     *
     * @param topLeftCorner position of the display's top-left corner, in window coordinates.
     * @param dimensions    width and height of the display.
     * @param livesNum      counter of the player's lives.
     */
    public NumericDisplay(Vector2 topLeftCorner, Vector2 dimensions, Counter livesNum, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesNum;
        this.numLives = livesNum.value();
        this.gameObjects = gameObjects;
        this.textRenderable = new TextRenderable(Integer.toString(this.numLives));
        fitColor();
        this.renderer().setRenderable(this.textRenderable);
    }

    /**
     * Updates the displayed number and its color when the lives counter changes.
     *
     * @param deltaTime time passed since the last frame, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value() != this.numLives){
            this.numLives = this.livesCounter.value();
            this.textRenderable.setString(String.valueOf(this.numLives));
            fitColor();
        }
    }

    /*
     * Sets the color of the number: green for 3 lives or more, yellow for 2, and red for 1.
     */
    private void fitColor(){
        if (this.numLives >= 3) {
            this.textRenderable.setColor(Color.green);
        } else if (this.numLives == 2) {
            this.textRenderable.setColor(Color.yellow);
        } else if (this.numLives == 1) {
            this.textRenderable.setColor(Color.red);
        }
    }
}
