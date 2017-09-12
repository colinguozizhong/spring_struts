package com.ustcsoft.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectUtil {
	private static final Log logger = LogFactory.getLog(ReflectUtil.class);

	public static void setFieldValue(Object target, String fname, Class ftype,
			Object fvalue) {
		if ((target == null)
				|| (fname == null)
				|| ("".equals(fname))
				|| ((fvalue != null) && (!ftype.isAssignableFrom(fvalue
						.getClass())))) {
			return;
		}
		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod("set"
					+ Character.toUpperCase(fname.charAt(0))
					+ fname.substring(1), new Class[] { ftype });
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			method.invoke(target, new Object[] { fvalue });
		} catch (Exception me) {
			if (logger.isDebugEnabled())
				logger.debug(me);
			try {
				Field field = clazz.getDeclaredField(fname);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				field.set(target, fvalue);
			} catch (Exception fe) {
				if (logger.isDebugEnabled())
					logger.debug(fe);
			}
		}
	}
	
	// 该方法实现对对象的拷贝操作
    public Object copy(Object object) throws Exception
    {
        Class<?> classType = object.getClass();


        /* 生成新的对象的讨论
        // 获得Constructor对象,此处获取第一个无参数的构造方法的
        Constructor cons = classType.getConstructor(new Class[] {});//不带参数，所以传入一个为空的数组
        // 通过构造方法来生成一个对象
        Object obj = cons.newInstance(new Object[] {});

        // 以上两行代码等价于：
        Object obj11 = classType.newInstance();  // 这行代码无法处理构造函数有参数的情况
        
        //用第二个带参数的构造方法生成对象
        Constructor cons2 = classType.getConstructor(new Class[] {String.class, int.class});
        Object obj2 = cons2.newInstance(new Object[] {"ZhangSan",20});
        
        */
        
        Object objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
        
        //获得对象的所有成员变量
        Field[] fields = classType.getDeclaredFields();
        for(Field field : fields)
        {
            //获取成员变量的名字
            String name = field.getName();    //获取成员变量的名字，此处为id，name,age
            //System.out.println(name);

            //获取get和set方法的名字
            String firstLetter = name.substring(0,1).toUpperCase();    //将属性的首字母转换为大写            
            String getMethodName = "get" + firstLetter + name.substring(1);
            String setMethodName = "set" + firstLetter + name.substring(1);            
            //System.out.println(getMethodName + "," + setMethodName);
            
            //获取方法对象
            Method getMethod = classType.getMethod(getMethodName, new Class[]{});
            Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});//注意set方法需要传入参数类型
            
            //调用get方法获取旧的对象的值
            Object value = getMethod.invoke(object, new Object[]{});
            //调用set方法将这个值复制到新的对象中去
            setMethod.invoke(objectCopy, new Object[]{value});

        }

        return objectCopy;

    }
}