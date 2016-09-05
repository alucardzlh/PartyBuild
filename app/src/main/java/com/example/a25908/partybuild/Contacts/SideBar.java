package com.example.a25908.partybuild.Contacts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.a25908.partybuild.R;


public class SideBar extends View
{
	// touching event
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26 letters
	public static String[] b = 
		{
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", 
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#" 
		};
	// if choosed
	private int choose  = -1;
	private Paint paint = new Paint();
	
	private TextView mTextDialog;
	
	public void setmTextDialog(TextView mTextDialog)
	{
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public SideBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SideBar(Context context)
	{
		super(context);
	}

	// override onDraw function
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// get the height
		int height = getHeight();
		// get the width
		int width = getWidth();
		// get one letter height
		int singleHeight = height / b.length;
		
		for (int i = 0; i < b.length; i++)
		{
			paint.setColor(Color.rgb(33, 65, 98));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(24);
			
			// if choosed 
			if(i == choose)
			{
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}

			// draw text
			float x = width / 2 - paint.measureText(b[i]) / 2;
			float y = singleHeight * i + singleHeight;
			canvas.drawText(b[i], x, y, paint);
			paint.reset();
		}
	}
	
	

	@SuppressWarnings("deprecation")
	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		final int action = event.getAction();
		final float y = event.getY(); // get the Y 
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener changedListener = onTouchingLetterChangedListener;
		final int letterPos = (int)( y / getHeight() * b.length);
		
		switch (action)
		{
			case MotionEvent.ACTION_UP:
				setBackgroundDrawable(new ColorDrawable(0x00000000));
				choose = -1;
				invalidate();
				if (mTextDialog != null)
					mTextDialog.setVisibility(View.INVISIBLE);
				break;
				
			default:
				setBackgroundResource(R.drawable.bg_sidebar);
				if (oldChoose != letterPos)
				{
					if (letterPos >= 0 && letterPos < b.length)
					{
						if (changedListener != null)
							changedListener.onTouchingLetterChanged(b[letterPos]);
						if (mTextDialog != null)
						{
							mTextDialog.setText(b[letterPos]);
							mTextDialog.setVisibility(View.VISIBLE);
						}
						
						choose = letterPos;
						invalidate();
					}
				}
				break;
		}
		return true;
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener changedListener)
	{
		this.onTouchingLetterChangedListener = changedListener;
	}
	
	public interface OnTouchingLetterChangedListener
	{
		public void onTouchingLetterChanged(String str);
	}
}
