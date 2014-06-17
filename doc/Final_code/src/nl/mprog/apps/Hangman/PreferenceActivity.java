package nl.mprog.apps.Hangman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreferenceActivity extends Activity {

	public static final String PREFS_NAME = "HangmanSettings";

	private boolean evil;
	private int guesses;
	private int wordLength;

	private Button evilBtn;
	private Button normalBtn;
	private Button guesses4Btn;
	private Button guesses6Btn;
	private Button guesses8Btn;
	private Button wordLength4Btn;
	private Button wordLength6Btn;
	private Button wordLength8Btn;
	private Button wordLength10Btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		evilBtn = (Button) findViewById(R.id.button0);
		normalBtn = (Button) findViewById(R.id.button1);
		guesses4Btn = (Button) findViewById(R.id.button2);
		guesses6Btn = (Button) findViewById(R.id.button3);
		guesses8Btn = (Button) findViewById(R.id.button4);
		wordLength4Btn = (Button) findViewById(R.id.button5);
		wordLength6Btn = (Button) findViewById(R.id.button6);
		wordLength8Btn = (Button) findViewById(R.id.button7);
		wordLength10Btn = (Button) findViewById(R.id.button8);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		evil = settings.getBoolean("evil", true);
		guesses = settings.getInt("guesses", 6);
		wordLength = settings.getInt("wordLength", 8);

		updateButtons();

	}

	private void updateButtons() {

		if (evil) {
			evilBtn.setBackgroundResource(R.drawable.letter_up);
			normalBtn.setBackgroundResource(R.drawable.letter_down);
		} else {
			normalBtn.setBackgroundResource(R.drawable.letter_up);
			evilBtn.setBackgroundResource(R.drawable.letter_down);
		}

		if (guesses == 4) {
			guesses4Btn.setBackgroundResource(R.drawable.letter_up);
			guesses6Btn.setBackgroundResource(R.drawable.letter_down);
			guesses8Btn.setBackgroundResource(R.drawable.letter_down);
		} else if (guesses == 6) {
			guesses4Btn.setBackgroundResource(R.drawable.letter_down);
			guesses6Btn.setBackgroundResource(R.drawable.letter_up);
			guesses8Btn.setBackgroundResource(R.drawable.letter_down);
		} else if (guesses == 8) {
			guesses4Btn.setBackgroundResource(R.drawable.letter_down);
			guesses6Btn.setBackgroundResource(R.drawable.letter_down);
			guesses8Btn.setBackgroundResource(R.drawable.letter_up);
		}

		wordLength4Btn.setBackgroundResource(R.drawable.letter_down);
		wordLength6Btn.setBackgroundResource(R.drawable.letter_down);
		wordLength8Btn.setBackgroundResource(R.drawable.letter_down);
		wordLength10Btn.setBackgroundResource(R.drawable.letter_down);

		if (wordLength == 4) {
			wordLength4Btn.setBackgroundResource(R.drawable.letter_up);
		} else if (wordLength == 6) {
			wordLength6Btn.setBackgroundResource(R.drawable.letter_up);
		} else if (wordLength == 8) {
			wordLength8Btn.setBackgroundResource(R.drawable.letter_up);
		} else if (wordLength == 10) {
			wordLength10Btn.setBackgroundResource(R.drawable.letter_up);
		}

	}

	public void buttonClick(View v) {
		
		switch (v.getId()) {
		case R.id.button0:
			evil = true;
			break;
		case R.id.button1:
			evil = false;
			break;
		case R.id.button2:
			guesses = 4;
			break;
		case R.id.button3:
			guesses = 6;
			break;
		case R.id.button4:
			guesses = 8;
			break;
		case R.id.button5:
			wordLength = 4;
			break;
		case R.id.button6:
			wordLength = 6;
			break;
		case R.id.button7:
			wordLength = 8;
			break;
		case R.id.button8:
			wordLength = 10;
			break;
		case R.id.button9:
			
			// Check if we are in evil mode
			if(evil) {
				Intent intent = new Intent(this, EvilGameplayActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, GoodGameplayActivity.class);
				startActivity(intent);
			}
			
			return;
		}

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putBoolean("evil", evil);
		editor.putInt("guesses", guesses);
		editor.putInt("wordLength", wordLength);

		editor.commit();
		
		updateButtons();
	}

}
