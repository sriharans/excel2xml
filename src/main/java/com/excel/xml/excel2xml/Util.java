package com.excel.xml.excel2xml;

public class Util {

	public static int columnStrtoInt(String args) {
		
        int result = 0;
        for (int i = 0; i < args.length(); i++) {
            result *= 26;
            result += args.charAt(i) - 'A' ;
        }
       return result;
    }
	
}
