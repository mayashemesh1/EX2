package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

	private static final float MOVEMENT_SPEED = 300;
	private final UserInputListener inputListener;
	private final float windowWidth;
	private final float borderWidth;


	public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,  Vector2 windowDimensions, float borderWidth) {
		super(topLeftCorner, dimensions, renderable);
		this.inputListener = inputListener;
		this.windowWidth = windowDimensions.x();
		this.borderWidth = borderWidth;
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
		float minX = borderWidth;
		float maxX = windowWidth - borderWidth - getDimensions().x();
		if (topLeft.x() < minX) {
			setTopLeftCorner(new Vector2(minX, topLeft.y()));
		} else if (topLeft.x() > maxX) {
			setTopLeftCorner(new Vector2(maxX, topLeft.y()));
		}
	}
}
