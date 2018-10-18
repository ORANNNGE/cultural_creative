<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>词库价格管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/cultural/order/lexiconPrice/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="lexiconPrice" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">楹联词库：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/couplets/lexicon/data" id="lexicon" name="lexicon.id" value="${lexiconPrice.lexicon.id}" labelName="lexicon.title" labelValue="${lexiconPrice.lexicon.title}"
							 title="选择楹联词库" cssClass="form-control required" fieldLabels="标题|上联|下联|横批" fieldKeys="title|rightline|leftline|topline" searchLabels="标题|上联|下联|横批" searchKeys="title|rightline|leftline|topline" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">作者：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/role/author/data" id="author" name="author.id" value="${lexiconPrice.author.id}" labelName="author.name" labelValue="${lexiconPrice.author.name}"
							 title="选择作者" cssClass="form-control required" fieldLabels="姓名" fieldKeys="name" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">字体：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/typeface/data" id="typeface" name="typeface.id" value="${lexiconPrice.typeface.id}" labelName="typeface.name" labelValue="${lexiconPrice.typeface.name}"
							 title="选择字体" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">尺寸：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/size/data" id="size" name="size.id" value="${lexiconPrice.size.id}" labelName="size.name" labelValue="${lexiconPrice.size.name}"
							 title="选择尺寸" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">楹联框：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/frame/data" id="frame" name="frame.id" value="${lexiconPrice.frame.id}" labelName="frame.name" labelValue="${lexiconPrice.frame.name}"
							 title="选择楹联框" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">制作工艺：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/craft/data" id="craft" name="craft.id" value="${lexiconPrice.craft.id}" labelName="craft.name" labelValue="${lexiconPrice.craft.name}"
							 title="选择制作工艺" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">价格：</label></td>
					<td class="width-35">
						<form:input path="price" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>