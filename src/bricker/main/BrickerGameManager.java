package bricker.main;

import bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import bricker.gameobjects.Paddle;


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

		Sound collisionSound = soundReader.readSound("assets/blop.wav");
		GameObject ball = new Ball(new Vector2(0, 0), new Vector2(20, 20), ballImage, collisionSound);

		this.gameObjects().addGameObject(ball);
		ball.setVelocity(Vector2.DOWN.mult(200));
		Vector2 windowDim = windowController.getWindowDimensions();

		ball.setCenter(windowDim.mult(0.5f));
		gameObjects().addGameObject(ball);

		Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
		//creat user paddles





		GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener);
		userPaddle.setCenter(new Vector2(windowDim.x()/2,(int)windowDim.y()-30));
		gameObjects().addGameObject(userPaddle);



		//AI paddle


		GameObject AIPaddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener);
		AIPaddle.setCenter(new Vector2(windowDim.x()/2,30));
		gameObjects().addGameObject(AIPaddle);



		buildWalls();
		setBackground(imageReader, windowDim);



	}

	private void setBackground(ImageReader imageReader, Vector2 windowDim) {
		Renderable backgroundImg = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
		GameObject background = new GameObject(Vector2.ZERO, windowDim, backgroundImg);
		background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
		gameObjects().addGameObject(background, Layer.BACKGROUND);
	}

	public void buildWalls(){
		GameObject wallR= new GameObject(new Vector2(0,0), new Vector2(5, 500), null );
		GameObject wallL= new GameObject(new Vector2(695,0), new Vector2(5, 500), null );
		GameObject wallU= new GameObject(new Vector2(0,0), new Vector2(700, 5), null );
		GameObject wallD= new GameObject(new Vector2(0,495), new Vector2(700, 5), null );
		gameObjects().addGameObject(wallL);
		gameObjects().addGameObject(wallR);
		gameObjects().addGameObject(wallU);
		gameObjects().addGameObject(wallD);
	}
}
