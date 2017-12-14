/**
 * 
 */
package org.openurp.basis.edu.base.code.web.action;

import java.util.Date;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.openurp.basis.edu.base.code.service.CertificateLevelImporter;
import org.openurp.edu.base.code.model.CertificateLevel;

/**
 * @author zhouqi 2017年12月11日
 *
 */
public class CertificateLevelAction extends ExternBaseAction<Integer, CertificateLevel> {
  
  protected String entityName() {
    return "level";
  }
  
  protected void settingOtherInSearch(OqlBuilder<CertificateLevel> builder) {
    Date nowAt = new Date();
    builder.where(entityName + ".beginOn <= :nowAt", nowAt);
    builder.where(entityName + ".endOn is null or " + entityName + ".endOn >= :nowAt", nowAt);
  }
  
  protected void configImporter(EntityImporter importer) {
    importer.addListener(new CertificateLevelImporter(entityDao));
  }
}
