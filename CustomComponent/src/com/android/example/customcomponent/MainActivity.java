package com.android.example.customcomponent;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author nishantgupta
 *
 */
public class MainActivity extends Activity  {

	TextView mTextViewTesting;
	Button mButtonSetLimit;

	float screenTouchX, screenTouchY;
	Button mButtonMinus, mButtonPlus;
	EditText mEditTextOuterCircleColor, mEditTextCenterCircleColor,
	mEditTextInnerCircleColor, mEditTextLineColor, mEditTextWordColor,
	mEditTextSectionColor, mEditTextSubSectionColor;
	CircularControlView circularControlView;

	EditText[] editText;	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Resources res = getResources();
		setContentView(R.layout.activity_main);
		circularControlView = (CircularControlView) this.findViewById(R.id.circular_view);
		
		mButtonMinus = (Button) findViewById(R.id.button_minus);
		mButtonPlus = (Button) findViewById(R.id.button_plus);
		mEditTextOuterCircleColor = (EditText) findViewById(R.id.edittext_outer_circle_color);
		mEditTextCenterCircleColor = (EditText) findViewById(R.id.edittext_center_circle_color);
		mEditTextInnerCircleColor = (EditText) findViewById(R.id.edittext_inner_circle_color);
		mEditTextLineColor = (EditText) findViewById(R.id.edittext_line_color);
		mEditTextWordColor = (EditText) findViewById(R.id.edittext_word_color);
		mEditTextSectionColor = (EditText) findViewById(R.id.edittext_section_color);
		mEditTextSubSectionColor = (EditText) findViewById(R.id.edittext_subsection_color);
		mButtonSetLimit= (Button) findViewById(R.id.button_setlimit);
		
		//set ByDefault value to textboxes for demo
		setEditTextBoxValues();
		
		//set Number of Section
		circularControlView.setNumberOfSection(6);

		/*circularControlView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				circularControlView.getTouch(event);
				return true;
			}
		});*/
		/*
		 * Deciding Section Count of the Controlandin each Section ,number of
		 * subSection
		 */
		mButtonMinus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			if (circularControlView.getNumberOfSection() > 1) {
					// myView1.sectionValue[myView1.numberOfSection-1]=0;
					circularControlView.mNumberOfSection--;
					circularControlView.mSelectedSection = 0;
					
				}
				
				
				//setALLPaintColor();
			}
		});
		mButtonPlus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (circularControlView.getNumberOfSection() < circularControlView.getMaxNumberofSection()){
					circularControlView.mNumberOfSection++;
				}

				//circularControlView.addSection("aa");
				
				//setALLPaintColor();
			}
		});

		mButtonSetLimit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Activity activity = MainActivity.this;  
				// new ColorPicker(activity, MainActivity.this, Color.RED).show();


				try{
					circularControlView.setLimitValueByIndex(Integer.parseInt(mEditTextOuterCircleColor.getText().toString()),0);
					circularControlView.setLimitValueByIndex(Integer.parseInt(mEditTextCenterCircleColor.getText().toString()),1);
					circularControlView.setLimitValueByIndex(Integer.parseInt(mEditTextInnerCircleColor.getText().toString()),2);
					circularControlView.setLimitValueByIndex(Integer.parseInt(mEditTextLineColor.getText().toString()),3);
					circularControlView.setLimitValueByIndex(Integer.parseInt(mEditTextWordColor.getText().toString()),4);
					circularControlView.setLimitValueByIndex(Integer.parseInt(mEditTextSectionColor.getText().toString()),5);


				}catch(NumberFormatException e){
					e.printStackTrace();
				}

			}
		});
	}
	void setEditTextBoxValues() {

		mEditTextOuterCircleColor.setText("15");
		mEditTextCenterCircleColor.setText("150");
		mEditTextInnerCircleColor.setText("10");
		mEditTextLineColor.setText("60");
		mEditTextWordColor.setText("6");
		mEditTextSectionColor.setText("20");
	}
	
	protected void setALLPaintColor(){
		circularControlView.setPaintColor(circularControlView.mPaintOuterCircle,
				mEditTextOuterCircleColor.getText().toString());
		circularControlView.setPaintColor(circularControlView.mPaintCenterCircle,
				mEditTextCenterCircleColor.getText().toString());
		circularControlView.setPaintColor(circularControlView.mPaintInnerCircle,
				mEditTextInnerCircleColor.getText().toString());
		circularControlView.setPaintColor(circularControlView.mPaintLine, mEditTextLineColor
				.getText().toString());
		circularControlView.setPaintColor(circularControlView.mPaintWord, mEditTextWordColor
				.getText().toString());
		circularControlView.setPaintColor(circularControlView.mPaintSectionSelectedArc,
				mEditTextSectionColor.getText().toString());
		circularControlView.setPaintColor(circularControlView.mPaintSubSectionArc,
				mEditTextSubSectionColor.getText().toString());	
	}




	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "landscape-Nishant", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			Toast.makeText(this, "portrait-Nishant", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


}


