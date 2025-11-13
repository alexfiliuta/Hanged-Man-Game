# Quick Start Guide - Hangman Game

## What I Built

I've created a complete Hangman desktop game using Java Spring Boot and JavaFX with the following features:

### Game Features
✅ Random words of at least 12 characters
✅ One letter given as a hint at game start
✅ Interactive alphabet buttons (A-Z)
✅ Visual hangman drawing (6 stages)
✅ Color-coded feedback (Green = correct, Red = wrong)
✅ Letter locking after use
✅ Win/loss detection
✅ New Game button to restart

### Files Created/Modified

1. **`pom.xml`** - Added JavaFX dependencies and Maven plugin
2. **`src/main/java/com/example/demo/DemoApplication.java`** - Main JavaFX application
3. **`src/main/java/com/example/demo/model/HangmanGame.java`** - Game logic
4. **`src/main/java/com/example/demo/ui/HangmanUI.java`** - User interface

### Word List
The game includes 25+ words such as:
- extraordinary
- unbelievable
- magnificent
- revolutionary
- incomprehensible
- transformation
- etc.

## How to Run

### Prerequisites
- Java 17+ installed
- JAVA_HOME environment variable set

### Running the Game

**Option 1: Using JavaFX Maven Plugin (Recommended)**
```powershell
.\mvnw.cmd clean javafx:run
```

**Option 2: Using Maven Wrapper with Spring Boot**
```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

**Option 3: From your IDE**
- Add VM arguments to your run configuration:
  ```
  --module-path "path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics
  ```
- Right-click on `DemoApplication.java`
- Select "Run"

### If JAVA_HOME is not set:
```powershell
# Set JAVA_HOME temporarily
$env:JAVA_HOME = "C:\Path\To\Your\JDK"
.\mvnw.cmd clean javafx:run
```

## Troubleshooting

1. **JAVA_HOME not defined**: Make sure Java is installed and JAVA_HOME points to your JDK installation
2. **JavaFX modules not found**: The pom.xml includes JavaFX dependencies which will be downloaded automatically
3. **Port already in use**: Spring Boot uses port 8080 by default, but this is a desktop app so it shouldn't matter

## Game Play

1. **Start**: Game shows underscores for the hidden word + 1 revealed letter (green)
2. **Guess**: Click letter buttons to guess
3. **Feedback**: 
   - Correct = Button turns green, letter appears in word
   - Wrong = Button turns red, hangman gets a new part drawn
4. **Win**: Complete the word before 6 wrong guesses
5. **Lose**: 6 wrong guesses and hangman is complete
6. **Restart**: Click "New Game" button

Enjoy! 🎮
