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
public class CertificateLevelImporter extends IdentificationAppBaseCodeImporterListener<Integer, CertificateLevel> {
  
  public CertificateLevelImporter(EntityDao entityDao) {
    super(entityDao);
  }
}
