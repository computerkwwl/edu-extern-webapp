/**
 * 
 */
package org.openurp.edu.extern.identification.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.edu.base.model.Course;

/**
 * @author zhouqi 2017年12月13日
 *
 */
@Entity(name = "org.openurp.edu.extern.identification.model.CertificateCourseSubsitution")
public class CertificateCourseSubsitution extends NumberIdTimeObject<Integer> {
  
  private static final long serialVersionUID = 5870515378726652544L;
  
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Certificate certificate;
  
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Course course;
  
  @NotNull
  private String score;
  
  public Certificate getCertificate() {
    return certificate;
  }
  
  public void setCertificate(Certificate certificate) {
    this.certificate = certificate;
  }
  
  public Course getCourse() {
    return course;
  }
  
  public void setCourse(Course course) {
    this.course = course;
  }
  
  public String getScore() {
    return score;
  }
  
  public void setScore(String score) {
    this.score = score;
  }
}
