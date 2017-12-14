/**
 * 
 */
package org.openurp.basis.edu.base.code.service;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.transfer.TransferMessage;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.listener.ItemImporterListener;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public abstract class IdentificationAppImporterListener extends ItemImporterListener {
  
  protected EntityDao entityDao;
  
  protected final List<TransferMessage> tmpList = CollectUtils.newArrayList();
  
  private IdentificationAppImporterListener() {
    super();
  }
  
  public IdentificationAppImporterListener(EntityDao entityDao) {
    this();
    this.entityDao = entityDao;
  }
  
  @Override
  public final void onItemStart(TransferResult tr) {
    itemStart(tr);
    
    // 为了记数
    tmpList.addAll(tr.getErrs());
    tr.getErrs().clear();
    // 为了绕过转换
    importer.getCurData().clear();
  }

  protected abstract void itemStart(TransferResult tr);
  
  @Override
  public final void onItemFinish(TransferResult tr) {
    tr.getErrs().addAll(tmpList);
    
    if (!tr.hasErrors()) {
      itemBeforeSave(tr);
      entityDao.saveOrUpdate(importer.getCurrent());
    }
  }
  
  protected abstract void itemBeforeSave(TransferResult tr);
}
