/**
 * 楹联词库页
 */
var url = '../fore/getLexiconList';
//默认分页大小
var defaultPageSize = 6;
function getLexicon(pageNum,pageSize){
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
var lexiconData = getLexicon(1,defaultPageSize);
var lexiconPage = lexiconData.page;
var lexiconList = lexiconPage.list;


//Vue实例
var lexiconVm = new Vue({
    el:'#lexicon',
    data: {
        lexicons:lexiconList,
        page:lexiconPage,
    },
    methods:{
        //下拉加载更多
        loadMore:function(pageNum,pageSize){
            console.log(pageNum);
            console.log(pageSize);
            var loadData = getLexicon(pageNum,pageSize);
            var loadPage = loadData.page;
            var loadLexicon = loadPage.list;
            //遍历将加载的数据加到couplets
            for(var i = 0 ;i < loadLexicon.length; i++){
                console.log("load:"+i);
                var item = loadLexicon[i];
                lexiconVm.lexicons.push(item);
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
            var page = lexiconVm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            console.log('pageNum:'+pageNum);
            if(!isLastPage){
                lexiconVm.loadMore(pageNum,defaultPageSize);
            }
    }
})
