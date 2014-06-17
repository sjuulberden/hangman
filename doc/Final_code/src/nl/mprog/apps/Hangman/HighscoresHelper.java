package nl.mprog.apps.Hangman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;

public class HighscoresHelper {
	
	public static ArrayList<Highscore> getHighscores(Activity activity) {
		
		// Get shared preferences
		SharedPreferences settings = activity.getSharedPreferences(HighscoresActivity.PREFS_NAME, 0);
		
		// Initialize output list
		ArrayList<Highscore> output = new ArrayList<Highscore>();
		
		try {
			// Get high scores from shared preferences
			JSONArray highscores = new JSONArray(settings.getString("highscores", "[]"));
			
			// Add all high scores to the output list
			for(int i = 0; i < highscores.length(); i++) {
				JSONObject hs = highscores.getJSONObject(i);
				output.add(new Highscore(hs.getString("name"), hs.getInt("score")));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		// Return output
		return output;
	}
	
	public static void saveHighscores(Activity activity, ArrayList<Highscore> highscores) {
		
		// Get shared preferences
		SharedPreferences settings = activity.getSharedPreferences(HighscoresActivity.PREFS_NAME, 0);
		
		// Get editor for shared preferences
		SharedPreferences.Editor editor = settings.edit();
		
		// Sort high scores on score
		Collections.sort(highscores, new Comparator<Highscore>() {
			@Override
			public int compare(Highscore lhs, Highscore rhs) {
				return rhs.getScore() - lhs.getScore();
			}
		});
		
		// Create serialized (JSON) list of the first 10 high scores
		// Initialize JSON array
		JSONArray json = new JSONArray();
		
		// Get top 10
		if(highscores.size() > 10) {
			highscores = (ArrayList<Highscore>) highscores.subList(0, 9);
		}
		
		// Convert to JSON
		try {
			for(Highscore hs : highscores) {
				JSONObject obj = new JSONObject();
				obj.put("name", hs.getName());
				obj.put("score", hs.getScore());
				json.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Save high scores
		if(json.length() > 0) {
			editor.putString("highscores", json.toString());
			editor.commit();
		}
		
	}
	
}
