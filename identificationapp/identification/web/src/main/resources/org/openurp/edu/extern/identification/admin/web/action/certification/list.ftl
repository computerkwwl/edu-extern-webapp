[#ftl]
[@b.head/]
  [@b.grid id="certification" items=certifications var="certification"]
    [@b.gridbar]
      bar.addItem("申请", function() {
        //bg.form.addInput(action.getForm(), "step", "1", "hidden");
        bg.form.submit(action.getForm(), "${b.url("!apply")}");
      }, "new.png");
      bar.addItem("${b.text("action.info")}", action.info());
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="学号" property="std.code" width="110px"/]
      [@b.col title="姓名" property="std.name" width="150px"/]
      [@b.col title="年级" property="std.state.grade" width="10%"/]
      [@b.col title="证书代码" property="certificate.code" width="110px"/]
      [@b.col title="证书名称" property="certificate.name"]<a href="javascript:void(0)">${certification.certificate.name}</a>[/@]
      [@b.col title="报考省份" property="certificate.division.code" width="65px"]${(certification.certificate.division.code[0..1] + "-" + certification.certificate.division.name)!"全国"}[/@]
      [@b.col title="入库人" property="happenBy" width="60px"]${certification.happenBy}[/@]
      [@b.col title="入库时间" property="happenAt" width="90px"]${certification.happenAt?string("yyyy-MM-dd")}[/@]
    [/@]
  [/@]
  <script>
    $(function() {
      $(document).ready(function() {
        $("#certification a").click(function() {
          var form = action.getForm();
          bg.form.addInput(form, "certification.id", $(this).parent().parent().find(":checkbox").val(), "hidden");
          bg.form.submit(form, "${b.url("!info")}", "certifications");
        });
      });
    });
  </script>
[@b.foot/]
