<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>成品楹联管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.all.js"></script>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  var docContent = UE.getEditor('editor').getContent();
			  $('#intro').val(docContent);
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
					jp.post("${ctx}/cultural/couplets/couplets/save",$('#inputForm').serialize(),function(data){
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
//			$('#details').summernote({
//				height: 300,
//                lang: 'zh-CN'
//            });
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="couplets" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>标题：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="16"  minlength="5"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>词库：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/couplets/lexicon/data" id="lexicon" name="lexicon.id" value="${couplets.lexicon.id}" labelName="lexicon.title" labelValue="${couplets.lexicon.title}"
							 title="选择词库" cssClass="form-control required" fieldLabels="标题|上联|下联|横批" fieldKeys="title|rightline|leftline|topline" searchLabels="标题|上联|下联|横批" searchKeys="title|rightline|leftline|topline" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">尺寸：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/size/data" id="size" name="size.id" value="${couplets.size.id}" labelName="size.name" labelValue="${couplets.size.name}"
							 title="选择尺寸" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">楹联框：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/frame/data" id="frame" name="frame.id" value="${couplets.frame.id}" labelName="frame.name" labelValue="${couplets.frame.name}"
							 title="选择楹联框" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">制作工艺：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/craft/data" id="craft" name="craft.id" value="${couplets.craft.id}" labelName="craft.name" labelValue="${couplets.craft.name}"
							 title="选择制作工艺" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>图片：</label></td>
					<td class="width-35">
						<form:hidden id="picture" path="picture" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="picture" type="files" uploadPath="/cultural/couplets/couplets" selectMultiple="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>详情：</label></td>
					<td class="width-55">
                        <%--<input type="hidden" name="details"/>--%>
						<%--<div id="details">--%>
                          <%--${fns:unescapeHtml(couplets.details)}--%>
                        <%--</div>--%>
							<form:hidden path="details" htmlEscape="true"/>
							<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
					</td>
					<td class="width-15 active"><label class="pull-right">推荐到首页：</label></td>
					<td class="width-35">
						<form:radiobuttons path="recommend" items="${fns:getDictList('cultural_recommend')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">定价：</label></td>
					<td class="width-35">
						<form:input path="price" htmlEscape="false"    class="form-control  isFloatGteZero"/>
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
		var content = $('#intro').val();
		// 判断ueditor 编辑器是否创建成功
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