package nl.mprog.apps.Hangman;

import android.util.Log;

public class EvilGameplayActivity extends GameplayActivity {

	@Override
	protected String pickNewWordWithLetters(char guessedLetter) {
		
		// Loop through all words
		WordLoop:
		for(String word : words) {
			
			// Word should be the same length
			if(word.length() != currWord.length()) {
				continue WordLoop;
			}
			
			// Current guess should not be correct
			if(word.contains(String.valueOf(guessedLetter))) {
				continue WordLoop;
			}
			
			// It should comply with all the guesses so far
			for(String guess : guessedLetters) {
				if(word.contains(guess)) {
					continue WordLoop;
				}
			}
			
			// It passed all the tests
			Log.d("Hangman", "Changed word from " + currWord + " to " + word);
			return word;
		}
		
		Log.d("Hangman", "No evil alternative was found");
		
		// No evil alternative was found
		return null;
	}

}
