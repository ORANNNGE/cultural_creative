<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#lexiconOrderTable').bootstrapTable({
		 
		  //请求方法
               method: 'get',
               //类型json
               dataType: "json",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/cultural/order/lexiconOrder/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                   	edit(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该楹联词库订单记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/cultural/order/lexiconOrder/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#lexiconOrderTable').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	})
                   	   
                   	});
                      
                   } 
               },
              
               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'customer.nickname',
		        title: '用户',
		        sortable: true
		        ,formatter:function(value, row , index){
 			    if(value == null){
		            	return "<a href='javascript:edit(\""+row.id+"\")'>-</a>";
		            }else{
		                return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
		            }
		        }
		       
		    }
			,{
		        field: 'lexicon.title',
		        title: '楹联词库',
		        sortable: true
		       
		    }
			,{
		        field: 'lexiconPrice.price',
		        title: '楹联价格',
		        sortable: true
		       
		    }
			,{
			   field: 'lexiconPrice.lexiconName',
			   title: '楹联标题',
			   sortable: true

			}
			,{
			   field: 'lexiconPrice.authorname',
			   title: '作者',
			   sortable: true

			}
			,{
			   field: 'lexiconPrice.typefaceName',
			   title: '字体',
			   sortable: true

			}
			,{
			   field: 'lexiconPrice.sizeName',
			   title: '尺寸',
			   sortable: true

			}
			,{
			   field: 'lexiconPrice.frameName',
			   title: '框',
			   sortable: true

			}
			,{
			   field: 'lexiconPrice.craftName',
			   title: '工艺',
			   sortable: true

			}
			,{
		        field: 'address.district',
		        title: '收货地址',
		        sortable: true
		       
		    }
			,{
			   field: 'address.details',
			   title: '详细地址',
			   sortable: true

			}
			,{
			   field: 'address.name',
			   title: '收货人',
			   sortable: true

			}
			,{
			   field: 'address.phonenum',
			   title: '联系电话',
			   sortable: true

			}
			,{
		        field: 'installer.name',
		        title: '安装人员',
		        sortable: true
		       
		    }
			,{
		        field: 'installer.phonenum',
		        title: '联系电话',
		        sortable: true

		    }
			,{
		        field: 'num',
		        title: '数量',
		        sortable: true
		       
		    }
			,{
		        field: 'totalPrice',
		        title: '总价',
		        sortable: true
		       
		    }
			,{
		        field: 'status',
		        title: '订单状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('order_status'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#lexiconOrderTable').bootstrapTable("toggleView");
		}
	  
	  $('#lexiconOrderTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#lexiconOrderTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#lexiconOrderTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/cultural/order/lexiconOrder/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jp.loading('  正在导入，请稍等...');
				        }
				        inputForm.submit();
					    jp.close(index);
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		});
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#lexiconOrderTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#lexiconOrderTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#lexiconOrderTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该楹联词库订单记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/cultural/order/lexiconOrder/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#lexiconOrderTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
   function add(){
	  jp.openDialog('新增楹联词库订单', "${ctx}/cultural/order/lexiconOrder/form",'800px', '500px', $('#lexiconOrderTable'));
  }
  function edit(id){//没有权限时，不显示确定按钮
  	  if(id == undefined){
			id = getIdSelections();
		}
	   <shiro:hasPermission name="cultural:order:lexiconOrder:edit">
	  jp.openDialog('编辑楹联词库订单', "${ctx}/cultural/order/lexiconOrder/form?id=" + id,'800px', '500px', $('#lexiconOrderTable'));
	   </shiro:hasPermission>
	  <shiro:lacksPermission name="cultural:order:lexiconOrder:edit">
	  jp.openDialogView('查看楹联词库订单', "${ctx}/cultural/order/lexiconOrder/form?id=" + id,'800px', '500px', $('#lexiconOrderTable'));
	  </shiro:lacksPermission>
  }

</script>