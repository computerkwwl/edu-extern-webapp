package org.openurp.edu.extern.identification.code.web.action;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.openurp.basis.edu.base.code.web.action.ExternBaseAction;
import org.openurp.code.geo.model.Division;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamSubject;
import org.openurp.edu.base.code.model.ExternExamTime;
import org.openurp.edu.extern.identification.model.Certificate;

/**
 * @author zhouqi 2018年1月21日
 *
 */
public abstract class CertificationSupportAction<ID extends Serializable, E extends Entity<ID>> extends ExternBaseAction<ID, E> {
  
  private final String EXAM_SUBJECT = "examSubject";
  
  private final String CERT_TYPE = "certType";
  
  private final String CERT_LEVEL = "certLevel";
  
  private final String DIVISION = "division";
  
  private final Map<String, Integer> dataFromMap = initDataFromMap();
  
  private Map<String, Integer> initDataFromMap() {
    Map<String, Integer> map = CollectUtils.newHashMap();
    map.put(EXAM_SUBJECT, 1);
    map.put(CERT_TYPE, 2);
    map.put(CERT_LEVEL, 3);
    map.put(DIVISION, 4);
    return map;
  }
  
  protected void indexSetting() {
    put("examSubjects", codeService.getCodes(ExternExamSubject.class));
  }
  
  protected void extraEdit(ID id) {
    indexSetting();
  }
  
  public String dataAjax() {
    String from = get("from");
    
    Integer examSubjectId = getIntId("examSubject");
    Integer certTypeId = getIntId("certType");
    Integer certLevelId = getIntId("certLevel");
    Integer divisionId = getIntId("division");
    
    switch (dataFromMap.get(from)) {
      case 1: {
        OqlBuilder<CertificateType> builder = OqlBuilder.from(CertificateType.class, "certType");
        if (null != examSubjectId) {
          StringBuilder where = new StringBuilder();
          where.append("exists (");
          where.append("  from ").append(Certificate.class.getName()).append(" certificate");
          where.append(" where certificate.type = certType");
          where.append("   and certificate.type.examSubject.id = :id");
          where.append(")");
          builder.where(where.toString(), examSubjectId);
        }
        builder.where("certType.beginOn <= :now");
        builder.where("certType.endOn is null or certType.endOn >= :now", new Date());
        put("certTypes", entityDao.search(builder));
      }
      case 2: {
        OqlBuilder<CertificateLevel> builder = OqlBuilder.from(CertificateLevel.class, "certLevel");
        StringBuilder where = new StringBuilder();
        where.append("exists (");
        where.append("  from ").append(Certificate.class.getName()).append(" certificate");
        where.append(" where certificate.level = certLevel");
        if (null == certTypeId) {
          where.append("   and certificate.type.examSubject.id = :examSubjectId");
        } else {
          where.append("   and certificate.type.id = :certTypeId");
        }
        where.append(")");
        if (null == certTypeId) {
          builder.where(where.toString(), examSubjectId);
        } else {
          builder.where(where.toString(), certTypeId);
        }
        builder.where("certLevel.beginOn <= :now");
        builder.where("certLevel.endOn is null or certLevel.endOn >= :now", new Date());
        List<CertificateLevel> certLevels = entityDao.search(builder);
        if (CollectionUtils.isEmpty(certLevels)) {
          certLevels = codeService.getCodes(CertificateLevel.class);
        }
        put("certLevels", certLevels);
      }
      case 3: {
        OqlBuilder<Division> builder = OqlBuilder.from(Division.class, "division");
        StringBuilder where = new StringBuilder();
        List<Object> params = CollectUtils.newArrayList();
        where.append("exists (");
        where.append("  from ").append(Certificate.class.getName()).append(" certificate");
        where.append(" where certificate.division = division");
        where.append("   and certificate.type.examSubject.id = :examSubjectId");
        params.add(examSubjectId);
        if (null != certTypeId) {
          where.append("   and certificate.type.id = :certTypeId");
          params.add(certTypeId);
        }
        if (null != certLevelId) {
          where.append("   and certificate.level.id = :certLevelId");
          params.add(certLevelId);
        }
        where.append(")");
        Condition condition = new Condition(where.toString());
        condition.params(params);
        builder.where(condition);
        builder.where("division.code like '__0000'");
        builder.where("division.beginOn <= :now");
        builder.where("division.endOn is null or division.endOn >= :now", new Date());
        List<Division> divisions = entityDao.search(builder);
        if (CollectionUtils.isEmpty(divisions)) {
          divisions = entityDao.search(OqlBuilder.from(Division.class, "division").where("division.code like '__0000'").where("division.beginOn <= :now and (division.endOn is null or division.endOn >= :now)", new Date()));
        }
        put("divisions", divisions);
      }
      default: {
        OqlBuilder<ExternExamTime> builder = OqlBuilder.from(ExternExamTime.class, "examTime");
        StringBuilder where = new StringBuilder();
        List<Object> params = CollectUtils.newArrayList();
        where.append("exists (");
        where.append("  from ").append(Certificate.class.getName()).append(" certificate");
        where.append(" where certificate.examTime = examTime");
        where.append("   and certificate.type.examSubject.id = :examSubjectId");
        params.add(examSubjectId);
        if (null != certTypeId) {
          where.append("   and certificate.type.id = :certTypeId");
          params.add(certTypeId);
        }
        if (null != certLevelId) {
          where.append("   and certificate.level.id = :certLevelId");
          params.add(certLevelId);
        }
        if (null != divisionId) {
          where.append("   and certificate.division.id = :id");
          params.add(divisionId);
        }
        where.append(")");
        Condition condition = new Condition(where.toString());
        condition.params(params);
        builder.where(condition);
        builder.where("examTime.beginOn <= :now");
        builder.where("examTime.endOn is null or examTime.endOn >= :now", new Date());
        List<ExternExamTime> examTimes = entityDao.search(builder);
        if (CollectionUtils.isEmpty(examTimes)) {
          examTimes = codeService.getCodes(ExternExamTime.class);
        }
        put("examTimes", examTimes);
      }
    }
    
    switch (dataFromMap.get(from)) {
      case 1:
        return "/component/certificate/dataFromExamSubjectAjax";
      case 2:
        return "/component/certificate/dataFromCertTypeAjax";
      case 3:
        return "/component/certificate/dataFromCertLevelAjax";
      default:
        return "/component/certificate/dataFromDivsionAjax";
    }
  }
}
