package org.openurp.edu.extern.identification.code.web.action;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.basis.edu.base.code.web.action.ExternBaseAction;
import org.openurp.code.geo.model.Division;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamSubject;
import org.openurp.edu.base.code.model.ExternExamTime;
import org.openurp.edu.base.model.Course;
import org.openurp.edu.extern.identification.model.CertScore;
import org.openurp.edu.extern.identification.model.Certificate;

/**
 * @author zhouqi 2018年1月20日
 *
 */
public class CertScoreAction extends ExternBaseAction<Integer, CertScore> {
  
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
    put("certTypes", codeService.getCodes(CertificateType.class));
    put("certLevels", codeService.getCodes(CertificateLevel.class));
    put("divisions", entityDao.search(OqlBuilder.from(Division.class, "division").where("division.code like '__0000'").where("division.beginOn <= :now and (division.endOn is null or division.endOn >= :now)", new Date())));
    put("examTimes", codeService.getCodes(ExternExamTime.class));
  }
  
  protected void extraEdit(Integer id) {
    put("examSubjects", codeService.getCodes(ExternExamSubject.class));
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
        put("certLevels", entityDao.search(builder));
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
        put("examTimes", entityDao.search(builder));
      }
    }
    
    switch (dataFromMap.get(from)) {
      case 1:
        return "dataFromExamSubjectAjax";
      case 2:
        return "dataFromCertTypeAjax";
      case 3:
        return "dataFromCertLevelAjax";
      default:
        return "dataFromDivsionAjax";
    }
  }
  
  public String coursesAjax() {
    OqlBuilder<Course> builder = OqlBuilder.from(Course.class, "course");
    builder.where("course.code like '%' || :term || '%' or course.name like '%' || :term || '%'", get("term"));
    builder.limit(getPageLimit());
    put("courses", entityDao.search(builder));
    return forward();
  }

  public String checkAjax() {
    Integer id = getInt("id");
    Integer examSubjectId = getIntId("examSubject");
    Integer certTypeId = getIntId("certType");
    Integer certLevelId = getIntId("certLevel");
    Integer divisionId = getIntId("division");
    Integer examTimeId = getIntId("examTime");
    
    OqlBuilder<CertScore> builder = OqlBuilder.from(CertScore.class, "certScore");
    if (null != id) {
      builder.where("certScore.id != :id", id);
    }
    builder.where("certScore.examSubject.id = :examSubjectId", examSubjectId);
    if (null != certTypeId) {
      builder.where("certScore.certType.id = :certTypeId or certScore.certType is null", certTypeId);
    }
    if (null != certLevelId) {
      builder.where("certScore.certLevel.id = :certLevelId or certScore.certLevel is null", certLevelId);
    }
    if (null != divisionId) {
      builder.where("certScore.division.id = :divisionId or certScore.division is null", divisionId);
    }
    if (null != examTimeId) {
      builder.where("certScore.examTime.id = :examTimeId or certScore.examTime is null", examTimeId);
    }
    put("isOk", entityDao.search(builder).isEmpty());
    
    return forward();
  }
  
  protected void beforeSave(CertScore certScore) {
    // 如果“得分显示”不填，则表示其值即“本校得分”
    if (StringUtils.isBlank(certScore.getScoreValue())) {
      certScore.setScoreValue(new DecimalFormat("0.####").format(certScore.getScore()));
    }
    
    certScore.clearCourses();
    certScore.addCourses(entityDao.get(Course.class, getLongIds("course")));
    
    super.beforeSave(certScore);
  }
}
