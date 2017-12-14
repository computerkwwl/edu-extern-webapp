/**
 * 
 */
package org.openurp.edu.base.code.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 证书类型
 * 
 * @author zhouqi 2017年12月8日
 *
 */
@Entity(name = "org.openurp.edu.base.code.model.CertificateType")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class CertificateType extends Code<Integer> {
  
  private static final long serialVersionUID = -5945724520204722167L;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExternExamSubject examSubject;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private CertificateLevel level;
  
  public ExternExamSubject getExamSubject() {
    return examSubject;
  }
  
  public void setExamSubject(ExternExamSubject examSubject) {
    this.examSubject = examSubject;
  }
  
  public CertificateLevel getLevel() {
    return level;
  }
  
  public void setLevel(CertificateLevel level) {
    this.level = level;
  }
}
