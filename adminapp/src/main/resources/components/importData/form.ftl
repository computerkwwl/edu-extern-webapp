[#ftl/]
[@b.head/]
[@b.toolbar title=Parameters["display"]!("导入")]
    bar.addItem("模板下载","downloadTemplate()","${base}/static/images/action/download.gif");
    bar.addBack();
[/@]
[@b.messages/]
<br/>
[@b.form action="!importData" theme="list" enctype="multipart/form-data"]
    [@b.field label="文件目录"]
    <input type="file" name="importFile" id="importFile"/>
    [/@]  
    [@b.formfoot]
      [@b.submit value="提交" onsubmit="validateExtendName"/]
      <input type="reset" value="重置" class="buttonStyle"/>
    [#list Parameters?keys as key]
          [#if key!='method']<input type="hidden" name="${key}" value="${Parameters[key]}" />[/#if]
     [/#list]
    [/@]
[/@]
<div style="color:red;font-size:2">上传文件中的所有信息均要采用文本格式。对于日期和数字等信息也是一样。</div>
 [@b.form name="downloadForm" action="/adminclass/downloadAdminclassStdTemp"]
     [#list Parameters?keys as key]
          [#if key!='method']<input type="hidden" name="${key}" value="${Parameters[key]}" />[/#if]
     [/#list]
 [/@]
<script type="text/javascript">
    function downloadTemplate(){
        [#if Parameters['templateDocumentId']??]
        self.location="dataTemplate!download.action?document.id=${Parameters['templateDocumentId']}";
        [#elseif Parameters['tempPath']??]
        	document.downloadForm.action = "${Parameters['tempPath']}";
        	document.downloadForm.submit();
        [#else]
        document.downloadForm.submit();
        [/#if]
    }
    function validateExtendName(form){
        var value = form.importFile.value;
        if(value == ""){
        	alert("请选择文件");
        	return false;
        }
        var index = value.indexOf(".xls");
        if((index < 1) || (index < (value.length - 4))){
            alert("${b.text("filed.file")}'.xls'");
            return false;
        }
        return true;
    }
</script>
[@b.foot/]