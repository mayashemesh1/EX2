package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

	private static final float MOVEMENT_SPEED = 300;
	private final UserInputListener inputListener;


	public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener) {
		super(topLeftCorner, dimensions, renderable);
		this.inputListener = inputListener;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		Vector2 movementDir= Vector2.ZERO;
		if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
			movementDir= movementDir.add(Vector2.LEFT);

		}
		if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
			movementDir= movementDir.add(Vector2.RIGHT);

		}
		setVelocity((movementDir.mult(MOVEMENT_SPEED)));
		keepInsideWindow();
	}

	private void keepInsideWindow() {
		Vector2 topLeft = getTopLeftCorner();
		float maxX = 700 - getDimensions().x();
		if (topLeft.x() < 0) {
			setTopLeftCorner(new Vector2(0, topLeft.y()));
		} else if (topLeft.x() > maxX) {
			setTopLeftCorner(new Vector2(maxX, topLeft.y()));
		}
	}
}
