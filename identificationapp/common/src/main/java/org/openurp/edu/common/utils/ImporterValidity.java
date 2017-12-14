/**
 * 
 */
package org.openurp.edu.common.utils;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.AbstractItemImporter;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public final class ImporterValidity {
  
  private TransferResult tr;
  
  private AbstractItemImporter importer;
  
  private EntityDao entityDao;
  
  private final java.sql.Date nowAt = new java.sql.Date(System.currentTimeMillis());
  
  private ImporterValidity() {
    super();
  }
  
  private ImporterValidity(TransferResult tr, AbstractItemImporter importer, EntityDao entityDao) {
    this();
    this.tr = tr;
    this.importer = importer;
    this.entityDao = entityDao;
  }
  
  public static ImporterValidity newInstance(TransferResult tr, AbstractItemImporter importer, EntityDao entityDao) {
    return new ImporterValidity(tr, importer, entityDao);
  }
  
  public boolean checkMustBe(String fieldName, String fieldMeaning) {
    String fieldValue = (String) importer.getCurData().get(fieldName);
    if (StringUtils.isBlank(fieldValue)) {
      tr.addFailure(fieldMeaning + "要求必填，但实际为空！！！", fieldValue);
      return false;
    }
    return true;
  }
  
  public boolean checkDate(String fieldName, String fieldMeaning, String dateFormat, boolean isDefaultNow) {
    String fieldValue = (String) importer.getCurData().get(fieldName);
    if (StringUtils.isBlank(fieldValue)) {
      if (isDefaultNow) {
        importer.getCurData().put(fieldName, nowAt);
      } else {
        importer.getCurData().put(fieldName, (java.sql.Date) null);
      }
    } else {
      if (DateUtils.isValidDate(fieldValue, "yyyy-MM-dd")) {
        importer.getCurData().put(fieldName, DateUtils.toSqlDate(fieldValue, "yyyy-MM-dd"));
      } else {
        tr.addFailure(fieldMeaning + "要求格式为 " + dateFormat + "，但是实际右边的格式！！！", fieldValue);
        return false;
      }
    }
    return true;
  }
  
  public boolean checkDateBetween(String fieldName1, String fieldMeaning1, String fieldName2, String fieldMeaning2, String dateFormat) {
    try {
      java.sql.Date date1 = (java.sql.Date) importer.getCurData().get(fieldName1);
      java.sql.Date date2 = (java.sql.Date) importer.getCurData().get(fieldName2);
      
      if (null == date2) {
        return true;
      }
      
      if (date2.before(date1)) {
        if (date1.equals(nowAt)) {
          tr.addFailure(fieldMeaning2 + "不能早于今天！！！", DateUtils.toFormatString(date2, dateFormat));
        } else {
          tr.addFailure(fieldMeaning1 + "不能晚于" + fieldMeaning2 + "！！！", fieldMeaning1 + "："
              + DateUtils.toFormatString(date1, dateFormat) + "，" + fieldMeaning2 + "："
              + DateUtils.toFormatString(date2, dateFormat));
        }
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Must be first called checkDate() method!!!");
    }
  }
  
  public <ID extends Serializable, E extends Entity<ID>> boolean checkCode(String fieldName, String fieldMeaning, Class<E> entityClass) {
    String codeValue = (String) importer.getCurData().get(fieldName);
    if (StringUtils.isNotBlank(codeValue)) {
      List<E> entities = entityDao.get(entityClass, "code", codeValue);
      if (entities.isEmpty()) {
        tr.addFailure(fieldMeaning + "不正确，原因没有找到对应的记录！！！", codeValue);
        return false;
      } else {
        importer.getCurData().put(fieldName, entities.get(0));
      }
    }
    return true;
  }
  
  public java.sql.Date getNowAt() {
    return nowAt;
  }
  
  public boolean hasError() {
    return tr.hasErrors();
  }
}
