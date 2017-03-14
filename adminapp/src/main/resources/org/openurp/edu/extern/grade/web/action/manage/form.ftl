[#ftl]
[@b.head/]
[@b.toolbar title="校外考试成绩维护"]
    bar.addBack("${b.text("action.back")}");
[/@]
[@b.form name="examGradeForm" action="!save" theme="list"]
    [#if (examGrade.std.id)?exists]
        [@b.field label="std.code"]${(examGrade.std.code)}[/@]
    [#else]
        [@b.field name="findstudent"]
            [@b.textfield   theme="xml" name="examGrade.std.code" maxlength="15"  id="stdCode" label="std.code" value="${(examGrade.std.code)!}" required="true" style="width:150px" comment="<input type='button' value='查询' onClick='searchStudent()'/>"/]
            <input type="hidden" id="stdId" name="examGrade.std.id" value="${(examGrade.std.id)!}"/>
        [/@]
    [/#if]
    [@b.field label="姓名"]
      <table>
       <tr>
            <td id="stdName">${(examGrade.std.name)?default("&nbsp;")}</td>
       </tr>
      </table>
    [/@]
    [#--]
    [@b.textfield name="examGrade.examNo" label="准考证号码" value="${(examGrade.examNo)!}" style="width:150px"/]
    [--]
    [@b.select name="examGrade.markStyle.id" items=markStyles label="记录方式" empty="..." required="true" value=(examGrade.markStyle.id)! style="width:150px"/]
    [@b.select name="examGrade.subject.id" items=examSubjects?sort_by("name") empty="..." required="true" label="考试科目" value=(examGrade.subject.id)! style="width:150px"/]
    [@b.field label="考试学期"]
        <select name="examGrade.semester.id"  style="width:150px">
            [#list semesters?sort_by("code")?reverse as se]
            <option value="${se.id}" [#if se.id?string=(examGrade.semester.id)?default('')?string]selected[/#if] title="${se.schoolYear}&nbsp;${se.name}">${se.schoolYear}&nbsp;${se.name}</option>
            [/#list]
        </select>
    [/@]
    [@b.textfield name="examGrade.score" maxlength="5" label="成绩" check="match('number').greaterThanOrEqualTo(0)" required="true" value="${(examGrade.score)!}" style="width:150px"/]
    [@b.radios name="examGrade.passed" items={"1":"合格", "0":"不合格"} label="是否合格" value=(examGrade.passed)!true?string("1", "0")/]
    [#--
    [@b.select name="examGrade.gradeLevel.id" items=gradeLevels label="成绩等级" value="(examGrade.gradeLevel.id)!" style="width:150px"/]
    --]
    [@b.textfield name="examGrade.certificateNumber" maxlength="100" label="证书编号" value="${(examGrade.certificateNumber)!}" style="width:150px"/]
    [@b.formfoot]
        <input type="hidden" name="examGrade.id" value="${(examGrade.id)!}" />
        
        [@b.submit value="action.submit"/]
    [/@]
[/@]
<script language="javascript" >
    var form = document.examGradeForm;
    function searchStudent(){
        jQuery.post("manage!searchStudent.action",{studentCode:$("#stdCode").val()},function(data){
            if (data == ""){
                $("#stdId").val("");
                $("#stdId").parent().find(".error").remove();
                $("#stdId").parent().append($("<label class='error' for='stdId'>查无此人!</label>"));
                $("#stdName").html("&nbsp;");
            } else {
                var dataObj = eval("(" + data + ")");
                $("#stdId").parent().find(".error").remove();
                $("#stdName").html(dataObj.student.person.formatedName);
                $("#stdId").val(dataObj.student.id);
            }
        },"text");
    }
    
</script>
[@b.foot/]