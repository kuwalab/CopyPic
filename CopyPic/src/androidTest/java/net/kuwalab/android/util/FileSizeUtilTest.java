package net.kuwalab.android.util;

import android.test.AndroidTestCase;

public class FileSizeUtilTest extends AndroidTestCase {
	public void testGetFileSizeForView() {
		assertGetFileSizeForView(1, "1B");
		assertGetFileSizeForView(999, "999B");
		assertGetFileSizeForView(1000, "0.977KB");
		assertGetFileSizeForView(1001, "0.978KB");
		assertGetFileSizeForView(1002, "0.979KB");
		assertGetFileSizeForView(1003, "0.979KB");
		assertGetFileSizeForView(1004, "0.980KB");
		assertGetFileSizeForView(1005, "0.981KB");
		assertGetFileSizeForView(1006, "0.982KB");
		assertGetFileSizeForView(1007, "0.983KB");
		assertGetFileSizeForView(1008, "0.984KB");
		assertGetFileSizeForView(1009, "0.985KB");
		assertGetFileSizeForView(1010, "0.986KB");
		assertGetFileSizeForView(1011, "0.987KB");
		assertGetFileSizeForView(1012, "0.988KB");
		assertGetFileSizeForView(1013, "0.989KB");
		assertGetFileSizeForView(1014, "0.990KB");
		assertGetFileSizeForView(1015, "0.991KB");
		assertGetFileSizeForView(1016, "0.992KB");
		assertGetFileSizeForView(1017, "0.993KB");
		assertGetFileSizeForView(1018, "0.994KB");
		assertGetFileSizeForView(1019, "0.995KB");
		assertGetFileSizeForView(1020, "0.996KB");
		assertGetFileSizeForView(1021, "0.997KB");
		assertGetFileSizeForView(1022, "0.998KB");
		assertGetFileSizeForView(1023, "0.999KB");
		assertGetFileSizeForView(1024, "1KB");
		assertGetFileSizeForView(1025, "1.00KB");

		assertGetFileSizeForView(1024, "1KB");
		assertGetFileSizeForView(1024 * 1024, "1MB");
		assertGetFileSizeForView(1024 * 1024 * 1024, "1GB");

	}

	public void assertGetFileSizeForView(long actual, String expected) {
		assertEquals(FileSizeUtil.getFileSizeForView(actual), expected);
	}
}
