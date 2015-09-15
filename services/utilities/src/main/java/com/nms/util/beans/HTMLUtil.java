
package com.nms.util.beans;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class HTMLUtil
{
	public static String[] splitString( String to_split, String separator )
	{
		// use @separator to split a string into string array
		if( to_split == null || to_split.length() == 0 )
			return null;

		int count = 0;
		int pos, prevpos;
		Vector temparr = new Vector();
		String[] retarr;

		prevpos = 0;
		pos = to_split.indexOf( separator );
		while( pos != -1 )
		{
			temparr.add( count, to_split.substring( prevpos, pos ) );
			count++;
			prevpos = pos + separator.length();
			pos = to_split.indexOf( separator, prevpos );
		}
		temparr.add( count, to_split.substring( prevpos ) );
		count++;

		retarr = new String[count];
		while( --count >= 0 )
			retarr[count] = (String) temparr.get( count );

		return retarr;
	}

	public static String replaceString( String src_str, String to_find, String repl_with )
	{
		if( src_str == null || to_find == null || repl_with == null )
			return src_str;

		StringBuffer result = new StringBuffer( "" );
		int len_1 = to_find.length();
		int pos = src_str.indexOf( to_find );
		int prevpos = 0;
		while( pos != -1 )
		{
			result.append( src_str.substring( prevpos, pos ) );
			result.append( repl_with );
			prevpos = pos + len_1;
			if( prevpos >= src_str.length() )
				return result.toString();
			pos = src_str.indexOf( to_find, prevpos );
		}
		result.append( src_str.substring( prevpos ) );

		return result.toString();
	}

	public static String replicate(String s, int count)
	{
		String r = "";
		if( s == null || s.length() == 0 || count < 1 )
			return r;
		for(int i=0; i<count; i++)
			r += s;
		return r;
	}

	public static String padRight(String s, int totalLen, String padChar)
	{
		String r = s;
		if( s == null || s.length() > totalLen || totalLen < 1 )
			return r;
		while(r.length() < totalLen)
			r += padChar;
		return r;
	}

	public static String padLeft(String s, int totalLen, String padChar)
	{
		String r = s;
		if( s == null || s.length() > totalLen || totalLen < 1 )
			return r;
		while(r.length() < totalLen)
			r = padChar + r;
		return r;
	}

	public static String stripRight(String s, char charToStrip)
	{
		if( s == null || s.length() == 0 )
			return s;
		while( s.charAt(s.length()-1) == charToStrip )
			s = s.substring(0, s.length()-1);
		return s;
	}

	public static String stripLeft(String s, char charToStrip)
	{
		if( s == null || s.length() == 0 )
			return s;
		while( s.charAt(0) == charToStrip )
			s = s.substring(1);
		return s;
	}

	// Date functions
	public static String formatDate( String fmt_str, Date date )
	{
		return new java.text.SimpleDateFormat(fmt_str).format(date);
	}


/*
	public static String formatDateForLocale( Date date ) throws Exception
	{
		SimpleDateFormat fmt = new SimpleDateFormat(JSPUtil.getLocaleDateFormat());
		return fmt.format(date);
	}

	public static String formatDateFromLocale( String target_fmt, String locale_date ) throws ParseException
	{
		SimpleDateFormat fmt_from = new SimpleDateFormat(JSPUtil.getLocaleDateFormat());
		SimpleDateFormat fmt_to = new SimpleDateFormat(target_fmt);
		return fmt_to.format( fmt_from.parse(locale_date) );
	}

	public static String formatDateFromLocaleToOracle( String locale_date ) throws ParseException
	{
		return formatDateFromLocale( "dd-MMM-yyyy", locale_date );
	}

	public static String formatDateFromLocaleToDB2( String locale_date ) throws ParseException
	{
		return formatDateFromLocale( "yyyyMMdd", locale_date );
	}

*/
	public static String rollStringDate( String yyyy_mm_dd, int days )
	{
		int y = 0, m = 0, d = 0;

		try
		{
			y = Integer.parseInt( yyyy_mm_dd.substring( 0, 4 ) );
			m = Integer.parseInt( yyyy_mm_dd.substring( 5, 7 ) ) - 1;
			d = Integer.parseInt( yyyy_mm_dd.substring( 8, 10 ) );
		}
		catch( Exception e )
		{
			return yyyy_mm_dd;
		}

		Calendar cal = new GregorianCalendar();
		cal.set( y, m, d );
		cal.add( cal.DAY_OF_YEAR, days );
		return new java.text.SimpleDateFormat( "yyyy-MM-dd" ).format( cal.getTime() );
	}

	public static Date getCleanDate( Date dateWithTimeStamp )
	{
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime( dateWithTimeStamp );
		calendar.set( Calendar.HOUR, 0 );
		calendar.set( Calendar.MINUTE, 0 );
		calendar.set( Calendar.SECOND, 0 );
		calendar.set( Calendar.MILLISECOND, 0 );
		return calendar.getTime();
	}

	public static String formatServerSideMsgAsHTML(String errorMsg)
	{
		if( errorMsg == null )
			errorMsg = "java.lang.NullPointerException";
		int pos = errorMsg.indexOf( "Start server side stack trace:" );
		if( pos != -1 )
			errorMsg = errorMsg.substring(0, pos-1 );
		errorMsg = HTMLUtil.replaceString( errorMsg, "\n", "<br>" );
		errorMsg = HTMLUtil.replaceString( errorMsg, "\'", "\\\'" );
		return errorMsg;
	}
}
