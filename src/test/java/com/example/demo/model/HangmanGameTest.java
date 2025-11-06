package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HangmanGame Tests")
class HangmanGameTest {

    private HangmanGameModel game;

    @BeforeEach
    void setUp() {
        game = new HangmanGameModel();
    }

    @Test
    @DisplayName("Game initializes with a word of at least 12 characters")
    void testWordLength() {
        String word = game.getWordToGuess();
        assertNotNull(word, "Word should not be null");
        assertTrue(word.length() >= 12, 
            "Word should be at least 12 characters long, but was: " + word.length());
    }

    @Test
    @DisplayName("Game starts with one letter already revealed as a hint")
    void testInitialHintLetter() {
        String displayWord = game.getCurrentWordState();
        long revealedLetters = displayWord.chars()
            .filter(ch -> ch != '_' && ch != ' ')
            .count();
        
        assertTrue(revealedLetters >= 1, 
            "At least one letter should be revealed at start");
        assertFalse(game.isGameWon(), 
            "Game should not be won at start with only hint letter");
    }

    @Test
    @DisplayName("Correct guess reveals letters in the word")
    void testCorrectGuess() {
        String word = game.getWordToGuess();
        
        // Find a letter that exists in the word but hasn't been guessed
        char letterToGuess = '_';
        for (char c : word.toCharArray()) {
            if (!game.isLetterGuessed(c)) {
                letterToGuess = c;
                break;
            }
        }
        
        if (letterToGuess != '_') {
            String displayBefore = game.getCurrentWordState();
            boolean result = game.guesLetter(letterToGuess);
            String displayAfter = game.getCurrentWordState();
            
            assertTrue(result, "Guessing a correct letter should return true");
            assertNotEquals(displayBefore, displayAfter, 
                "Display word should change after correct guess");
            assertTrue(game.isLetterGuessed(letterToGuess), 
                "Letter should be marked as guessed");
        }
    }

    @Test
    @DisplayName("Wrong guess increases wrong guess count")
    void testWrongGuess() {
        String word = game.getWordToGuess();
        int wrongGuessesBefore = game.getWrongGuesses();
        
        // Find a letter that doesn't exist in the word
        char wrongLetter = 'Z';
        for (char c = 'A'; c <= 'Z'; c++) {
            if (word.indexOf(c) == -1) {
                wrongLetter = c;
                break;
            }
        }
        
        boolean result = game.guesLetter(wrongLetter);
        
        assertFalse(result, "Guessing a wrong letter should return false");
        assertEquals(wrongGuessesBefore + 1, game.getWrongGuesses(), 
            "Wrong guesses should increase by 1");
        assertTrue(game.isLetterGuessed(wrongLetter), 
            "Letter should be marked as guessed even if wrong");
    }

    @Test
    @DisplayName("Guessing the same letter twice returns false")
    void testDuplicateGuess() {
        String word = game.getWordToGuess();
        char letter = word.charAt(0);
        
        game.guesLetter(letter);
        boolean secondGuess = game.guesLetter(letter);
        
        assertFalse(secondGuess, 
            "Guessing the same letter twice should return false");
    }

    @Test
    @DisplayName("Game is won when all letters are guessed")
    void testGameWon() {
        String word = game.getWordToGuess();
        
        // Guess all unique letters in the word
        for (char c : word.toCharArray()) {
            if (!game.isLetterGuessed(c)) {
                game.guesLetter(c);
            }
        }
        
        assertTrue(game.isGameWon(), "Game should be won when all letters guessed");
        assertFalse(game.isGameLost(), "Game should not be lost when won");
        assertTrue(game.isGameOver(), "Game should be over when won");
    }

    @Test
    @DisplayName("Game is lost after 6 wrong guesses")
    void testGameLost() {
        String word = game.getWordToGuess();
        
        // Make 6 wrong guesses
        int wrongGuesses = 0;
        for (char c = 'A'; c <= 'Z' && wrongGuesses < 6; c++) {
            if (word.indexOf(c) == -1) {
                game.guesLetter(c);
                wrongGuesses++;
            }
        }
        
        assertTrue(game.isGameLost(), "Game should be lost after 6 wrong guesses");
        assertFalse(game.isGameWon(), "Game should not be won when lost");
        assertTrue(game.isGameOver(), "Game should be over when lost");
        assertEquals(6, game.getWrongGuesses(), "Should have exactly 6 wrong guesses");
        assertEquals(0, game.getRemainingGuesses(), "Should have 0 remaining guesses");
    }

