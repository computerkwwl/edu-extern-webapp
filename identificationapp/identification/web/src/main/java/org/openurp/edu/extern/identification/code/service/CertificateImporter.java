/**
 * 
 */
package org.openurp.edu.extern.identification.code.service;

import java.sql.Date;
import java.util.List;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.code.geo.model.Division;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamTime;
import org.openurp.edu.common.utils.BeanUtils;
import org.openurp.edu.extern.identification.model.Certificate;

/**
 * @author zhouqi 2017年12月14日
 *
 */
public class CertificateImporter extends IdentificationAppIdentificateImporterListener<Certificate> {
  
  public CertificateImporter(EntityDao entityDao) {
    super(entityDao);
  }
  
  @Override
  protected boolean beforeItemStart() {
    return validaty.checkTemplate("code", "name", "type.code", "level.code", "division.code", "examTime.code", "beginOn", "endOn");
  }
  
  protected void itemStartExtra() {
    validaty.checkMustBe("code", "代码");
    validaty.checkMustBe("name", "名称");
    
    // 下面 3 个字段是主键
    if (validaty.checkMustBe("type.code", "证书子类/考试科目代码")) {
      validaty.checkCode("type.code", "证书子类/考试科目代码", CertificateType.class);
    }
    validaty.checkCode("level.code", "证书级别代码", CertificateLevel.class);
    validaty.checkCode("division.code", "报考省份代码", Division.class);
    if (validaty.checkMustBe("examTime.code", "报考时间代码")) {
      validaty.checkCode("examTime.code", "报考时间代码", ExternExamTime.class);
    }
    
    if (validaty.checkDate("beginOn", "启用日期", "yyyy-MM-dd", false)
        && validaty.checkDate("endOn", "截止日期", "yyyy-MM-dd", false)) {
      validaty.checkDateBetween("beginOn", "启用日期", "endOn", "截止日期", "yyyy-MM-dd");
    }
    
    if (hasError()) {
      return;
    }
    
    importer.getCurData().put("certificate", loadCertificate());
  }
  
  private Certificate loadCertificate() {
    OqlBuilder<Certificate> builder = OqlBuilder.from(Certificate.class, "certificate");
    builder.where("certificate.code = :code", importer.getCurData().get("code"));
    List<Certificate> certificates = entityDao.search(builder);
    Certificate certificate = null;
    if (certificates.isEmpty()) {
      certificate = new Certificate();
    } else {
      certificate = certificates.get(0);
    }
    
    importer.getCurData().put("certificate", certificate);
    return certificate;
  }
  
  protected Certificate loadExistedEntity() {
    return (Certificate) importer.getCurData().get("certificate");
  }
  
  protected void settingFullPropertyInEntity(Certificate certificate) throws Exception {
    BeanUtils.setPropertyIfNotNull(certificate, "code", importer.getCurData().get("code"));
    BeanUtils.setPropertyIfNotNull(certificate, "name", importer.getCurData().get("name"));
    BeanUtils.setPropertyIfNotNull(certificate, "type", importer.getCurData().get("type.code"));
    BeanUtils.setProperty(certificate, "level", importer.getCurData().get("level.code"));
    BeanUtils.setProperty(certificate, "division", importer.getCurData().get("division.code"));
    BeanUtils.setPropertyIfNotNull(certificate, "examTime", importer.getCurData().get("examTime.code"));
    BeanUtils.setPropertyIfNotNull(certificate, "beginOn", importer.getCurData().get("beginOn"));
    BeanUtils.setPropertyIfNotNull(certificate, "endOn", importer.getCurData().get("endOn"));
  }
  
  protected void settingPropertyExtraInEntity(Certificate certificate) throws Exception {
    settingFullPropertyInEntity(certificate);
    if (null == certificate.getBeginOn()) {
      certificate.setBeginOn(new Date(System.currentTimeMillis()));
    }
    certificate.setUpdatedAt(validaty.getNowAt());
  }
}
