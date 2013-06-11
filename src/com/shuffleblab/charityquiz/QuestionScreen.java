package com.shuffleblab.charityquiz;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionScreen extends Activity implements View.OnClickListener {

	Button a1, a2, a3, a4;
	ImageView questionImage, heart1, heart2, heart3;
	TextView questionText, scoreView;

	public static Intent nextScreen;
	public static int score = 0;
	public static String scoreString = "0";
	public static int rightAnswers = 0;
	public static int wrongAnswers = 0;

	// Stores an integer which says on which place the right answer will be put.
	private int spot;
	// Keeps track of which spot the right answer is in.
	private int rightAnswer;
	// Keeps track of which answer the user chose.
	private int chosenAnswer;
	// Variable in which is stored if the user answered correct yes or no.
	public static boolean answerCorFals;
	// Variable that keeps track of amount of lives left.
	public static int lives = 3;

	public static ArrayList<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionscreen);
		initialize();

		// Get data from database
		DatabaseHelper db = new DatabaseHelper(this);
		db.openDataBase();
		data = db.getData();

		// Call method to load right questions, answers and picture.
		setQuestion();
		// Call method to show current amount of lives.
		setLives();
	}

	// Initialize the variables that will need to be changed or interacted with.
	private void initialize() {
		a1 = (Button) findViewById(R.id.bAnswer1);
		a2 = (Button) findViewById(R.id.bAnswer2);
		a3 = (Button) findViewById(R.id.bAnswer3);
		a4 = (Button) findViewById(R.id.bAnswer4);
		questionImage = (ImageView) findViewById(R.id.ivQPicture);
		heart1 = (ImageView) findViewById(R.id.ivHeart1);
		heart2 = (ImageView) findViewById(R.id.ivHeart2);
		heart3 = (ImageView) findViewById(R.id.ivHeart3);
		questionText = (TextView) findViewById(R.id.tvQuestion);
		scoreView = (TextView) findViewById(R.id.tvCurrentScore);

		// Set onclicklistener for answers.
		a1.setOnClickListener(this);
		a2.setOnClickListener(this);
		a3.setOnClickListener(this);
		a4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		// All buttons will result in continuing to the same page, so set up
		// Intent before cases.
		// Intent toAnswerScreen = new Intent(QuestionScreen.this,
		// AnswerScreen.class);

		// When button is clicked, assign clicked button to chosenAnswer. Then
		// call checkAnswer() to compare to the right answer.
		switch (v.getId()) {
		case R.id.bAnswer1:
			chosenAnswer = 1;
			checkAnswer();
			startActivity(checkGameOver());
			// Finish screen so users can't go back and repeat the question.
			finish();
			break;
		case R.id.bAnswer2:
			chosenAnswer = 2;
			checkAnswer();
			startActivity(checkGameOver());
			// Finish screen so users can't go back and repeat the question.
			finish();
			break;
		case R.id.bAnswer3:
			chosenAnswer = 3;
			checkAnswer();
			startActivity(checkGameOver());
			// Finish screen so users can't go back and repeat the question.
			finish();
			break;
		case R.id.bAnswer4:
			chosenAnswer = 4;
			checkAnswer();
			startActivity(checkGameOver());
			// Finish screen so users can't go back and repeat the question.
			finish();
			break;
		}
	}

	// Sets up the randomly chosen question, answers and picture.
	public void setQuestion() {

		// Set question.
		questionText.setText(data.get(0));

		// Set image.
		String text = data.get(1);
		int resId = getResources().getIdentifier(text, "drawable",
				getPackageName());
		questionImage.setImageResource(resId);

		// Use a random variable to choose which spot the right answer will be.
		Random chooseSpot = new Random();
		spot = chooseSpot.nextInt(4);

		// Check random variable and sign right answer to that spot, put wrong
		// answers on other spot.
		// Also assign rightAnswer variable to check if user gave the right
		// answer.
		if (spot == 0) {
			a1.setText(data.get(2));
			a2.setText(data.get(3));
			a3.setText(data.get(4));
			a4.setText(data.get(5));
			rightAnswer = 1;
		} else if (spot == 1) {
			a2.setText(data.get(2));
			a1.setText(data.get(3));
			a3.setText(data.get(4));
			a4.setText(data.get(5));
			rightAnswer = 2;
		} else if (spot == 2) {
			a3.setText(data.get(2));
			a1.setText(data.get(3));
			a2.setText(data.get(4));
			a4.setText(data.get(5));
			rightAnswer = 3;
		} else if (spot == 3) {
			a4.setText(data.get(2));
			a1.setText(data.get(3));
			a2.setText(data.get(4));
			a3.setText(data.get(5));
			rightAnswer = 4;
		}

		// Display current score;
		scoreView.setText(scoreString);

	}

	// Method to compare the chosen answer to the right answer.
	private boolean checkAnswer() {
		// If the chosen answers equals the right answer, set variable
		// answerCorFals to true.
		if (chosenAnswer == rightAnswer) {
			answerCorFals = true;
			// Add 8 to score for good answer.
			score += 8;
			scoreString = Integer.toString(score);
			// Add 1 to right answers.
			rightAnswers++;
		}
		// If the chosen answer doesn't equal the right answer, set variable
		// answerCorFals to false and take 1 life.
		else {
			answerCorFals = false;
			lives--;
			// add 1 to wrong answers.
			wrongAnswers++;
		}

		return answerCorFals;
	}

	// Show right amount of hearts left, so user knows how many lives he has
	// left.
	public void setLives() {
		// If user has 2 lives left, delete the third heart.
		if (lives == 2) {
			heart3.setImageDrawable(null);
		}
		// If user has 1 life left, delete the third and second heart.
		else if (lives == 1) {
			heart3.setImageDrawable(null);
			heart2.setImageDrawable(null);
		}
	}

	private Intent checkGameOver() {
		if (lives == 0) {
			nextScreen = new Intent(QuestionScreen.this, GameOver.class);
		} else {
			nextScreen = new Intent(QuestionScreen.this, AnswerScreen.class);
		}

		return nextScreen;
	}

}
