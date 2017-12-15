/**
 * 
 */
package org.openurp.basis.edu.base.code.service;

import org.beangle.commons.dao.EntityDao;
import org.openurp.edu.base.code.model.CertificateLevel;

/**
 * @author zhouqi 2017年12月13日
 *
 */
public class CertificateLevelImporter extends IdentificationAppBaseCodeImporterListener<CertificateLevel> {
  
  public CertificateLevelImporter(EntityDao entityDao) {
    super(entityDao);
  }

  @Override
  protected boolean beforeItemStart() {
    return validaty.checkTemplate("code", "name", "beginOn", "endOn");
  }
}
