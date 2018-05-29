/**
 * 
 */
package com.welltech;

import java.math.BigDecimal;

import com.welltech.common.util.MD5Util;

/**
 * Created by Zhujia at 2017年7月19日 下午3:56:08
 */
public class MethodTest {

	
	public static void main(String[] args) {
		BigDecimal num = new BigDecimal(3.5f);
		System.out.println(num.setScale(0,BigDecimal.ROUND_UP));
	}
}
