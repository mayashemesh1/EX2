package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericDisplay extends GameObject {
    private int numLives;
    private GameObjectCollection gameObjects;
    private final TextRenderable textRenderable;
    private Counter livesCounter;

    public NumericDisplay(Vector2 topLeftCorner, Vector2 dimensions, Counter livesNum, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesNum;
        this.numLives = livesNum.value();
        this.gameObjects = gameObjects;
        this.textRenderable = new TextRenderable(Integer.toString(this.numLives));
        fitColor();
        this.renderer().setRenderable(this.textRenderable);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value()<this.numLives){
            this.numLives--;
            this.textRenderable.setString(String.valueOf(this.numLives));
            fitColor();
        }
    }

    public void fitColor(){
        if (this.numLives >= 3) {
            this.textRenderable.setColor(Color.green);
        } else if (this.numLives == 2) {
            this.textRenderable.setColor(Color.yellow);
        } else if (this.numLives == 1) {
            this.textRenderable.setColor(Color.red);
        }
    }
}
