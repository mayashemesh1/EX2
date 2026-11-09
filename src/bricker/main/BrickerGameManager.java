package bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager{

	public BrickerGameManager (String windowTitle, Vector2 windowDim) {
		super(windowTitle, windowDim);
	}
	public static void main(String[] args){
		new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
	}

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);

		Renderable ballImage =
				imageReader.readImage("assets/ball.png", true);

		GameObject ball = new GameObject(new Vector2(0, 0), new Vector2(20, 20), ballImage);

		this.gameObjects().addGameObject(ball);
		ball.setVelocity(Vector2.DOWN.mult(100));
		Vector2 windowDim = windowController.getWindowDimensions();

		ball.setCenter(windowDim.mult(0.5f));
		gameObjects().addGameObject(ball);

		Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
		GameObject paddle = new GameObject(Vector2.ZERO, new Vector2(100, 15), paddleImage);
		gameObjects().addGameObject(paddle);
		paddle.setCenter(new Vector2(windowDim.x()/2, windowDim.y()-30));
	}
}
