/*
 * @(#)MetadataUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.image;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentData;
import com.drew.imaging.jpeg.JpegSegmentReader;
import com.drew.metadata.Metadata;

/**
 * <p>
 * <a href="MetadataUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: MetadataUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class MetadataUtils {
	//~ Static fields/initializers =============================================
	
	private static final byte MARKER_LEADING = (byte) 0xFF;    // First byte of Marker
	private static final byte MARKER_SOI = (byte)0xD8; // Start Of Image
	private static final byte MARKER_SOS = (byte)0xDA; // Start Of Stream
	private static final byte MARKER_EOI = (byte)0xD9; // End Of Image
	
	//~ Instance fields ========================================================
	
	//~ Constructors ===========================================================

	//~ Methods ================================================================
	
	public static JpegSegmentData readSegments(InputStream inputStream) throws JpegProcessingException {
		return new JpegSegmentReader(inputStream).getSegmentData();
	}
	
	public static JpegSegmentData readSegments(File file) throws JpegProcessingException {
		return new JpegSegmentReader(file).getSegmentData();
	}
	
	public static Metadata extractMetadataFromSegmentData(JpegSegmentData segmentData) {
		return JpegMetadataReader.extractMetadataFromJpegSegmentReader(
				new JpegSegmentReader(segmentData));
	}
	
	public static Metadata extractMetadata(InputStream inputStream) throws JpegProcessingException {
		return JpegMetadataReader.readMetadata(inputStream);
	}
	
	public static Metadata extractMetadata(File file) throws JpegProcessingException {
		return JpegMetadataReader.readMetadata(file);
	}
	
	@SuppressWarnings("unchecked")
	public static void toFile(File file, JpegSegmentData data) throws IOException {
		try {
			Field field = data.getClass().getDeclaredField("_segmentDataMap");
			field.setAccessible(true);
			Map segmentsData = (Map) field.get(data);
			writeToFile(file, segmentsData);
		} catch (SecurityException e) {
			
		} catch (NoSuchFieldException e) {
			
		} catch (IllegalArgumentException e) {
			
		} catch (IllegalAccessException e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void writeToFile(File file, Map segmentsData) throws IOException {
		FileOutputStream out = null;
		
		try {
			out = new FileOutputStream(file);
			// starts from binary value '0xFFD8'
			out.write(MARKER_LEADING);
			out.write(MARKER_SOI);

			for (Iterator iterator = segmentsData.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				byte segmentMarker = ((Byte) entry.getKey()).byteValue();
				List segmentList = (List) entry.getValue();

				for (Iterator iter = segmentList.iterator(); iter.hasNext();) {
					byte[] segmentBytes = (byte[]) iter.next();

					out.write(MARKER_LEADING);
					out.write(segmentMarker);

					int segmentLength = (segmentBytes.length + 2) & 0xFFFF;

					byte[] segmentLengthBytes = new byte[2];
					
					segmentLengthBytes[0] = (byte)((segmentLength >> 8) & 0xFF);
					segmentLengthBytes[1] = (byte)(segmentLength & 0xFF);
					
					out.write(segmentLengthBytes);
					out.write(segmentBytes);
				}
			}
			out.write(MARKER_LEADING);
			out.write(MARKER_SOS);
			out.write((byte) 0x0);
			out.write((byte) 0x02);
			
			// ends by binary value '0xFFD9'
			out.write(MARKER_LEADING);
			out.write(MARKER_EOI);
			
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * This method is used to read metadata from metadata file(without image data)
	 * 
	 * @param inputStream
	 * @return
	 * @throws JpegProcessingException
	 */
	public static Metadata extractMetadataFromStream(InputStream inputStream) throws JpegProcessingException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(inputStream);
			
			int read = 0;
			byte[] buf = new byte[1024 * 4];
			while ( (read = in.read(buf)) != -1 ) {
				out.write(buf, 0, read);
			}
			
			return JpegMetadataReader.extractMetadataFromJpegSegmentReader(
					new JpegSegmentReader(out.toByteArray()));
			
		} catch (IOException e) {
			// throw new JpegProcessingException("IOException processing Jpeg file", ioe);
            throw new JpegProcessingException("IOException processing Jpeg file: " + e.getMessage(), e);
		}
	}

	public static Metadata extractMetadata(String address) throws JpegProcessingException {
		InputStream in = null;
		
		try {
			URL url = new URL(address);
			in = url.openStream();
			return extractMetadataFromStream(in);
		} catch (IOException e) {
			// throw new JpegProcessingException("IOException processing Jpeg file", ioe);
            throw new JpegProcessingException("IOException processing Jpeg file: " + e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// throw new JpegProcessingException("IOException processing Jpeg file", ioe);
	                throw new JpegProcessingException("IOException processing Jpeg file: " + e.getMessage(), e);
				}
			}
		}
	}

}
