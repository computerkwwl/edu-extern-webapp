package org.openurp.edu.extern.identification.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.openurp.edu.base.model.Course;

/**
 * @author zhouqi 2018年4月9日
 *
 */
@Entity(name = "org.openurp.edu.extern.identification.model.CertificationCourse")
public class CertificationCourse extends NumberIdObject<Integer> {
  
  private static final long serialVersionUID = 4389788706207331936L;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Certification certification;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;
  
  @NotNull
  private Float score;
  
  private String scoreValue;
  
  public Certification getCertification() {
    return certification;
  }
  
  public void setCertification(Certification certification) {
    this.certification = certification;
  }
  
  public Course getCourse() {
    return course;
  }
  
  public void setCourse(Course course) {
    this.course = course;
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
}
