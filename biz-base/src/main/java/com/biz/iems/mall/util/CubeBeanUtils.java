package com.biz.iems.mall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class CubeBeanUtils {

    private static Logger logger = LoggerFactory.getLogger(CubeBeanUtils.class);
    private static Map<Class<?>, Object> simpleBeanTypeMap = getSimpleBeanTypeMap();

    public CubeBeanUtils() {
    }

    public static void copyProperties(Object target, Object source, String... ignoreProperties) {
        copyProperties(target, source, false, ignoreProperties);
    }

    public static void copyProperties(Object target, Object source, boolean copyNull, String... ignoreProperties) {
        if (target != null && source != null) {
            if (!simpleBeanTypeMap.containsKey(target.getClass()) && !simpleBeanTypeMap.containsKey(source.getClass())) {
                PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
                List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
                PropertyDescriptor[] var6 = targetPds;
                int var7 = targetPds.length;
                for(int var8 = 0; var8 < var7; ++var8) {
                    PropertyDescriptor targetPd = var6[var8];
                    Method writeMethod = targetPd.getWriteMethod();
                    if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                        PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                        if (sourcePd != null) {
                            Method readMethod = sourcePd.getReadMethod();
                            if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                                try {
                                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                        readMethod.setAccessible(true);
                                    }
                                    Object value = readMethod.invoke(source);
                                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                        writeMethod.setAccessible(true);
                                    }
                                    if (value != null) {
                                        if (!simpleBeanTypeMap.containsKey(value.getClass())) {
                                            if (value instanceof Collection) {
                                                value = collectionCopy(targetPd, value, copyNull);
                                            } else if (value instanceof Map) {
                                                value = mapCopy(targetPd, value);
                                            } else {
                                                Object newObj = value;
                                                try {
                                                    newObj = writeMethod.getParameterTypes()[0].newInstance();
                                                    copyProperties(newObj, value, copyNull);
                                                } catch (Exception var16) {
                                                    logger.error("targetPd newInstance Exception:", var16);
                                                }
                                                value = newObj;
                                            }
                                        }
                                        writeMethod.invoke(target, value);
                                    } else if (copyNull) {
                                        writeMethod.invoke(target, value);
                                    }
                                } catch (Throwable var17) {
                                    String errorMsg = "copyProperties Exception==>> sourceClassName:{}" + source.getClass() + ", targetClassName:{}" + target.getClass() + ", fieldName:" + sourcePd.getName() + ", sourceFieldClass:" + sourcePd.getPropertyType() + ", targetFieldClass:" + targetPd.getPropertyType();
                                    throw new RuntimeException(errorMsg);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            logger.info("---------copyProperties-------target or source is null");
        }
    }

    private static Object mapCopy(PropertyDescriptor descriptor, Object value) throws Exception {
        if (value == null) {
            return null;
        } else {
            Object targetMap;
            if (value.getClass().getSimpleName().contains("Persistent")) {
                targetMap = new HashMap();
            } else {
                targetMap = (Map)value.getClass().newInstance();
            }
            Map sourceMap = (Map)value;
            Set keySet = sourceMap.keySet();
            Iterator var5 = keySet.iterator();

            while(var5.hasNext()) {
                Object key = var5.next();
                Object sourceObj = sourceMap.get(key);
                if (key != null && sourceObj != null) {
                    ((Map)targetMap).put(key, sourceObj);
                }
            }
            return targetMap;
        }
    }

    private static Object collectionCopy(PropertyDescriptor descriptor, Object value, boolean copyNull) {
        if (value == null) {
            return null;
        } else {
            Object targetCollection = null;
            try {
                Class<?> genericClazz = getGenericClazz(descriptor, value.getClass(), 0);
                Collection sourceCollection = (Collection)value;
                if (sourceCollection.isEmpty()) {
                    return sourceCollection;
                }
                String simpleName = value.getClass().getSimpleName();
                if (simpleName.contains("Persistent")) {
                    if (value instanceof Set) {
                        targetCollection = new HashSet();
                    } else if (value instanceof List) {
                        targetCollection = new ArrayList();
                    }
                } else if (!simpleName.contains("ArrayList") && !simpleName.contains("SingletonList")) {
                    targetCollection = (Collection)value.getClass().newInstance();
                } else {
                    targetCollection = new ArrayList();
                }
                copyCollection((Collection)targetCollection, sourceCollection, genericClazz, copyNull);
            } catch (Exception var7) {
                logger.error("collectionCopy Exception:", var7);
            }
            return targetCollection;
        }
    }

    private static Class<?> getGenericClazz(PropertyDescriptor descriptor, Class<?> clazz, int index) {
        Class<?> genericClazz = clazz;
        Type fc = descriptor.getWriteMethod().getGenericParameterTypes()[0];
        if (fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)fc;
            genericClazz = (Class)pt.getActualTypeArguments()[index];
        }
        return genericClazz;
    }

    public static Map<Class<?>, Object> getSimpleBeanTypeMap() {
        if (simpleBeanTypeMap == null) {
            simpleBeanTypeMap = new HashMap();
            simpleBeanTypeMap.put(BigDecimal.class, (Object)null);
            simpleBeanTypeMap.put(BigInteger.class, (Object)null);
            simpleBeanTypeMap.put(Boolean.class, (Object)null);
            simpleBeanTypeMap.put(Byte.class, (Object)null);
            simpleBeanTypeMap.put(Character.class, (Object)null);
            simpleBeanTypeMap.put(Double.class, (Object)null);
            simpleBeanTypeMap.put(Float.class, (Object)null);
            simpleBeanTypeMap.put(Integer.class, (Object)null);
            simpleBeanTypeMap.put(Long.class, (Object)null);
            simpleBeanTypeMap.put(Short.class, (Object)null);
            simpleBeanTypeMap.put(String.class, (Object)null);
            simpleBeanTypeMap.put(Class.class, (Object)null);
            simpleBeanTypeMap.put(Date.class, (Object)null);
            simpleBeanTypeMap.put(LocalDateTime.class, (Object)null);
            simpleBeanTypeMap.put(Calendar.class, (Object)null);
            simpleBeanTypeMap.put(File.class, (Object)null);
            simpleBeanTypeMap.put(java.sql.Date.class, (Object)null);
            simpleBeanTypeMap.put(Time.class, (Object)null);
            simpleBeanTypeMap.put(Timestamp.class, (Object)null);
            simpleBeanTypeMap.put(URL.class, (Object)null);
            simpleBeanTypeMap.put(Currency.class, (Object)null);
            simpleBeanTypeMap.put(UUID.class, (Object)null);
        }

        return simpleBeanTypeMap;
    }

    public static void copyCollection(Collection target, Collection source, Class<?> targetClazz) {
        copyCollection(target, source, targetClazz, false);
    }

    public static void copyCollection(Collection target, Collection source, Class<?> targetClazz, boolean copyNull) {
        if (source != null && !source.isEmpty()) {
            Iterator var4 = source.iterator();
            while(true) {
                while(var4.hasNext()) {
                    Object sourceObj = var4.next();
                    if (simpleBeanTypeMap.containsKey(sourceObj.getClass())) {
                        target.add(sourceObj);
                    } else {
                        Object newObj;
                        try {
                            newObj = targetClazz.newInstance();
                        } catch (Exception var8) {
                            logger.error("copyCollection newInstance Exception:", var8);
                            target.add(sourceObj);
                            continue;
                        }
                        copyProperties(newObj, sourceObj, copyNull);
                        target.add(newObj);
                    }
                }
                return;
            }
        }
    }

}
