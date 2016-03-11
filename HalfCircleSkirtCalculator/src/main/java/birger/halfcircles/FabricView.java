package birger.halfcircles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.PorterDuff;

public class FabricView extends View {
	
	private float width;  // width of fabric
	private float length; // length of fabric
	private float halves;
	private float radius;
	private float x1;
	private float x2;
	private float delta_x;
	
	private Paint line;

	public FabricView(Context context) {
		this(context, null);
	}
	
	public FabricView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		line = new Paint();
		line.setStyle(Paint.Style.STROKE);
		line.setColor(Color.WHITE);
	}
	
    public void setParameters(float fabric_width, float num_halves, float skirt_radius){
    	
    	width = fabric_width;
    	halves = num_halves;
    	radius = skirt_radius;
    	
    	if (radius > width){
    		length = Float.NaN;
    		x1 = x2 = delta_x = 0;
    	} else {

    		x1 = radius;
	    	if (width > 2*radius){
	    		delta_x = 0;
	    	} else {
	    		delta_x = (float) Math.sqrt(4*radius*radius-width*width);
	    	}
	    	x2 = x1 + delta_x;
	
	    	if (delta_x < radius){
	    		delta_x = radius;
	    	}
	    	
	    	if (halves%2 == 1){
	    		// odd number of halves
	    		length = x1 + (float) Math.floor((halves-1)/2)*2*delta_x + radius;
	    	} else {
	    		// even number of halves
	    		length = x2 + (float) Math.floor((halves-1)/2)*2*delta_x + radius;
	    	}
    	}
    	
    	invalidate();
    }
    
    public float getFabricRequirement(){
    	return length;
    }
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (Float.isNaN(length)){
			// radius > fabric_width
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			return;
		}
		
		float h_scale, w_scale, scale;
		if (width < getHeight() && length < getWidth()){
			scale = 1;
		} else {
			h_scale = getHeight() / (width+10);
			w_scale = getWidth() / (length+10);
			scale = Math.min(h_scale, w_scale);
		}
		float x_offset = (getWidth() - length*scale + 1) / 2;
		float y_offset = (getHeight() - width*scale + 1) / 2;
		
		canvas.save();
		canvas.translate(x_offset, y_offset);
		canvas.scale(scale, scale);
		
		canvas.drawRect(0,0,length-1,width-1, line);

		RectF bounds = new RectF();
		float x,y, sweep;
		
		for (int i = 0; i < halves; i++){
			x = (float) (((i%2==0) ? x1 : x2) + Math.floor(i/2)*2*delta_x);
			y = (i%2) * width;
			sweep = (i%2==0) ? -180 : 180;
			bounds.set(x-radius, y-radius, x+radius, y+radius);
			canvas.drawArc(bounds, 180, sweep, false, line);
		}
			
		canvas.restore();
	}
}
