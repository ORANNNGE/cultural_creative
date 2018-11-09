<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>帮助管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<script type="text/javascript" charset="utf-8" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.all.js"></script>
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
              var docContent = UE.getEditor('editor').getContent();
              $('#details').val(docContent);
			  jp.loading();
					// $("input[name='details']").val($('#details').summernote('code'));//取富文本的值
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/cultural/platform/platHelp/save",$('#inputForm').serialize(),function(data){
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
			// $('#details').summernote({
			// 	height: 300,
            //     lang: 'zh-CN'
            // });
		});
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="platHelp" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">详情：</label></td>
					<td class="width-55">
                        <%--<input type="hidden" name="details"/>--%>
						<%--<div id="details">--%>
                          <%--${fns:unescapeHtml(platHelp.details)}--%>
                        <%--</div>--%>
								<form:hidden path="details" htmlEscape="true"/>
							<script id="editor" type="text/plain" style="width:100%;height:500px;"></script>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
                    <script type="text/javascript">
                    var ue = UE.getEditor('editor');
                    $(function () {
                        var content = $('#details').val();
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