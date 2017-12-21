package org.openurp.edu.extern.identification.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.openurp.edu.base.code.model.ExternExamSubject;

/**
 * 证书大类数据来源配置
 * 
 * @author zhouqi 2017年12月17日
 *
 */
@Entity(name = "org.openurp.edu.extern.identification.model.ExternExamSubjectSetting")
@Table(name = "xb_exam_subject_settings")
public class ExternExamSubjectSetting extends NumberIdTimeObject<Integer> {
  
  private static final long serialVersionUID = -1701921462477141937L;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExternExamSubject examSubject;
  
  @NotNull
  private String url;
  
  @NotNull
  private String keys;
  
  /** 生效时间 */
  @NotNull
  private Date beginOn;
  
  /** 失效时间 */
  private Date endOn;
  
  public ExternExamSubject getExamSubject() {
    return examSubject;
  }
  
  public void setExamSubject(ExternExamSubject examSubject) {
    this.examSubject = examSubject;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getKeys() {
    return keys;
  }
  
  public void setKeys(String keys) {
    this.keys = keys;
  }
  
  public Date getBeginOn() {
    return beginOn;
  }
  
  public void setBeginOn(Date beginOn) {
    this.beginOn = beginOn;
  }
  
  public Date getEndOn() {
    return endOn;
  }
  
  public void setEndOn(Date endOn) {
    this.endOn = endOn;
  }
}
