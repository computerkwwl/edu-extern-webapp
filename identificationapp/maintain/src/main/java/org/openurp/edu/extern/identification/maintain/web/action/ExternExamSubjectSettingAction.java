package org.openurp.edu.extern.identification.maintain.web.action;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.basis.edu.base.code.web.action.ExternBaseAction;
import org.openurp.edu.base.code.model.ExternExamSubject;
import org.openurp.edu.extern.identification.model.ExternExamSubjectSetting;

/**
 * @author zhouqi 2017年12月17日
 *
 */
public class ExternExamSubjectSettingAction extends ExternBaseAction<Integer, ExternExamSubjectSetting> {
  
  protected String entityName() {
    return "setting";
  }
  
  protected void indexSetting() {
    put("examSubjects", codeService.getCodes(ExternExamSubject.class));
  }
  
  protected void extraEdit(Integer id) {
    put("examSubjects", entityDao.search(OqlBuilder.from(ExternExamSubject.class, "examSubject").where("not exists (from "
        + entityType.getName()
        + " setting where setting.examSubject = examSubject and "
        + (null == id ? "1 = 1" : "setting.id != :settingId") + ")", id)));
  }
  
  public String checkAjax() {
    Integer id = getInt("id");
    Integer examSubjectId = getIntId("examSubject");
    
    OqlBuilder<ExternExamSubjectSetting> builder = OqlBuilder.from(entityType, entityName);
    if (null != id) {
      builder.where(entityName + ".id != :id", id);
    }
    builder.where(entityName + ".examSubject.id = :examSubjectId", examSubjectId);
    put("isOk", entityDao.search(builder).isEmpty());
    
    return forward();
  }
  
  public void beforeSave(ExternExamSubjectSetting setting) {
    setting.setKeys(StringUtils.join(getAll("keys", String.class), ","));
  }
}
