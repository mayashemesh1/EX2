package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
/**
 * A ball that bounces off every object it collides with,
 * and plays a sound on each collision.
 */

public class Ball extends GameObject {
	private final Sound collisionSound;

	/**
	 * Constructs a new ball.
	 *
	 * @param topLeftCorner  position of the ball's top-left corner, in window coordinates.
	 * @param dimensions     width and height of the ball.
	 * @param renderable     the image of the ball.
	 * @param collisionSound the sound played when the ball collides.
	 */
	public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound)  {
		super(topLeftCorner, dimensions, renderable);
		this.collisionSound = collisionSound;

	}

	/**
	 * Bounces the ball off the object it collided with, and plays the collision sound.
	 *
	 * @param other     the object the ball collided with.
	 * @param collision information about the collision.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		Vector2 newVel= getVelocity().flipped(collision.getNormal());
		setVelocity(newVel);
		collisionSound.play();
	}
}
