[#ftl]
[@b.head/]
  <script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/ajax-chosen.js"></script>
  [@b.toolbar title="<span style=\"color: blue\">" + (certScore.id)?exists?string("修改", "添加") + "</span>证书认定成绩标准配置"]
    bar.addItem("返回", function() {
        bg.form.submit(document.searchForm, "${b.url("!search")}", "certScores");
      }, "backward.png");
  [/@]
  [@b.form name="certScoreForm" action="!save" target="certScores" theme="list"]
    [#assign styleHTML = "width: 200px"/]
    [#--FIXME 2018-03-08 zhouqi 下面的联动需要再研究一下--]
    [@b.select label="大类" name="certScore.examSubject.id" items=examSubjects?sort_by(["code"]) required="true" value=(certScore.examSubject.id)! style=styleHTML/]
    [@b.select label="科目/子类" name="certScore.certType.id" empty="全部"  value=(certScore.certType.id)! style=styleHTML comment="（数据来自“证书配置”）"/]
    [@b.select label="级别" name="certScore.certLevel.id" empty="全部"  value=(certScore.certLevel.id)! style=styleHTML comment="（数据来自“证书配置”）"/]
    [@b.select label="省份" name="certScore.division.id" option=r"${ item.code[0..1] + '-' + item.name }" empty="全国" value=(certScore.division.id)! style=styleHTML comment="（数据来自“证书配置”）"/]
    [@b.select label="报考时间" name="certScore.examTime.id" empty="全部" value=(certScore.examTime.id)! style=styleHTML comment="（数据来自“证书配置”）"/]
    [@b.validity]
      function check() {
        var isOk = false;
        
        $.ajax({
          "type": "POST",
          "url": "${b.url("!checkAjax")}",
          "async": false,
          "dataType": "json",
          "data": {
            "id": document.certScoreForm["certScore.id"].value,
            "examSubjectId": document.certScoreForm["certScore.examSubject.id"].value,
            "certTypeId": document.certScoreForm["certScore.certType.id"].value,
            "certLevelId": document.certScoreForm["certScore.certLevel.id"].value,
            "divisionId": document.certScoreForm["certScore.division.id"].value,
            "examTimeId": document.certScoreForm["certScore.examTime.id"].value
          },
          "success": function(data) {
            isOk = data.isOk;
          }
        });
        
        return isOk;
      }
      
      $("[name='certScore.certType.id']", document.certScoreForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
      
      $("[name='certScore.examSubject.id']", document.certScoreForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
      
      $("[name='certScore.certLevel.id']", document.certScoreForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
      
      $("[name='certScore.division.id']", document.certScoreForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
      
      $("[name='certScore.examTime.id']", document.certScoreForm).assert(function() {
        return check();
      }, "当前的配置数据已存在！！！");
    [/@]
    [@b.textfield label="本校得分" name="certScore.score" value=(certScore.score?string("0.#"))! required="true" maxlength="10" check="match('number')" comment="说明：具体分值。比如：85。" style=styleHTML/]
    [@b.textfield label="得分显示" name="certScore.scoreValue" value=(certScore.scoreValue)! maxlength="50" comment="说明：本校得分的含义。若不填，则“提交”时取默认值“本校得分”。比如，优，A+，合格……" style=styleHTML/]
    [#--FIXME 2018-03-06 zhouqi 考虑是否要在提交前加入对应的课程，可能需要使用colorbox来实现--]
    [#--
    [@b.select label="可替课程" name="courseIds" items=(certScore.courses)![] required="true" multiple="true" style=styleHTML/]
    --]
    [@b.field label="可替课程" required="true"]
      <select id="courseIds" multiple="true" style="${styleHTML}">
        [#assign courseIds = ""/]
        [#list (certScore.courses)! as course]
        <option value="${course.id}" selected>${course.name}(${course.code})</option>
          [#assign courseIds = courseIds + (courseIds?length == 0)?string("", ",") + course.id/]
        [/#list]
      </select>
      <input id="courseIds_" type="hidden" name="courseIds" title="可替课程" value="${courseIds}"/>
    [/@]
    [@b.validity]
      $("[name=courseIds]", document.certScoreForm).require().match("notBlank");
    [/@]
    [@b.datepicker id="beginOn" label="启用日期" name="certScore.beginOn" value=(certScore.beginOn?string('yyyy-MM-dd'))?default('') required="true" style="width:200px" format="yyyy-MM-dd" maxDate="#F{$dp.$D(\\'endOn\\')}"/]  
    [@b.datepicker id="endOn" label="截止日期" name="certScore.endOn" value=(certScore.endOn?string('yyyy-MM-dd'))?default('') style="width:200px" format="yyyy-MM-dd" minDate="#F{$dp.$D(\\'beginOn\\')}"/]
    [@b.formfoot]
      <input type="hidden" name="certScore.id" value="${(certScore.id)!}"/>
      [@b.submit value="提交"/]
    [/@]
  [/@]
  <script>
    $(function() {
      $(document).ready(function() {
        var form = document.certScoreForm;
        
        var examSubject = form["certScore.examSubject.id"];
        var certType = form["certScore.certType.id"];
        var certLevel = form["certScore.certLevel.id"];
        var division = form["certScore.division.id"];
        var examTime = form["certScore.examTime.id"];
        
        var certTypeId = certType.value;
        var certLevelId = certLevel.value;
        var divisionId = division.value;
        var examTimeId = examTime.value;
        
        $(examSubject).change(function() {
          $(certType).empty();
          $(certLevel).empty();
          $(division).empty();
          $(examTime).empty();
          
          $.ajax({
            "type": "POST",
            "url": "${b.url("!dataAjax")}",
            "async": false,
            "dataType": "json",
            "data": {
              "from": "examSubject",
              "examSubjectId": $(this).val()
            },
            "success": function(data) {
              $(certType).append(data.certTypeOptions);
              $(certLevel).append(data.certLevelOptions);
              $(division).append(data.divisionOptions);
              $(examTime).append(data.examTimeOptions);
              
              if (certTypeId) {
                $(certType).val(certTypeId);
                
                certTypeId = null;
              }
              
              if (certLevelId) {
                $(certLevel).change();
                $(certLevel).val(certLevelId);
                
                certLevelId = null;
                return;
              }
              if (divisionId) {
                $(division).change();
                $(division).val(divisionId);
                
                divisionId = null;
                return;
              }
              if (examTimeId) {
                $(examTime).val(examTimeId);
                examTimeId = null;
              }
            }
          });
        });
        
        $(certType).change(function() {
          $(certLevel).empty();
          $(division).empty();
          $(examTime).empty();
          
          $.ajax({
            "type": "POST",
            "url": "${b.url("!dataAjax")}",
            "async": false,
            "dataType": "json",
            "data": {
              "from": "certType",
              "examSubjectId": $(examSubject).val(),
              "certTypeId": $(this).val()
            },
            "success": function(data) {
              $(certLevel).append(data.certLevelOptions);
              $(division).append(data.divisionOptions);
              $(examTime).append(data.examTimeOptions);
              
              $(certLevel).val(certLevelId);
              
              certLevelId = null;
              
              if (divisionId) {
                $(division).change();
                $(division).val(divisionId);
                
                divisionId = null;
                return;
              }
              if (examTimeId) {
                $(examTime).val(examTimeId);
                examTimeId = null;
              }
            }
          });
        });
        
        $(certLevel).change(function() {
          $(division).empty();
          $(examTime).empty();
          
          $.ajax({
            "type": "POST",
            "url": "${b.url("!dataAjax")}",
            "async": false,
            "dataType": "json",
            "data": {
              "from": "certLevel",
              "examSubjectId": $(examSubject).val(),
              "certTypeId": $(certType).val(),
              "certLevelId": $(this).val()
            },
            "success": function(data) {
              $(division).append(data.divisionOptions);
              $(examTime).append(data.examTimeOptions);
              
              $(division).val(divisionId);
              
              divisionId = null;
              
              if (examTimeId) {
                $(examTime).val(examTimeId);
                examTimeId = null;
              }
            }
          });
        });
        
        $(division).change(function() {
          $(examTime).empty();
          
          console.log(examTimeId);
          
          $.ajax({
            "type": "POST",
            "url": "${b.url("!dataAjax")}",
            "async": false,
            "dataType": "html",
            "data": {
              "from": "division",
              "examSubjectId": $(examSubject).val(),
              "certTypeId": $(certType).val(),
              "certLevelId": $(certLevel).val(),
              "divisionId": $(this).val()
            },
            "success": function(data) {
              $(examTime).append(data);
            }
          });
        });
        
        $(examSubject).change();
        
        $("#courseIds", form).children().attr("selected", "");
        
        $("#courseIds", form).ajaxChosen({
          "method": "GET",
          "url": "${b.url("!coursesAjax?pageNo=1&pageSize=10")}",
          "dataType": "json"
        }, function(data) {
          return data.courses;
        });
        
        $("#courseIds", form).change(function() {
          $(form["courseIds"]).val($(this).val());
        });
      });
    });
  </script>
[@b.foot/]
