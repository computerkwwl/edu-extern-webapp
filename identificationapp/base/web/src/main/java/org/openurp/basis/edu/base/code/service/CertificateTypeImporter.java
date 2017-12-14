/**
 * 
 */
package org.openurp.basis.edu.base.code.service;

import org.beangle.commons.dao.EntityDao;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamSubject;
import org.openurp.edu.common.utils.BeanUtils;

/**
 * @author zhouqi 2017年12月13日
 *
 */
public class CertificateTypeImporter extends IdentificationAppBaseCodeImporterListener<CertificateType> {
  
  public CertificateTypeImporter(EntityDao entityDao) {
    super(entityDao);
  }
  
  protected void itemStartExtra() {
    validaty.checkCode("level.code", "级别代码", CertificateLevel.class);
    
    if (null != importer.getCurData().get("CertificateType") || validaty.checkMustBe("examSubject.code", "证书大类代码")) {
      validaty.checkCode("examSubject.code", "证书大类代码", ExternExamSubject.class);
    }
  }
  
  protected void settingPropertyExtraInEntity(CertificateType type) {
    BeanUtils.setPropertyIfNotNull(type, "level", importer.getCurData().get("level.code"));
    BeanUtils.setPropertyIfNotNull(type, "examSubject", importer.getCurData().get("examSubject.code"));
  }
}
