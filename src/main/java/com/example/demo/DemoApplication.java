package com.example.demo;

import com.example.demo.ui.HangmanUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication extends Application {

	private static ConfigurableApplicationContext springContext;
	private static String[] savedArgs;

	public static void main(String[] args) {
		savedArgs = args;
		Application.launch(DemoApplication.class, args);
	}

	@Override
	public void init() {
		springContext = SpringApplication.run(DemoApplication.class, savedArgs);
	}

	@Override
	public void start(Stage primaryStage) {
		HangmanUI hangmanUI = new HangmanUI(primaryStage);
		hangmanUI.show();
	}

	@Override
	public void stop() {
		springContext.close();
		Platform.exit();
	}
}
