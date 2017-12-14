package org.openurp.edu.extern.identification.code.service;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.TransferResult;
import org.openurp.edu.common.service.IdentificationAppImporterListener;
import org.openurp.edu.common.utils.ImporterValidity;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public abstract class IdentificationAppIdentificateImporterListener<T extends Entity<Integer>> extends IdentificationAppImporterListener<Integer, T> {
  
  protected ImporterValidity validaty;
  
  public IdentificationAppIdentificateImporterListener(EntityDao entityDao) {
    super(entityDao);
  }
  
  protected void itemStart(TransferResult tr) {
    validaty = ImporterValidity.newInstance(tr, importer, entityDao);
    
    itemStartExtra();
    
    if (!tr.hasErrors()) {
      try {
        T entity = loadExistedEntity();
        settingPropertyExtraInEntity(entity);
        importer.setCurrent(entity);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  protected abstract T loadExistedEntity();
  
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
