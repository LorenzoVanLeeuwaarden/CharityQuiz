package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOver extends Activity implements View.OnClickListener {

	Button mainMenu, playAgain;
	TextView scoreView, rightAnswersTV, wrongAnswersTV, endScore;
	ImageView soundButton;
	MediaPlayer newHighscore, gameover;

	public String wrongAnswerString;
	public String rightAnswerString;
	public int finalScore;
	public String finalScoreString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		initialize();
		setHighScore();
	}

	// Initialize the variables that will need to be changed or interacted with.
	private void initialize() {
		mainMenu = (Button) findViewById(R.id.bToMainMenu);
		playAgain = (Button) findViewById(R.id.bPlayAgain);
		scoreView = (TextView) findViewById(R.id.tvCurrentScore);
		rightAnswersTV = (TextView) findViewById(R.id.tvRightAnswers);
		wrongAnswersTV = (TextView) findViewById(R.id.tvWrongAnswers);
		endScore = (TextView) findViewById(R.id.tvTotalScore);
		soundButton = (ImageView) findViewById(R.id.ivSoundButton);
		newHighscore = MediaPlayer.create(GameOver.this, R.raw.highscore);
		gameover = MediaPlayer.create(GameOver.this, R.raw.gameover);

		mainMenu.setOnClickListener(this);
		playAgain.setOnClickListener(this);
		soundButton.setOnClickListener(this);

		// Display current score.
		scoreView.setText(QuestionScreen.scoreString);

		// Set correct sound button.
		if (QuestionScreen.soundOn == true) {
			soundButton.setImageResource(R.drawable.soundon);
		} else {
			soundButton.setImageResource(R.drawable.soundoff);
		}

		// Calculate players final score.
		finalScore = QuestionScreen.rightAnswers * 8;
		// Set it in a String variable so it can be displayed.
		finalScoreString = Integer.toString(finalScore);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// If main menu button is pressed, return player to the main menu and
		// reset variables.
		case R.id.bToMainMenu:
			resetVariables();
			Intent toGameOverScreen = new Intent(GameOver.this, MainMenu.class);
			startActivity(toGameOverScreen);
			finish();
			break;
		// If main menu button is pressed, start a new game and reset variables.
		case R.id.bPlayAgain:
			resetVariables();
			Intent playAgain = new Intent(GameOver.this, QuestionScreen.class);
			startActivity(playAgain);
			// Finish screen so users can't go back to game over screen.
			finish();
			break;
		case R.id.ivSoundButton:
			setSoundButton();
			break;
		}
	}

	// Set players scores along with the amount of right and wrong answers.
	private void setHighScore() {
		Database database = new Database(this);
		database.open();
		int tableScore = 0;
		Cursor c = database.ourDatabase.query(Database.DATABASE_TABLE,
				database.columns, null, null, null, null, null);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			tableScore = database.getScore(c);
		}
		// If score in database is lower, put new score in database
		if (tableScore < finalScore) {
			database.ourDatabase.execSQL("DELETE FROM "
					+ Database.DATABASE_TABLE + " WHERE _id=1");
			database.createEntry(finalScore, QuestionScreen.rightAnswers,
					QuestionScreen.wrongAnswers);
			playSound(true);
		} else {
			playSound(false);
		}
		wrongAnswerString = Integer.toString(QuestionScreen.wrongAnswers);
		rightAnswerString = Integer.toString(QuestionScreen.rightAnswers);

		wrongAnswersTV.setText(wrongAnswerString);
		rightAnswersTV.setText(rightAnswerString);
		endScore.setText(finalScoreString);
	}

	private void playSound(boolean b) {
		// TODO Auto-generated method stub
		if (QuestionScreen.soundOn == true) {
			if (b == true) {
				newHighscore.start();
			} else {
				gameover.start();
			}
		}
	}

	// Reset all variables so user can start over again.
	public void resetVariables() {
		QuestionScreen.lives = 3;
		QuestionScreen.score = 0;
		QuestionScreen.scoreString = "0";
		QuestionScreen.rightAnswers = 0;
		QuestionScreen.wrongAnswers = 0;
	}

	// Set the sound button image and sound on or off.
	private boolean setSoundButton() {
		// If the sound is currently on while button is pressed, set soundOn to
		// false and change image to sound off image.
		if (QuestionScreen.soundOn == true) {
			soundButton.setImageResource(R.drawable.soundoff);
			QuestionScreen.soundOn = false;
		}
		// If the sound is currently off while button is pressed, set soundOn to
		// true and change image to sound on image.
		else {
			soundButton.setImageResource(R.drawable.soundon);
			QuestionScreen.soundOn = true;
		}
		return QuestionScreen.soundOn;
	}

}