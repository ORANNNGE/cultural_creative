<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>单图文管理</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="../video/videoList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">单图文管理列表</h3>
        </div>
        <div class="panel-body">
            <sys:message content="${message}"/>
            <div class="col-xs-12">
                <!-- tab选项卡 -->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="${ctx}/weixin/wxMediaFiles/list">单图文</a></li>
                    <li><a href="${ctx}/weixin/wxMediaFiles/list1">多图文</a></li>
                </ul>
                <!-- 面板 -->
                <div class="tab-content">
                    <div class="tab-pane active" id="pan1">
                        <div class="accordion-group">
                            <div id="collapseTwo" class="accordion-body collapse">
                                <div class="accordion-inner">
                                    <form:form id="searchForm" modelAttribute="wxMediaFiles"
                                               class="form form-horizontal well clearfix">
                                        <div class="col-xs-12 col-sm-6 col-md-4">
                                            <label class="label-item single-overflow pull-left" title="标题：">标题：</label>
                                            <form:input path="title" htmlEscape="false" maxlength="20"
                                                        class=" form-control"/>
                                        </div>
                                        <div class="col-xs-12 col-sm-6 col-md-4">
                                            <div class="form-group">
                                                <label class="label-item single-overflow pull-left"
                                                       title="创建时间：">&nbsp;创建时间：</label>
                                                <div class="col-xs-12">
                                                    <div class="col-xs-12 col-sm-5">
                                                        <div class='input-group date' id='beginCreateDate'
                                                             style="left: -10px;">
                                                            <input type='text' name="beginCreateDate"
                                                                   class="form-control"/>
                                                            <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-1">
                                                        ~
                                                    </div>
                                                    <div class="col-xs-12 col-sm-5">
                                                        <div class='input-group date' id='endCreateDate'
                                                             style="left: -10px;">
                                                            <input type='text' name="endCreateDate"
                                                                   class="form-control"/>
                                                            <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-6 col-md-4">
                                            <div style="margin-top:26px">
                                                <a id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                                        class="fa fa-search"></i> 查询</a>
                                                <a id="reset"
                                                   class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                                        class="fa fa-refresh"></i> 重置</a>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                        <!-- 工具栏 -->
                        <div id="toolbar">
                            <shiro:hasPermission name="weixin:wxMediaFiles:add">
                                <a id="add" class="btn btn-primary" href="${ctx}/weixin/wxMediaFiles/form" title="图文管理"><i
                                        class="glyphicon glyphicon-plus"></i> 新建</a> <a id="add1"
                                                                                        class="btn btn-primary"
                                                                                        title="同步音频"><i
                                    class="fa fa-arrow-down"></i>批量同步图文</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="weixin:wxMediaFiles:edit">
                                <button id="edit" class="btn btn-success" disabled onclick="edit()">
                                    <i class="glyphicon glyphicon-edit"></i> 修改
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="weixin:wxMediaFiles:del">
                                <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                                    <i class="glyphicon glyphicon-remove"></i> 删除
                                </button>
                            </shiro:hasPermission>

                            <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseTwo">
                                <i class="fa fa-search"></i> 检索
                            </a>
                        </div>
                        <!-- 表格 -->
                        <table id="wxMediaFilesTable" data-toolbar="#toolbar"></table>
                        <!-- context menu -->
                        <ul id="context-menu" class="dropdown-menu">
                            <shiro:hasPermission name="weixin:wxMediaFiles:edit">
                                <li data-item="edit"><a>编辑</a></li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="weixin:wxMediaFiles:del">
                                <li data-item="delete"><a>删除</a></li>
                            </shiro:hasPermission>
                            <li data-item="action1"><a>取消</a></li>
                        </ul>
                    </div>
                    <div class="tab-pane fade in" id="pan2">搜索面板内容搜索面板内容</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>