package birger.halfcircles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.Math;

import birger.widgets.NumberSlider;

public class HalfCircleSkirtCalculatorActivity extends Activity implements NumberSlider.OnValueChangedListener {
	
	private NumberSlider width;
	private NumberSlider halves;
	private NumberSlider radius;
	private TextView result;
	private FabricView fabric;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Get references to all views:
        width = (NumberSlider) findViewById(R.id.width);
        halves = (NumberSlider) findViewById(R.id.halves);
        radius = (NumberSlider) findViewById(R.id.radius);
        result = (TextView) findViewById(R.id.result);
        fabric = (FabricView) findViewById(R.id.fabric);
        
        
        // Width of fabric:
        width.setOnValueChangedListener(this);
        width.setMinimum(40);
        width.setMaximum(320);
        width.setStep(5);
        width.setValue(150);
        
        // Number of half circles:
        halves.setMinimum(1);
        halves.setMaximum(8);
        halves.setValue(3);
        halves.setOnValueChangedListener(this);
        
        // Radius of half circles:
        radius.setMinimum(40);
        radius.setMaximum(180);
        radius.setStep(5);
        radius.setValue(95);
        radius.setOnValueChangedListener(this);
        
        // Do initial calculation:
        onValueChanged(null,0);
    }
    
    public void onValueChanged(NumberSlider which, int new_value){

    	fabric.setParameters(width.getValue(), halves.getValue(), radius.getValue());
    	float requirement = fabric.getFabricRequirement();
    	if (Float.isNaN(requirement)){
    		result.setText("Radius is larger than fabric width!");
    	} else {
    		result.setText("Fabric requirement: " + (int)(Math.ceil(requirement/10)*10));
    	}
    }
}