<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>成品楹联价格管理</title>
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
					jp.post("${ctx}/cultural/order/coupletsPrice/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="coupletsPrice" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">类型：</label></td>
					<td class="width-35">
						<form:radiobuttons path="type" items="${fns:getDictList('cultural_lexicon_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
					<td class="width-15 active"><label class="pull-right">尺寸：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/spec/size/data" id="size" name="size.id" value="${coupletsPrice.size.id}" labelName="size.name" labelValue="${coupletsPrice.size.name}"
							 title="选择尺寸" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">套餐：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/order/combo/data" id="combo" name="combo.id" value="${coupletsPrice.combo.id}" labelName="combo.name" labelValue="${coupletsPrice.combo.name}"
							 title="选择套餐" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">价格：</label></td>
					<td class="width-35">
						<form:input path="price" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>