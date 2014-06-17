package nl.mprog.apps.Hangman;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class HighscoresActivity extends Activity {

	public static final String PREFS_NAME = "HangmanHighscores";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscores);

		// Get list view
		ListView list = (ListView) findViewById(R.id.listView1);
		
		// Get high scores
		ArrayList<Highscore> highscores = HighscoresHelper.getHighscores(this);
		
		// Set high score adapter
		list.setAdapter(new HighscoresAdapter(this, R.layout.highscore, R.id.textView1, highscores));
		
	}

}
