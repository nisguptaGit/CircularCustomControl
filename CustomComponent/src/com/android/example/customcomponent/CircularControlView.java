package com.android.example.customcomponent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.Toast;
/**
 * 
 * @author nishantgupta
 * 
 */
public class CircularControlView extends View {
	private final int MAX_NO_OF_SECTION = 6;

	private int mViewHeight;
	private int mViewWidth;
	private boolean mIsInsideInnermostCircle;

	public int mNumberOfSection, mNumberOfSubSection;
	public int mSelectedSection;

	private float mViewTouchX, mViewTouchY;
	private RectF mRectSectionArcOuter, mRectSectionArcInner, mRectSubSectionArc;
	public Paint mPaintOuterCircle, mPaintCenterCircle, mPaintSubSectionArc,
			mPaintLine, mPaintWord, mPaintInnerCircle,
			mPaintSectionSelectedArc, mPaintSectionName;
	
	private float[] xLines;
	private float[] yLines;
	private float[] xT;
	private float[] yT;
	private int[] sectionProgress;
	private int[] sectionMaxLimit;
	private String[] sectionNames;
	public boolean flagForHandleTouch;
	boolean isFirstTimeCallonDraw;

	private float circleCenterPointX, circleCenterPointY;

	private int minViewScreenSide;

	private float minViewScreenDivide2;
	private float minViewScreenDivide3;
	private float minViewScreenDivide4;
	private float minViewScreenDivide5;
	private float minViewScreenDivide8;
	private float minViewScreenDivide10;
	private float minViewScreenDivide12;
	private float minViewScreenDivide80;

	// int circleCenterPointY;
	// int circleCenterPointX ;

	public CircularControlView(Context context) {
		super(context);

		initView();
	}

