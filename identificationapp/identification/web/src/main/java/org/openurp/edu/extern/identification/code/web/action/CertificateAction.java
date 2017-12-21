package org.openurp.edu.extern.identification.code.web.action;

import java.util.Date;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.openurp.basis.edu.base.code.web.action.ExternBaseAction;
import org.openurp.code.geo.model.Division;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamSubject;
import org.openurp.edu.base.code.model.ExternExamTime;
import org.openurp.edu.extern.identification.code.service.CertificateImporter;
import org.openurp.edu.extern.identification.model.Certificate;

/**
 * @author zhouqi 2017年12月13日
 *
 */
public class CertificateAction extends ExternBaseAction<Integer, Certificate> {
  
  protected void indexSetting() {
    put("subjects", codeService.getCodes(ExternExamSubject.class));
    put("types", codeService.getCodes(CertificateType.class));
    put("divisions", entityDao.search(OqlBuilder.from(Division.class, "division").where("division.code like '__0000'").where("division.beginOn <= :now and (division.endOn is null or division.endOn >= :now)", new Date())));
    put("times", codeService.getCodes(ExternExamTime.class));
    // put("educations", codeService.getCodes(Education.class));
    // put("stdTypes", codeService.getCodes(StdType.class));
    // put("departments", entityDao.getAll(Department.class));
  }
  
  protected void settingOtherInSearch(OqlBuilder<Certificate> builder) {
    Date nowAt = new Date();
    builder.where(entityName + ".beginOn <= :nowAt", nowAt);
    builder.where(entityName + ".endOn is null or " + entityName + ".endOn >= :nowAt", nowAt);
  }
  
  protected void extraEdit(Integer id) {
    indexSetting();
  }
  
  public String checkAjax() {
    Integer id = getInt("id");
    Integer typeId = getIntId("type");
    Integer divisionId = getIntId("division");
    Integer examTimeId = getIntId("examTime");
    
    OqlBuilder<Certificate> builder = OqlBuilder.from(entityType, entityName);
    if (null != id) {
      builder.where(entityName + ".id != :id", id);
    }
    builder.where(entityName + ".type.id = :typeId", typeId);
    if (null == divisionId) {
      builder.where(entityName + ".division.id is null");
    } else {
      builder.where(entityName + ".division.id = :divisionId", divisionId);
    }
    builder.where(entityName + ".examTime.id = :examTimeId", examTimeId);
    put("isOk", entityDao.search(builder).isEmpty());
    
    return forward();
  }
  
  protected void configImporter(EntityImporter importer) {
    importer.addListener(new CertificateImporter(entityDao));
  }
}
