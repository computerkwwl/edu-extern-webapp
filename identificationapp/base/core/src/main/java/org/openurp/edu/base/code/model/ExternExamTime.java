package org.openurp.edu.base.code.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;


/**
 * @author zhouqi 2017年12月12日
 *
 */
@Entity(name = "org.openurp.edu.base.code.model.ExternExamTime")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class ExternExamTime extends Code<Integer> {

  private static final long serialVersionUID = 764987555815353396L;
  
}
