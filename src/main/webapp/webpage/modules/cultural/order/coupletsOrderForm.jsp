<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>成品楹联订单管理</title>
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
					jp.post("${ctx}/cultural/order/coupletsOrder/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="coupletsOrder" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">用户：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/role/customer/data" id="customer" name="customer.id" value="${coupletsOrder.customer.id}" labelName="customer.nickname" labelValue="${coupletsOrder.customer.nickname}"
							 title="选择用户" cssClass="form-control required" fieldLabels="用户" fieldKeys="nickname" searchLabels="用户" searchKeys="nickname" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">成品楹联：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/couplets/couplets/data" id="couplets" name="couplets.id" value="${coupletsOrder.couplets.id}" labelName="couplets.name" labelValue="${coupletsOrder.couplets.name}"
							 title="选择成品楹联" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">价格：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/order/coupletsPrice/data" id="coupletsPrice" name="coupletsPrice.id" value="${coupletsOrder.coupletsPrice.id}" labelName="coupletsPrice.price" labelValue="${coupletsOrder.coupletsPrice.price}"
							 title="选择价格" cssClass="form-control required" fieldLabels="价格|尺寸|套餐" fieldKeys="price|sizeName|comboName" searchLabels="价格|尺寸|套餐" searchKeys="price|sizeName|comboName" ></sys:gridselect>
					</td>
					<td class="width-15 active"><label class="pull-right">收货地址：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/order/address/data" id="address" name="address.id" value="${coupletsOrder.address.id}" labelName="address.district" labelValue="${coupletsOrder.address.district}"
							 title="选择收货地址" cssClass="form-control required" fieldLabels="地区|详情|收货人|电话" fieldKeys="district|details|name|phonenum" searchLabels="地区|详情|收货人|电话" searchKeys="district|details|name|phonenum" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">安装人员：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/cultural/role/installer/data" id="installer" name="installer.id" value="${coupletsOrder.installer.id}" labelName="installer.name" labelValue="${coupletsOrder.installer.name}"
							 title="选择安装人员" cssClass="form-control required" fieldLabels="安装人员|联系方式" fieldKeys="name|phonenum" searchLabels="安装人员|联系方式" searchKeys="name|phonenum" ></sys:gridselect>
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