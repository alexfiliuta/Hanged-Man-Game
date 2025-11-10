package com.example.demo.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class HangmanGameModel {

    private String wordToGuess;
    private Set<Character> guessedLetters;
    private int wrongGuesses;
    private Set<Character> correctGuesses;
    private static final int MAX_WRONG_GUESSES = 6;
    private static final List<String> WORD_LIST = Arrays.asList(
        "extraordinary", "unbelievable", "magnificent", "revolutionary",
        "incomprehensible", "transformation", "encyclopedia", "championship",
        "architecture", "Philadelphia", "international", "neighborhood",
        "announcement", "appreciation", "distinguished", "understanding",
        "accomplishment", "administrator", "opportunities", "Mediterranean",
        "responsibility", "extraordinary", "investigation", "philosophical",
        "sophisticated", "refrigerator", "relationships", "neighborhood"
    );

    public HangmanGameModel() {
        reset();
    }

    public void reset() {
        Random random = new Random();
        this.wordToGuess = WORD_LIST.get(random.nextInt(WORD_LIST.size())).toUpperCase();
        this.guessedLetters = new HashSet<>();
        this.correctGuesses = new HashSet<>();
        this.wrongGuesses = 0;
    
        //Give one random letter as a hint
        Random letteRandomr = new Random();
        char hintLetter = wordToGuess.charAt(letteRandomr.nextInt(wordToGuess.length()));
        guessedLetters.add(hintLetter);
        correctGuesses.add(hintLetter);
    }

    public boolean guesLetter(char letter) {
        char chosenLetter = Character.toUpperCase(letter);
        if (guessedLetters.contains(chosenLetter)) {
            return false; // Letter has already been guessed
        }

        guessedLetters.add(chosenLetter);

        if (wordToGuess.indexOf(chosenLetter) >= 0) {
            correctGuesses.add(chosenLetter);
            return true; // Correct guess
        } else {
            wrongGuesses++;
            return false; // Incorrect guess
        }
    }

    public boolean isLetterGuessed(char letter) {
        return guessedLetters.contains(Character.toUpperCase(letter));
    }

    public boolean isGameWon() {
        for (char c : wordToGuess.toCharArray()) {
            if (!correctGuesses.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameLost() {
        return wrongGuesses >= MAX_WRONG_GUESSES;
    }

    public boolean isGameOver() {
        return isGameLost() || isGameWon();
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public int getRemainingGuesses() {
        return MAX_WRONG_GUESSES - wrongGuesses;
    }

    public String getCurrentWordState() {
        StringBuilder currentState = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (correctGuesses.contains(c)) {
                currentState.append(c).append(" ");
            } else {
                currentState.append("_ ");
            }
        }
        return currentState.toString().trim();
    }
    
}