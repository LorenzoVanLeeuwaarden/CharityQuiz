package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerScreen extends Activity implements View.OnClickListener {

	Button nextLevel;
	TextView explanation, goodfalse, scoreView;
	ImageView charityImage, heart1, heart2, heart3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerscreen);
		initialize();

		// Load method that sets the picture, explanation and good/false text.
		setAnswer();
		// Call method to show current amount of lives.
		setLives();
	}

	// Initialize the variables that will need to be changed or interacted with.
	private void initialize() {
		nextLevel = (Button) findViewById(R.id.bNextLevel);
		explanation = (TextView) findViewById(R.id.tvExplanation);
		goodfalse = (TextView) findViewById(R.id.tvGoodWrong);
		scoreView = (TextView) findViewById(R.id.tvCurrentScore);
		charityImage = (ImageView) findViewById(R.id.ivResultLogo);
		heart1 = (ImageView) findViewById(R.id.ivHeart1);
		heart2 = (ImageView) findViewById(R.id.ivHeart2);
		heart3 = (ImageView) findViewById(R.id.ivHeart3);

		nextLevel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bNextLevel:
			Intent toNextQuestion = new Intent(AnswerScreen.this,
					QuestionScreen.class);
			startActivity(toNextQuestion);
			// Finish screen so users can't go back.
			finish();
			break;
		}
	}

	// Method to set up the correct image, good/wrong text and explanation.
	public void setAnswer() {
		// Set explanation
		explanation.setText(QuestionScreen.data.get(7));

		// Set image.
		String text = QuestionScreen.data.get(6);
		int resId = getResources().getIdentifier(text, "drawable",
				getPackageName());
		charityImage.setImageResource(resId);

		// Set good/wrong text.
		if (QuestionScreen.answerCorFals == false) {
			goodfalse.setText(R.string.wrong);
		} else {
			goodfalse.setText(R.string.good);
		}

		// Display current score
		scoreView.setText(QuestionScreen.scoreString);
	}

	public void setLives() {
		if (QuestionScreen.lives == 2) {
			heart3.setImageDrawable(null);
		} else if (QuestionScreen.lives == 1) {
			heart3.setImageDrawable(null);
			heart2.setImageDrawable(null);
		}
	}
}