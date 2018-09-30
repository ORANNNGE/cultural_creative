/**
 * 书画作品页
 */
var url;
var defaultPageSize = 4;
//获取书法作品
function getClgrafAndPaintList(type,pageNum,pageSize){
    if(type == '1'){
       url = '../fore/getCalligraphyList';
    }
    if(type == '2'){
        url = '../fore/getPaintingList';
    }
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

//书法作品
var calligraphyData = getClgrafAndPaintList('1',1,defaultPageSize);
var calligraphyPage = calligraphyData.page;
var calligraphyList = calligraphyPage.list;
//美术楹联
var paintingData = getClgrafAndPaintList('2',1,defaultPageSize);
var paintingPage = paintingData.page;
var paintingList = paintingPage.list;

//Vue实例
var vm = new Vue({
    el:'#clgrafAndPaint',
    data: {
        list:calligraphyList,
        page:calligraphyPage,
    },
    methods:{
        //下拉加载更多
        loadMore:function(type,pageNum,pageSize){
            // console.log(type);
            // console.log(pageNum);
            // console.log(pageSize);
            var loadData = getClgrafAndPaintList(type,pageNum,pageSize);
            var loadPage = loadData.page;
            var loadList = loadPage.list;
            //遍历将加载的数据加到couplets
            for(var i = 0 ;i < loadList.length; i++){
                console.log("load:"+i);
                var item = loadList[i];
                vm.list.push(item);
            }
            this.page = loadPage;

        },
        calligraphies:function () {
            this.list = calligraphyList;
            this.page = calligraphyPage;
            $('#calligraphies').attr("class","on");
            $('#paintings').attr("class","");
        },
        paintings:function () {
            this.list = paintingList;
            this.page = paintingPage;
            $('#calligraphies').attr("class","");
            $('#paintings').attr("class","on");
        },
        //跳转到书画详情页
        clgrafAndPaintDetails:function (id) {
            var isCalligraphyOn =  $('#calligraphies').attr("class") == 'on' ? true : false;
            var isPaintingsOn =  $('#paintings').attr("class") == 'on' ? true : false;
            console.log(isCalligraphyOn);
            console.log(isPaintingsOn);
            if(isCalligraphyOn) location.href='calligraphyDetails.jsp?id='+id;
            if(isPaintingsOn) location.href='paintingDetails.jsp?id='+id;
        },
    }
})
//下拉加载
$(window).scroll(function(){
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    //判断当前显示的书画类型
    var calligraphyOn = $('#calligraphies').attr('class') == 'on' ? true : false;
    var paintingOn = $('#paintings').attr('class') == 'on' ? true : false;
    if(scrollTop + windowHeight == scrollHeight){
        if(calligraphyOn){
            var page = vm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            // console.log('C pageNum:'+pageNum);
            if(!isLastPage){
                vm.loadMore('1',pageNum,defaultPageSize);
            }
        }
        if(paintingOn){
            var page = vm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            // console.log('P pageNum:'+pageNum);
            if(!isLastPage){
                vm.loadMore('2',pageNum,defaultPageSize);
            }
        }
    }
})
