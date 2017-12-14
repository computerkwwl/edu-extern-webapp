/**
 * 
 */
package org.openurp.edu.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhouqi 2017年12月13日
 *
 */
public class DateUtils {
  
  private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
  
  public static boolean isValidDate(String dateValue, String dateFormat) {
    if (StringUtils.isBlank(dateValue)) {
      return false;
    }
    
    if (StringUtils.isBlank(dateFormat)) {
      throw new RuntimeException("dateFormat of arg is null or empty!!!");
    }
    
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
      Date parseDate = sdf.parse(dateValue);
      return StringUtils.equals(dateValue, sdf.format(parseDate));
    } catch (ParseException e) {
      logger.error(e.getMessage());
      return false;
    }
  }
  
  public static Date toUtilDate(String dateValue, String dateFormat) {
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    try {
      return sdf.parse(dateValue);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static java.sql.Date toSqlDate(String dateValue, String dateFormat) {
    return new java.sql.Date(toUtilDate(dateValue, dateFormat).getTime());
  }
}
