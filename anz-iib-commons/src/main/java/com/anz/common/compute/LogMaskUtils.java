package com.anz.common.compute;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.impl.ComputeUtils;


public class LogMaskUtils {
	
	//private static final Logger logger = LogManager.getLogger();
	private static final int EMPTY = 0;
	private static final int INIT_TO_ZERO = 0;
	
	
	/**
	 * Read regular expressions from local environment, evironementVariable/fileName.
	 * @param environmentVariable
	 * @param fileName
	 * @return List of regular expressions
	 */	
	public static List<String> getGlobalMasks(String environmentVariable, String fileName) {
		
		List<String> regexList = new ArrayList<>();
		
		//logger.info("getGlobalMasks");
		
		try{
		
			String path =  System.getenv(environmentVariable);
			File regexFile = new File(path + "/" + fileName);
			FileInputStream fileInputStream = new FileInputStream(regexFile);
			byte[] data = new byte[(int) regexFile.length()];
			fileInputStream.read(data);
			fileInputStream.close();		
			String json = new String(data, "UTF-8");
			regexList = ComputeUtils.jsonStringToArray(json);
			
		} catch (Exception e) {
			
			//logger.throwing(e);
			
		} finally {
			
			return regexList;
			
		}
		
	}
	
	/**
	 * Read regular expressions from <pattern-name>-java/src/main/resources/com.anz.<pattern-name>.compute/fileName
	 * @param patternClass
	 * @param fileName
	 * @return List of regular expressions
	 */	
	public static List<String> getPatternMasks(InputStream inputStream, String fileName) {
		
		List<String> regexList = new ArrayList<>();
		
		//logger.info("getPatternMasks");
		
		try {		
			
			//InputStream inputStream = patternClass.getResourceAsStream(fileName);
			String json = new Scanner(inputStream).useDelimiter("\\A").next();
			regexList = ComputeUtils.jsonStringToArray(json);
			
		} catch (Exception e) {
			
			//logger.throwing(e);
			
		} finally {
			
			return regexList;
			
		}
		
	}
	
	
	/**
	 * For message replace all regex matches with maskChar array of equal length.
	 * @param regexArray
	 * @param message
	 * @param maskChar
	 * @return masked message
	 */
	public static String mask(List<String> regexArray, String message, char maskChar) {	
		
		//logger.info("mask");
		
		assert !regexArray.isEmpty();
		
		// If no regexs defined or exception reading regex.json
		if(regexArray.size() == EMPTY) {
			
			// Output unmasked message
			//logger.info("regular expression array empty");
			return message;
			
		}
		
		// For each regular expression
		for(String regex: regexArray) {
		
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(message);
		
			// For each occurence of single regular expression in message
			while(matcher.find()) {
				
				// Get length of string to be replaced
				int length = matcher.end() - matcher.start();
				char[] mask = new char[length];
				
				// Create replacement string the same length of the message to be replaced
				for(int index = INIT_TO_ZERO; index < length; index++) {
					
					mask[index] = maskChar;
				
				}
				
				String replacement = new String(mask);
				
				// Replace 
				message = matcher.replaceFirst(replacement);
			
			}
		
		}
		
		return message;
		
	}

	
}