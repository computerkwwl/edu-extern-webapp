package org.openurp.basis.edu.base.code.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.TransferResult;
import org.openurp.edu.common.service.IdentificationAppImporterListener;
import org.openurp.edu.common.utils.BeanUtils;
import org.openurp.edu.common.utils.ImporterValidity;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public abstract class IdentificationAppBaseCodeImporterListener<T extends Entity<Integer>> extends IdentificationAppImporterListener<Integer, T> {
  
  private final Class<T> entityType = entityType();
  
  protected ImporterValidity validaty;
  
  private final Class<T> entityType() {
    Type e = getClass().getGenericSuperclass();
    Type[] es = ((ParameterizedType) e).getActualTypeArguments();
    return (Class<T>) es[0];
  }
  
  public IdentificationAppBaseCodeImporterListener(EntityDao entityDao) {
    super(entityDao);
  }
  
  protected void itemStart(TransferResult tr) {
    Map<String, Object> dataMap = importer.getCurData();
    
    validaty = ImporterValidity.newInstance(tr, importer, entityDao);
    if (validaty.checkMustBe("code", "代码")) {
      List<T> entities = entityDao.get(entityType, "code", importer.getCurData().get("code"));
      if (entities.isEmpty()) {
        validaty.checkMustBe("name", "名称");
      } else {
        importer.getCurData().put(entityType.getSimpleName(), entities.get(0));
      }
    } else {
      validaty.checkMustBe("name", "名称");
    }
    
    if (validaty.checkDate("beginOn", "启用日期", "yyyy-MM-dd", true)
        && validaty.checkDate("endOn", "截止日期", "yyyy-MM-dd", false)) {
      validaty.checkDateBetween("beginOn", "启用日期", "endOn", "截止日期", "yyyy-MM-dd");
    }
    
    itemStartExtra();
    
    if (!tr.hasErrors()) {
      try {
        T entity = (T) importer.getCurData().get(entityType.getSimpleName());
        if (null == entity) {
          entity = entityType.newInstance();
        }
        BeanUtils.setPropertyIfNotNull(entity, "code", dataMap.get("code"));
        BeanUtils.setPropertyIfNotNull(entity, "name", dataMap.get("name"));
        BeanUtils.setPropertyIfNotNull(entity, "beginOn", dataMap.get("beginOn"));
        BeanUtils.setPropertyIfNotNull(entity, "endOn", dataMap.get("endOn"));
        BeanUtils.setPropertyIfNotNull(entity, "updatedAt", validaty.getNowAt());
        settingPropertyExtraInEntity(entity);
        importer.setCurrent(entity);
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }
  
  protected void itemStartExtra() {
    ;
  }
  
  protected void settingPropertyExtraInEntity(T entity) {
    ;
  }
  
  protected void itemBeforeSave(TransferResult tr) {
    ;
  }
}
