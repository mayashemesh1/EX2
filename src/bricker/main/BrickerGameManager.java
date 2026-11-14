package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import java.awt.event.KeyEvent;
import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.StrategyFactory;

import java.util.Random;


public class BrickerGameManager extends GameManager{

	private static final float BALL_SPEED = 200;
	private static final float BALL_RADIUS = 20;
	private static final float PADDLE_HEIGHT = 15;
	private static final float PADDLE_WIDTH = 100;
	private static final float WALL_WIDTH = 5;
	private static final float BRICK_GAP = 5;
	private static final float BRICK_HEIGHT = 15;
	private static final int LIVES = 3;
	private static final int HEART_SIZE = 20;
	private static final int BOUNDARY = 40;
	private static final int TEXT_SIZE = 30;
	private static final int LOCATION_X_TEXT = 100;
	private static final float WINDOW_WIDTH = 700;
	private static final float WINDOW_HEIGHT = 500;
	private static final float PADDLE_BOTTOM_OFFSET = 30;
	private static final float PUCK_SIZE_FACTOR = 0.75f;
	private static final int MAX_LIVES = 4;


	private static final int DEFAULT_BRICKS_IN_ROW = 8;
	private static final int DEFAULT_NUM_ROWS = 7;

	private final int numBricksinRow;
	private final int numRows;
	private Ball ball;
	private Vector2 windowDim;
    private WindowController windowController;
	private Counter brickCounter;
	private Counter livesCounter;
	private int lives;
	private UserInputListener inputListener;


