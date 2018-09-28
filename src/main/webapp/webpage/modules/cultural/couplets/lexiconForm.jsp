<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>词库管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
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
              $("#meaning").val(docContent);

			  jp.loading();
					//$("input[name='meaning']").val($('#meaning').summernote('code'));//取富文本的值
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/cultural/couplets/lexicon/save",$('#inputForm').serialize(),function(data){
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
			
				//富文本初始化
			/*$('#meaning').summernote({
				height: 300,
                lang: 'zh-CN'
            });*/
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="lexicon" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>上联：</label></td>
					<td class="width-35">
						<form:textarea path="rightline" htmlEscape="false" rows="4" maxlength="32"  minlength="4"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下联：</label></td>
					<td class="width-35">
						<form:textarea path="leftline" htmlEscape="false" rows="4" maxlength="32"  minlength="4"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">横批：</label></td>
					<td class="width-35">
						<form:textarea path="topline" htmlEscape="false" rows="4" maxlength="32"  minlength="4"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>类型：</label></td>
					<td class="width-35">
						<form:radiobuttons path="type" items="${fns:getDictList('cultural_lexicon_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否原创：</label></td>
					<td class="width-35">
						<form:radiobuttons path="isOriginal" items="${fns:getDictList('cultural_lexicon_original')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">作者：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/role/author/data" id="author" name="author.id" value="${lexicon.author.id}" labelName="author.name" labelValue="${lexicon.author.name}"
							 title="选择作者" cssClass="form-control required" fieldLabels="作者" fieldKeys="name" searchLabels="作者" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">寓意：</label></td>
					<td class="width-55">
                        <%--<input type="hidden" name="meaning"/>
						<div id="meaning">
                          ${fns:unescapeHtml(lexicon.meaning)}
                        </div>--%>
							<form:hidden path="meaning" htmlEscape="true"/>
							<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">图片：</label></td>
					<td class="width-35">
						<form:hidden id="picture" path="picture" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="picture" type="files" uploadPath="/cultural/couplets/lexicon" selectMultiple="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
<script type="text/javascript">
	var ue = UE.getEditor('editor');
		$(function () {
			var content = $('#meaning').val();
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