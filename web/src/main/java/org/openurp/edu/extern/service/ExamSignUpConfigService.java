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
package org.openurp.edu.extern.service;

import org.openurp.edu.extern.code.model.ExamCategory;
import org.openurp.edu.extern.model.ExamSignUpConfig;

public interface ExamSignUpConfigService {

  // public void createDefaultCategory(ExamCategory examCategory, ExamSignUpConfig
  // config);

  public void createDefaultSubject(ExamCategory examCategory, ExamSignUpConfig config);

  public ExamSignUpConfig configDefaultSubject(ExamCategory examCategory,
      ExamSignUpConfig config);

}
