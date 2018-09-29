/**
 * 年画页
 */
var url = '../fore/getNewYearPicList';
//默认分页大小
var defaultPageSize = 6;
function getNewYearPic(pageNum,pageSize){
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
var newYearPicData = getNewYearPic(1,defaultPageSize);
var newYearPicPage = newYearPicData.page;
var newYearPicList = newYearPicPage.list;


//Vue实例
var newYearPicVm = new Vue({
    el:'#newYearPic',
    data: {
        newYearPics:newYearPicList,
        page:newYearPicPage,
    },
    methods:{
        //下拉加载更多
        loadMore:function(pageNum,pageSize){
            console.log(pageNum);
            console.log(pageSize);
            var loadData = getNewYearPic(pageNum,pageSize);
            var loadPage = loadData.page;
            var loadNewYearPic = loadPage.list;
            //遍历将加载的数据加到couplets
            for(var i = 0 ;i < loadNewYearPic.length; i++){
                console.log("load:"+i);
                var item = loadNewYearPic[i];
                newYearPicVm.newYearPics.push(item);
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
        var page = newYearPicVm.page;
        var pageNum = page.pageNum + 1;
        var isLastPage = page.isLastPage;
        console.log('pageNum:'+pageNum);
        if(!isLastPage){
            newYearPicVm.loadMore(pageNum,defaultPageSize);
        }
    }
})
