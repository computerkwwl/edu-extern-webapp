/*
 * OpenURP, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2015, OpenURP Software.
 *
 * OpenURP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenURP is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenURP.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.other.service.listener;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.event.Event;
import org.beangle.commons.event.EventListener;
import org.openurp.edu.other.model.OtherExamSignUp;
import org.openurp.fee.code.model.PayState;
import org.openurp.fee.event.BillStateChangeEvent;
import org.openurp.fee.model.Bill;

public class OtherExamBillStateChangeEventListener extends BaseServiceImpl implements
    EventListener<BillStateChangeEvent> {

  public void onEvent(BillStateChangeEvent event) {
    Bill bill = event.getSource();
    if (!bill.getState().getId().equals(PayState.PAID)) { return; }
    List<OtherExamSignUp> signUps = entityDao.get(OtherExamSignUp.class, "bill", bill);
    if (!signUps.isEmpty()) {
      OtherExamSignUp signUp = signUps.get(0);
      signUp.setPayState(Model.newInstance(PayState.class, PayState.PAID));
      entityDao.saveOrUpdate(signUp);
    }
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return BillStateChangeEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
