package org.openurp.edu.extern.identification.maintain.web.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.basis.edu.base.code.web.action.ExternBaseAction;
import org.openurp.edu.base.code.model.ExternExamSubject;
import org.openurp.edu.extern.IdentificationConst;
import org.openurp.edu.extern.identification.model.ExternExamSubjectField;
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
  
  @Override
  protected void otherSomethingInSearch() {
    put("fixedRequestFieldMap", IdentificationConst.fixedRequestFieldMap);
    put("fixedResponseFieldMap", IdentificationConst.fixedResponseFieldMap);
  }
  
  protected void extraEdit(Integer id) {
    put("examSubjects", entityDao.search(OqlBuilder.from(ExternExamSubject.class, "examSubject").where("not exists (from "
        + entityType.getName()
        + " setting where setting.examSubject = examSubject and "
        + (null == id ? "1 = 1" : "setting.id != :settingId") + ")", id)));
    
    put("fields", entityDao.get(ExternExamSubjectField.class, "innerField", IdentificationConst.innerFields));
    put("fixedRequestFields", IdentificationConst.fixedRequestFields);
    put("fixedResponseFields", IdentificationConst.fixedResponseFields);
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
  
  public boolean beforeSave(ExternExamSubjectSetting setting) {
    // FIXME 2018-05-18 zhouqi 严格来说，在这里后台，也应该验证判空，但目前由于初版，暂且缓缓。日后一定要补上。
    setting.getRequestFields().clear();
    setting.getRequestFields().addAll(entityDao.get(ExternExamSubjectField.class, getIntIds("requestField")));
    setting.getResponseFields().clear();
    setting.getResponseFields().addAll(entityDao.get(ExternExamSubjectField.class, getIntIds("responseField")));
    
    List<ExternExamSubjectField> requestFields = entityDao.get(ExternExamSubjectField.class, "innerField", IdentificationConst.fixedRequestFields);
    List<ExternExamSubjectField> responseFields = entityDao.get(ExternExamSubjectField.class, "innerField", IdentificationConst.fixedResponseFields);
    
    if (CollectionUtils.isEmpty(requestFields) || CollectionUtils.isEmpty(responseFields)) {
      return false;
    }
    
    setting.getRequestFields().addAll(requestFields);
    setting.getResponseFields().addAll(responseFields);
    
    return true;
  }
}
