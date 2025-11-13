package com.example.demo.model;

import java.util.*;

public class HangmanGame {
    private String word;
    private Set<Character> guessedLetters;
    private Set<Character> correctGuesses;
    private int wrongGuesses;
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
    
    public HangmanGame() {
        reset();
    }
    
    public void reset() {
        Random random = new Random();
        this.word = WORD_LIST.get(random.nextInt(WORD_LIST.size())).toUpperCase();
        this.guessedLetters = new HashSet<>();
        this.correctGuesses = new HashSet<>();
        this.wrongGuesses = 0;
        
        // Give one random letter as a hint
        Random letterRandom = new Random();
        char hintLetter = word.charAt(letterRandom.nextInt(word.length()));
        guessedLetters.add(hintLetter);
        correctGuesses.add(hintLetter);
    }
    
    public boolean guessLetter(char letter) {
        char upperLetter = Character.toUpperCase(letter);
        
        if (guessedLetters.contains(upperLetter)) {
            return false; // Already guessed
        }
        
        guessedLetters.add(upperLetter);
        
        if (word.indexOf(upperLetter) >= 0) {
            correctGuesses.add(upperLetter);
            return true;
        } else {
            wrongGuesses++;
            return false;
        }
    }
    
    public boolean isLetterGuessed(char letter) {
        return guessedLetters.contains(Character.toUpperCase(letter));
    }
    
    public boolean isGameWon() {
        for (char c : word.toCharArray()) {
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
        return isGameWon() || isGameLost();
    }
    
    public String getDisplayWord() {
        StringBuilder display = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (correctGuesses.contains(c)) {
                display.append(c).append(" ");
            } else {
                display.append("_ ");
            }
        }
        return display.toString().trim();
    }
    
    public String getWord() {
        return word;
    }
    
    public int getWrongGuesses() {
        return wrongGuesses;
    }
    
    public int getRemainingGuesses() {
        return MAX_WRONG_GUESSES - wrongGuesses;
    }
    
    public Set<Character> getGuessedLetters() {
        return new HashSet<>(guessedLetters);
    }
}
