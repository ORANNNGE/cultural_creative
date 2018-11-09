<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>成品楹联管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="coupletsList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">成品楹联列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="couplets" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="标题：">标题：</label>
				<form:input path="name" htmlEscape="false" maxlength="255"  class=" form-control"/>
			</div>
			<%-- <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="词库：">词库：</label>
				<sys:gridselect url="${ctx}/cultural/couplets/lexicon/data" id="lexicon" name="lexicon.id" value="${couplets.lexicon.id}" labelName="lexicon.title" labelValue="${couplets.lexicon.title}"
					title="选择词库" cssClass="form-control required" fieldLabels="标题|上联|下联|横批" fieldKeys="title|rightline|leftline|topline" searchLabels="标题|上联|下联|横批" searchKeys="title|rightline|leftline|topline" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="尺寸：">尺寸：</label>
				<sys:gridselect url="${ctx}/cultural/spec/size/data" id="size" name="size.id" value="${couplets.size.id}" labelName="size.name" labelValue="${couplets.size.name}"
					title="选择尺寸" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="楹联框：">楹联框：</label>
				<sys:gridselect url="${ctx}/cultural/spec/frame/data" id="frame" name="frame.id" value="${couplets.frame.id}" labelName="frame.name" labelValue="${couplets.frame.name}"
					title="选择楹联框" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="制作工艺：">制作工艺：</label>
				<sys:gridselect url="${ctx}/cultural/spec/craft/data" id="craft" name="craft.id" value="${couplets.craft.id}" labelName="craft.name" labelValue="${couplets.craft.name}"
					title="选择制作工艺" cssClass="form-control required" fieldLabels="名称" fieldKeys="name" searchLabels="名称" searchKeys="name" ></sys:gridselect>
			</div>--%>
			 <%--<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="推荐到首页：">&nbsp;推荐到首页：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="recommend" items="${fns:getDictList('cultural_recommend')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
			</div>--%>
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
			<shiro:hasPermission name="cultural:couplets:couplets:add">
				<a id="add" class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="cultural:couplets:couplets:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="cultural:couplets:couplets:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="cultural:couplets:couplets:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/cultural/couplets/couplets/import" method="post" enctype="multipart/form-data"
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
	<table id="coupletsTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="cultural:couplets:couplets:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="cultural:couplets:couplets:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>