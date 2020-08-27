package com.cos.instagram.util;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	public static List<String> tapParse(String tags) {
		String temp[] = tags.split("#");
		List<String> list = new ArrayList<String>();

		for (int i = 1; i < temp.length; i++) {
			list.add(temp[i]);
		}
		return list;
	}

	public static void 파싱테스트() {
		String tags = "#태그1 #태그2";
		String temp[] = tags.split("#");
		List<String> list = new ArrayList<String>();

		for (int i = 1; i < temp.length; i++) {
			list.add(temp[i]);
		}
		System.out.println(list.size());
	}

	public static void main(String[] args) {
		파싱테스트();
	}
}
