package application;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SnakeGame extends Application {
	
	private final int WIDTH = 400, HEIGHT = 400;
	private final int ROW = 40, COL = 40;
	private final int GRID_SIZE = WIDTH / ROW;
	
	private Image head, body, apple;
	
	private int appleX, appleY;
	private int[] x = new int[ROW * COL];
	private int[] y = new int[ROW * COL];
	
	private int currentSize;
	
	private boolean right = true, left = false, up=false, down = false;
	
	private boolean isGameOver = false;
	
	private boolean hasDrawn = false;
	
	private Group container;
	private Scene scene;
	
	private Canvas canvas;
	private GraphicsContext gc;

	public void init() {
		container = new Group();
		scene = new Scene(container, WIDTH, HEIGHT);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(hasDrawn) {
					if(event.getCode() == KeyCode.LEFT && !right) {
						left = true;
						down = up = false;
						hasDrawn = false;
					} else
					if(event.getCode() == KeyCode.RIGHT && !left) {
						right = true;
						down = up = false;
						hasDrawn = false;
					} else
					if(event.getCode() == KeyCode.UP && !down) {
						up = true;
						right = left = false;
						hasDrawn = false;
					} else
					if(event.getCode() == KeyCode.DOWN && !up) {
						down = true;
						right = left = false;
						hasDrawn = false;
					}
				}
			}
		});
		
		canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
		container.getChildren().add(canvas);
		
//		gc.setFill(Color.RED);
//		gc.fillRect(20, 20, 20, 100);
		
		loadImages();
		initApple();
		initSnake();
		
//		drawFrame();
		
//		Timeline tl = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				drawFrame();
//			}
//		}));
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(100), ayam -> drawFrame()));
		tl.setCycleCount(Animation.INDEFINITE);
		tl.play();
	}
	

	private void drawFrame() {
		if(isGameOver) {
			return;
		}
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		drawApple();
		drawSnake();
		moveSnake();
		eatApple();
		checkCollision();
	}

	private void checkCollision() {
		if(x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
			isGameOver = true;
		}
		for(int i = 1; i < currentSize; i++) {
			if(x[0] == x[i] && y[0] == y[i]) {
				isGameOver = true;
				break;
			}
		}
	}

	private void eatApple() {
		if(x[0] == appleX && y[0] == appleY) {
			currentSize++;
			initApple();
		}
	}


	private void moveSnake() {
		for(int i = currentSize-1; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if(left) {
			x[0] -= GRID_SIZE;
		} else
		if(right) {
			x[0] += GRID_SIZE;
		} else
		if(up) {
			y[0] -= GRID_SIZE;
		} else
		if(down) {
			y[0] += GRID_SIZE;
		}
	}


	private void drawSnake() {
		for(int i = 0; i < currentSize; i++) {
			if(i==0)
				gc.drawImage(head, x[i], y[i]);
			else
				gc.drawImage(body, x[i], y[i]);
		}
		hasDrawn = true;
	}

	private void drawApple() {
		gc.drawImage(apple, appleX, appleY);
	}

	private void initSnake() {
		currentSize = 3;
		for(int i = 0; i < currentSize; i++) {
			x[i] = (currentSize-i-1) * GRID_SIZE;
			y[i] = 0;
		}
	}
	
	private void initApple() {
		appleX = (int)(Math.random() * COL) * GRID_SIZE;
		appleY = (int)(Math.random() * ROW) * GRID_SIZE;
	}

	private void loadImages() {
		head = new Image(new File("Images/head.png").toURI().toString());
		body = new Image(new File("Images/body.png").toURI().toString());
		apple = new Image(new File("Images/apple.png").toURI().toString());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(scene);
		primaryStage.setTitle("Snake");
		primaryStage.show();
	}
	
	
}
