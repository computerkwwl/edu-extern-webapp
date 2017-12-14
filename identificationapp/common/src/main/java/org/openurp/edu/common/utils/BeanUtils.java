package org.openurp.edu.common.utils;

/**
 * @author zhouqi 2017年12月15日
 *
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
  
  public static void setPropertyIfNotNull(Object bean, String name, Object value) {
    if (null != value) {
      try {
        setProperty(bean, name, value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
