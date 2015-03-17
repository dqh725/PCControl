package alba.office.pccontrol;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class IndexActivity extends ActionBarActivity {

	ImageButton terminal, monitor;
	Button test;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		terminal = (ImageButton)findViewById(R.id.terminal);
		monitor = (ImageButton)findViewById(R.id.monitor);

		terminal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
			    startActivity(intent);
			}
		});
		monitor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
			    startActivity(intent);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
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
