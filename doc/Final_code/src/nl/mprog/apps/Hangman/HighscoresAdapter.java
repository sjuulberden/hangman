package nl.mprog.apps.Hangman;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HighscoresAdapter extends ArrayAdapter<Highscore> {
	
	private ArrayList<Highscore> highscores;
	
	public HighscoresAdapter(Context context, int resource, int textViewResource, ArrayList<Highscore> highscores) {
		super(context, resource, textViewResource, highscores);
		this.highscores = highscores;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = super.getView(position, convertView, parent);

		TextView nameTxt = (TextView) row.findViewById(R.id.textView1);
		TextView scoreTxt = (TextView) row.findViewById(R.id.textView2);
		
		if(nameTxt != null) { nameTxt.setText(highscores.get(position).getName()); }
		if(scoreTxt != null) { scoreTxt.setText(String.valueOf(highscores.get(position).getScore())); }
		
		return row;

	}

}
