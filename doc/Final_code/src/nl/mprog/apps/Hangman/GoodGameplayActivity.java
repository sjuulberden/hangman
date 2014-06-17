package nl.mprog.apps.Hangman;


public class GoodGameplayActivity extends GameplayActivity {

	@Override
	protected String pickNewWordWithLetters(char letterChar) {
		// The 'good' game play should not provide other words, always return null:
		return null;
	}

}
