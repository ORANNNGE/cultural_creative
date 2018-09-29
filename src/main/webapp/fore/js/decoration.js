/**
 * 楹联词库页
 */
var url = '../fore/getDecorationList';
//默认分页大小
var defaultPageSize = 6;
function getDecorationList(pageNum,pageSize){
    var data;
    var param = {
        pageNum:pageNum,
        pageSize:pageSize
    }
    $.ajax({
        type:'post',
        url:url,
        dataType:'json',
        data:param,
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}
//初始化page
var decorationData = getDecorationList(1,defaultPageSize);
var decorationPage = decorationData.page;
var decorationList = decorationPage.list;


//Vue实例
var decorationVm = new Vue({
    el:'#decoration',
    data: {
        decorations:decorationList,
        page:decorationPage,
    },
    methods:{
        //下拉加载更多
        loadMore:function(pageNum,pageSize){
            console.log(pageNum);
            console.log(pageSize);
            var loadData = getDecorationList(pageNum,pageSize);
            var loadPage = loadData.page;
            var loadDecoration = loadPage.list;
            //遍历将加载的数据加到couplets
            for(var i = 0 ;i < loadDecoration.length; i++){
                console.log("load:"+i);
                var item = loadDecoration[i];
                decorationVm.decorations.push(item);
            }
            this.page = loadPage;
        },
    }
})
//下拉加载
$(window).scroll(function(){
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    if(scrollTop + windowHeight == scrollHeight){
        var page = decorationVm.page;
        var pageNum = page.pageNum + 1;
        var isLastPage = page.isLastPage;
        console.log('pageNum:'+pageNum);
        if(!isLastPage){
            decorationVm.loadMore(pageNum,defaultPageSize);
        }
    }
})
