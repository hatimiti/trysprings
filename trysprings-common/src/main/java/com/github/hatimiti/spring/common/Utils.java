package com.github.hatimiti.spring.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class Utils {
	public static Date toDate(final String yyyyMmDdHhMmSs) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf.parse(yyyyMmDdHhMmSs);
		} catch (ParseException e) {
			return new Date();
		}
	}
}