package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerScreen extends Activity implements View.OnClickListener{
	
	Button nextLevel;
	TextView explanation, goodfalse, scoreView;
	ImageView charityImage, heart1, heart2, heart3, soundButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerscreen);
		initialize();
		
		//Load method that sets the picture, explanation and good/false text.
		setAnswer();
		//Call method to show current amount of lives.
		setLives();
	}
	
	//Initialize the variables that will need to be changed or interacted with.
	private void initialize(){
		nextLevel = (Button) findViewById(R.id.bNextLevel);
		explanation = (TextView) findViewById(R.id.tvExplanation);
		goodfalse = (TextView) findViewById(R.id.tvGoodWrong);
		scoreView = (TextView) findViewById(R.id.tvCurrentScore);
		charityImage = (ImageView) findViewById(R.id.ivResultLogo);
		heart1 = (ImageView) findViewById(R.id.ivHeart1);
		heart2 = (ImageView) findViewById(R.id.ivHeart2);
		heart3 = (ImageView) findViewById(R.id.ivHeart3);
		soundButton = (ImageView) findViewById(R.id.ivSoundButton);
		
		// Display correct sound button
				if(QuestionScreen.soundOn==true){
					soundButton.setImageResource(R.drawable.soundon);
				}
				else{
					soundButton.setImageResource(R.drawable.soundoff);
				}
		
		soundButton.setOnClickListener(this);
		nextLevel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bNextLevel:
			Intent toNextQuestion = new Intent(AnswerScreen.this, QuestionScreen.class);
			startActivity(toNextQuestion);
			//Finish screen so users can't go back.
			finish();
			break;
		case R.id.ivSoundButton:
			setSoundButton();
			break;
		}
	}
	
	//Method to set up the correct image, good/wrong text and explanation.
	public void setAnswer(){
		//Set explanation
		explanation.setText(QuestionScreen.data.get(7));
		
		//Set image.
		String text= QuestionScreen.data.get(6);
		int resId = getResources().getIdentifier(text,"drawable",getPackageName());
		charityImage.setImageResource(resId);
		 
		//Set good/wrong text.
		if(QuestionScreen.answerCorFals == false){ 
			goodfalse.setText(R.string.wrong);
		}
		else{
			goodfalse.setText(R.string.good);
		}
		
		//Display current score
		scoreView.setText(QuestionScreen.scoreString);
	}
	
	public void setLives() {
		Log.d("heartsA",QuestionScreen.lives+"");
		if (QuestionScreen.lives == 3) {
			heart1.setImageResource(R.drawable.heart);
			heart2.setImageResource(R.drawable.heart);
			heart3.setImageResource(R.drawable.heart);
			
		}
		// If user has 2 lives left, delete the third heart.
		if (QuestionScreen.lives == 2) {
			heart1.setImageResource(R.drawable.heart);
			heart2.setImageResource(R.drawable.heart);
			heart3.setImageDrawable(null);
		}
		// If user has 1 life left, delete the third and second heart.
		else if (QuestionScreen.lives == 1) {
			heart1.setImageResource(R.drawable.heart);
			heart3.setImageDrawable(null);
			heart2.setImageDrawable(null);
		}
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