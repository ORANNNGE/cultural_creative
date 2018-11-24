/**
 * 楹联成品页
 */
var url = '../fore/getCoupletsList';
var defaultPageSize = 6;
function getCouplets(type,pageNum,pageSize){
    var data;
    var param = {
        type:type,
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
//机关单位楹联
var governmentData = getCouplets('1',1,defaultPageSize);
var governmentPage = governmentData.page;
var governmentCouplets = governmentPage.list;
//商户楹联
var companyCoupletsData = getCouplets('2',1,defaultPageSize);
var companyPage = companyCoupletsData.page;
var companyCouplets = companyPage.list;
//家庭楹联
var homeCoupletsData = getCouplets('3',1,defaultPageSize);
var homePage = homeCoupletsData.page;
var homeCouplets = homePage.list;

//Vue实例
var coupletsVm = new Vue({
    el:'#couplets',
    data: {
        couplets:homeCouplets,
        page:homePage,
    },
    methods:{
        //下拉加载更多
        loadMore:function(type,pageNum,pageSize){
            console.log(type);
            console.log(pageNum);
            console.log(pageSize);
            var loadData = getCouplets(type,pageNum,pageSize);
            var loadPage = loadData.page;
            var loadCouplets = loadPage.list;
            //遍历将加载的数据加到couplets
            for(var i = 0 ;i < loadCouplets.length; i++){
                console.log("load:"+i);
                var item = loadCouplets[i];
                coupletsVm.couplets.push(item);
            }
            this.page = loadPage;

        },
        //显示机关单位楹联
        governmentCouplets:function () {
            //选种效果
            $('#government').attr('class','on');
            $('#company').attr('class','');
            $('#home').attr('class','');
            this.couplets = governmentCouplets;
            this.page = governmentPage;
        },
        //显示商户楹联
        companyCouplets:function () {
            //选种效果
            $('#government').attr('class','');
            $('#company').attr('class','on');
            $('#home').attr('class','');
            this.couplets = companyCouplets;
            this.page = companyPage;
        },
        //显示家庭楹联
        homeCouplets:function () {
            //选种效果
            $('#government').attr('class','');
            $('#company').attr('class','');
            $('#home').attr('class','on');
            this.couplets = homeCouplets;
            this.page = homePage;
        },
    }
})
//下拉加载
$(window).scroll(function(){
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    //判断当前显示的楹联类型
    var governmentOn = $('#government').attr('class') == 'on' ? true : false;
    var companyOn = $('#company').attr('class') == 'on' ? true : false;
    var homeOn = $('#home').attr('class') == 'on' ? true : false;
    // console.log(govermentOn);
    if(scrollTop + windowHeight == scrollHeight){
        if(governmentOn){
            var page = coupletsVm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            console.log('pageNum:'+pageNum);
            if(!isLastPage){
                coupletsVm.loadMore('1',pageNum,defaultPageSize);
            }
        }
        if(companyOn){
            var page = coupletsVm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            console.log('pageNum:'+pageNum);
            if(!isLastPage){
                coupletsVm.loadMore('2',pageNum,defaultPageSize);
            }
        }
        if(homeOn){
            var page = coupletsVm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            console.log('pageNum:'+pageNum);
            if(!isLastPage){
                coupletsVm.loadMore('3',pageNum,defaultPageSize);
            }
        }
    }
})
