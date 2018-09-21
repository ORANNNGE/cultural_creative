/**
 * 首页index
 */
//首页的数据
var data;
//请求首页的数据
function getIndexData(){
    $.ajax({
        type:'post',
        dataType:'json',
        url:'../fore/getIndexData',
        async:false,
        success:function(result) {
            data = result.body;
        }
    })
}
//调用ajax请求
getIndexData();

//测试过滤器
Vue.filter('test',function (value) {
    return value + '...';
})

//机关单位楹联
var government = data.government;
//单位楹联
var company = data.company;
// 家庭楹联
var home = data.home;
//装饰品
var decorations = data.decorations;
//年画
var newYearPics = data.newYearPics;
//美术作品
var paintings = data.paintings;
//书法作品
var calligraphies = data.calligraphies;

// Vue实例
var indexVm = new Vue({
    el: '#index',
    data:{
        gvmtAndCpnData: government,
        clgpAndPntData: calligraphies,
        homeData: home,
        decorationsData: decorations,
        newYearPicsData: newYearPics,
    },
    methods:{
        calligraphies:function () {
            this.clgpAndPntData = calligraphies;
            $('#calligraphies').attr("class","on");
            $('#paintings').attr("class","");
        },
        paintings:function () {
            this.clgpAndPntData = paintings;
            $('#calligraphies').attr("class","");
            $('#paintings').attr("class","on");
        },
        companyCouplets:function () {
            this.gvmtAndCpnData = company;
            $('#companyCouplets').attr("class","on");
            $('#governmentCouplets').attr("class","");
        },
        governmentCouplets:function () {
            this.gvmtAndCpnData = government;
            $('#companyCouplets').attr("class","");
            $('#governmentCouplets').attr("class","on");
        }
    }
})

/*
function toIndexGovAndComp(){
    var type = '1';
    var governmentOn =  $('#governmentCouplets').attr("class") == 'on' ? true : false;
    if(!governmentOn){
        type = '2';
    }
    location.href = ''
}*/
