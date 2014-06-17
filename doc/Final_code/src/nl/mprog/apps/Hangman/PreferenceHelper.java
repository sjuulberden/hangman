package nl.mprog.apps.Hangman;

import android.app.Activity;
import android.content.SharedPreferences;

public class PreferenceHelper {
	
	public static boolean isEvil(Activity activity) {
		// Load shared preferences
		SharedPreferences settings = activity.getSharedPreferences(PreferenceActivity.PREFS_NAME, 0);
		
		// Get whether we're playing in 'evil' mode
		return settings.getBoolean("evil", true);
	}
	
}
