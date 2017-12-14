/**
 * 
 */
package org.openurp.basis.edu.base.code.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.transfer.TransferResult;
import org.openurp.edu.base.code.model.CertificateLevel;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamSubject;

/**
 * @author zhouqi 2017年12月13日
 *
 */
public class CertificateTypeImporter extends IdentificationAppBaseCodeImporterListener<Integer, CertificateType> {
  
  private CertificateLevel level;
  
  private ExternExamSubject examSubject;
  
  public CertificateTypeImporter(EntityDao entityDao) {
    super(entityDao);
  }
  
  protected void itemStartExtra(TransferResult tr) {
    String levelCode = (String) importer.getCurData().get("level.code");
    if (StringUtils.isNotBlank(levelCode)) {
      List<CertificateLevel> levels = entityDao.get(CertificateLevel.class, "code", levelCode);
      if (levels.isEmpty()) {
        tr.addFailure("级别代码不正确，原因没有找到对应的记录！！！", levelCode);
      } else {
        level = levels.get(0);
      }
    }
    
    String examSubjectCode = (String) importer.getCurData().get("examSubject.code");
    if (StringUtils.isBlank(examSubjectCode)) {
      tr.addFailure("证书大类代码要求必填，但是实际为空！！！", examSubjectCode);
    } else {
      List<ExternExamSubject> examSubjects = entityDao.get(ExternExamSubject.class, "code", examSubjectCode);
      if (examSubjects.isEmpty()) {
        tr.addFailure("证书大楼代码不正确，原因没有找到对应的记录！！！", examSubjectCode);
      } else {
        examSubject = examSubjects.get(0);
      }
    }
  }
  
  protected void settingPropertyExtraInEntity(CertificateType type) {
    type.setLevel(level);
    type.setExamSubject(examSubject);
  }
}
