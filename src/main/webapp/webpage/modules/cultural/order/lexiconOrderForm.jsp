<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>楹联词库订单管理</title>
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
					jp.post("${ctx}/cultural/order/lexiconOrder/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="lexiconOrder" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">用户：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/role/customer/data" id="customer" name="customer.id" value="${lexiconOrder.customer.id}" labelName="customer.nickname" labelValue="${lexiconOrder.customer.nickname}"
							 title="选择用户" cssClass="form-control required" fieldLabels="用户" fieldKeys="nickname" searchLabels="用户" searchKeys="nickname" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">楹联词库：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/couplets/lexicon/data" id="lexicon" name="lexicon.id" value="${lexiconOrder.lexicon.id}" labelName="lexicon.title" labelValue="${lexiconOrder.lexicon.title}"
							 title="选择楹联词库" cssClass="form-control required" fieldLabels="标题|上联|下联|横批" fieldKeys="title|rightline|leftline|topline" searchLabels="title|rightline|leftline|topline" searchKeys="title|rightline|leftline|topline" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">楹联价格：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/order/lexiconPrice/data" id="lexiconPrice" name="lexiconPrice.id" value="${lexiconOrder.lexiconPrice.id}" labelName="lexiconPrice.price" labelValue="${lexiconOrder.lexiconPrice.price}"
							 title="选择楹联价格" cssClass="form-control required" fieldLabels="价格" fieldKeys="price" searchLabels="价格" searchKeys="price" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">收货地址：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/order/address/data" id="address" name="address.id" value="${lexiconOrder.address.id}" labelName="address.district" labelValue="${lexiconOrder.address.district}"
							 title="选择收货地址" cssClass="form-control required" fieldLabels="地区|详情" fieldKeys="district|details" searchLabels="地区|详情" searchKeys="district|details" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">安装人员：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/role/installer/data" id="installer" name="installer.id" value="${lexiconOrder.installer.id}" labelName="installer.name" labelValue="${lexiconOrder.installer.name}"
							 title="选择安装人员" cssClass="form-control required" fieldLabels="name" fieldKeys="姓名" searchLabels="name" searchKeys="姓名" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">数量：</label></td>
					<td class="width-35">
						<form:input path="num" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">总价：</label></td>
					<td class="width-35">
						<form:input path="totalPrice" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">订单状态：</label></td>
					<td class="width-35">
						<form:radiobuttons path="status" items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
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