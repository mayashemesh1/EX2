package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
/**
 * A paddle controlled by the player with the left and right arrow keys.
 * The paddle cannot move past the walls at the window's edges.
 */
public class Paddle extends GameObject {

	private static final float MOVEMENT_SPEED = 300;
	private final UserInputListener inputListener;
	private final float windowWidth;
	private final float borderWidth;


	/**
	 * Constructs a new paddle.
	 *
	 * @param topLeftCorner    position of the paddle's top-left corner, in window coordinates.
	 * @param dimensions       width and height of the paddle.
	 * @param renderable       the image of the paddle.
	 * @param inputListener    listener for the player's keyboard input.
	 * @param windowDimensions dimensions of the game window.
	 * @param borderWidth      width of the walls at the window's edges.
	 */
	public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,  Vector2 windowDimensions, float borderWidth) {
		super(topLeftCorner, dimensions, renderable);
		this.inputListener = inputListener;
		this.windowWidth = windowDimensions.x();
		this.borderWidth = borderWidth;
	}

	/**
	 * Moves the paddle according to the pressed arrow keys, and keeps it between the walls.
	 *
	 * @param deltaTime time passed since the last frame, in seconds.
	 */
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

	/*
	 * Moves the paddle back between the walls if it crossed one of them.
	 */
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
