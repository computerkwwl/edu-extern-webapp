[#ftl]
[@b.head/]
  [@b.toolbar title="证书类型（证书名称/证书子类）基础数据管理"/]
  <table class="indexpanel">
    <tr>
      <td class="index_view">
        [@b.form title="ui.searchForm" name="searchForm" action="!search" target="types" theme="search"]
          [@b.textfields names="type.code;代码,type.name;名称"/]
          [@b.select label="大类" name="type.examSubject.id" items=examSubjects?sort_by(["code"]) empty="..."/]
          [@b.select label="级别" name="type.level.id" items=levels?sort_by(["code"]) empty="..."/]
        [/@]
      </td>
      <td class="index_content">[@b.div id="types"/]</td>
    </tr>
  </table>
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.submit(document.searchForm, "${b.url("!search")}", "types");
      });
    });
  </script>
[@b.foot/]
