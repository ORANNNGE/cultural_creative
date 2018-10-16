/**
 * 楹联详情页
 */
var pathName = window.location.pathname;
console.log(pathName);
var url;
switch (pathName) {
    case "/fore/coupletsDetails.jsp":
        url = "getCoupletsById";
        break;
    case "/fore/newYearPicDetails.jsp":
        url = "getNewYearPicById";
        break;
    case "/fore/calligraphyDetails.jsp":
        url = "getCalligraphyById";
        break;
    case "/fore/paintingDetails.jsp":
        url = "getPaintingById";
        break;
    case "/fore/decorationDetails.jsp":
        url = "getDecorationById";
        break;
    case "/fore/lexiconDetails.jsp":
        url = "getLexiconById";
        break;

    default:
        url = "";
}

//获取规格
var specUrl = 'getCoupletsSpec';
function getCoupletsSpec(){
    var data;
    $.ajax({
        type:'post',
        url:specUrl,
        dataType:'json',
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}

//获取详情
console.log(url);
var param = getParaFromURL('id');
function getDetails(){
    var data;
    var params = {
        id:param
    }
    $.ajax({
        type:'post',
        url:url,
        dataType:'json',
        data:params,
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}
var specData = getCoupletsSpec();
// var spec = specData.data;

var data = getDetails();
//详情
var details = data.data;
//Vue实例
new Vue({
    el:'#details',
    data: {
        data:details,
        spec:specData,
    },
    computed: {
        details:function () {
            var text = this.data.details;
            return parseBlob(text);
        },
        meaning:function () {
            var text = this.data.meaning;
            return parseBlob(text);
        }

    }
})

/*
查询价格
 */
var sizeId;
var frameId;
var craftId;
function getCoupletsPrice(object) {
    var type = $(object).attr('data-type');
    var type = $(object).attr('data-type');
    $(object).css('background','#f00');
    if(type == 'size'){
        sizeId = $(object).attr('data-id');
    }
    if(type == 'frame'){
        frameId = $(object).attr('data-id');
    }
    if(type == 'craft'){
        craftId = $(object).attr('data-id');
    }
}