    @Test
    @DisplayName("Remaining guesses decreases with wrong guesses")
    void testRemainingGuesses() {
        String word = game.getWordToGuess();
        int initialRemaining = game.getRemainingGuesses();
        
        assertEquals(6, initialRemaining, "Should start with 6 remaining guesses");
        
        // Make a wrong guess
        char wrongLetter = 'Z';
        for (char c = 'A'; c <= 'Z'; c++) {
            if (word.indexOf(c) == -1) {
                wrongLetter = c;
                break;
            }
        }
        
        game.guesLetter(wrongLetter);
        assertEquals(5, game.getRemainingGuesses(), 
            "Remaining guesses should decrease after wrong guess");
    }

    @Test
    @DisplayName("Reset game creates new word and resets state")
    void testReset() {
        // Make some guesses
        game.guesLetter('A');
        game.guesLetter('E');
        
        game.reset();
        
        assertEquals(0, game.getWrongGuesses(), 
            "Wrong guesses should reset to 0");
        assertEquals(6, game.getRemainingGuesses(), 
            "Remaining guesses should reset to 6");
        assertFalse(game.isGameOver(), 
            "Game should not be over after reset");
        
        // Word might be same or different (random), but should be valid
        String newWord = game.getWordToGuess();
        assertNotNull(newWord, "Word should not be null after reset");
        assertTrue(newWord.length() >= 12, 
            "Word should still be at least 12 characters after reset");
    }

    @Test
    @DisplayName("Display word shows underscores for unguessed letters")
    void testDisplayWord() {
        String displayWord = game.getCurrentWordState();
        
        assertNotNull(displayWord, "Display word should not be null");
        assertTrue(displayWord.contains("_"), 
            "Display word should contain underscores for unguessed letters");
        
        // After guessing all letters, should have no underscores
        String word = game.getWordToGuess();
        for (char c : word.toCharArray()) {
            if (!game.isLetterGuessed(c)) {
                game.guesLetter(c);
            }
        }
        
        String finalDisplay = game.getCurrentWordState();
        assertFalse(finalDisplay.contains("_"), 
            "Display word should not contain underscores when all letters guessed");
    }

    @Test
    @DisplayName("Guessed letters are tracked correctly")
    void testGuessedLetters() {
        assertTrue(game.getGuessedLetters().size() >= 1, 
            "Should have at least the hint letter guessed");
        
        game.guesLetter('A');
        game.guesLetter('E');
        game.guesLetter('I');
        
        assertTrue(game.getGuessedLetters().contains('A'), 
            "Guessed letters should contain A");
        assertTrue(game.getGuessedLetters().contains('E'), 
            "Guessed letters should contain E");
        assertTrue(game.getGuessedLetters().contains('I'), 
            "Guessed letters should contain I");
    }

    @Test
    @DisplayName("Case insensitive letter guessing")
    void testCaseInsensitive() {
        String word = game.getWordToGuess();
        char letter = '_';
        
        // Find an unguessed letter
        for (char c : word.toCharArray()) {
            if (!game.isLetterGuessed(c)) {
                letter = c;
                break;
            }
        }
        
        if (letter != '_') {
            // Guess with lowercase
            char lowercase = Character.toLowerCase(letter);
            game.guesLetter(lowercase);
            
            assertTrue(game.isLetterGuessed(letter), 
                "Uppercase version should be marked as guessed");
            assertTrue(game.isLetterGuessed(lowercase), 
                "Lowercase version should be marked as guessed");
        }
    }

    @Test
    @DisplayName("Word is always uppercase")
    void testWordIsUppercase() {
        String word = game.getWordToGuess();
        assertEquals(word, word.toUpperCase(), 
            "Word should be in uppercase");
    }

    @Test
    @DisplayName("Game not over at start")
    void testGameNotOverAtStart() {
        assertFalse(game.isGameOver(), "Game should not be over at start");
        assertFalse(game.isGameWon(), "Game should not be won at start");
        assertFalse(game.isGameLost(), "Game should not be lost at start");
    }
}
