/**
 * 
 */
package org.openurp.basis.edu.base.code.service;

import org.beangle.commons.dao.EntityDao;
import org.openurp.edu.base.code.model.ExternExamTime;

/**
 * @author zhouqi 2017年12月13日
 *
 */
public class ExternExamTimeImporter extends IdentificationAppBaseCodeImporterListener<Integer, ExternExamTime> {
  
  public ExternExamTimeImporter(EntityDao entityDao) {
    super(entityDao);
  }
}
