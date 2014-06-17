package nl.mprog.apps.Hangman;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

abstract public class GameplayActivity extends Activity {

	// Body part images
	private ImageView[] bodyParts;
	
	// Number of body parts
	private int numParts = 6;
	
	// Current part - will increment when wrong answers are chosen
	private int currPart;
	
	// Number correctly guessed
	private int numCorr;
	
	// Word list
	protected String[] words;
	
	// Random generator
	protected Random rand;
	
	// Current word
	protected String currWord;
	
	// Parent view for character views
	private LinearLayout wordLayout;
	
	// Character views
	private TextView[] charViews;
	
	// Letter keyboard view
	private GridView letters;
	
	// Letter adapter
	private LetterAdapter ltrAdapt;

	// Guessed letters
	protected ArrayList<String> guessedLetters = new ArrayList<String>();
	
	// Evil mode?
	private boolean evil;
	
	// Number of guesses
	private int guesses;
	
	// Word length
	private int wordLength;

	// Mistakes left view
	private TextView mistakesLeft;

	/**
	 * Loads settings, loads the GUI and creates references to the views in the GUI
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d("Hangman", "Gameplay activity created");
		
		// Call parent constructor
		super.onCreate(savedInstanceState);
		
		// Load layout
		setContentView(R.layout.activity_game);
		
		// Load shared preferences
		SharedPreferences settings = getSharedPreferences(
				PreferenceActivity.PREFS_NAME, 0);

		// Get settings
		evil = settings.getBoolean("evil", true);
		guesses = settings.getInt("guesses", 6);
		wordLength = settings.getInt("wordLength", 8);

		// Initialize body parts array
		numParts = guesses;
		bodyParts = new ImageView[guesses];
		
		// Set body part image views
		if (guesses == 4) {
			bodyParts[0] = (ImageView) findViewById(R.id.arm1);
			bodyParts[1] = (ImageView) findViewById(R.id.arm2);
			bodyParts[2] = (ImageView) findViewById(R.id.leg1);
			bodyParts[3] = (ImageView) findViewById(R.id.leg2);
		} else if (guesses == 6) {
			bodyParts[0] = (ImageView) findViewById(R.id.head);
			bodyParts[1] = (ImageView) findViewById(R.id.body);
			bodyParts[2] = (ImageView) findViewById(R.id.arm1);
			bodyParts[3] = (ImageView) findViewById(R.id.arm2);
			bodyParts[4] = (ImageView) findViewById(R.id.leg1);
			bodyParts[5] = (ImageView) findViewById(R.id.leg2);
		} else {
			bodyParts[0] = new ImageView(this);
			bodyParts[1] = new ImageView(this);
			bodyParts[2] = (ImageView) findViewById(R.id.head);
			bodyParts[3] = (ImageView) findViewById(R.id.body);
			bodyParts[4] = (ImageView) findViewById(R.id.arm1);
			bodyParts[5] = (ImageView) findViewById(R.id.arm2);
			bodyParts[6] = (ImageView) findViewById(R.id.leg1);
			bodyParts[7] = (ImageView) findViewById(R.id.leg2);
		}

		// Get mistakes left text view
		mistakesLeft = (TextView) findViewById(R.id.textView1);
		
		// Update mistakes left
		mistakesLeft.setText(getResources().getString(R.string.mistakes_left, String.valueOf(guesses)));
		
		// Get words
		Resources res = getResources();
		words = res.getStringArray(R.array.all_words);
		
		// Get random generator
		rand = new Random();
		
		// Initialize current word
		currWord = "";
		
		// Get layout view that will contain the word
		wordLayout = (LinearLayout) findViewById(R.id.word);
		
		// Get letters grid view
		letters = (GridView) findViewById(R.id.letters);
		
		// Start game
		playGame();
	}
	
	// Select a word with chosen length
	protected String getNewWord(int numberOfLetters, String previousWord) {
		
		String output = words[rand.nextInt(words.length)];
		
		Log.d("Hangman", "New word: " + output);
		
		while (output.equals(previousWord) || output.length() != numberOfLetters) {
			output = words[rand.nextInt(words.length)];
			Log.d("Hangman", "New word: " + output);
		}
		return output;
	}
	
	protected void playGame() {
		
		Log.d("Hangman", "playGame called.");
		
		// Hide all body parts at the start of the game
		for (int p = 0; p < numParts; p++) {
			bodyParts[p].setVisibility(View.INVISIBLE);
		}
		
		// Get a random new word
		String newWord = getNewWord(wordLength, currWord);
		
		// Reset 
		currPart = 0;
		numCorr = 0;
		
		// Set current word to the word
		currWord = newWord;
		
		// Initialize a text view for each character
		charViews = new TextView[currWord.length()];
		
		// Initialize the letter adapter
		ltrAdapt = new LetterAdapter(this);
		
		// Set the letter adapter as adapter or the letter grid view 
		letters.setAdapter(ltrAdapt);
		
		// Set character views
		setCharacterViews();
		
	}
	
	private void setCharacterViews() {
		
		// Remove all previous views (if they were there)
		wordLayout.removeAllViews();
		
		// Loop over every character
		for (int c = 0; c < currWord.length(); c++) {
			
			// Initialize text view
			charViews[c] = new TextView(this);
			
			// Set text
			charViews[c].setText("" + currWord.charAt(c));
			
			// Set layout parameters
			charViews[c].setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			// Set text position in view
			charViews[c].setGravity(Gravity.CENTER);
			
			// Set text color
			charViews[c].setTextColor(Color.WHITE);
			
			// Set background resource
			charViews[c].setBackgroundResource(R.drawable.letter_bg);
			
			// Add character text view to layout
			wordLayout.addView(charViews[c]);
		}
	}

	public void letterPressed(View view) {
		
		// Get the letter that was pressed
		String ltr = ((TextView) view).getText().toString();
		
		Log.d("Hangman", "Letter pressed: " + ltr);
		
		// Convert string to char
		char letterChar = ltr.charAt(0);
		
		// Disable view
		view.setEnabled(false);
		
		// Change style to disabled style
		view.setBackgroundResource(R.drawable.letter_down);
		
		// Assume incorrect letter
		boolean correct = false;
		
		// Loop over each character
		for (int k = 0; k < currWord.length(); k++) {
			
			// Add letter to guessed letters
			guessedLetters.add(ltr);
			
			if (currWord.charAt(k) == letterChar) {
				
				// Letter is correct, see if we can change the word (when in evil mode)
				String newCurrWord = pickNewWordWithLetters(letterChar);
				
				Log.d("Hangman", "New word (if in evil mode): " + newCurrWord);
				
				// If a new current word is returned, set it as new current word
				if(newCurrWord != null) {
					// Set new current word
					currWord = newCurrWord;
					
					// Set character views
					setCharacterViews();
					
				// 'Good' mode or no other word could be found
				} else {
					
					// Letter is correct
					correct = true;
					
					// Increase number of correctly chosen letters
					numCorr++;
					
					// Change text color so that the user can read this letter
					charViews[k].setTextColor(Color.BLACK);
				}
			}
		}

		// After a letter is pressed we check whether the player won, lost or may continue.
		if (correct) {
			
			Log.d("Hangman", "Guessed correct!");
			
			// Check if all letters are correctly guessed
			if (numCorr == wordLength) {
				// Disable Buttons
				disableBtns();

				// Display Alert Dialog
				AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
				winBuild.setTitle("YAY");
				winBuild.setMessage("You win!\n\nThe answer was:\n" + currWord + "\n\nPlease enter your name:");
				
				// Create text input
				final EditText nameInput = new EditText(this);
				
				// Set text view as body of dialog
				winBuild.setView(nameInput);
				
				// Set positive button
				winBuild.setPositiveButton("Submit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								// Get name
								String name = nameInput.getText().toString();
								
								// Calculate score
								int score = 20;
								if(evil) { score += 20; }
								score = ((score * currWord.length() / 10) - currPart) * 100;
								
								// Get high scores
								ArrayList<Highscore> highscores = HighscoresHelper.getHighscores(GameplayActivity.this);
								
								// Add high score
								highscores.add(new Highscore(name, score));
								
								// Save high scores
								HighscoresHelper.saveHighscores(GameplayActivity.this, highscores);
								
								// Go to high score activity
								Intent intent = new Intent(GameplayActivity.this, HighscoresActivity.class);
								startActivity(intent);
								
							}
						});

				// Set negative button
				winBuild.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								GameplayActivity.this.finish();
							}
						});

				// Show dialog window
				winBuild.show();
				
			}
			
		// Check if there are guesses left
		} else if (currPart < numParts) {
			// Some guesses left
			bodyParts[currPart].setVisibility(View.VISIBLE);
			currPart++;
		
		// No guesses left
		} else {
			// user has lost
			disableBtns();

			// Display Alert Dialog
			AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
			loseBuild.setTitle("OOPS");
			loseBuild.setMessage("You lose!\n\nThe answer was:\n\n" + currWord);
			loseBuild.setPositiveButton("Play Again",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							GameplayActivity.this.playGame();
						}
					});

			loseBuild.setNegativeButton("Exit",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							GameplayActivity.this.finish();
						}
					});

			loseBuild.show();
		}

		// Update mistakes left text view
		mistakesLeft.setText(getResources().getString(R.string.mistakes_left,
				String.valueOf(guesses - currPart)));

	}
	
	protected abstract String pickNewWordWithLetters(char letterChar);
	
	// Disable all buttons
	public void disableBtns() {
		int numLetters = letters.getChildCount();
		for (int l = 0; l < numLetters; l++) {
			letters.getChildAt(l).setEnabled(false);
		}
	}
}
