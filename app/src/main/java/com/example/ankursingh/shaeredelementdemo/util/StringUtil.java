package com.example.ankursingh.shaeredelementdemo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;

//import org.apache.regexp.RE;

/**
 * Utility class useful when dealing with string objects. This class is a
 * collection of static functions, and the usage is:
 *
 * StringUtil.method()
 *
 * it is not allowed to create instances of this class
 */
public class StringUtil

{
	private static final int EOF = -1;

	public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
		long count = 0;
		int n = 0;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static long copyLarge(Reader input, Writer output) throws IOException {
		return copyLarge(input, output, new char[4096]);
	}

	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}





	/**
	 * This method check if a string ends with the specified suffix ignoring the
	 * case.
	 *
	 * @param str
	 *            - string to check suffix - the suffix to find
	 * @return true if the character sequence represented by the argument is a
	 *         suffix of the character sequence represented by this object;
	 *         false otherwise.
	 */

	public static boolean endsWithIgnoreCase(final String str, final String suffix) {

		return str.toLowerCase().endsWith(suffix.toLowerCase());

	}

	/**
	 * This method is missing in CLDC 1.0 String implementation
	 */
	public static boolean equalsIgnoreCase(final String string1, final String string2) {
		// Strings are both null, return true
		if ((string1 == null) && (string2 == null)) {
			return true;
		}
		// One of the two is null, return false
		if ((string1 == null) || (string2 == null)) {
			return false;
		}
		// Both are not null, compare the lowercase strings
		if (string1.toLowerCase().equals(string2.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method extracts from address the resources
	 *
	 * @param url
	 *            eg. http://127.0.0.1:8080/sync
	 * @return http://127.0.0.1:8080
	 */
	public static String extractAddressFromUrl(String url) {
		int start = 0;
		if (url.startsWith("https://")) {
			start = 8;
		} else if (url.startsWith("http://")) {
			start = 7;
		}
		// If we have resource location in the address then we strip
		// everything after the '/'
		final int pos = url.substring(start).indexOf('/');
		if (pos >= 0) {
			url = url.substring(0, pos + start);
		}
		return url;
	}

	/**
	 * This method extracts from address the protocol port and resources
	 *
	 * @param url
	 *            eg. http://127.0.0.1:8080/sync
	 * @param protocol
	 *            http
	 * @return 127.0.0.1
	 */
	public static String extractAddressFromUrl(String url, final String protocol) {
		final String prefix = protocol + "://";
		if (url.startsWith(prefix)) {
			url = url.substring(prefix.length(), url.length());
		}
		// If we have port number in the address we strip everything
		// after the port number
		int pos = url.indexOf(':');
		if (pos >= 0) {
			url = url.substring(0, pos);
		}

		// If we have resource location in the address then we strip
		// everything after the '/'
		pos = url.indexOf('/');
		if (pos >= 0) {
			url = url.substring(0, pos);
		}
		return url;
	}

	/**
	 * Find two consecutive newlines in a string.
	 *
	 * @param s
	 *            - The string to search
	 * @return int: the position of the empty line
	 */
	public static int findEmptyLine(final String s) {
		int ret = 0;

		// Find a newline
		while ((ret = s.indexOf("\n", ret)) != -1) {
			// Skip carriage returns, if any
			while (s.charAt(ret) == '\r') {
				ret++;
			}
			if (s.charAt(ret) == '\n') {
				// Okay, it was empty
				ret++;
				break;
			}
		}
		return ret;
	}


	/**
	 * Util method for retrieve a boolean primitive type from a String.
	 * Implemented because Boolean class doesn't provide parseBoolean() method.
	 * Returns true if the input string is equal to "true" (case insensitive)
	 * false otherwise
	 */
	public static boolean getBooleanValue(final String string) {
		if ((string == null) || string.equals("")) {
			return false;
		} else {
			if (StringUtil.equalsIgnoreCase(string, "true")) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * This method retrieves the protocol used in the given url.
	 *
	 * @param url
	 *            eg. http://127.0.0.1:8080/sync
	 * @return the url protocol (e.g. http). Return null if the protocol is not
	 *         found.
	 */
	public static String getProtocolFromUrl(final String url) {
		final int protocolEndIndex = url.indexOf("://");
		if (protocolEndIndex > 0) {
			return url.substring(0, protocolEndIndex);
		}
		return null;
	}



	/**
	 * Returns true if the given string is null or empty.
	 */
	public static boolean isNullOrEmpty(final String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * @return "" or trimmed pObject, if pObject is a not-null String
	 */
	public static String getAsString(Object pObject) {
		String _returnValue = "";
		if (pObject instanceof String) {
			_returnValue = ((String) pObject).trim();
			if (_returnValue.equalsIgnoreCase("null")) {
				_returnValue = "";
			}
		}
		return _returnValue;
	}

	/**
	 * @param pCollection
	 * @param pKey
	 * @return
	 */
	public static boolean containsIgnoreCase(Collection<String> pCollection, String pKey) {
		try {
			if (pCollection.contains(pKey)) {
				return true;
			}

			for (String _tempKey : pCollection) {
				if (_tempKey.equalsIgnoreCase(pKey)) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * This method check if the protocol used in the given url is valid.
	 *
	 * @param url
	 *            eg. http://127.0.0.1:8080/sync
	 * @return the url protocol (e.g. http). Return null if the protocol is not
	 *         found.
	 */
	public static boolean isValidProtocol(final String url) {
		final String protocol = StringUtil.getProtocolFromUrl(url);
		if (protocol == null) {
			return false;
		} else if (protocol.equals("http") || protocol.equals("https")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Join the given strings into a single string using sep as separator for
	 * individual values.
	 *
	 * @param list
	 *            the string array to join
	 * @param sep
	 *            the separator to use
	 *
	 * @return the joined string
	 */
	public static String join(final String[] list, final String sep) {
		final StringBuffer buffer = new StringBuffer(list[0]);
		final int len = list.length;

		for (int i = 1; i < len; i++) {
			buffer.append(sep).append(list[i]);
		}
		return buffer.toString();
	}

	/**
	 * Removes unwanted backslashes characters
	 *
	 * @param content
	 *            The string containing the backslashes to be removed
	 * @return the content without backslashes
	 */
	public static String removeBackslashes(final String content) {
		return StringUtil.removeChar(content, '\\');
	}

	/**
	 * Removes unwanted blank characters
	 *
	 * @param content
	 * @return String
	 */
	public static String removeBlanks(final String content) {
		return StringUtil.removeChar(content, ' ');
	}

	/**
	 * Removes unwanted characters
	 *
	 * @param content
	 *            The string containing the backslashes to be removed
	 * @param ch
	 *            the character to be removed
	 * @return the content without backslashes
	 */
	public static String removeChar(final String content, final char ch) {

		if (content == null) {
			return null;
		}

		final StringBuffer buff = new StringBuffer();
		buff.append(content);

		final int len = buff.length();
		for (int i = len - 1; i >= 0; i--) {
			if (ch == buff.charAt(i)) {
				buff.deleteCharAt(i);
			}
		}
		return buff.toString();
	}

	public static String removePortFromUrl(String url, final String protocol) {
		final String prefix = protocol + "://";
		int beginning = 0;
		if (url.startsWith(prefix)) {
			beginning = protocol.length() + 3;
		}

		final int pos = url.indexOf(':', beginning);
		if (pos >= 0) {
			final int slash = url.indexOf('/', pos);
			url = url.substring(0, pos) + url.substring(slash);
		}
		return url;
	}

	/**
	 * Replace all characters c1 with c2
	 *
	 * @param s
	 *            the String to be manipulated
	 * @param c1
	 *            the char to be replaced
	 * @param c2
	 *            the char to put in place of c1
	 * @return the new string
	 */
	public static String replaceAll(final String s, final char c1, final char c2) {
		final StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < sb.length(); i++) {
			final char c = sb.charAt(i);
			if (c == c1) {
				sb.setCharAt(i, c2);
			}
		}
		return sb.toString();
	}

	/**
	 * Replace any occurrence of one string with another one
	 *
	 * @param s
	 *            the String to be manipulated
	 * @param src
	 *            the string to be replaced
	 * @param tgt
	 *            the replacement string
	 * @return the replaced string (newly allocated). If src is not found the
	 *         method returns a string identical to the original one
	 */
	public static String replaceAll(final String s, final String src, final String tgt) {
		final StringBuffer sb = new StringBuffer();
		int pos = s.indexOf(src);

		int start = 0;
		while (pos != -1) {

			final String portion = s.substring(start, pos);
			sb.append(portion);
			sb.append(tgt);

			if (pos + src.length() < s.length()) {
				// Get ready for another round
				start = pos + src.length();
				pos = s.indexOf(src, pos + src.length());
			} else {
				pos = -1;
				start = s.length();
			}
		}

		// Append the last chunk
		if (start < s.length()) {
			sb.append(s.substring(start));
		}

		return sb.toString();
	}


	/**
	 * Removes characters 'c' from the beginning and the end of the string
	 */
	public static String trim(final String s, final char c) {
		if (s == null) {
			return null;
		}
		if (s.length() == 0) {
			return "";
		}

		int start = 0;
		int end = s.length() - 1;

		while (s.charAt(start) == c) {
			if (++start >= end) {
				// The string is made by c only
				return "";
			}
		}

		while (s.charAt(end) == c) {
			if (--end <= start) {
				return "";
			}
		}

		return s.substring(start, end + 1);
	}

	public static byte[] streamToBArray(InputStream is) {
		if (is == null) {
			return new byte[0];
		}

		byte[] data = new byte[2048];
		int read = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			while ((read = is.read(data)) != -1) {
				baos.write(data, 0, read);
			}
			bytes = baos.toByteArray();
		} catch (IOException e) {
			LogUtils.error("StringUtils", e.toString(), e);
		} finally {
			try {
				if (baos != null) {
					baos.close();
					baos = null;
				}
			}catch (Exception ex){
				LogUtils.error("StringUtils", ex.toString(), ex);
			}
		}
		return bytes;
	}

	// This class cannot be instantiated
	private StringUtil() {
	}

	/*
	 * 
	 * public static boolean matches(String regexp, String s) { RE r = new
	 * RE(regexp); return r.match(s); }
	 * 
	 * public static boolean containsDigit(String str) { char [] c =
	 * str.toCharArray(); int size =c.length; for(int i =0 ; i < size ;i++) {
	 * if(CharacterUtilities.isDigit(c[i])) { return true; } } return false; }
	 */

	/**
	 * @param pStr
	 * @param startIndex
	 * @param pEndIndex
	 * @return int value, parsed from a substring of pStr
	 */
	public static int parseInt(String pStr, int startIndex, int pEndIndex) {
		if (pStr == null || pStr.length() < pEndIndex) {
			return 0;
		}
		try {
			return Integer.parseInt(pStr.substring(startIndex, pEndIndex));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static boolean convertBooleanValue(String pIntegerValue) {
		if (pIntegerValue == null) {
			return false;
		} else {
			return pIntegerValue.equals("1") ? true : false;
		}

	}

}
