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
var specUrl;
switch (pathName) {
    case "/fore/coupletsDetails.jsp":
        specUrl = "getCoupletsSpec";
        break;
    case "/fore/lexiconDetails.jsp":
        specUrl = "getLexiconSpec";
        break;

}
function getSpec(){
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
var specData = getSpec();
// var spec = specData.data;

var data = getDetails();
//详情
var details = data.data;
//Vue实例
var vm = new Vue({
    el:'#details',
    data: {
        data:details,
        spec:specData,
        thePrice:{
            'price':{'price':0}
        },
        num:1
    },
    computed: {
        details:function () {
            var text = this.data.details;
            return parseBlob(text);
        },
        meaning:function () {
            var text = this.data.meaning;
            return parseBlob(text);
        },
        totalPrice:function () {
            var num = parseFloat(this.num);
            var price = parseFloat(this.thePrice.price.price);
            return parseFloat(num*price).toFixed(2);
        }

    },
    methods:{
        plus:function () {
            this.num++;
        },
        minus:function () {
            if(this.num == 0) return;
            this.num--;
        }
    }
})
