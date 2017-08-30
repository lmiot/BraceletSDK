package com.lmiot.BraceletDemo.Util;

/**
 * Created by ming on 2016/4/6.
 */
public class math {
	public static double getMax(double...count){
		double max=count[0];
		for(int i=1;i<count.length;++i) {
			if (max < count[i]) {
				max = count[i];
			}
		}
		return max;
	}
}
