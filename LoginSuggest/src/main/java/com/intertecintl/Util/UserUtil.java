package com.intertecintl.Util;

import java.util.Random;

public class UserUtil {

	public static String randomString(String s,int size){
		StringBuffer sb = new StringBuffer();
	    Random random = new Random();
	    for (int i = 0; i < size; i++) {
	    	int index = random.nextInt(s.length());		   
			sb.append(s.charAt(index));
		}
	    
	    return sb.toString();
	}
}
