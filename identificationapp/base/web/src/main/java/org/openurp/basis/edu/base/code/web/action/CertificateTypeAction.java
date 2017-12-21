/**
 * 
 */
package org.openurp.basis.edu.base.code.web.action;

import java.util.Date;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.openurp.basis.edu.base.code.service.CertificateTypeImporter;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamSubject;

/**
 * @author zhouqi 2017年12月8日
 *
 */
public class CertificateTypeAction extends ExternBaseAction<Integer, CertificateType> {
  
  protected String entityName() {
    return "type";
  }
  
  protected void indexSetting() {
    put("levels", codeService.getCodes(CertificateLevel.class));
    put("examSubjects", codeService.getCodes(ExternExamSubject.class));
  }
  
  protected void settingOtherInSearch(OqlBuilder<CertificateType> builder) {
    Date nowAt = new Date();
    builder.where(entityName + ".beginOn <= :nowAt", nowAt);
    builder.where(entityName + ".endOn is null or " + entityName + ".endOn >= :nowAt", nowAt);
  }
  
  protected void extraEdit(Integer id) {
    indexSetting();
  }
  
  protected void configImporter(EntityImporter importer) {
    importer.addListener(new CertificateTypeImporter(entityDao));
  }
}