	public CircularControlView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView();
	}

	public CircularControlView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
	
	public void initView() {
		this.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				mViewTouchX = event.getX();
				mViewTouchY = event.getY();

				switch (event.getAction()) {

				case android.view.MotionEvent.ACTION_DOWN:
					setflagHandleTouch(true);
					// istouchActionDown=true;
					// isInsideInnermostCircle=false;
					setTouchPoint(mViewTouchX, mViewTouchY);
					break;

				case android.view.MotionEvent.ACTION_UP:
					setflagHandleTouch(false);
					// istouchActionDown=false;
					setTouchPoint(mViewTouchX, mViewTouchY);
					mIsInsideInnermostCircle = false;
					break;

				case android.view.MotionEvent.ACTION_MOVE:
					setflagHandleTouch(true);
					// isInsideInnermostCircle=true;
					// istouchActionDown=false;
					setTouchPoint(mViewTouchX, mViewTouchY);
					break;

				}

				return true;
			}
		});
		
		mPaintOuterCircle = new Paint();
		mPaintOuterCircle.setColor(Color.parseColor("#FFB2BFDE"));
		mPaintOuterCircle.setAntiAlias(true);
		mPaintOuterCircle.setTextSize(25);
		mPaintOuterCircle.setShadowLayer(1, circleCenterPointX,circleCenterPointY, Color.GRAY);
		
		mPaintCenterCircle = new Paint();
		mPaintCenterCircle.setColor(Color.parseColor("#ffdde9"));
		mPaintCenterCircle.setShadowLayer(1, circleCenterPointX, circleCenterPointY, Color.GRAY);
		
		mPaintSubSectionArc=new Paint();
		mPaintSubSectionArc.setColor(Color.parseColor("#e79dc1"));
		mPaintSubSectionArc.setShadowLayer(1, circleCenterPointX, circleCenterPointY, Color.GRAY);
		
		mPaintLine = new Paint();
		mPaintLine.setColor(Color.parseColor("#104040"));
		mPaintLine.setAntiAlias(true);
		mPaintLine.setTextSize(40);
		mPaintLine.setShadowLayer(1, circleCenterPointX, circleCenterPointY, Color.GRAY);
		
		mPaintWord = new Paint();
		mPaintWord.setColor(Color.parseColor("#233467"));
		mPaintWord.setAntiAlias(true);
		mPaintWord.setTextSize(40);
		mPaintWord.setShadowLayer(1, circleCenterPointX, circleCenterPointY, Color.GRAY);
		
		mPaintInnerCircle = new Paint();
		mPaintInnerCircle.setColor(Color.parseColor("#899dbe"));
		mPaintInnerCircle.setAntiAlias(true);
		mPaintInnerCircle.setTextSize(25);
		mPaintInnerCircle.setShadowLayer(1, circleCenterPointX,	circleCenterPointY, Color.GRAY);
		
		mPaintSectionSelectedArc = new Paint();
		mPaintSectionSelectedArc.setColor(Color.parseColor("#899dbe"));
		mPaintSectionSelectedArc.setAntiAlias(true);
		mPaintSectionSelectedArc.setShadowLayer(5, circleCenterPointX, circleCenterPointY, Color.GRAY);

		mPaintSectionName = new Paint();
		mPaintSectionName.setColor(Color.parseColor("#233467"));
		mPaintSectionName.setAntiAlias(true);
		mPaintSectionName.setTextSize(25);
		mPaintSectionName.setStrokeWidth(0.3f);
		mPaintSectionName.setShadowLayer(1, circleCenterPointX, circleCenterPointY, Color.GRAY);
		mPaintSectionName.setTextAlign(Align.CENTER);
		
		
		// set default value to all RectF
		mRectSubSectionArc = new RectF(0, 0, 0, 0);
		mRectSectionArcOuter = new RectF(0, 0, 0, 0);
		mRectSectionArcInner = new RectF(0, 0, 0, 0);
		
		// Set initial Values
		mNumberOfSection = 1;
		mNumberOfSubSection = 6;
		mViewTouchX = 0;
		mViewTouchY = 0;
		sectionProgress = new int[10];
		sectionMaxLimit = new int[10];
		sectionNames = new String[10];
		mSelectedSection = 0;
		flagForHandleTouch = false;
		//isFirstTimeCallonDraw = true;

		for (int i = 0; i < 10; i++) {
			sectionProgress[i] = 0;
			sectionMaxLimit[i] = 6;
			sectionNames[i] = "Section - " + i;
		}
		//mMaxNumberofSection = 6;


		mViewTouchX = getTouchPointX();
		mViewTouchY = getTouchPointY();

		mViewHeight = getHeight();
		mViewWidth = getWidth();
		minViewScreenSide = Math.min(mViewHeight, mViewWidth);
		

	}

	public int getMaxNumberofSection() {
		return MAX_NO_OF_SECTION;
	}

	

	public String[] getSectionName() {
		return sectionNames;
	}

	public void setSectionName(String[] sectionName) {
		this.sectionNames = sectionName;
	}

	public void setSectionNameByIndex(String sectionName, int index) {
		if (index >= 0 && index < mNumberOfSection)
			this.sectionNames[index] = sectionName;
	}

	public int[] getLimitValue() {
		return sectionMaxLimit;
	}

	public void setLimitValue(int limitValue[]) {
		this.sectionMaxLimit = limitValue;
	}

	public void setLimitValueByIndex(int limitValue, int index) {
		this.sectionMaxLimit[index] = limitValue;
		if (limitValue < sectionProgress[index])
			this.sectionProgress[index] = limitValue;
	}

	public void setPaintColor(Paint paint, String color) {

		try{
			paint.setColor(Color.parseColor(color));
		}catch(Exception e){}
	}

	public void setPaintColor(Paint paint, int color) {

		paint.setColor(color);
	}

	public int getNumberOfSection() {
		return mNumberOfSection;
	}

	public void setNumberOfSection(int numberOfSection) {
		this.mNumberOfSection = numberOfSection;
	}

	public int getNumberOfSubSection() {
		return mNumberOfSubSection;
	}

	public void setNumberOfSubSection(int numberOfSubSection) {
		this.mNumberOfSubSection = numberOfSubSection;
	}

	public void setflagHandleTouch(boolean flag) {
		this.flagForHandleTouch = flag;
	}

	boolean addSection(String Name){
		
		
		
		return false;
	}
	
	// get points on orbit of circle with respective radius for drawing Lines 
	private void getPointsSection(float x0, float y0, double radius, int noOfDividingPoints) {

		double angle = 0;

		xLines = new float[noOfDividingPoints];
		yLines = new float[noOfDividingPoints];

		for (int i = 0; i < noOfDividingPoints; i++) {
			angle = i * (360 / noOfDividingPoints);

			xLines[i] = (float) (x0 + radius * Math.cos(Math.toRadians(angle)));
			yLines[i] = (float) (y0 + radius * Math.sin(Math.toRadians(angle)));

		}

	}

	// get points on orbit of circle with respective radius for detecting touch
	private void getPointsSectionPatitionTouch(float x0, float y0, double radius, int noOfDividingPoints) {

		double angle = 0;

		xT = new float[noOfDividingPoints];
		yT = new float[noOfDividingPoints];

		for (int i = 0; i < noOfDividingPoints; i++) {
			angle = i * (360 / noOfDividingPoints);

			xT[i] = (float) (x0 + radius * Math.cos(Math.toRadians(angle)));
			yT[i] = (float) (y0 + radius * Math.sin(Math.toRadians(angle)));

		}

	}

	public void setTouchPoint(float x, float y) {
		mViewTouchX = x;
		mViewTouchY = y;
		
	}

	public float areaOfTriangle(float pointX1, float pointY1, float pointX2,
			float pointY2, float pointX3, float pointY3) {

		return Math.abs((pointX1 * (pointY2 - pointY3) + pointX2
				* (pointY3 - pointY1) + pointX3 * (pointY1 - pointY2)) / 2);

	}

	// finding which section selected by touch
	public void setSectionTouch(float touchPointX, float touchPointY, float circleCenterPointX, float circleCenterPointY) {
		/**
		 * Touch logic using Area calculation 
		 */
		/*
		float areaFullSection, areaPartSection1, areaPartSection2, areaPartSection3, areaAddPart;

		for (int i = 0; i < mNumberOfSection; i++) {
			
			areaFullSection = areaOfTriangle(xT[i], yT[i], xT[(i + 1) % mNumberOfSection], yT[(i + 1) % mNumberOfSection], circleCenterPointX, circleCenterPointY);
			areaPartSection1 = areaOfTriangle(xT[i], yT[i], xT[(i + 1) % mNumberOfSection], yT[(i + 1) % mNumberOfSection], touchPointX, touchPointY);
			areaPartSection2 = areaOfTriangle(xT[i], yT[i], touchPointX, touchPointY, circleCenterPointX, circleCenterPointY);
			areaPartSection3 = areaOfTriangle(xT[(i + 1) % mNumberOfSection], yT[(i + 1) % mNumberOfSection], touchPointX, touchPointY, circleCenterPointX, circleCenterPointY);

			areaAddPart = areaPartSection1 + areaPartSection2
					+ areaPartSection3;

			if ((areaFullSection == areaAddPart)
					|| ((areaFullSection - areaAddPart) <= 1 && (areaFullSection - areaAddPart) >= -1)) {
				if(mSectionSelected == i){
					mIsInsideInnermostCircle = false;
				}
				if ((mIsInsideInnermostCircle == false)) {
					mSectionSelected = i;
				}
			}
		}

*/		
		/**
		 * Touch logic using angle calculation 
		 */
	     float deltaX = touchPointX - circleCenterPointX;
	     float deltaY = touchPointY - circleCenterPointY;
	     double angleInDegrees = Math.atan(deltaY / deltaX) * 180 / Math.PI;
	     /**
	      * angle calculation to make degree 0-360
	      */
	     if(angleInDegrees < 0){
	     	angleInDegrees += 180;
	     }
	      if(circleCenterPointY > touchPointY){
	     	 angleInDegrees += 180;
	      }
	    // Log.d("degree",""+angleInDegrees);
	     
	     int sectionAngleInDrgree=360 / mNumberOfSection;
	     
	     int sectionTouch=(int) (angleInDegrees / sectionAngleInDrgree);
	     Log.d("SectionTouch ",""+sectionTouch);
	     if(mSelectedSection == sectionTouch){
				mIsInsideInnermostCircle = false;
		 }
		 if ((mIsInsideInnermostCircle == false)) {
				mSelectedSection = sectionTouch;
	     }
	}

	// finding distance between two Points
	public double distanceBetweenPoints(float touchPointX, float touchPointY,
			float circleCenterPointX, float circleCenterPointY) {
		return Math.sqrt((touchPointX - circleCenterPointX)
				* (touchPointX - circleCenterPointX)
				+ (touchPointY - circleCenterPointY)
				* (touchPointY - circleCenterPointY));
	}

	// Checking touch is InSide of Circle or not
	public boolean isInsideCircle(float touchPointX, float touchPointY,
			float circleCenterPointX, float circleCenterPointY, float radius) {
		float distance = (float) distanceBetweenPoints(touchPointX,
				touchPointY, circleCenterPointX, circleCenterPointY);
		// Log.v("distance", "" + distance);
		if ((distance - radius) <= 0)
			return true;
		return false;
	}

	public float getTouchPointX() {
		return mViewTouchX;

	}

	public float getTouchPointY() {
		return mViewTouchY;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int sectionAngleInDrgree = 360 / mNumberOfSection;
		int totalSubSection = mNumberOfSection * mNumberOfSubSection;

		mViewTouchX = getTouchPointX();
		mViewTouchY = getTouchPointY();
		
		if (mViewHeight != getHeight() && mViewWidth != getWidth()) {

			// Call when change occur in View's Height or Width
			setValuesOnOrientationChanged();
			
		}

		// Set RectF objects left, top, right, bottom parameter for drawing Arc at runtime

		mRectSubSectionArc.set(circleCenterPointX
				- minViewScreenDivide3 - 5, circleCenterPointY
				- minViewScreenDivide3 - 5, circleCenterPointX
				+ minViewScreenDivide3 + 5, circleCenterPointY
				+ minViewScreenDivide3 + 5);
		
		mRectSectionArcOuter.set(circleCenterPointX
				- minViewScreenDivide2 + 10, circleCenterPointY
				- minViewScreenDivide2 + 10, circleCenterPointX
				+ minViewScreenDivide2 - 10, circleCenterPointY
				+ minViewScreenDivide2 - 10);
		
		mRectSectionArcInner.set(circleCenterPointX
				- minViewScreenDivide3 + 5, circleCenterPointY
				- minViewScreenDivide3 + 5, circleCenterPointX
				+ minViewScreenDivide3 - 5, circleCenterPointY
				+ minViewScreenDivide3 - 5);


		// outermost Circle
		canvas.drawCircle(circleCenterPointX, circleCenterPointY,
				minViewScreenDivide2 - 10, mPaintOuterCircle);// circle 1
		//Draw Arc for show which Section is Selected
		canvas.drawArc(mRectSectionArcOuter, mSelectedSection * sectionAngleInDrgree, sectionAngleInDrgree, true, mPaintSectionSelectedArc);

		// Center Circle  
		canvas.drawCircle(circleCenterPointX, circleCenterPointY, minViewScreenDivide3 + 5, mPaintCenterCircle);

		//Calculate the Points of lines lies on circle orbit 
		getPointsSection(circleCenterPointX, circleCenterPointY, minViewScreenDivide3 + 5, mNumberOfSection * mNumberOfSubSection);
		
		for (int i = 0; i < totalSubSection; i++) {
			// smaller line for Seperation of Each Section into 6 Equal Parts
			canvas.drawLine(xLines[i], yLines[i], circleCenterPointX, circleCenterPointY,	mPaintLine);// chote line
		}

		for (int i = 0; i < mNumberOfSection; i++) {

			//Draw Arc for shows Each Section Value
			canvas.drawArc(	mRectSubSectionArc,	(i * 360) / mNumberOfSection, ((360 * sectionProgress[i]) / (mNumberOfSection * sectionMaxLimit[i])),	true, mPaintSubSectionArc);
		}

		//Center Circle with Same color of OuterCircle  
		canvas.drawCircle(circleCenterPointX, circleCenterPointY, minViewScreenDivide3 - 5, mPaintOuterCircle);

		//Draw Arc for show which Section is Selected
		canvas.drawArc(mRectSectionArcInner, mSelectedSection * sectionAngleInDrgree, sectionAngleInDrgree, true, mPaintSectionSelectedArc);

		//Calculate the Points of lines lies on circle orbit
		getPointsSection(circleCenterPointX, circleCenterPointY, minViewScreenDivide2 - 10, mNumberOfSection);
		for (int i = 0; i < mNumberOfSection; i++) {
			// bigger line for Seperation of Section
			canvas.drawLine(xLines[i], yLines[i], circleCenterPointX, circleCenterPointY, mPaintLine); 
		}

		//Inner Circle
		canvas.drawCircle(circleCenterPointX, circleCenterPointY, minViewScreenDivide4, mPaintInnerCircle);


		//Innermost  Circle which color withrespective to section Selected
		canvas.drawCircle(circleCenterPointX, circleCenterPointY,
		  minViewScreenDivide5, mPaintSectionSelectedArc);
		 
		
		canvas.drawText("+", circleCenterPointX - minViewScreenDivide80,
				circleCenterPointY - minViewScreenDivide10, mPaintWord);
		canvas.drawText("_", circleCenterPointX - minViewScreenDivide80,
				circleCenterPointY + minViewScreenDivide8, mPaintWord);
		if (isInsideCircle(mViewTouchX, mViewTouchY, circleCenterPointX, circleCenterPointY, minViewScreenDivide4) && flagForHandleTouch ) {

			/**
			 * First Check if Touch is Inside of Innermost Circle
			 */
			
			flagForHandleTouch = false;
			mIsInsideInnermostCircle = true;
			
			if (mViewTouchY < circleCenterPointY) {

				mPaintWord.setTextSize(60);
				canvas.drawText("+", circleCenterPointX - minViewScreenDivide80	- 6, circleCenterPointY - minViewScreenDivide10 + 6, mPaintWord);
				mPaintWord.setTextSize(40);
				if (sectionProgress[mSelectedSection] < sectionMaxLimit[mSelectedSection])
					sectionProgress[mSelectedSection]++;
			} else {

				mPaintWord.setTextSize(60);
				canvas.drawText("_", circleCenterPointX - minViewScreenDivide80	- 5, circleCenterPointY + minViewScreenDivide8,	mPaintWord);
				mPaintWord.setTextSize(40);
				if (sectionProgress[mSelectedSection] > 0)
					sectionProgress[mSelectedSection]--;
			}
		} else if (isInsideCircle(mViewTouchX, mViewTouchY, circleCenterPointX, circleCenterPointY, minViewScreenDivide2 - 10) && flagForHandleTouch ) {
			

			/**
			 * First Check if Touch is Inside of Outermost Circle
			 */
			
			flagForHandleTouch = false;
			
			if (mNumberOfSection > 2) {
				getPointsSectionPatitionTouch(circleCenterPointX, circleCenterPointY, (minViewScreenDivide2 - 10) * 5, mNumberOfSection);
				setSectionTouch(mViewTouchX, mViewTouchY, circleCenterPointX,	circleCenterPointY);
			} else if (mNumberOfSection == 2) {
				if (mViewTouchY < circleCenterPointY)
					mSelectedSection = 1;
				else
					mSelectedSection = 0;
			}

		} /*else if (!(isInsideCircle(viewTouchX, viewTouchY, circleCenterPointX, circleCenterPointY, minViewScreenDivide2 - 10))) {
		}*/

		// Display the Selected Section's value Depends on its Length
		switch (String.valueOf(sectionProgress[mSelectedSection]).length()) {
		case 1:
			canvas.drawText("" + sectionProgress[mSelectedSection],	circleCenterPointX - 7, circleCenterPointY + 12, mPaintWord);
			break;
		case 2:
			canvas.drawText("" + sectionProgress[mSelectedSection], circleCenterPointX - 20, circleCenterPointY + 12, mPaintWord);  
			break;
		case 3:
			canvas.drawText("" + sectionProgress[mSelectedSection],	circleCenterPointX - 30, circleCenterPointY + 12, mPaintWord);
			break;
		default:
			canvas.drawText("" + sectionProgress[mSelectedSection], circleCenterPointX - 40, circleCenterPointY + 12, mPaintWord);
		}

		Path path[] = new Path[mNumberOfSection];
		// Display Name of each Section on Circular Path
		for (int i = 0; i < (mNumberOfSection); i++) {
			path[i] = new Path();
			path[i].addArc(mRectSectionArcOuter, (i * 360) / mNumberOfSection,  sectionAngleInDrgree);
			canvas.drawTextOnPath(sectionNames[i], path[i], 0, minViewScreenDivide12, mPaintSectionName);
		}

		invalidate();
	}

	// Set values depends on current size of View.
	public void setValuesOnOrientationChanged() {

		mViewHeight = this.getHeight();
		mViewWidth = this.getWidth();

		circleCenterPointY = mViewHeight / 2;
		circleCenterPointX = mViewWidth / 2;

		minViewScreenSide =Math.min(mViewHeight, mViewWidth);

		minViewScreenDivide2 = minViewScreenSide / 2;
		minViewScreenDivide3 = minViewScreenSide / 3;
		minViewScreenDivide4 = minViewScreenSide / 4;// minViewScreenDivide2 << 1;;
		minViewScreenDivide5 = minViewScreenSide / 5;
		minViewScreenDivide8 = minViewScreenSide / 8;// minViewScreenDivide4 << 1;
		minViewScreenDivide10 = minViewScreenSide / 10;// minViewScreenDivide5 << 1;
		minViewScreenDivide12 = minViewScreenSide / 12;// minViewScreenDivide3 << 2;
		minViewScreenDivide80 = minViewScreenSide / 80;// minViewScreenDivide10 << 4;
	}

}
