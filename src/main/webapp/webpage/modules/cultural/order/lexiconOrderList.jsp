<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>楹联词库订单管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="lexiconOrderList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">楹联词库订单列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="lexiconOrder" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="用户：">用户：</label>
				<sys:gridselect url="${ctx}/cultural/role/customer/data" id="customer" name="customer.id" value="${lexiconOrder.customer.id}" labelName="customer.nickname" labelValue="${lexiconOrder.customer.nickname}"
					title="选择用户" cssClass="form-control required" fieldLabels="用户" fieldKeys="nickname" searchLabels="用户" searchKeys="nickname" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="楹联词库：">楹联词库：</label>
				<sys:gridselect url="${ctx}/cultural/couplets/lexicon/data" id="lexicon" name="lexicon.id" value="${lexiconOrder.lexicon.id}" labelName="lexicon.title" labelValue="${lexiconOrder.lexicon.title}"
					title="选择楹联词库" cssClass="form-control required" fieldLabels="标题|上联|下联|横批" fieldKeys="title|rightline|leftline|topline" searchLabels="title|rightline|leftline|topline" searchKeys="title|rightline|leftline|topline" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="楹联价格：">楹联价格：</label>
				<sys:gridselect url="${ctx}/cultural/order/lexiconPrice/data" id="lexiconPrice" name="lexiconPrice.id" value="${lexiconOrder.lexiconPrice.id}" labelName="lexiconPrice.price" labelValue="${lexiconOrder.lexiconPrice.price}"
					title="选择楹联价格" cssClass="form-control required" fieldLabels="价格" fieldKeys="price" searchLabels="价格" searchKeys="price" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="收货地址：">收货地址：</label>
				<sys:gridselect url="${ctx}/cultural/order/address/data" id="address" name="address.id" value="${lexiconOrder.address.id}" labelName="address.district" labelValue="${lexiconOrder.address.district}"
					title="选择收货地址" cssClass="form-control required" fieldLabels="地区|详情|收货人|电话" fieldKeys="district|details|name|phonenum" searchLabels="地区|详情|收货人|电话" searchKeys="district|details|name|phonenum" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="安装人员：">安装人员：</label>
				<sys:gridselect url="${ctx}/cultural/role/installer/data" id="installer" name="installer.id" value="${lexiconOrder.installer.id}" labelName="installer.name" labelValue="${lexiconOrder.installer.name}"
					title="选择安装人员" cssClass="form-control required" fieldLabels="name" fieldKeys="姓名" searchLabels="name" searchKeys="姓名" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="订单状态：">&nbsp;订单状态：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="status" items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
			</div>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<shiro:hasPermission name="cultural:order:lexiconOrder:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="cultural:order:lexiconOrder:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="cultural:order:lexiconOrder:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="cultural:order:lexiconOrder:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/cultural/order/lexiconOrder/import" method="post" enctype="multipart/form-data"
							 style="padding-left:20px;text-align:center;" ><br/>
							<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
							
							
						</form>
				</div>
			</shiro:hasPermission>
	        	<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
		    </div>
		
	<!-- 表格 -->
	<table id="lexiconOrderTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="cultural:order:lexiconOrder:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="cultural:order:lexiconOrder:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>