package glassapptest.app;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.glass.view.WindowUtils;

public class MainActivity extends Activity {

	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private TextView text5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		setContentView(R.layout.activity_main);
		
		this.text1 = (TextView) findViewById(R.id.text1);
		this.text2 = (TextView) findViewById(R.id.text2);
		this.text3 = (TextView) findViewById(R.id.text3);
		this.text4 = (TextView) findViewById(R.id.text4);
		this.text5 = (TextView) findViewById(R.id.text5);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.exit) {
			finish();
			return true;
		}
		if(id == R.id.scan){
			doScan();
			return true;
		}

		if(id == R.id.speak){
			doSpeak();
			return true;
		}

		if(id == R.id.report){
			doReport();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			openOptionsMenu();
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == 0){
			if (resultCode == RESULT_OK && null != data) {
				String contents = data.getStringExtra("SCAN_RESULT");
				//String format  = data.getStringExtra("SCAN_RESULT_FORMAT");
				this.text1.setText("Equipment Id: " + contents);
				this.text2.setText("Modality: CT");
				this.text3.setText("FacilityID: 215DSE");
			}
		}
		if(requestCode == 1){
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				this.text4.setText("Issue: " + text.get(0));
			}
		}
	}
	
	@Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {
                case R.id.scan:
                	Log.d("voice menu", "scan selected");
                	doScan();
                    break;
                case R.id.speak:
                	Log.d("voice menu", "speak selected");
                	doSpeak();
                    break;
                case R.id.report:
                	Log.d("voice menu", "report selected");
                	doReport();
                    break;
                case R.id.exit:
                	Log.d("voice menu", "exit selected");
                	finish();
                    break;
                default:
                	Toast.makeText(this, "its confusing Try again", Toast.LENGTH_LONG).show();
                    return true;
            }
            return true;
        }
        // Good practice to pass through to super if not handled
        return super.onMenuItemSelected(featureId, item);
    }
	
	public void doScan(){
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		//intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "CODE_128");
		startActivityForResult(intent, 0);
	}
	
	public void doSpeak(){
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-us");
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak issue with Asset");
		startActivityForResult(intent, 1);
	}
	
	public void doReport(){
		this.text5.setText("Status: working....");
		new AsyncTask<String, Integer, String>() {

			@Override
			protected String doInBackground(String... arg0) {

				//MainActivity.this.text5.setText("Status: working....");
				ServiceCallHandler handler = new ServiceCallHandler(getApplicationContext());
				String token;
				String requestNo="";
				String jsonRequest = "";
				try {
					token = handler.getServiceToken();
					jsonRequest = new JsonParser().createRequestJson();
					requestNo = handler.raiseServiceRequest(token, jsonRequest);
				} catch (KeyManagementException | CertificateException
						| KeyStoreException | NoSuchAlgorithmException
						| IOException e) {
					e.printStackTrace(System.out);
				}			
				return requestNo;
			}
			@Override
			protected void onProgressUpdate(Integer... progress) {

			}

			@Override
			protected void onPostExecute(String result) {
				MainActivity.this.text5.setText("Status: " + result);
			}
		}.execute("");
		
	}
	
	
}
