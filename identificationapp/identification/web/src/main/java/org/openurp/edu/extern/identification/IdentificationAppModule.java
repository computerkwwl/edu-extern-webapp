/**
 * 
 */
package org.openurp.edu.extern.identification;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.extern.identification.admin.web.action.CertificationAction;
import org.openurp.edu.extern.identification.code.web.action.CertScoreAction;
import org.openurp.edu.extern.identification.code.web.action.CertificateAction;

/**
 * @author zhouqi 2017年12月8日
 *
 */
public class IdentificationAppModule extends AbstractBindModule {
  
  @Override
  protected void doBinding() {
    bind(CertificateAction.class, CertScoreAction.class);
    
    bind(CertificationAction.class);
  }
}
