[#ftl]
[@b.head/]
  [@b.toolbar title="证书配置"]
    bar.addBack();
  [/@]
  [@b.form name="certificateForm" action="!save" target="certificates" theme="list"]
    [@b.select label="类型-级别" name="certificate.type.id" items=types option=r"${item.name}-${item.level.name}" required="true" value=(certificate.type.id)! style="width:300px"/]
    [@b.select label="报考省份" name="certificate.division.id" items=divisions?sort_by(["code"]) option=r"${item.code[0..1] + '-' + item.name}" empty="全国" value=(certificate.division.id)! style="width:200px"/]
    [@b.select label="报考时间" name="certificate.examTime.id" items=times?sort_by(["code"]) required="true" value=(certificate.examTime.id)! style="width:200px"/]
    [@b.validity]
      function check() {
        var isOk = false;
        
        $.ajax({
          "type": "POST",
          "url": "${b.url("!checkAjax")}",
          "async": false,
          "dataType": "json",
          "data": {
            "id": document.certificateForm["certificate.id"].value,
            "typeId": document.certificateForm["certificate.type.id"].value,
            "divisionId": document.certificateForm["certificate.division.id"].value,
            "examTimeId": document.certificateForm["certificate.examTime.id"].value
          },
          "success": function(data) {
            isOk = data.isOk;
          }
        });
        
        return isOk;
      }
      
      $("[name='certificate.type.id']", document.certificateForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
      
      $("[name='certificate.division.id']", document.certificateForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
      
      $("[name='certificate.examTime.id']", document.certificateForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
    [/@]
    [@b.datepicker id="beginOn" label="启用日期" name="certificate.beginOn" value=(certificate.beginOn?string('yyyy-MM-dd'))?default('') required="true" style="width:200px" format="yyyy-MM-dd" maxDate="#F{$dp.$D(\\'endOn\\')}"/]  
    [@b.datepicker id="endOn" label="截止日期" name="certificate.endOn" value=(certificate.endOn?string('yyyy-MM-dd'))?default('') style="width:200px" format="yyyy-MM-dd" minDate="#F{$dp.$D(\\'beginOn\\')}"/]
    [@b.formfoot]
      <input type="hidden" name="certificate.id" value="${(certificate.id)!}"/>
      [@b.submit value="提交"/]
    [/@]
  [/@]
[@b.foot/]
