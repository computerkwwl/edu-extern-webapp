/**
 * 
 */
package org.openurp.edu.extern.identification.code.service;

import java.util.List;

import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.code.geo.model.Division;
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
  
  protected void itemStartExtra() {
    // 下面 3 个字段是主键
    if (validaty.checkMustBe("type.code", "证书类型代码")) {
      validaty.checkCode("type.code", "证书类型代码", CertificateType.class);
    }
    validaty.checkCode("division.code", "报考省份代码", Division.class);
    if (validaty.checkMustBe("examTime.code", "报考时间代码")) {
      validaty.checkCode("examTime.code", "报考时间代码", ExternExamTime.class);
    }
    
    if (!validaty.hasError()) {
      Certificate certificate = loadCertificate();
      
      if (null == certificate.getId()) {
        if (validaty.checkDate("beginOn", "启用日期", "yyyy-MM-dd", true)
            && validaty.checkDate("endOn", "截止日期", "yyyy-MM-dd", false)) {
          validaty.checkDateBetween("beginOn", "启用日期", "endOn", "截止日期", "yyyy-MM-dd");
        }
      }
      if (!validaty.hasError()) {
        importer.getCurData().put("certificate", certificate);
      }
    }
  }
  
  private Certificate loadCertificate() {
    CertificateType type = (CertificateType) importer.getCurData().get("type.code");
    Division division = (Division) importer.getCurData().get("division.code");
    ExternExamTime examTime = (ExternExamTime) importer.getCurData().get("examTime.code");
    OqlBuilder<Certificate> builder = OqlBuilder.from(Certificate.class, "certificate");
    builder.where("certificate.type = :type", type);
    if (null == division) {
      ;
      builder.where("certificate.division is null");
    } else {
      builder.where("certificate.division = :division", division);
    }
    builder.where("certificate.examTime = :examTime", examTime);
    List<Certificate> certificates = entityDao.search(builder);
    Certificate certificate = null;
    if (certificates.isEmpty()) {
      certificate = new Certificate();
    } else {
      certificate = certificates.get(0);
    }
    return certificate;
  }
  
  protected Certificate loadExistedEntity() {
    return (Certificate) importer.getCurData().get("certificate");
  }
  
  protected void settingPropertyExtraInEntity(Certificate certificate) {
    BeanUtils.setPropertyIfNotNull(certificate, "type", importer.getCurData().get("type.code"));
    BeanUtils.setPropertyIfNotNull(certificate, "division", importer.getCurData().get("division.code"));
    BeanUtils.setPropertyIfNotNull(certificate, "examTime", importer.getCurData().get("examTime.code"));
    BeanUtils.setPropertyIfNotNull(certificate, "beginOn", importer.getCurData().get("beginOn"));
    BeanUtils.setPropertyIfNotNull(certificate, "endOn", importer.getCurData().get("endOn"));
    certificate.setUpdatedAt(validaty.getNowAt());
  }
}
