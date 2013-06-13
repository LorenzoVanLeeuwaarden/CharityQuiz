package com.shuffleblab.charityquiz;

import java.util.ArrayList;
import java.util.Random;
import android.media.MediaPlayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionScreen extends Activity implements View.OnClickListener {

	Button a1, a2, a3, a4;
	ImageView questionImage, heart1, heart2, heart3, soundButton ;
	ImageButton powerupButton1, powerupButton2, powerupButton3;
	TextView questionText, scoreView;
	MediaPlayer correctSound, wrongSound;

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
	// Variables about the prices of the powerups 
	public static int powerup1price = 30;
	public static int powerup2price = 20;
	public static int powerup3price = 50;
	
	public static int aantalRemoved;
    public static boolean shieldActive = false; 
    
    
 // Variable that keeps track if the sound is on or off.
    public static boolean soundOn = true;
	
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
		powerupButton1 = (ImageButton) findViewById(R.id.powerupButton1);
		powerupButton2 = (ImageButton) findViewById(R.id.powerupButton2);
		powerupButton3 = (ImageButton) findViewById(R.id.powerupButton3);
		soundButton = (ImageView) findViewById(R.id.ivSoundButton);
		correctSound = MediaPlayer.create(QuestionScreen.this, R.raw.correct);
		wrongSound = MediaPlayer.create(QuestionScreen.this, R.raw.wrong);
		
		
		aantalRemoved = 0;
		
		// Set onclicklistener for answers.
		a1.setOnClickListener(this);
		a2.setOnClickListener(this);
		a3.setOnClickListener(this);
		a4.setOnClickListener(this);
		
		// Set onclicklistener for powerups.
		powerupButton1.setOnClickListener(this);
		powerupButton2.setOnClickListener(this);
		powerupButton3.setOnClickListener(this);
		
		// Set onclicklistener for sound button.
		soundButton.setOnClickListener(this);
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
			if(shieldActive == false){
				chosenAnswer = 1;
				checkAnswer();
				startActivity(checkGameOver());
				// Finish screen so users can't go back and repeat the question.
				finish();
			}else if(shieldActive == true){
				chosenAnswer = 1;
				checkAnswer();
				if (answerCorFals == false){
					a1.setText("");
					a1.setEnabled(false);
					shieldActive = false;
				}else if(answerCorFals == true){
					shieldActive = false;
					startActivity(checkGameOver());
				}
			}
			break;
		case R.id.bAnswer2:
			if(shieldActive == false){
				chosenAnswer = 2;
				checkAnswer();
				startActivity(checkGameOver());
				// Finish screen so users can't go back and repeat the question.
				finish();
			}else if(shieldActive == true){
				chosenAnswer = 2;
				checkAnswer();
				if (answerCorFals == false){
					a2.setText("");
					a2.setEnabled(false);
					shieldActive = false;
				}else if(answerCorFals == true){
					shieldActive = false;
					startActivity(checkGameOver());
				}
			}
			break;
		case R.id.bAnswer3:
			if(shieldActive == false){
				chosenAnswer = 3;
				checkAnswer();
				startActivity(checkGameOver());
				// Finish screen so users can't go back and repeat the question.
				finish();
			}else if(shieldActive == true){
				chosenAnswer = 3;
				checkAnswer();
				if (answerCorFals == false){
					a3.setText("");
					a3.setEnabled(false);
					shieldActive = false;
				}else if(answerCorFals == true){
					shieldActive = false;
					startActivity(checkGameOver());
				}
			}
			break;
		case R.id.bAnswer4:
			if(shieldActive == false){
				chosenAnswer = 4;
				checkAnswer();
				startActivity(checkGameOver());
				// Finish screen so users can't go back and repeat the question.
				finish();
			}else if(shieldActive == true){
				chosenAnswer = 4;
				checkAnswer();
				if (answerCorFals == false){
					a4.setText("");
					a4.setEnabled(false);
					shieldActive = false;
				}else if(answerCorFals == true){
					shieldActive = false;
					startActivity(checkGameOver());
				}
			}
			break;
		case R.id.powerupButton1: //When powerupbutton1 is clicked
			if(score >= powerup1price && aantalRemoved < 2){ //If score/coins is greater than the price of this powerup and this powerup hasn't been used yet.
				
				new AlertDialog.Builder(this) //Create a dialog asking for confirmation if the player really wants to use this powerup 
			    .setTitle("Powerup gebruiken")
			    .setMessage("Met deze powerup kun je 2 foute antwoorden weghalen! Weet je zeker dat je deze powerup wilt gebruiken? Het kost "+ powerup1price + " coins.")
			    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { //If confirmed
			        	score -= powerup1price;					// Deduct score/coins
			            scoreString = Integer.toString(score); 	 
			            scoreView.setText(scoreString);			// Update score
			        	Random remove = new Random();			
						int removePlace = remove.nextInt(4) + 1;	//New random number to remove a random answer
						
						Boolean a1weg = false;		//Check if answer has already been removed
						Boolean a2weg = false;
						Boolean a3weg = false;
						Boolean a4weg = false;
			        		
						while(aantalRemoved < 2){	//While less than 2 answers have been removed
							if(removePlace == rightAnswer){ //If the randomnumber wants to remove the right answer, pick a new number
								removePlace = remove.nextInt(4) + 1;
							}else if(removePlace == 1 && a1weg == false){ //if the randomnumber is 1, and this answer hasn't been removed yet, remove it
								a1.setText("");
								a1.setEnabled(false);
								a1weg = true;
								aantalRemoved += 1;						
								removePlace = remove.nextInt(4) + 1;
							}else if(removePlace == 2 && a2weg == false){ //if the randomnumber is 2, and this answer hasn't been removed yet, remove it
								a2.setText("");
								a2.setEnabled(false);
								a2weg = true;
								aantalRemoved += 1;
								removePlace = remove.nextInt(4) + 1;
							}else if(removePlace == 3 && a3weg == false){ //if the randomnumber is 3, and this answer hasn't been removed yet, remove it
								a3.setText("");
								a3.setEnabled(false);
								a3weg = true;
								aantalRemoved += 1;
								removePlace = remove.nextInt(4) + 1;
							}else if(removePlace == 4 && a4weg == false){ //if the randomnumber is 4, and this answer hasn't been removed yet, remove it
								a4.setText("");
								a4.setEnabled(false);
								a4weg = true;
								aantalRemoved += 1;
								removePlace = remove.nextInt(4) + 1;
							}else{
								removePlace = remove.nextInt(4) + 1;	//If everything fails, get a new number and try again.
							}
							
						}	
			        }
			     })
			    .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // Do nothing.
			        }
			     })
			     .show();
				
				
			
			}else if(aantalRemoved >= 2){ // If there are already 2 answers removed, tell the player they have already used this powerup this round
				new AlertDialog.Builder(this)
			    .setTitle("Powerup al gebruikt!")
			    .setMessage("Je hebt deze power up al gebruikt, wacht op de volgende ronde!")
			    .setPositiveButton("Ga terug", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // Do nothing.
			        }
			     })
			     .show();
			}else if(score < powerup1price){ // If score/coins is less than the price, tell the player they need more coins. 
				new AlertDialog.Builder(this)
			    .setTitle("Niet genoeg coins.")
			    .setMessage("Je hebt nog niet genoeg coins om deze powerup te gebruiken!")
			    .setPositiveButton("Ga terug", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // Do nothing.
			        }
			     })
			     .show();	
			}
			
			break;
		case R.id.powerupButton2: //When powerupbutton2 is clicked
			if(score >= powerup2price){
				new AlertDialog.Builder(this)
			    .setTitle("Powerup gebruiken")
			    .setMessage("Met deze powerup kun je een fout antwoord geven zonder een leven te verliezen! Weet je zeker dat je deze powerup wilt gebruiken? Het kost "+ powerup2price + " coins.")
			    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { //If confirmed, add a life, deduct coins/score, set new score, set new lives.
			        	Log.d("prijs1", score+"");
			        	Log.d("prijs1", powerup2price+"");
			        	score -= powerup2price;					// Deduct score/coins
			        	Log.d("prijs2", score+"");
			        	Log.d("prijs2", powerup2price+"");
			            scoreString = Integer.toString(score); 	 
			            Log.d("prijsString", scoreString);
			            scoreView.setText(scoreString);
						shieldActive = true;
			        }
			     })
			    .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // Do nothing.
			        }
			     })
			     .show();
				
			}else if(score < powerup2price){
				new AlertDialog.Builder(this)
			    .setTitle("Niet genoeg coins.")
			    .setMessage("Je hebt nog niet genoeg coins om deze powerup te gebruiken!")
			    .setPositiveButton("Ga terug", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // Do nothing.
			        }
			     })
			     .show();				
			}
			break;
		case R.id.powerupButton3: //When powerupbutton3 is clicked
			if(score >= powerup3price){  // If score is greater than the cost of this powerup.
				if(lives < 3){// If the player doesn't have the maximum amount of lives, pop up verification, use the powerup.
					new AlertDialog.Builder(this)
				    .setTitle("Powerup gebruiken")
				    .setMessage("Met deze powerup krijg je er een leven bij! Weet je zeker dat je deze powerup wilt gebruiken? Het kost "+ powerup3price + " coins.")
				    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { //If confirmed, add a life, deduct coins/score, set new score, set new lives.
				            lives += 1;
				            score -= powerup3price;
				            scoreString = Integer.toString(score);
				            scoreView.setText(scoreString);
				            setLives();
				        }
				     })
				    .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // Do nothing.
				        }
				     })
				     .show();
				}else if(lives == 3){ // The player already has the maximum amount of lifes, explain this with a pop-up message, do nothing.
					new AlertDialog.Builder(this)
				    .setTitle("Maximaal aantal levens!")
				    .setMessage("Je hebt momenteel al het maximaal aantal levens!")
				    .setPositiveButton("Terug", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // Do nothing.
				        }
				     })
				     .show();
				}
			
			}else if(score < powerup3price){
				new AlertDialog.Builder(this)
			    .setTitle("Niet genoeg coins.")
			    .setMessage("Je hebt nog niet genoeg coins om deze powerup te gebruiken!")
			    .setPositiveButton("Ga terug", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // Do nothing.
			        }
			     })
			     .show();				
			}
			break;
		case R.id.ivSoundButton:
			setSoundButton();
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
		
		// Display correct sound button
				if(soundOn==true){
					soundButton.setImageResource(R.drawable.soundon);
				}
				else{
					soundButton.setImageResource(R.drawable.soundoff);
				}

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
			// Play correct sound
			if (soundOn == true) {
				correctSound.start();
			}
			
		}
		// If the chosen answer doesn't equal the right answer, set variable
		// answerCorFals to false and take 1 life.
		else {
			answerCorFals = false;
			if(shieldActive == false){
				lives--;
			}
			// add 1 to wrong answers.
			wrongAnswers++;
			// Play wrong sound.
			if (soundOn == true) {
				wrongSound.start();
			}
		}

		return answerCorFals;
	}

	// Show right amount of hearts left, so user knows how many lives he has
	// left.
	public void setLives() {
		if (lives == 3) {
			heart1.setImageResource(R.drawable.heart);
			heart2.setImageResource(R.drawable.heart);
			heart3.setImageResource(R.drawable.heart);
			
		}
		// If user has 2 lives left, delete the third heart.
		if (lives == 2) {
			heart1.setImageResource(R.drawable.heart);
			heart2.setImageResource(R.drawable.heart);
			heart3.setImageDrawable(null);
		}
		// If user has 1 life left, delete the third and second heart.
		else if (lives == 1) {
			heart1.setImageResource(R.drawable.heart);
			heart3.setImageDrawable(null);
			heart2.setImageDrawable(null);
		}
	}

	// Check if player is game over, if so return intent to game over, else
	// return intent to answerscreen.
	private Intent checkGameOver() {
		Log.d("heartsQ",lives+"");
		if (lives == 0) {
			nextScreen = new Intent(QuestionScreen.this, GameOver.class);
		} else {
			nextScreen = new Intent(QuestionScreen.this, AnswerScreen.class);
		}

		return nextScreen;
	}
	
	// Set the sound button image and sound on or off.
		private boolean setSoundButton() {
			// If the sound is currently on while button is pressed, set soundOn to
			// false and change image to sound off image.
			if (soundOn == true) {
				soundButton.setImageResource(R.drawable.soundoff);
				soundOn = false;
			}
			// If the sound is currently off while button is pressed, set soundOn to
			// true and change image to sound on image.
			else {
				soundButton.setImageResource(R.drawable.soundon);
				soundOn = true;
			}
			return soundOn;
		}
	
	

}
