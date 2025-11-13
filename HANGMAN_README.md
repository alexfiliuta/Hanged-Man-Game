# Hangman Game - Java Spring Boot + JavaFX

A desktop Hangman game built with Java Spring Boot and JavaFX.

## Features

- Random words of at least 12 characters from English language
- One letter is given as a hint at the start
- Interactive alphabet buttons for letter guessing
- Visual hangman drawing that updates with each wrong guess
- 6 wrong guesses allowed before game over
- Letters are locked after being used
- Win/loss detection with appropriate messages
- "New Game" button to restart

## Requirements

- Java 17 or higher (tested with Java 25)
- Maven 3.6+

## How to Run

### Option 1: Using Maven Spring Boot Plugin
```bash
mvn clean install
mvn spring-boot:run
```

### Option 2: Using JavaFX Maven Plugin
```bash
mvn clean javafx:run
```

### Option 3: Build and Run JAR
```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## How to Play

1. Launch the application
2. You'll see a random word with underscores (one letter is revealed as a hint)
3. Click on letter buttons to guess
4. Green buttons = correct guess
5. Red buttons = wrong guess
6. Game ends when:
   - You complete the word (WIN!)
   - The hangman is fully drawn after 6 wrong guesses (LOSE)
7. Click "New Game" to play again

## Project Structure

```
src/main/java/com/example/demo/
├── DemoApplication.java          # Main Spring Boot + JavaFX application
├── model/
│   └── HangmanGame.java          # Game logic (word selection, guessing, state)
└── ui/
    └── HangmanUI.java            # JavaFX user interface
```

## Game Rules

- Words are randomly selected from a predefined list
- All words have at least 12 characters
- One random letter is revealed at the start
- Each letter can only be guessed once
- 6 wrong guesses = game over
- Complete the word = victory!

Enjoy the game! 🎮
