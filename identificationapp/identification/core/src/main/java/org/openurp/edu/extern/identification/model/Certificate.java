package org.openurp.edu.extern.identification.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.code.geo.model.Division;
import org.openurp.edu.base.code.model.CertificateType;
import org.openurp.edu.base.code.model.ExternExamTime;

/**
 * @author zhouqi 2017年12月13日
 *
 */
@Entity(name = "org.openurp.edu.extern.identification.model.Certificate")
public class Certificate extends NumberIdTimeObject<Integer> {
  
  private static final long serialVersionUID = 3980122866996113913L;
  
//  /** 学生 */
//  @NotNull
//  @ManyToOne(fetch = FetchType.LAZY)
//  private Student std;
  
  /** 证书类型（含级别） */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CertificateType type;
  
  /** 报考省份 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Division division;
  
  /** 报考时间 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ExternExamTime examTime;
  
//  @OneToMany(mappedBy = "certificate", orphanRemoval = true)
//  @Cascade(CascadeType.ALL)
//  private List<CertificateCourseSubsitution> subsitions;
  
  /** 生效时间 */
  @NotNull
  private Date beginOn;
  
  /** 失效时间 */
  private Date endOn;
  
  public CertificateType getType() {
    return type;
  }
  
  public void setType(CertificateType type) {
    this.type = type;
  }
  
  public Division getDivision() {
    return division;
  }
  
  public void setDivision(Division division) {
    this.division = division;
  }
  
  public ExternExamTime getExamTime() {
    return examTime;
  }
  
  public void setExamTime(ExternExamTime examTime) {
    this.examTime = examTime;
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
