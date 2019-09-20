package com.jdy.util;

import java.util.Map;

/**
 * 常用工具
 * @author Dale
 *
 */
public class Util {

	private Util() {
	}
	
	/**
     *  检查 @param reference 是否为空
     *  如果 @param reference 为空 则报空指针异常， 异常类容为 @param format
     *  @param reference  被检查的对象
     *  @param format 空指针异常输出的内容
     *  @param args 异常类容中的参数， 比如： 当   @param format 参数为： " %s 不能为空 "
     *  @return    
     */
	public static <T> T checkNotNull(T reference, String format, Object... args) {
		if (null == reference)
			throw new NullPointerException(String.format(format, args));
		return reference;
	}

	public static void takeNotes(Map<String, Object> dataMap, Map<String, Object> targetMap) {
	}
}
