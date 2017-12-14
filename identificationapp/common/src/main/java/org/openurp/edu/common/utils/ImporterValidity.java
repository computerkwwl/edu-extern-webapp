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
  
  public final java.sql.Date nowAt = new java.sql.Date(System.currentTimeMillis());
  
  private ImporterValidity() {
    super();
  }
  
  private ImporterValidity(TransferResult tr, AbstractItemImporter importer, EntityDao entityDao) {
    this();
    this.tr = tr;
    this.importer = importer;
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
  
  public boolean checkDate(String fieldName, String fieldMeaning, String dateFormat) {
    String fieldValue = (String) importer.getCurData().get(fieldName);
    if (StringUtils.isNotBlank(fieldValue)) {
      if (!DateUtils.isValidDate(fieldValue, "yyyy-MM-dd")) {
        tr.addFailure(fieldMeaning + "要求格式为 " + dateFormat + "，但是实际右边的格式！！！", fieldValue);
        return false;
      }
    }
    return true;
  }
  
  public boolean checkDateBetween(String fieldName1, String fieldMeaning1, String fieldName2, String fieldMeaning2, String dateFormat) {
    String fieldValue1 = (String) importer.getCurData().get(fieldName1);
    String fieldValue2 = (String) importer.getCurData().get(fieldName2);
    
    java.sql.Date date1 = nowAt;
    java.sql.Date date2 = null;
    if (StringUtils.isNotBlank(fieldValue1) && StringUtils.isNotBlank(fieldValue2)) {
      date1 = DateUtils.toSqlDate(fieldValue1, dateFormat);
      date2 = DateUtils.toSqlDate(fieldValue2, dateFormat);
      if (date2.before(date1)) {
        tr.addFailure(fieldMeaning1 + "不能晚于截止日期！！！", fieldMeaning1 + "：" + fieldValue1 + "，"
            + fieldMeaning2 + "：" + fieldValue2);
        return false;
      }
    } else if (StringUtils.isBlank(fieldValue1) && StringUtils.isNotBlank(fieldValue2)) {
      date2 = DateUtils.toSqlDate(fieldValue2, dateFormat);
      if (date2.before(nowAt)) {
        tr.addFailure(fieldMeaning2 + "不能早于今天！！！", fieldMeaning2 + "：" + fieldValue2);
        return false;
      }
    } else {
      importer.getCurData().put(fieldName1, date1);
      importer.getCurData().put(fieldName2, date2);
    }
    return true;
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
}
