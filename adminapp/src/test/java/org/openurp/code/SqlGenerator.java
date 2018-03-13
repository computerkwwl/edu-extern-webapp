/*
 * OpenURP, Agile University Resource Planning Solution
 *
 * Copyright (c) 2014-2016, OpenURP Software.
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
package org.openurp.code;

import org.beangle.orm.hibernate.tool.DdlGenerator;
import org.hibernate.dialect.PostgreSQL82Dialect;

public class SqlGenerator {

  public static void main(String[] args) throws Exception {
    DdlGenerator.main(new String[] { PostgreSQL82Dialect.class.getName(), "D:\\工作区\\资料\\仓库\\记录\\SQL\\temp", "zh_CN", null });
  }
}
