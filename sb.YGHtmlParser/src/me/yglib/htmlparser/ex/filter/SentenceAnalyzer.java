package me.yglib.htmlparser.ex.filter;

import java.util.ArrayList;
import java.util.List;

public class SentenceAnalyzer {
	
	public static List<String> getSeparatedSentence(String paragraph){
		ArrayList<String> lstSent = new ArrayList<String>();
		String[] split = paragraph.split("\n|\\D\\.\\D|\r");
		for(String s : split){
			s = s.trim();
			if(s.length() > 0){	//changed from 1 to 0
				lstSent.add(s);
			}
		}
		return lstSent;
	}
	
	public static String convertHtmlToGenText(String source){
		String retStr = source.replaceAll("&nbsp;", " ");
		return retStr;
	}
	
	public static boolean containsLetter(String source){
		source = convertHtmlToGenText(source);
		source = source.trim();
		
		for(int i=0;i<source.length();i++){
			if(Character.isLetter(source.charAt(i))){
				return true;
			}
		}
		return false;
	}
	
	
	public static void main(String ... v){
		//..2010-12-04 17:36
		//List<String> ss = getSeparatedSentence("�̰��� �����ΰ�\n���Ŀ��� ����� ������ ���� �ִ� �������� �������� 2.5�� L4 ������ ��������� ��ȭ�� ����� ���� ������ �� �پ ���� ������ �����Ѵ�... ������ 3210����~3690�����̴�.");
//		List<String> ss = getSeparatedSentence("2010-12-04 17:36 &nbsp;&nbsp; ������");
//		for(String str : ss){
//			System.out.println("--->" + str);
//		}
		
		String a = "2010-12-04 17:36 &nbsp;&nbsp; ";
		//System.out.println(convertHtmlToGenText(a));
		System.out.println(containsLetter(a));
	}
}
