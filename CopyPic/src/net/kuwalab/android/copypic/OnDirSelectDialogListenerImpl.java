package net.kuwalab.android.copypic;

import java.io.File;

import net.kuwalab.android.copypic.DirSelectDialog.OnDirSelectDialogListener;

public class OnDirSelectDialogListenerImpl implements OnDirSelectDialogListener {
	private MainActivity activity;

	public OnDirSelectDialogListenerImpl(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClickDirSelect(File file) {
		activity.onClickDirSelect(file);
	}

}