	public BrickerGameManager (String windowTitle, Vector2 windowDim, int numBricksinRow, int numRows) {
		super(windowTitle, windowDim);
		this.numBricksinRow = numBricksinRow;
		this.numRows = numRows;

	}
	public static void main(String[] args){
		int argsNum = args.length;
		int BricksinRow = DEFAULT_BRICKS_IN_ROW;
		int rows = DEFAULT_NUM_ROWS;
		if (argsNum == 2) {
			BricksinRow = Integer.parseInt(args[0]);
			rows = Integer.parseInt(args[1]);
		}
		new BrickerGameManager("Bricker", new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT),
				BricksinRow, rows).run();
	}

	@Override
	public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
							   WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.lives = LIVES;
		this.livesCounter = new Counter(this.lives);
        this.windowController = windowController;
		this.windowDim = windowController.getWindowDimensions();
		this.brickCounter = new Counter(numBricksinRow*numRows);
		this.inputListener = inputListener;

		Sound collisionSound = soundReader.readSound("assets/blop.wav");

		//create ball
		Renderable ballImage =
				imageReader.readImage("assets/ball.png", true);
		createBall(ballImage, collisionSound);

		//create user paddle
		Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
		Paddle userPaddle = setUserPaddle(paddleImage, inputListener);


		//create bricks
		Renderable puckImage = imageReader.readImage("assets/mockBall.png", true);
		Vector2 puckSize = new Vector2(BALL_RADIUS, BALL_RADIUS).mult(PUCK_SIZE_FACTOR);
		Sound explosionSound = soundReader.readSound("assets/explosion.wav");
		Brick[][] bricks = new Brick[numRows][numBricksinRow];
		Renderable heartImage = imageReader.readImage("assets/heart.png", true);

		StrategyFactory strategyFactory = new StrategyFactory(gameObjects(), brickCounter,
				puckImage, collisionSound, puckSize, BALL_SPEED, windowDim,
				paddleImage, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), inputListener, WALL_WIDTH,
				explosionSound, bricks,
				heartImage, new Vector2(HEART_SIZE, HEART_SIZE), userPaddle, livesCounter, MAX_LIVES);

		Renderable brickImage = imageReader.readImage("assets/brick.png", false);
		float brickWidth = (windowDim.x() - (2 * WALL_WIDTH) - (BRICK_GAP * (this.numBricksinRow - 1)))
				/ this.numBricksinRow;
		for (int row = 0; row < this.numRows; row++) {
			for (int col = 0; col < this.numBricksinRow; col++) {
				float x = WALL_WIDTH + (col * (brickWidth + BRICK_GAP));
				float y = WALL_WIDTH + (row * (BRICK_HEIGHT + BRICK_GAP));
				Vector2 position = new Vector2(x, y);
				bricks[row][col] = createBrick(brickImage, brickWidth, position, row, col,
						strategyFactory.getStrategy());
			}
		}
		//create graphic lives
		createGraphicDisplay(heartImage);

		//create numeric lives
		createNumericDisplay();

		buildWalls();
		setBackground(imageReader, windowDim);
	}

	private void createNumericDisplay() {
		Vector2 numericStartPos = new Vector2(LOCATION_X_TEXT, windowDim.y() - BOUNDARY);
		Vector2 numericDim = new Vector2(TEXT_SIZE, TEXT_SIZE);
		NumericDisplay numericDisplay = new NumericDisplay(numericStartPos, numericDim,
				this.livesCounter, gameObjects());
		gameObjects().addGameObject(numericDisplay, danogl.collisions.Layer.UI);
	}

	private void createGraphicDisplay(Renderable heartImage) {
		Vector2 heartsStartPos = new Vector2(HEART_SIZE, windowDim.y() - BOUNDARY);
		Vector2 heartDim = new Vector2(HEART_SIZE, HEART_SIZE);
		GraphicDisplay graphicDisplay = new GraphicDisplay(heartsStartPos, heartDim, heartImage, gameObjects(), this.livesCounter, MAX_LIVES);
		gameObjects().addGameObject(graphicDisplay, Layer.UI);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float ballHeight = ball.getCenter().y();
		String prompt = "";
		if (ballHeight>windowDim.y()) {
			if (this.livesCounter.value() > 1) {
				this.livesCounter.decrement();
				returnBallToCenter();
			}
			else {
				prompt = "You lose!";
			}
		}

		if (this.brickCounter.value() <= 0 || this.inputListener.isKeyPressed(KeyEvent.VK_W)){
			prompt = "You win!";
		}
		if (!prompt.isEmpty()){
			prompt+= " Play again";
			if (windowController.openYesNoDialog(prompt)) {
				windowController.resetGame();
			}
			else {
				windowController.closeWindow();
			}
		}

		}

	private void returnBallToCenter() {
		ball.setCenter(windowDim.mult(0.5f));
		float ballVelY = BALL_SPEED;
		float ballVelX = BALL_SPEED;
		Random rand = new Random();
		if (rand.nextBoolean()){
			ballVelX *=-1;
		}
		if (rand.nextBoolean()){
			ballVelY *=-1;
		}
		ball.setVelocity(new Vector2(ballVelX, ballVelY));
	}

	private Brick createBrick(Renderable brickImage, float brickWidth, Vector2 position,
							  int row, int col, CollisionStrategy strategy) {
		Brick brick = new Brick(position, new Vector2(brickWidth, BRICK_HEIGHT),
				brickImage, row, col, strategy);
		gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
		return brick;
	}

	private void createBall(Renderable ballImage, Sound collisionSound){
		this.ball = new Ball(new Vector2(0, 0), new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        returnBallToCenter();
		gameObjects().addGameObject(ball);

	}

	private Paddle setUserPaddle(Renderable paddleImage, UserInputListener inputListener) {
		Paddle userPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
				paddleImage, inputListener, windowDim, WALL_WIDTH);
		userPaddle.setCenter(new Vector2(windowDim.x() / 2, windowDim.y() - PADDLE_BOTTOM_OFFSET));
		gameObjects().addGameObject(userPaddle);
		return userPaddle;
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
		gameObjects().addGameObject(wallL, danogl.collisions.Layer.STATIC_OBJECTS);
		gameObjects().addGameObject(wallR, danogl.collisions.Layer.STATIC_OBJECTS);
		gameObjects().addGameObject(wallU, danogl.collisions.Layer.STATIC_OBJECTS);
	}
}
