package org.openurp.basis.edu.base.code.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.TransferResult;
import org.openurp.edu.common.service.IdentificationAppImporterListener;
import org.openurp.edu.common.utils.ImporterValidity;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public abstract class IdentificationAppBaseCodeImporterListener<ID extends Serializable, E extends Entity<ID>> extends IdentificationAppImporterListener {
  
  private final Class<E> entityType = entityType();
  
  protected ImporterValidity validaty;
  
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
    
    validaty = ImporterValidity.newInstance(tr, importer, entityDao);
    validaty.checkMustBe("code", "代码");
    validaty.checkMustBe("name", "名称");
    
    validaty.checkDate("endOn", "截止日期", "yyyy-MM-dd");
    validaty.checkDateBetween("beginOn", "启用日期", "endOn", "截止日期", "yyyy-MM-dd");
    
    itemStartExtra(tr);
    
    if (!tr.hasErrors()) {
      try {
        List<E> entities = entityDao.get(entityType, "code", dataMap.get("code"));
        
        E entity = null;
        if (entities.isEmpty()) {
          entity = entityType.newInstance();
          BeanUtils.setProperty(entity, "code", dataMap.get("code"));
        } else {
          entity = entities.get(0);
        }
        BeanUtils.setProperty(entity, "name", dataMap.get("name"));
        BeanUtils.setProperty(entity, "beginOn", dataMap.get("beginOn"));
        BeanUtils.setProperty(entity, "endOn", dataMap.get("endOn"));
        BeanUtils.setProperty(entity, "updatedAt", validaty.nowAt);
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
