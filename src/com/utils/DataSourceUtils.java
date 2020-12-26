package com.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtils {
	
	public static final String TEST = "test";
	private static DataSource ds = null;
	static {
		init();
	}

	public static DataSource getDataSource() {
		if (ds == null) {
			init();
		}
		return ds;
	}

	private static void init() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TEA102G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
