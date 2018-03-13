package org.openurp.edu.extern.identification.code.web.action;

import java.io.Serializable;
import java.util.Date;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.openurp.basis.edu.base.code.web.action.ExternBaseAction;
import org.openurp.code.geo.model.Division;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamTime;
import org.openurp.edu.extern.identification.model.Certificate;

/**
 * @author zhouqi 2018年1月21日
 *
 */
public abstract class CertificationSupportAction<ID extends Serializable, E extends Entity<ID>> extends ExternBaseAction<ID, E> {
  
  public String loadCertTypesAjax() {
    OqlBuilder<CertificateType> builder = OqlBuilder.from(CertificateType.class, "certType");
    StringBuilder where = new StringBuilder();
    where.append("exists (");
    where.append("  from ").append(Certificate.class.getName()).append(" certificate");
    where.append(" where certificate.type = certType");
    where.append("   and certificate.type.examSubject.id = :id");
    where.append(")");
    builder.where(where.toString(), getInt("id"));
    builder.where("certType.beginOn <= :now");
    builder.where("certType.endOn is null or certType.endOn >= :now", new Date());
    put("certTypes", entityDao.search(builder));
    
    return forward();
  }
  
  /**
   * FIXME 2018-02-26 zhouqi 还没有完成
   * @return
   */
  public String loadCertLevelsAjax() {
    OqlBuilder<CertificateLevel> builder = OqlBuilder.from(CertificateLevel.class, "certLevel");
    StringBuilder where = new StringBuilder();
    where.append("exists (");
    where.append("  from ").append(Certificate.class.getName()).append(" certificate");
    where.append(" where certificate.level = certLevel");
    where.append("   and certificate.level.id = :id");
    where.append(")");
    builder.where(where.toString(), getInt("id"));
    builder.where("certLevel.beginOn <= :now");
    builder.where("certLevel.endOn is null or certType.endOn >= :now", new Date());
    put("certLevels", entityDao.search(builder));
    
    return forward();
  }
  
  public String loadDivisionsAjax() {
    OqlBuilder<Division> builder = OqlBuilder.from(Division.class, "division");
    StringBuilder where = new StringBuilder();
    where.append("exists (");
    where.append("  from ").append(Certificate.class.getName()).append(" certificate");
    where.append(" where certificate.division = division");
    where.append("   and certificate.type.id = :id");
    where.append(")");
    builder.where(where.toString(), getInt("id"));
    builder.where("division.code like '__0000'");
    builder.where("division.beginOn <= :now");
    builder.where("division.endOn is null or division.endOn >= :now", new Date());
    put("divisions", entityDao.search(builder));
    
    return forward();
  }
  
  public String loadExamTimesAjax() {
    Integer certTypeId = getIntId("certType");
    Integer id = getInt("id");
    
    OqlBuilder<ExternExamTime> builder = OqlBuilder.from(ExternExamTime.class, "examTime");
    StringBuilder where = new StringBuilder();
    where.append("exists (");
    where.append("  from ").append(Certificate.class.getName()).append(" certificate");
    where.append(" where certificate.examTime = examTime");
    where.append("   and certificate.type.id = :certTypeId");
    if (null != id) {
      where.append("   and certificate.division.id = :id");
    }
    where.append(")");
    builder.where(where.toString(), certTypeId, id);
    builder.where("examTime.beginOn <= :now");
    builder.where("examTime.endOn is null or examTime.endOn >= :now", new Date());
    put("examTimes", entityDao.search(builder));
    
    return forward();
  }
}
