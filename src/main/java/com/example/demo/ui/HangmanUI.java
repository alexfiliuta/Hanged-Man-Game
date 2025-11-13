package com.example.demo.ui;

import com.example.demo.model.HangmanGame;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HangmanUI {
    private HangmanGame game;
    private Stage stage;
    private Label wordLabel;
    private Label statusLabel;
    private Label remainingGuessesLabel;
    private Canvas canvas;
    private GridPane letterButtons;
    private Button resetButton;
    private Pane confettiPane;
    private List<ConfettiParticle> confettiParticles;
    
    public HangmanUI(Stage stage) {
        this.stage = stage;
        this.game = new HangmanGame();
        this.confettiParticles = new ArrayList<>();
        setupUI();
    }
    
    private void setupUI() {
        StackPane root = new StackPane();
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(40));
        
        // Modern gradient background
        String gradientStyle = "-fx-background-color: linear-gradient(to bottom, #667eea 0%, #764ba2 100%);";
        mainPane.setStyle(gradientStyle);
        
        // Top: Title and status with modern styling
        VBox topBox = new VBox(15);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 30, 0));
        
        Label titleLabel = new Label("🎮 HANGMAN");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 48));
        titleLabel.setTextFill(Color.WHITE);
        DropShadow titleShadow = new DropShadow();
        titleShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        titleShadow.setRadius(10);
        titleShadow.setOffsetX(0);
        titleShadow.setOffsetY(4);
        titleLabel.setEffect(titleShadow);
        
        statusLabel = new Label("Guess the word!");
        statusLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 20));
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 10 20; -fx-background-radius: 25;");
        
        remainingGuessesLabel = new Label("❤".repeat(6));
        remainingGuessesLabel.setFont(Font.font("Segoe UI", 24));
        
        topBox.getChildren().addAll(titleLabel, statusLabel, remainingGuessesLabel);
        mainPane.setTop(topBox);
        
        // Center: Modern card-style container for canvas and word
        VBox centerBox = new VBox(25);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMaxWidth(600);
        
        // Canvas container with modern card styling
        StackPane canvasContainer = new StackPane();
        canvasContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); " +
                                 "-fx-background-radius: 20; " +
                                 "-fx-padding: 20;");
        DropShadow cardShadow = new DropShadow();
        cardShadow.setColor(Color.rgb(0, 0, 0, 0.3));
        cardShadow.setRadius(15);
        cardShadow.setOffsetY(5);
        canvasContainer.setEffect(cardShadow);
        
        canvas = new Canvas(350, 350);
        canvasContainer.getChildren().add(canvas);
        
        // Word display with modern styling
        wordLabel = new Label(game.getDisplayWord());
        wordLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 32));
        wordLabel.setTextFill(Color.rgb(70, 70, 90));
        wordLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); " +
                          "-fx-padding: 20 40; " +
                          "-fx-background-radius: 15; " +
                          "-fx-border-color: rgba(102, 126, 234, 0.3); " +
                          "-fx-border-width: 2; " +
                          "-fx-border-radius: 15;");
        wordLabel.setEffect(cardShadow);
        
        centerBox.getChildren().addAll(canvasContainer, wordLabel);
        mainPane.setCenter(centerBox);
        
        // Bottom: Modern letter buttons and reset button
        VBox bottomBox = new VBox(30);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(30, 0, 30, 0));
        
        letterButtons = createLetterButtons();
        
        resetButton = new Button("🔄 NEW GAME");
        resetButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        resetButton.setStyle("-fx-background-color: linear-gradient(to bottom, #f093fb 0%, #f5576c 100%); " +
                            "-fx-text-fill: white; " +
                            "-fx-padding: 15 40; " +
                            "-fx-background-radius: 30; " +
                            "-fx-cursor: hand;");
        resetButton.setEffect(cardShadow);
        resetButton.setOnMouseEntered(e -> {
            resetButton.setStyle("-fx-background-color: linear-gradient(to bottom, #f5576c 0%, #f093fb 100%); " +
                                "-fx-text-fill: white; " +
                                "-fx-padding: 15 40; " +
                                "-fx-background-radius: 30; " +
                                "-fx-cursor: hand; " +
                                "-fx-scale-x: 1.1; " +
                                "-fx-scale-y: 1.1;");
        });
        resetButton.setOnMouseExited(e -> {
            resetButton.setStyle("-fx-background-color: linear-gradient(to bottom, #f093fb 0%, #f5576c 100%); " +
                                "-fx-text-fill: white; " +
                                "-fx-padding: 15 40; " +
                                "-fx-background-radius: 30; " +
                                "-fx-cursor: hand;");
        });
        resetButton.setOnAction(e -> resetGame());
        
        bottomBox.getChildren().addAll(letterButtons, resetButton);
        mainPane.setBottom(bottomBox);
        
        // Confetti pane overlay
        confettiPane = new Pane();
        confettiPane.setMouseTransparent(true);
        
        root.getChildren().addAll(mainPane, confettiPane);
        
        Scene scene = new Scene(root, 1000, 950);
        stage.setScene(scene);
        stage.setTitle("🎮 Hangman Game");
        stage.setResizable(false);
        
        drawHangman();
        updateRemainingGuesses();
    }
    
    private GridPane createLetterButtons() {
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int col = 0;
        int row = 0;
        
        for (char letter : alphabet.toCharArray()) {
            Button btn = new Button(String.valueOf(letter));
            btn.setMinSize(50, 50);
            btn.setMaxSize(50, 50);
            btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
            
            // Modern button styling
            String defaultStyle = "-fx-background-color: linear-gradient(to bottom, #667eea 0%, #764ba2 100%); " +
                                 "-fx-text-fill: white; " +
                                 "-fx-background-radius: 10; " +
                                 "-fx-cursor: hand;";
            btn.setStyle(defaultStyle);
            
            DropShadow btnShadow = new DropShadow();
            btnShadow.setColor(Color.rgb(0, 0, 0, 0.3));
            btnShadow.setRadius(5);
            btnShadow.setOffsetY(2);
            btn.setEffect(btnShadow);
            
            // Check if this letter was given as a hint
            if (game.isLetterGuessed(letter)) {
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: linear-gradient(to bottom, #56ab2f 0%, #a8e063 100%); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 10; " +
                            "-fx-opacity: 1.0;");
            } else {
                // Hover effects for enabled buttons
                btn.setOnMouseEntered(e -> {
                    if (!btn.isDisabled()) {
                        btn.setStyle("-fx-background-color: linear-gradient(to bottom, #764ba2 0%, #667eea 100%); " +
                                    "-fx-text-fill: white; " +
                                    "-fx-background-radius: 10; " +
                                    "-fx-cursor: hand; " +
                                    "-fx-scale-x: 1.1; " +
                                    "-fx-scale-y: 1.1;");
                    }
                });
                btn.setOnMouseExited(e -> {
                    if (!btn.isDisabled()) {
                        btn.setStyle(defaultStyle);
                    }
                });
            }
            
            btn.setOnAction(e -> handleLetterClick(btn, letter));
            
            grid.add(btn, col, row);
            col++;
            if (col > 12) {
                col = 0;
                row++;
            }
        }
        
        return grid;
    }
    
    private void handleLetterClick(Button btn, char letter) {
        boolean correct = game.guessLetter(letter);
        
        btn.setDisable(true);
        if (correct) {
            btn.setStyle("-fx-background-color: linear-gradient(to bottom, #56ab2f 0%, #a8e063 100%); " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-opacity: 1.0;");
            statusLabel.setText("✓ Great guess!");
            statusLabel.setStyle("-fx-background-color: rgba(86, 171, 47, 0.3); " +
                               "-fx-padding: 10 20; " +
                               "-fx-background-radius: 25; " +
                               "-fx-text-fill: white;");
        } else {
            btn.setStyle("-fx-background-color: linear-gradient(to bottom, #eb3349 0%, #f45c43 100%); " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-opacity: 1.0;");
            statusLabel.setText("✗ Wrong! " + game.getRemainingGuesses() + " left");
            statusLabel.setStyle("-fx-background-color: rgba(235, 51, 73, 0.3); " +
                               "-fx-padding: 10 20; " +
                               "-fx-background-radius: 25; " +
                               "-fx-text-fill: white;");
        }
        
        wordLabel.setText(game.getDisplayWord());
        drawHangman();
        updateRemainingGuesses();
        
        if (game.isGameWon()) {
            statusLabel.setText("🎉 CONGRATULATIONS! YOU WON! 🎉");
            statusLabel.setStyle("-fx-background-color: rgba(86, 171, 47, 0.5); " +
                               "-fx-padding: 15 30; " +
                               "-fx-background-radius: 25; " +
                               "-fx-text-fill: white; " +
                               "-fx-font-size: 22px; " +
                               "-fx-font-weight: bold;");
            disableAllLetters();
            launchConfetti();
        } else if (game.isGameLost()) {
            statusLabel.setText("💀 Game Over! The word was: " + game.getWord());
            statusLabel.setStyle("-fx-background-color: rgba(235, 51, 73, 0.5); " +
                               "-fx-padding: 15 30; " +
                               "-fx-background-radius: 25; " +
                               "-fx-text-fill: white; " +
                               "-fx-font-size: 18px;");
            wordLabel.setText(game.getWord());
            disableAllLetters();
        }
    }
    
    private void disableAllLetters() {
        letterButtons.getChildren().forEach(node -> {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        });
    }
    
    private void resetGame() {
        game.reset();
        wordLabel.setText(game.getDisplayWord());
        statusLabel.setText("Guess the word!");
        statusLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); " +
                           "-fx-padding: 10 20; " +
                           "-fx-background-radius: 25; " +
                           "-fx-text-fill: white;");
        
        // Clear confetti
        confettiPane.getChildren().clear();
        confettiParticles.clear();
        
        // Re-create letter buttons
        StackPane root = (StackPane) stage.getScene().getRoot();
        BorderPane mainPane = (BorderPane) root.getChildren().get(0);
        VBox bottomBox = (VBox) mainPane.getBottom();
        bottomBox.getChildren().remove(letterButtons);
        letterButtons = createLetterButtons();
        bottomBox.getChildren().add(0, letterButtons);
        
        drawHangman();
        updateRemainingGuesses();
    }
    
    private void updateRemainingGuesses() {
        int remaining = game.getRemainingGuesses();
        remainingGuessesLabel.setText("❤".repeat(remaining) + "🖤".repeat(6 - remaining));
    }
    
    private void drawHangman() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        int wrongGuesses = game.getWrongGuesses();
        
        // Modern styling with gradients
        gc.setLineWidth(4);
        gc.setStroke(Color.rgb(70, 70, 90));
        
        // Base with shadow
        gc.setFill(Color.rgb(70, 70, 90, 0.3));
        gc.fillRect(20, 330, 130, 8);
        gc.strokeLine(20, 330, 150, 330);
        
        // Pole
        gc.strokeLine(60, 330, 60, 30);
        
        // Top beam
        gc.strokeLine(60, 30, 200, 30);
        
        // Rope
        gc.setLineWidth(3);
        gc.strokeLine(200, 30, 200, 70);
        
        if (wrongGuesses >= 1) {
            // Head with color
            gc.setLineWidth(4);
            gc.setStroke(Color.rgb(235, 51, 73));
            gc.strokeOval(175, 70, 50, 50);
        }
        
        if (wrongGuesses >= 2) {
            // Body
            gc.strokeLine(200, 120, 200, 210);
        }
        
        if (wrongGuesses >= 3) {
            // Left arm
            gc.strokeLine(200, 140, 160, 170);
        }
        
        if (wrongGuesses >= 4) {
            // Right arm
            gc.strokeLine(200, 140, 240, 170);
        }
        
        if (wrongGuesses >= 5) {
            // Left leg
            gc.strokeLine(200, 210, 170, 270);
        }
        
        if (wrongGuesses >= 6) {
            // Right leg
            gc.strokeLine(200, 210, 230, 270);
            
            // Face (game over)
            gc.setLineWidth(3);
            gc.strokeLine(185, 87, 192, 94); // Left eye X
            gc.strokeLine(192, 87, 185, 94);
            gc.strokeLine(208, 87, 215, 94); // Right eye X
            gc.strokeLine(215, 87, 208, 94);
            // Sad mouth
            gc.beginPath();
            gc.arc(200, 105, 12, 12, 0, 180);
            gc.stroke();
        }
    }
    
    private void launchConfetti() {
        Random random = new Random();
        
        // Create confetti particles from all four corners
        for (int i = 0; i < 150; i++) {
            double startX, startY;
            double velocityX, velocityY;
            
            // Determine which corner
            int corner = i % 4;
            switch (corner) {
                case 0: // Top-left
                    startX = 0;
                    startY = 0;
                    velocityX = random.nextDouble() * 4 + 2;
                    velocityY = random.nextDouble() * 4 + 2;
                    break;
                case 1: // Top-right
                    startX = 1000;
                    startY = 0;
                    velocityX = -(random.nextDouble() * 4 + 2);
                    velocityY = random.nextDouble() * 4 + 2;
                    break;
                case 2: // Bottom-left
                    startX = 0;
                    startY = 950;
                    velocityX = random.nextDouble() * 4 + 2;
                    velocityY = -(random.nextDouble() * 4 + 2);
                    break;
                default: // Bottom-right
                    startX = 1000;
                    startY = 950;
                    velocityX = -(random.nextDouble() * 4 + 2);
                    velocityY = -(random.nextDouble() * 4 + 2);
                    break;
            }
            
            ConfettiParticle particle = new ConfettiParticle(startX, startY, velocityX, velocityY);
            confettiParticles.add(particle);
            confettiPane.getChildren().add(particle.circle);
        }
        
        // Animate confetti
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (ConfettiParticle particle : confettiParticles) {
                    particle.update();
                }
                
                // Stop after 5 seconds
                Timeline stopTimer = new Timeline(new KeyFrame(Duration.seconds(5), e -> this.stop()));
                stopTimer.setCycleCount(1);
                stopTimer.play();
            }
        };
        timer.start();
    }
    
    // Confetti particle class
    private class ConfettiParticle {
        Circle circle;
        double velocityX;
        double velocityY;
        double rotation;
        double rotationSpeed;
        
        public ConfettiParticle(double x, double y, double vx, double vy) {
            Random random = new Random();
            
            circle = new Circle(x, y, random.nextDouble() * 5 + 3);
            
            // Random bright colors
            Color[] colors = {
                Color.rgb(255, 107, 107),
                Color.rgb(255, 195, 113),
                Color.rgb(255, 234, 167),
                Color.rgb(206, 255, 147),
                Color.rgb(130, 204, 221),
                Color.rgb(162, 155, 254),
                Color.rgb(255, 159, 243)
            };
            circle.setFill(colors[random.nextInt(colors.length)]);
            
            this.velocityX = vx;
            this.velocityY = vy;
            this.rotation = 0;
            this.rotationSpeed = random.nextDouble() * 10 - 5;
        }
        
        public void update() {
            circle.setCenterX(circle.getCenterX() + velocityX);
            circle.setCenterY(circle.getCenterY() + velocityY);
            
            // Apply gravity
            velocityY += 0.15;
            
            // Rotation
            rotation += rotationSpeed;
            circle.setRotate(rotation);
            
            // Fade out
            if (circle.getOpacity() > 0) {
                circle.setOpacity(circle.getOpacity() - 0.005);
            }
        }
    }
    
    public void show() {
        stage.show();
    }
}
