[#ftl]
[@b.head/]
  [#include "const.ftl"/]
  [@b.grid items=settings var="setting"]
    [@b.gridbar]
      bar.addItem("${b.text("action.new")}", action.add());
      bar.addItem("${b.text("action.modify")}", action.edit());
      bar.addItem("${b.text("action.delete")}", action.remove("确认要删除吗？"));
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="证书大类" property="examSubject.name" width="200px"/]
      [@b.col title="数据来源URL" property="url"/]
      [@b.col title="关键字段" property="keys"][#assign keys = setting.keys?split(",")/][#list keys as key]${inMap[key]}[#if key_has_next],[/#if][/#list][/@]
      [@b.col title="启动日期" property="beginOn" width="100px"]${setting.beginOn?string("yyyy-MM-dd")}[/@]
      [@b.col title="截止日期" property="endOn" width="100px"]${(setting.endOn?string("yyyy-MM-dd"))!}[/@]
    [/@]
  [/@]
[@b.foot/]
