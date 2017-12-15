/**
 * 
 */
package org.openurp.basis.edu.base.code.service;

import org.beangle.commons.dao.EntityDao;
import org.openurp.edu.base.code.model.ExternExamSubject;

/**
 * @author zhouqi 2017年12月13日
 *
 */
public class ExternExamSubjectImporter extends IdentificationAppBaseCodeImporterListener<ExternExamSubject> {
  
  public ExternExamSubjectImporter(EntityDao entityDao) {
    super(entityDao);
  }
  
  @Override
  protected boolean beforeItemStart() {
    return validaty.checkTemplate("code", "name", "beginOn", "endOn");
  }
}
