package me.dwliu.ebase.sample;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class A {
	public static void main(String[] args) {
		Date dateTime1 = DateUtil.beginOfDay(new Date());
		Date dateTime = DateUtil.endOfDay(new Date());

		System.out.println(dateTime1);
		System.out.println(dateTime);
		//昨天
		System.out.println(DateUtil.yesterday());
		//明天
		System.out.println(DateUtil.tomorrow());
		//上周
		System.out.println(DateUtil.lastWeek());
		//下周
		System.out.println(DateUtil.nextWeek());
		//上个月
		System.out.println(DateUtil.lastMonth());
		//下个月
		System.out.println(DateUtil.nextMonth());




	}
}
