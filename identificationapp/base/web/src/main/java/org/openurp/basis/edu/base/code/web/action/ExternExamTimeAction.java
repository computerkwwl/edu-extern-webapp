package org.openurp.basis.edu.base.code.web.action;

import java.util.Date;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.openurp.basis.edu.base.code.service.ExternExamTimeImporter;
import org.openurp.edu.base.code.model.ExternExamTime;



/**
 * @author zhouqi 2017年12月12日
 *
 */
public class ExternExamTimeAction extends ExternBaseAction<Integer, ExternExamTime> {
  
  protected String entityName() {
    return "examTime";
  }
  
  protected void settingOtherInSearch(OqlBuilder<ExternExamTime> builder) {
    Date nowAt = new Date();
    builder.where(entityName + ".beginOn <= :nowAt", nowAt);
    builder.where(entityName + ".endOn is null or " + entityName + ".endOn >= :nowAt", nowAt);
  }
  
  protected void configImporter(EntityImporter importer) {
    importer.addListener(new ExternExamTimeImporter(entityDao));
  }
}
