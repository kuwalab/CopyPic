package net.kuwalab.copypic;

import java.util.Calendar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	private EditText localPathText;
	private EditText serverPathText;
	private EditText serverIdText;
	private EditText serverPasswordText;

	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		localPathText = (EditText) findViewById(R.id.localPath);
		serverPathText = (EditText) findViewById(R.id.serverPath);
		serverIdText = (EditText) findViewById(R.id.serverId);
		serverPasswordText = (EditText) findViewById(R.id.serverPassword);

		Button copyButton = (Button) findViewById(R.id.copyButton);
		copyButton.setOnClickListener(this);

		pref = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.copyButton:
			String date = DateFormat.format("yyyyMMdd", Calendar.getInstance())
					.toString();
			CopySetting cs = new CopySetting(
					localPathText.getText().toString(), serverPathText
							.getText().toString() + "pic" + date + "/",
					serverIdText.getText().toString(), serverPasswordText
							.getText().toString());

			new RefTask(v.getContext(), cs).execute();
			break;
		}

	}
}
