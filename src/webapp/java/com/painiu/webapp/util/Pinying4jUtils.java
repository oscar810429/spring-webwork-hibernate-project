package com.painiu.webapp.util;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinying4jUtils {

	public static String makeStringByStringSet(Set<String> stringSet) {
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringSet) {
			if (i == stringSet.size() - 1) {
				str.append(s);
			} else {
			    str.append(s + ",");
			    //str.append(s);
			}
			i++;
			//break;
		}
		return str.toString().toLowerCase();
	}

	public static Set<String> getPinyin(String src)
			throws BadHanyuPinyinOutputFormatCombination {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = src.toCharArray();
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

			String[][] temp = new String[src.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
					temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i],hanYuPinOutputFormat);
				} else if (((int) c >= 65 && (int) c <= 90) || ((int) c >= 97 && (int) c <= 122)) {
					temp[i] = new String[] { String.valueOf(srcChar[i]) };
				} else {
					temp[i] = new String[] { "" };
				}
			}
			String[] pingyinArray = Exchange(temp);
			Set<String> pinyinSet = new HashSet<String>();
			for (int i = 0; i < pingyinArray.length; i++) {
				pinyinSet.add(pingyinArray[i]);
			}
			return pinyinSet;
		}
		return null;
	}
	
	public static String PingyinToString(String src) throws BadHanyuPinyinOutputFormatCombination{

	    char[] t1 = null;
	    t1=src.toCharArray();
	    String[] t2 = new String[t1.length];
	    HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
	    t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	    t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	    t3.setVCharType(HanyuPinyinVCharType.WITH_V);
	    String t4="";
	    int t0=t1.length;
	    for (int i=0;i<t0;i++)
	      {

	         if(java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+"))   
	              {
	              t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);              
	              t4+=t2[0];
	              }
	          else
	              t4+=java.lang.Character.toString(t1[i]);
	      }
	      return t4;

	} 

	public static String[] Exchange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}
	
	public static String getPinYinHeadChar(String str) {
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
		      char word = str.charAt(j);
		      String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word,hanYuPinOutputFormat);
		      if (pinyinArray != null) {
		      convert += pinyinArray[0].charAt(0);
		      }else {
		      convert += word;
		      }
		}
		return convert;
		}

	public static String getCnASCII(String cnStr)
		{
		StringBuffer   strBuf   =   new   StringBuffer();
		byte[]   bGBK   =   cnStr.getBytes();
		        for(int   i=0;i <bGBK.length;i++){
		                strBuf.append(Integer.toHexString(bGBK[i]&0xff));
		        }
		        return strBuf.toString();
		} 
	
	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
		
		ResourceBundle props = ResourceBundle.getBundle("pinying4j");
		
		//System.out.println(makeStringByStringSet(getPinyin(props.getString("city1"))));  
		//System.out.println(makeStringByStringSet(getPinyin(props.getString("city2"))));  
		//System.out.println(makeStringByStringSet(getPinyin(props.getString("city3")))); 
		//String str1=StringUtils.left(props.getString("city2"),props.getString("city2").length()-1).trim();
		System.out.println(PingyinToString(props.getString("city1")));
		System.out.println(PingyinToString(props.getString("city2")));
		System.out.println(PingyinToString(props.getString("city3")));
		System.out.println(PingyinToString(props.getString("city4")));
		System.out.println(PingyinToString(props.getString("city5")));
		System.out.println(PingyinToString(props.getString("city6")));

	}

}
