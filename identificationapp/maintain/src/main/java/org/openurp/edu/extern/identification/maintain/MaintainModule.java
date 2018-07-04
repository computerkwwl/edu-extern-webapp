package org.openurp.edu.extern.identification.maintain;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.extern.identification.maintain.web.action.ExternExamSubjectFieldAction;
import org.openurp.edu.extern.identification.maintain.web.action.ExternExamSubjectSettingAction;

/**
 * @author zhouqi 2017年12月17日
 *
 */
public class MaintainModule extends AbstractBindModule {
  
  @Override
  protected void doBinding() {
    bind(ExternExamSubjectFieldAction.class, ExternExamSubjectSettingAction.class);
  }
}
