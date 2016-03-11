package birger.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class NumberSlider extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {

	public interface OnValueChangedListener {
		public void onValueChanged(NumberSlider which, int new_value);
	}
	
	public void setOnValueChangedListener(OnValueChangedListener listener){
		if (listener != null){
			registered_listener = listener;
		} else {
			registered_listener = DO_NOTHING;
		}
	}
	
	private static final OnValueChangedListener DO_NOTHING = new OnValueChangedListener(){
		public void onValueChanged(NumberSlider which, int new_value){
			// Do nothing
		}
	};

	private OnValueChangedListener registered_listener = DO_NOTHING;
	
	private SeekBar slider;
	private TextView number;
	
	private int minimum;
	private int maximum;
	private int step;

	public NumberSlider(Context context) {
		this(context, null);
	}
	
	public NumberSlider(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		minimum = 0;
		maximum = 100;
		step = 1;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.number_slider, this, true);
		slider = (SeekBar) findViewById(R.id.slider);
		slider.setOnSeekBarChangeListener(this);
		number = (TextView) findViewById(R.id.number);
	}
	
	public void setMinimum(int new_minimum){
		minimum = new_minimum;
		slider.setMax((maximum-minimum)/step);
	}
	public void setMaximum(int new_maximum){
		maximum = new_maximum;
		slider.setMax((maximum-minimum)/step);
	}
	public void setStep(int new_step){
		step = new_step;
		slider.setMax((maximum-minimum)/step);
	}
	public void setValue(int new_value){
		int slider_value = (new_value-minimum)/step;
		if (new_value < minimum){
			slider_value = 0;
		} else if (new_value > maximum) {
			slider_value = slider.getMax();
		}
		slider.setProgress(slider_value);
	}
	public int getValue(){
		return minimum+step*slider.getProgress();
	}
	
	// SeekBar.OnSeekBarChangeListener interface
	public void onProgressChanged(SeekBar seek_bar, int progress_value, boolean from_user){
		number.setText(Integer.toString(minimum+step*progress_value));
		registered_listener.onValueChanged(this, minimum+step*progress_value);
	}
	public void onStartTrackingTouch(SeekBar seek_bar){
		
	}
	public void onStopTrackingTouch(SeekBar seek_bar){
		
	}
}
