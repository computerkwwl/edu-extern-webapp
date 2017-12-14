/**
 * 
 */
package org.openurp.basis.edu.base.code.service;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.transfer.TransferResult;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamSubject;

/**
 * @author zhouqi 2017年12月13日
 *
 */
public class CertificateTypeImporter extends IdentificationAppBaseCodeImporterListener<Integer, CertificateType> {
  
  public CertificateTypeImporter(EntityDao entityDao) {
    super(entityDao);
  }
  
  protected void itemStartExtra(TransferResult tr) {
    validaty.checkCode("level.code", "级别代码", CertificateLevel.class);
    
    if (!validaty.checkMustBe("examSubject.code", "证书大类代码")) {
      validaty.checkCode("examSubject.code", "证书大类代码", ExternExamSubject.class);
    }
  }
  
  protected void settingPropertyExtraInEntity(CertificateType type) {
    type.setLevel((CertificateLevel) importer.getCurData().get("level.code"));
    type.setExamSubject((ExternExamSubject) importer.getCurData().get("examSubject.code"));
  }
}
