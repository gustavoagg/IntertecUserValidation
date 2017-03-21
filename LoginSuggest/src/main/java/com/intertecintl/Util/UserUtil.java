package com.intertecintl.Util;

import java.util.Random;

public class UserUtil {

	/**
	 * @param sourceString
	 * @param length
	 * @return A random String with the provided length, using the letters from the sourceString
	 */
	public static String randomString(String sourceString,int length){
		StringBuffer sb = new StringBuffer();
	    Random random = new Random();
	    for (int i = 0; i < length; i++) {
	    	int index = random.nextInt(sourceString.length());		   
			sb.append(sourceString.charAt(index));
		}
	    
	    return sb.toString();
	}
}
