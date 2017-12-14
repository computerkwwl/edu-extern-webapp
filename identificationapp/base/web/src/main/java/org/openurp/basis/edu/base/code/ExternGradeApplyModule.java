/**
 * 
 */
package org.openurp.basis.edu.base.code;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.basis.edu.base.code.web.action.CertificateLevelAction;
import org.openurp.basis.edu.base.code.web.action.CertificateTypeAction;
import org.openurp.basis.edu.base.code.web.action.ExternExamSubjectAction;
import org.openurp.basis.edu.base.code.web.action.ExternExamTimeAction;

/**
 * @author zhouqi 2017年12月8日
 *
 */
public class ExternGradeApplyModule extends AbstractBindModule {
  
  @Override
  protected void doBinding() {
    bind(ExternExamSubjectAction.class, CertificateLevelAction.class, CertificateTypeAction.class, ExternExamTimeAction.class);
  }
}
