[#ftl]
<option value="">全国</option>
[#list examTimes?sort_by(["code"]) as examTime]
  <option value="${examTime.id}">${examTime.name}</option>
[/#list]
