[#ftl]
[@b.head/]
  [@b.grid items=certificates var="certificate"]
    [@b.gridbar]
      bar.addItem("${b.text("action.new")}", action.add());
      bar.addItem("${b.text("action.modify")}", action.edit());
      bar.addItem("${b.text("action.delete")}", action.remove("确认要删除吗？"));
      function importParam() {
        action.addParam("file", "template/excel/certificate.xls");
        action.addParam("display", "证书（配置）导入模板");
        action.addParam("importTitle", "证书（配置）导入");
      }
      var menuBar = bar.addMenu("导入", function() {
        importParam();
        bg.form.submit(action.getForm(), "${b.url("!importForm")}", "certificates");
      });
      menuBar.addItem("导入模板下载", function() {
        importParam();
        bg.form.submit(action.getForm(), "${b.url("!downloadTemplate")}", "_self");
      }, "${base}/static/images/action/download.gif");
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="大类" property="type.examSubject.name" width="200px"/]
      [@b.col title="类型（子类）" property="type.name" width="200px"/]
      [@b.col title="级别" property="type.level.name"/]
      [@b.col title="报考省份" property="division.code" width="100px"]${(certificate.division.code[0..1] + "-" + certificate.division.name)!"全国"}[/@]
      [@b.col title="报考时间" property="examTime.name"/]
      [@b.col title="启动日期" property="beginOn" width="100px"]${certificate.beginOn?string("yyyy-MM-dd")}[/@]
      [@b.col title="截止日期" property="endOn" width="100px"]${(certificate.endOn?string("yyyy-MM-dd"))!}[/@]
    [/@]
  [/@]
[@b.foot/]
