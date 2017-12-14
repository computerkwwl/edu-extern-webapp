/**
 * 
 */
package org.openurp.edu.base.code.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;


/**
 * @author zhouqi 2017年12月11日
 *
 */
@Entity(name = "org.openurp.edu.base.code.model.ExternExamSubject")
@Table(name = "xb_ext_exam_subjects")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class ExternExamSubject extends Code<Integer> {

  private static final long serialVersionUID = -3512209266179913677L;
}
