/**
 * 
 */
package org.openurp.edu.extern.identification.code;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.extern.identification.code.web.action.CertificateAction;

/**
 * @author zhouqi 2017年12月8日
 *
 */
public class IdentificationAppModule extends AbstractBindModule {
  
  @Override
  protected void doBinding() {
    bind(CertificateAction.class);
  }
}
