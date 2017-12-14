/**
 * 
 */
package org.openurp.edu.base.code.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 证书级别
 * 
 * @author zhouqi 2017年12月8日
 *
 */
@Entity(name = "org.openurp.edu.base.code.model.CertificateLevel")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class CertificateLevel extends Code<Integer> {
  
  private static final long serialVersionUID = 4789178885097321849L;
}
