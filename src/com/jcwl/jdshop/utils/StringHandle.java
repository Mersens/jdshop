package com.jcwl.jdshop.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class StringHandle {

	private static int red = Color.argb(255, 253, 90, 85);

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return dateFormat.format(date);
	}

	public static Spannable textToSpan(String text, String nameRes) {
		Spannable name = new SpannableString(text+nameRes);
		name.setSpan(new ForegroundColorSpan(red), name.length()-nameRes.length(), name.length(),
				Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		return name;
	}

	public static File getFileFromBytes(String name,String path) {  
	    byte[] b=name.getBytes();  
	     BufferedOutputStream stream = null;  
	     File file = null;  
	     try {  
	         file = new File(path);  
	         FileOutputStream fstream = new FileOutputStream(file);  
	         stream = new BufferedOutputStream(fstream);  
	         stream.write(b);  
	     } catch (Exception e) {  
	         e.printStackTrace();  
	     } finally {  
	         if (stream != null) {  
	             try {  
	                 stream.close();  
	             } catch (IOException e1) {  
	                 e1.printStackTrace();  
	             }  
	         }  
	     }  
	     return file;  
	 } 
	
	public static boolean isPhone(String name){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");       
        Matcher m = p.matcher(name);
        return m.matches();
	}
	
	
}
