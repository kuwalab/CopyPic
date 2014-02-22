package net.kuwalab.android.copypic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class RefTask extends AsyncTask<String, Integer, Long> {
	private Context context;
	private CopySetting cs;
	private ProgressDialog dialog;

	public RefTask(Context context, CopySetting cs) {
		this.context = context;
		this.cs = cs;
	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		dialog.setTitle(context.getText(R.string.file_transfer));
		dialog.setMessage(context.getText(R.string.file_transfer_status));
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMax(0);
		dialog.setProgress(0);
		dialog.show();
	}

	@Override
	protected Long doInBackground(String... params) {
		try {
			File dir = new File(cs.getLocalPath());

			if (!dir.isDirectory()) {
				return null;
			}
			File[] files = dir.listFiles();
			dialog.setMax(files.length);

			SmbFile writeDir = new SmbFile("smb://" + cs.getServerPath(),
					new NtlmPasswordAuthentication(null, cs.getServerId(),
							cs.getServerPassword()));
			if (!writeDir.exists()) {
				writeDir.mkdirs();
			}

			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (!f.isFile()) {
					continue;
				}

				SmbFile file = new SmbFile(writeDir.getCanonicalPath(),
						f.getName(), new NtlmPasswordAuthentication(null,
								cs.getServerId(), cs.getServerPassword()));

				if (file.exists()) {
					publishProgress(i);
					continue;
				}
				copy(f, file);
				publishProgress(i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		dialog.setProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(Long result) {
		dialog.dismiss();
	}

	private void copy(File inputFile, SmbFile outputFile) throws IOException {
		InputStream is = new FileInputStream(inputFile);
		OutputStream os = outputFile.getOutputStream();

		byte[] buf = new byte[8192];
		try {
			int len = 0;
			while ((len = is.read(buf, 0, buf.length)) != -1) {
				os.write(buf, 0, len);
			}
			os.flush();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// ignore
			}
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
