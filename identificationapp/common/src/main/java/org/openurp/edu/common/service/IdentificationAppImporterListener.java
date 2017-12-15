package org.openurp.edu.common.service;

import java.io.Serializable;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.transfer.TransferMessage;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.IllegalImportFormatException;
import org.beangle.commons.transfer.importer.listener.ItemImporterListener;
import org.openurp.edu.common.utils.ImporterValidity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public abstract class IdentificationAppImporterListener<ID extends Serializable, E extends Entity<ID>> extends ItemImporterListener {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  protected EntityDao entityDao;
  
  protected ImporterValidity validaty;
  
  private List<TransferMessage> tmpList;
  
  private int errors = 0;
  
  private IdentificationAppImporterListener() {
    super();
  }
  
  public IdentificationAppImporterListener(EntityDao entityDao) {
    this();
    this.entityDao = entityDao;
  }
  
  @Override
  public final void onItemStart(TransferResult tr) {
    logger.debug(importer.getCurData().toString());
    validaty = ImporterValidity.newInstance(tr, importer, entityDao);
    tmpList = CollectUtils.newArrayList();
    errors = tr.errors();
    
    if (!beforeItemStart()) {
      StringBuilder errMsg = new StringBuilder();
      for (TransferMessage msg : tr.getErrs()) {
        if (errMsg.length() > 0) {
          errMsg.append("<br>");
        }
        errMsg.append(msg.getMessage());
      }
      tr.getErrs().clear();
      throw new IllegalImportFormatException(errMsg.toString());
    } else {
      itemStart();
      
      // 为了记数
      tmpList.addAll(tr.getErrs());
      tr.getErrs().clear();
      // 为了绕过转换
      importer.getCurData().clear();
    }
  }
  
  protected abstract boolean beforeItemStart();
  
  protected abstract void itemStart();
  
  @Override
  public final void onItemFinish(TransferResult tr) {
    tr.getErrs().addAll(tmpList);
    
    logger.debug(tr.errors() + "");
    if (!hasError()) {
      itemBeforeSave(tr);
      entityDao.saveOrUpdate(importer.getCurrent());
      logger.debug("save successful!!!");
    }
  }
  
  protected final boolean hasError() {
    return validaty.errors() != errors;
  }
  
  protected abstract void itemBeforeSave(TransferResult tr);
}