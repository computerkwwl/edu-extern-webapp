package org.openurp.basis.edu.base.code.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.TransferResult;
import org.openurp.edu.common.DateUtils;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public abstract class IdentificationAppBaseCodeImporterListener<ID extends Serializable, E extends Entity<ID>> extends IdentificationAppImporterListener {
  
  private final Class<E> entityType = entityType();
  
  private final Class<E> entityType() {
    Type e = getClass().getGenericSuperclass();
    Type[] es = ((ParameterizedType) e).getActualTypeArguments();
    return (Class<E>) es[1];
  }
  
  public IdentificationAppBaseCodeImporterListener(EntityDao entityDao) {
    super(entityDao);
  }
  
  protected void itemStart(TransferResult tr) {
    Map<String, Object> dataMap = importer.getCurData();
    
    String code = (String) dataMap.get("code");
    if (StringUtils.isBlank(code)) {
      tr.addFailure("代码要求必填，但实际为空！！！", code);
    }
    
    String name = (String) dataMap.get("name");
    if (StringUtils.isBlank(name)) {
      tr.addFailure("名称要求必填，但实际为空！！！", name);
    }
    
    String beginOnValue = (String) dataMap.get("beginOn");
    if (StringUtils.isNotBlank(beginOnValue)) {
      if (!DateUtils.isValidDate(beginOnValue, "yyyy-MM-dd")) {
        tr.addFailure("启用日期要求格式为 yyyy-MM-dd，但是实际右边的格式！！！", beginOnValue);
      }
    }
    
    String endOnValue = (String) dataMap.get("endOn");
    if (StringUtils.isNotBlank(endOnValue)) {
      if (!DateUtils.isValidDate(endOnValue, "yyyy-MM-dd")) {
        tr.addFailure("截止日期要求格式为 yyyy-MM-dd，但是实际右边的格式！！！", endOnValue);
      }
    }
    
    java.sql.Date nowAt = new java.sql.Date(System.currentTimeMillis());
    if (!tr.hasErrors()) {
      java.sql.Date beginOn = nowAt;
      java.sql.Date endOn = null;
      if (StringUtils.isNotBlank(beginOnValue) && StringUtils.isNotBlank(endOnValue)) {
        beginOn = DateUtils.toSqlDate(beginOnValue, "yyyy-MM-dd");
        endOn = DateUtils.toSqlDate(endOnValue, "yyyy-MM-dd");
        if (endOn.before(beginOn)) {
          tr.addFailure("启用日期不能晚于截止日期！！！", "启用日期：" + beginOnValue + "，截止日期：" + endOnValue);;
        }
      } else if (StringUtils.isBlank(beginOnValue) && StringUtils.isNotBlank(endOnValue)) {
        endOn = DateUtils.toSqlDate(endOnValue, "yyyy-MM-dd");
        if (endOn.before(nowAt)) {
          tr.addFailure("截止日期不能早于今天！！！", "截止日期：" + endOnValue);;
        }
      } else {
        dataMap.put("beginOn", beginOn);
        dataMap.put("endOn", endOn);
      }
    }
    
    itemStartExtra(tr);
    
    if (!tr.hasErrors()) {
      try {
        List<E> entities = entityDao.get(entityType, "code", code);
        
        E entity = null;
        if (entities.isEmpty()) {
          entity = entityType.newInstance();
          BeanUtils.setProperty(entity, "code", code);
        } else {
          entity = entities.get(0);
        }
        BeanUtils.setProperty(entity, "name", name);
        BeanUtils.setProperty(entity, "beginOn", dataMap.get("beginOn"));
        BeanUtils.setProperty(entity, "endOn", dataMap.get("endOn"));
        BeanUtils.setProperty(entity, "updatedAt", nowAt);
        settingPropertyExtraInEntity(entity);
        importer.setCurrent(entity);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  protected void itemStartExtra(TransferResult tr) {
    ;
  }
  
  protected void settingPropertyExtraInEntity(E entity) {
    ;
  }

  protected void itemBeforeSave(TransferResult tr) {
    ;
  }
}
