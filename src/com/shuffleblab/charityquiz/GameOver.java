package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends Activity implements View.OnClickListener {

	Button mainMenu, playAgain;
	TextView scoreView, rightAnswersTV, wrongAnswersTV, endScore;

	public String wrongAnswerString;
	public String rightAnswerString;

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

		mainMenu.setOnClickListener(this);
		playAgain.setOnClickListener(this);

		// Display current score
		scoreView.setText(QuestionScreen.scoreString);
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
		if (tableScore < QuestionScreen.score) {
			database.ourDatabase.execSQL("DELETE FROM "
					+ Database.DATABASE_TABLE + " WHERE _id=1");
			database.createEntry(QuestionScreen.score,
					QuestionScreen.rightAnswers, QuestionScreen.wrongAnswers);
		}
		wrongAnswerString = Integer.toString(QuestionScreen.wrongAnswers);
		rightAnswerString = Integer.toString(QuestionScreen.rightAnswers);

		wrongAnswersTV.setText(wrongAnswerString);
		rightAnswersTV.setText(rightAnswerString);
		endScore.setText(QuestionScreen.scoreString);
	}

	// Reset all variables so user can start over again.
	public void resetVariables() {
		QuestionScreen.lives = 3;
		QuestionScreen.score = 0;
		QuestionScreen.scoreString = "0";
		QuestionScreen.rightAnswers = 0;
		QuestionScreen.wrongAnswers = 0;
	}
}