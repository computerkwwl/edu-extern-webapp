<#include "/template/head.ftl"/>
 <#include "sum.ftl"/>
<body >
  <table id="otherExamSumBar" width="100%"></table>
  <@table.table  id="listTable" sortable="true" width="100%">
    <@table.thead>
      <td>院系</td>
      <#list categorys as category>
          <td>${category.name}</td>
      </#list>
    </@>
    <@table.tbody datas=depts;dept>
      <td>${dept.name}</td>
      <#list categorys as category>
      <#assign Key=dept.id+"_"+category.id />
      <td><a href="otherExamSignUpSummary.action?method=showDeptDetail&key=${Key}&semester.id=${semester.id}">${signUpMap[Key]?default("0")}</a></td>
      </#list>
    </@>
    <tr>
    <td align="center"><b>总计</b></td>
      <#list categorys as category>
          <@showSum signUpMap depts category/>
      </#list>
    </tr>
   </@>
    <form name="actionForm" method="post" action="" >
        <input type="hidden" id="key" name="key" value="" />
    </form>
    <script>
     var bar=new ToolBar('otherExamSumBar','资格考试报名汇总',null,true,true);
     bar.setMessage('<@getMessage/>');
     
   
    </script>
</body> 
<#include "/template/foot.ftl"/> 
