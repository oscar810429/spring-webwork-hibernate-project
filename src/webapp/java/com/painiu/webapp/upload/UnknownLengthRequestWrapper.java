/*
 * @(#)UnknownLengthRequestWrapper.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 这个类用在当Content-Length为-1的时候，尝试将数据读到磁盘。
 * 主要用在当Transfer-Encoding为chunked的时候
 * （J2ME的HTTPConnection强制这种传输方式，当数据超过一定大小）
 * 
 * <p>
 * <a href="UnknownLengthRequestWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: UnknownLengthRequestWrapper.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class UnknownLengthRequestWrapper extends HttpServletRequestWrapper {
	//~ Static fields/initializers =============================================

	private static final int MAINT_REQUESTS_INTERVAL = 50;
	
	private static int requests = 0;
	
	//~ Instance fields ========================================================

	private int maxSize;
	
	private String saveDir;
	
	private File file;
	
	private int length = 0;
	
	private ServletInputStream inputStream;
	
	//~ Constructors ===========================================================

	public UnknownLengthRequestWrapper(HttpServletRequest request, String saveDir, int maxSize) throws IOException {
		super(request);
		this.maxSize = maxSize;
		this.saveDir = saveDir;
		readStream();
	}
	
	//~ Methods ================================================================
	
	/*
	 * @see javax.servlet.ServletRequestWrapper#getContentLength()
	 */
	@Override
	public int getContentLength() {
		return length;
	}
	
	/*
	 * @see javax.servlet.ServletRequestWrapper#getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return inputStream;
	}
	
	private void readStream() throws IOException {
		InputStream in = null;	
		FileOutputStream out = null;
		
		file = createTempFile();
		
		try {
			in = getRequest().getInputStream();
			out = new FileOutputStream(file);
			
			byte[] buf = new byte[4096];
			int read = 0;
			
			while ((read = in.read(buf)) != -1) {
				out.write(buf, 0, read);
				length += read;
				
				if (length > maxSize) {
					in.close();
					break;
				}
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {}
			}
		}
		
		inputStream = new DelegatingServletInputStream(file);
	}
	
	private File createTempFile() {
		requests++;
		if (requests % MAINT_REQUESTS_INTERVAL == 0) {
			File dir = new File(saveDir);
			
			final long now = System.currentTimeMillis() - 18000; // half hour ago
			
			File[] chunkFiles = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if (!name.endsWith(".chk")) {
						return false;
					}
					try {
						long creationTime = Long.parseLong(name.substring(0, name.indexOf('_')));
						return creationTime < now;
					} catch (Exception e) {
						return false;
					}
				}
			});
			
			if (chunkFiles != null) {
				for (int i = 0; i < chunkFiles.length; i++) {
					chunkFiles[i].delete();
				}
			}
		}
		
		String filename = System.currentTimeMillis() + "_" + RandomStringUtils.randomAlphabetic(6) + ".chk";
		
		return new File(saveDir, filename);
	}
	
	static class DelegatingServletInputStream extends ServletInputStream {
		private File sourceFile;
		private InputStream sourceStream;
		
		public DelegatingServletInputStream(File sourceFile) throws IOException {
			this.sourceFile = sourceFile;
			this.sourceStream = new FileInputStream(this.sourceFile);
		}

		/*
		 * @see java.io.InputStream#close()
		 */
		@Override
		public void close() throws IOException {
			sourceStream.close();
		}

		/*
		 * @see java.io.InputStream#read()
		 */
		@Override
		public int read() throws IOException {
			int value = sourceStream.read();
			if (value == -1) {
				this.close();
				sourceFile.delete();
			}
			return value;
		}
	}
}
