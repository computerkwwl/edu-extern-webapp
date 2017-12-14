package org.openurp.basis.edu.base.code.web.action;

import java.util.Date;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.openurp.basis.edu.base.code.service.ExternExamSubjectImporter;
import org.openurp.edu.base.code.model.ExternExamSubject;

/**
 * @author zhouqi 2017年12月11日
 *
 */
public class ExternExamSubjectAction extends ExternBaseAction<Integer, ExternExamSubject> {
  
  protected String entityName() {
    return "examSubject";
  }
  
  protected void settingOtherInSearch(OqlBuilder<ExternExamSubject> builder) {
    Date nowAt = new Date();
    builder.where(entityName + ".beginOn <= :nowAt", nowAt);
    builder.where(entityName + ".endOn is null or " + entityName + ".endOn >= :nowAt", nowAt);
  }
  
  protected void configImporter(EntityImporter importer) {
    importer.addListener(new ExternExamSubjectImporter(entityDao));
  }
}
