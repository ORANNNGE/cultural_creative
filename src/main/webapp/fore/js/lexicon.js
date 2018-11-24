/**
 * 楹联词库页
 */
var url = '../fore/getLexiconList';
//默认分页大小
var defaultPageSize = 4;
function getLexicon(type,pageNum,pageSize){
    var data;
    var param = {
        'type':type,
        'pageNum':pageNum,
        'pageSize':pageSize
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
// var lexiconData = getLexicon('1',1,defaultPageSize);
// var lexiconPage = lexiconData.page;
// var lexiconList = lexiconPage.list;

//机关单位楹联
var governmentData = getLexicon('1',1,defaultPageSize);
var governmentPage = governmentData.page;
var governmentLexicon = governmentPage.list;
//商户楹联
var companyData = getLexicon('2',1,defaultPageSize);
var companyPage = companyData.page;
var companyLexicons = companyPage.list;
//家庭楹联
var homeData = getLexicon('3',1,defaultPageSize);
var homePage = homeData.page;
var homeLexicon = homePage.list;

//Vue实例
var lexiconVm = new Vue({
    el:'#lexicon',
    data: {
        lexicons:homeLexicon,
        page:homePage,
    },
    methods:{
        //下拉加载更多
        loadMore:function(type,pageNum,pageSize){
            console.log(pageNum);
            console.log(pageSize);
            var loadData = getLexicon(type,pageNum,pageSize);
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
        governmentLexicon:function () {
            $('#government').attr('class','on');
            $('#company').attr('class','');
            $('#home').attr('class','');
            this.lexicons = governmentLexicon;
            this.page = governmentPage;
        },
        companyLexicon:function () {
            //选种效果
            $('#government').attr('class','');
            $('#company').attr('class','on');
            $('#home').attr('class','');
            this.lexicons = companyLexicons;
            this.page = companyPage;
        },
        homeLexicon:function () {
            $('#government').attr('class','');
            $('#company').attr('class','');
            $('#home').attr('class','on');
            this.lexicons = homeLexicon;
            this.page = homePage;
        }
    }
})
//下拉加载
$(window).scroll(function(){
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    //判断当前显示的词库类型
    var governmentOn = $('#government').attr('class') == 'on' ? true : false;
    var companyOn = $('#company').attr('class') == 'on' ? true : false;
    var homeOn = $('#home').attr('class') == 'on' ? true : false;


    if(scrollTop + windowHeight == scrollHeight){
        if(governmentOn){
            var page = lexiconVm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            console.log('pageNum:'+pageNum);
            if(!isLastPage){
                lexiconVm.loadMore('1',pageNum,defaultPageSize);
            }
        }
        if(companyOn){
            var page = lexiconVm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            console.log('pageNum:'+pageNum);
            if(!isLastPage){
                lexiconVm.loadMore('2',pageNum,defaultPageSize);
            }
        }
        if(homeOn){
            var page = lexiconVm.page;
            var pageNum = page.pageNum + 1;
            var isLastPage = page.isLastPage;
            console.log('pageNum:'+pageNum);
            if(!isLastPage){
                lexiconVm.loadMore('3',pageNum,defaultPageSize);
            }
        }


    }
})
