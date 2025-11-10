package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.ui.GameUI;
import javafx.application.Platform;
import javafx.stage.Stage;

@SpringBootApplication
public class Application extends javafx.application.Application {

    private static ConfigurableApplicationContext springContext;
    private static String[] savedArgs;

    public static void main(String[] args) {
        savedArgs = args;
        Application.launch(Application.class, args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(Application.class, savedArgs);
    }

    @Override
    public void start(Stage primaryStage) {
        GameUI gameUI = new GameUI(primaryStage);
        gameUI.show();
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
}
