[#ftl]
[#assign certLevelOptions]
  <option value="">全部</option>
[#list certLevels?sort_by(["code"]) as certLevel]
  <option value="${certLevel.id}">${certLevel.name}</option>
[/#list]
[/#assign]

[#assign divisionOptions]
  <option value="">全国</option>
[#list divisions?sort_by(["code"]) as division]
  <option value="${division.id}">${ division.code[0..1] + '-' + division.name }</option>
[/#list]
[/#assign]

[#assign examTimeOptions]
  <option value="">全部</option>
[#list examTimes?sort_by(["code"]) as examTime]
  <option value="${examTime.id}">${examTime.name}</option>
[/#list]
[/#assign]
{
  "certLevelOptions": "${certLevelOptions?js_string}",
  "divisionOptions": "${divisionOptions?js_string}",
  "examTimeOptions": "${examTimeOptions?js_string}"
}