[#ftl]
[@b.head/]
  [#include "const.ftl"/]
  [@b.toolbar title="证书大类数据来源配置维护"]
    bar.addBack();
  [/@]
  [@b.form name="settingForm" action="!save" target="settings" theme="list"]
    [@b.select label="证书大类" name="setting.examSubject.id" items=examSubjects?sort_by(["code"]) required="true" value=(setting.examSubject.id)! style="width:200px"/]
    [@b.textfield label="数据来源URL" name="setting.url" value=(setting.url)! required="true" maxlength="280" style="width:200px"/]
    [#if (setting.keys)??]
      [#assign keysList = []/]
      [#list setting.keys?split(",") as key]
        [#assign keysList =  keysList + [ { "id": key } ]/]
      [/#list]
    [/#if]
    [@b.checkboxes label="关键字段" name="keys" items=inMap required="true" value=(keysList)! style="width:200px"/]
    [@b.datepicker id="beginOn" label="启用日期" name="setting.beginOn" value=(setting.beginOn?string('yyyy-MM-dd'))?default('') required="true" style="width:200px" format="yyyy-MM-dd" maxDate="#F{$dp.$D(\\'endOn\\')}"/]  
    [@b.datepicker id="endOn" label="截止日期" name="setting.endOn" value=(setting.endOn?string('yyyy-MM-dd'))?default('') style="width:200px" format="yyyy-MM-dd" minDate="#F{$dp.$D(\\'beginOn\\')}"/]
    [@b.formfoot]
      <input type="hidden" name="setting.id" value="${(setting.id)!}"/>
      [@b.submit value="提交"/]
    [/@]
  [/@]
[@b.foot/]
