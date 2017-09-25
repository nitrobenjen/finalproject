package com.util;

import java.text.*;
import java.util.*;

public class Util {

	// �Ϸù�ȣ ������ static ���� �غ�
	// ���۹�ȣ -> ���Ƿ� ����
	private static int num = 100;

	// �Ϸù�ȣ �ڵ� ���� static �޼ҵ� -> �ߺ� �Ұ�
	public static int autoNum() {
		// 1 -> 1
		// 2 -> 2
		// 100 -> 100
		// 101 -> 101
		return Util.num++;
	}

	// �ڸ����� ������ ���� ������ �Ϸù�ȣ ����
	public static String autoFormatNum() {
		// 1 -> 00001
		// 2 -> 00002
		// 100 -> 00100
		// 101 -> 00101
		return String.format("%05d", Util.num++);
	}

	// int[] -> Integer[] ��ȯ �޼ҵ�
	public static Integer[] toIntegerArray(int[] a) {
		Integer[] temp = new Integer[a.length];
		for (int i = 0; i < a.length; ++i) {
			temp[i] = new Integer(a[i]);
		}
		return temp;
	}

	// Object[](Integer[]) -> int[] ��ȯ �޼ҵ�
	public static int[] toIntArray(Object[] a) throws ClassCastException {
		int[] temp = new int[a.length];
		for (int i = 0; i < a.length; ++i) {
			// Object -> Integer ����ȯ �Ұ��� ��� ���� �߻�
			if (a[i] instanceof Integer) {
				temp[i] = ((Integer) a[i]).intValue();
			} else {
				throw new ClassCastException();
			}
		}
		return temp;
	}

	// �ý����� ���� ��¥->���ڿ� ���� yyyy-MM-dd
	public static String toDayString(String format) {
		String today = (new SimpleDateFormat(format)).format(new Date());
		return today;
	}

	// press any key to continue....
	public static void pressAnyKey(java.util.Scanner sc) {
		System.out.println();
		System.out.print("press enter key to continue....");
		System.out.println();
		sc.nextLine(); // EnterŰ ����
	}

	// ���� ������ ��¥���� �˻��ϴ� �޼ҵ�
	// ->Ʋ�� ��¥�� ��� ���� �߻�
	// ->try~catch �������� �˻� ����
	public static void dateCheck(String date) throws java.text.ParseException {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
		sdf.setLenient(false);
		sdf.parse(date); // ���� �߻�
	}

}
