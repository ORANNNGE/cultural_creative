<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>作者管理</title>
	<meta name="decorator" content="ani"/>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctxStatic}/plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctxStatic}/plugin/ueditor/ueditor.all.js"></script>

	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;

              var docContent = UE.getEditor('editor').getContent();
              $("#details").val(docContent);
			  jp.loading();
              //$("input[name='details']").val($('#details').summernote('code'));//取富文本的值
			  $("#inputForm").submit();
			  return true;
		  }
		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/cultural/role/author/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
            	  			jp.error(data.msg);
	                    }
					})
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="author" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">姓名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="10"  minlength="2"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">类型：</label></td>
					<td class="width-35">
						<form:radiobuttons path="type" items="${fns:getDictList('cultural_author_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">简介：</label></td>
					<td class="width-35">
						<form:input path="intro" htmlEscape="false" maxlength="32"  minlength="8"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">详情：</label></td>
					<td class="width-55">
                        <%--<input type="hidden" name="details"/>
						<div id="details">
                          ${fns:unescapeHtml(author.details)}
                        </div>--%>
						<%--<textarea name="details" id="editor" cols="200" value="author.details"></textarea>--%>
                            <form:hidden path="details" htmlEscape="true"/>
                            <script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">图片：</label></td>
					<td class="width-35">
						<form:hidden id="picture" path="picture" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="picture" type="files" uploadPath="/cultural/role/author" selectMultiple="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">薪酬：</label></td>
					<td class="width-35">
						<form:input path="pay" htmlEscape="false"    class="form-control  isFloatGteZero"/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
<script type="text/javascript">
    var ue = UE.getEditor('editor');
    $(function () {
        var content = $('#details').val();
        //判断ueditor 编辑器是否创建成功
        ue.addListener("ready", function () {
            // editor准备好之后才可以使用
            // ue.setContent(content);
            ue.setContent("");
            ue.execCommand('inserthtml', jp.unescapeHTML(content));

        });
    });
</script>
</body>
</html>