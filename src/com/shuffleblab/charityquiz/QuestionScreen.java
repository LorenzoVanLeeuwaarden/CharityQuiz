package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class QuestionScreen extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionscreen);
		findViewById(R.id.bAnswer4).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bAnswer4:
			Intent toAnswerScreen = new Intent(QuestionScreen.this,
					AnswerScreen.class);
			startActivity(toAnswerScreen);
			break;
		}
	}
}
