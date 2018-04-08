package org.openurp.edu.extern.identification.model;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.openurp.edu.base.model.Student;

/**
 * 学生证书
 * 
 * @author zhouqi 2018年3月14日
 *
 */
public class Certification extends NumberIdObject<Integer> {
  
  private static final long serialVersionUID = 1056722418216651938L;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Certificate certificate;
  
  @NotNull
  private String happenBy;
  
  @NotNull
  private Date happenAt;
  
  private Boolean passed;
  
  private Float score;
  
  private String scoreValue;
  
  private String passBy;
  
  private Date passAt;
  
  public Student getStd() {
    return std;
  }
  
  public void setStd(Student std) {
    this.std = std;
  }
  
  public Certificate getCertificate() {
    return certificate;
  }
  
  public void setCertificate(Certificate certificate) {
    this.certificate = certificate;
  }
  
  public String getHappenBy() {
    return happenBy;
  }
  
  public void setHappenBy(String happenBy) {
    this.happenBy = happenBy;
  }
  
  public Date getHappenAt() {
    return happenAt;
  }
  
  public void setHappenAt(Date happenAt) {
    this.happenAt = happenAt;
  }
  
  public Boolean getPassed() {
    return passed;
  }
  
  public void setPassed(Boolean passed) {
    this.passed = passed;
  }
  
  public Float getScore() {
    return score;
  }
  
  public void setScore(Float score) {
    this.score = score;
  }
  
  public String getScoreValue() {
    return scoreValue;
  }
  
  public void setScoreValue(String scoreValue) {
    this.scoreValue = scoreValue;
  }
  
  public String getPassBy() {
    return passBy;
  }
  
  public void setPassBy(String passBy) {
    this.passBy = passBy;
  }
  
  public Date getPassAt() {
    return passAt;
  }
  
  public void setPassAt(Date passAt) {
    this.passAt = passAt;
  }
}
