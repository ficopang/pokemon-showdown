package application;
	
import java.io.File;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	
	Group container;
	Scene scene;
	
	public void init() {
		container = new Group();
		scene = new Scene(container, 600, 400);
		
		Line line = new Line();
		line.setStartX(0);
		line.setStartY(0);
		line.setEndX(100);
		line.setEndY(200);
		
		Stop[] stops = new Stop[] {
			new Stop(0, Color.RED),
			new Stop(1, Color.BLUEVIOLET)
		};
		LinearGradient grad = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
		
		Rectangle rect = new Rectangle();
		rect.setX(200);
		rect.setY(200);
		rect.setHeight(200);
		rect.setWidth(50);
		rect.setFill(grad);
		
		Circle circle = new Circle();
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(50.0f);
		circle.setFill(Color.BROWN);
		circle.setStroke(Color.BLUE);
		circle.setOpacity(0.3);
		
		// animation
		RotateTransition rot = new RotateTransition();
		rot.setNode(rect);
		rot.setByAngle(90);
		rot.setDuration(Duration.seconds(2));
		rot.setCycleCount(5);
		rot.play();
		
		TranslateTransition tran = new TranslateTransition();
		tran.setNode(line);
		tran.setByX(100);
		tran.setByY(100);
		tran.setDuration(Duration.seconds(2));
		tran.play();
		
		ScaleTransition scale = new ScaleTransition();
		scale.setNode(circle);
		scale.setByX(1.5);
		scale.setDuration(Duration.seconds(2));
		scale.play();
		
		//media
		Image img = new Image(new File("bg.png").toURI().toString());
		ImageView iv = new ImageView(img);
		iv.setX(100);
		iv.setY(100);
		iv.setFitHeight(200);
		iv.setPreserveRatio(true);
		
//		Media med = new Media(new File("shop.mp3").toURI().toString());
//		MediaPlayer mp = new MediaPlayer(med);
//		mp.play();
		
		Media vid = new Media(new File("Opening.mp4").toURI().toString());
		MediaPlayer mp = new MediaPlayer(vid);
		MediaView mv = new MediaView(mp);
		mp.play();
		mv.setX(100);
		mv.setFitHeight(200);
		
		container.getChildren().add(line);
		container.getChildren().add(rect);
		container.getChildren().add(circle);
		container.getChildren().add(iv);
		container.getChildren().add(mv);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("JavaH4");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		launch(args);
//		Application.launch(SnakeGame.class, args);
		SnakeGame.launch(SnakeGame.class);
	}
}
