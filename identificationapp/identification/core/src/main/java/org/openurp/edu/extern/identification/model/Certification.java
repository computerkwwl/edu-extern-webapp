package org.openurp.edu.extern.identification.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.openurp.edu.base.model.Student;

/**
 * 学生证书
 * 
 * @author zhouqi 2018年3月14日
 *
 */
@Entity(name = "org.openurp.edu.extern.identification.model.Certification")
public class Certification extends NumberIdObject<Integer> {
  
  private static final long serialVersionUID = 1056722418216651938L;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Certificate certificate;
  
  /** 所获证书编号 */
  private String code;
  
  /** 所获证书的成绩 */
  @NotNull
  private String score;
  
  @NotNull
  private String happenBy;
  
  @NotNull
  private Date happenAt;
  
  @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  private List<CertificationCourse> courses;
  
  private String lastBy;
  
  private Date lastAt;
  
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
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getScore() {
    return score;
  }
  
  public void setScore(String score) {
    this.score = score;
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
  
  public List<CertificationCourse> getCourses() {
    return courses;
  }
  
  public void setCourses(List<CertificationCourse> courses) {
    this.courses = courses;
  }
  
  public String getLastBy() {
    return lastBy;
  }
  
  public void setLastBy(String lastBy) {
    this.lastBy = lastBy;
  }
  
  public Date getLastAt() {
    return lastAt;
  }
  
  public void setLastAt(Date lastAt) {
    this.lastAt = lastAt;
  }
}
