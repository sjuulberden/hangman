package nl.mprog.apps.Hangman;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call parent constructor
		super.onCreate(savedInstanceState);
		
		// Set layout
		setContentView(R.layout.activity_main);
		
		// Get button references
		Button playBtn = (Button) findViewById(R.id.playBtn);
		Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
		Button highscoresBtn = (Button) findViewById(R.id.highscoresBtn);

		// Set click listeners
		playBtn.setOnClickListener(this);
		settingsBtn.setOnClickListener(this);
		highscoresBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.playBtn) {
			// Check if we are in evil mode
			if(PreferenceHelper.isEvil(this)) {
				Intent intent = new Intent(this, EvilGameplayActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, GoodGameplayActivity.class);
				startActivity(intent);
			}
		} else if (view.getId() == R.id.settingsBtn) {
			Intent settingsIntent = new Intent(this, PreferenceActivity.class);
			startActivity(settingsIntent);
		} else if (view.getId() == R.id.highscoresBtn) {
			Intent intent = new Intent(this, HighscoresActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	// A placeholder fragment containing a simple view. 
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
