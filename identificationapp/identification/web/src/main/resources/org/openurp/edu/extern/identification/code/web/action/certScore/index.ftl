[#ftl]
[@b.head/]
  [@b.toolbar title="证书认定标准配置管理"/]
  <table class="indexpanel">
    <tr>
      <td class="index_view" style="white-space: nowrap">
        [@b.form title="ui.searchForm" name="searchForm" action="!search" target="certScores" theme="search"]
          [@b.select label="大类" name="certScore.examSubject.id" items=examSubjects?sort_by(["code"]) empty="全部"/]
          [@b.select label="科目/子类" name="certScore.certType.id" items=certTypes empty="全部"/]
          [@b.select label="级别" name="certScore.certLevel.id" items=certLevels?sort_by(["code"]) empty="全部"/]
          [@b.select label="省份" name="certScore.division.id" items=divisions?sort_by(["code"]) option=r"${ item.code[0..1] + '-' + item.name }" empty="全国"/]
          [@b.select label="报考时间" name="certScore.examTime.id" items=examTimes?sort_by(["code"]) empty="全部"/]
        [/@]
      </td>
      <td class="index_content">[@b.div id="certScores"/]</td>
    </tr>
  </table>
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.submit(document.searchForm, "${b.url("!search")}", "certScores");
      });
    });
  </script>
[@b.foot/]
