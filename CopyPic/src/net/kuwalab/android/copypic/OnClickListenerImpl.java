package net.kuwalab.android.copypic;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author kuwalab
 * 
 */
public class OnClickListenerImpl implements OnClickListener {
	private MainActivity activity;

	public OnClickListenerImpl(MainActivity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.autoSearch:
			activity.onClickAutoSearch(v);
			break;
		case R.id.copyButton:
			activity.onClickCopyButton(v);
			break;
		case R.id.dirSelectButton:
			activity.onClickDirSelectButton(v);
			break;
		}

	}
}
