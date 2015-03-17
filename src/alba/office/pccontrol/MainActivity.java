package alba.office.pccontrol;
import org.json.simple.parser.ParseException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {

	Bitmap bitmap,screen;
	Canvas canvas;
	Paint paint;
	private VelocityTracker mVelocityTracker = null;
	int width;
	int height;
	float downx = 0,upx = 0, downy=0, upy=0;
	ImageView touchView,imageView;
	RelativeLayout rl;
	Button click,lock;
	String IP =  "155.143.38.161";
	Receiver receiver = new Receiver();
	InstrcutionFactory insf = new InstrcutionFactory(IP);
	private int CENSITIVITY_LEVEL = 10;
	@SuppressLint("WrongCall")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rl = (RelativeLayout)findViewById(R.id.RelativeLayout1);
		imageView = new ImageView(getBaseContext());
		//screen = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);		
		rl.addView(imageView);
		setWH();
		bitmap =  Bitmap.createBitmap(width,height,
		        Bitmap.Config.ARGB_8888);
	   
	    paint = new Paint();
	    paint.setColor(Color.BLACK);
	    canvas = new Canvas(bitmap);
	    canvas.drawCircle(downx, downy, 20, paint);
	    
	    touchView = (ImageView)findViewById(R.id.imageView1);
	    touchView.setImageBitmap(bitmap); 
	    touchView.setOnTouchListener(this);
	    
	    click = (Button)findViewById(R.id.button01);
	    lock = (Button)findViewById(R.id.button02);
		

		
		receiver.start();
	    click.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insf.sendClickMsg();
				Toast.makeText(MainActivity.this,
				"Click Message has been sent",
				Toast.LENGTH_LONG).show();
			}
		});
	    
	    lock.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(lock.getText().equals("锁屏")){
					lock.setText("解锁");
					insf.sendLockMsg();
				}
				else{
					insf.sendUnlockMsg();
					lock.setText("锁屏");
				}
			}
		});
	}
	public void setWH(){
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		height = metrics.heightPixels;
		downx=width/2;
		downy=height/2;
	}
	
	
	  @SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
		    int action = event.getAction();
		    int index = event.getActionIndex();
		    int pointerId = event.getPointerId(index);
		 
		    
		    switch (action) {
		    case MotionEvent.ACTION_DOWN:    	
              if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                  mVelocityTracker = VelocityTracker.obtain();
              }
              else {
                    // Reset the velocity tracker back to its initial state.
                  mVelocityTracker.clear();
              }
              mVelocityTracker.addMovement(event);

		      break;
		    case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call 
                // computeCurrentVelocity(). Then call getXVelocity() 
                // and getYVelocity() to retrieve the velocity for each pointer ID. 
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                float speedX = VelocityTrackerCompat.getXVelocity(mVelocityTracker, 
                        pointerId)/CENSITIVITY_LEVEL;
                float  speedY = VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                        pointerId)/CENSITIVITY_LEVEL;
                Log.d("velo", "X velocity: " + speedX);
                Log.d("velo", "Y velocity: " + speedY);
                insf.sendMoveMsg(speedX, speedY);
                if(receiver.image!=null)
                	receiver.setSocket(insf.aSocket);
                	receiver.running = true;
                	imageView.setImageBitmap(receiver.image);

                canvas.drawCircle(event.getX(), event.getY(), 5, paint);
                touchView.invalidate();
		      break;
		    case MotionEvent.ACTION_UP:
		     
		      canvas.drawColor(0, Mode.CLEAR);
		      canvas.drawCircle(downx, downy, 20, paint);
		      
		      break;
		    case MotionEvent.ACTION_CANCEL:
		      mVelocityTracker.recycle();
		      break;
		    default:
		      break;
		    }
		    return true;
	}
	

//	private void sendMoveMsg(float deltaX, float deltaY) {
//		// TODO Auto-generated method stub	
//		String msg = insf.toJsonMove((int)deltaX/CENSITIVITY_LEVEL, 
//										(int)deltaY/CENSITIVITY_LEVEL);
//		insf.sendPacket(msg,IP);
//		//Log.i("info", msg);
//	}
//	private void sendClickMsg(){
//		String msg = insf.toJsonClick();
//		insf.sendPacket(msg, IP);
//		//Log.i("info", msg);
//	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
