/**
 * 楹联详情页
 */
var pathName = window.location.pathname;
console.log(pathName);
var url;
var sizeUrl;
var comboUrl;
var finishedType;
switch (pathName) {
    case "/fore/newYearPicDetails.jsp":
        url = "getNewYearPicById";
        finishedType = '1';
        break;
    case "/fore/paintingDetails.jsp":
        url = "getPaintingById";
        finishedType = '2';
        break;
    case "/fore/calligraphyDetails.jsp":
        url = "getCalligraphyById";
        finishedType = '3';
        break;
    case "/fore/decorationDetails.jsp":
        url = "getDecorationById";
        finishedType = '4';
        break;
    case "/fore/coupletsDetails.jsp":
        url = "getCoupletsById";
        sizeUrl = "getCoupletsSize";
        comboUrl = "getCoupletsCombo";
        break;
    case "/fore/lexiconDetails.jsp":
        url = "getLexiconById";
        sizeUrl = "getLexiconSize";
        comboUrl = "getLexiconCombo";
        break;

    default:
        url = "";
}


//获取详情
console.log(url);
//id
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
// var spec = specData.data;

var data = getDetails();
console.log(data);
//详情
var details = data.data;


//根据楹联类型获取尺寸
var type;
if(pathName == "/fore/coupletsDetails.jsp"){
    type = details.lexicon.type;
}
if(pathName == "/fore/lexiconDetails.jsp"){
    type = details.type;
}
console.log(type);
function getSize(){
    var data;
    $.ajax({
        type:'post',
        url:sizeUrl,
        data:{
          'type':type
        },
        dataType:'json',
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}
var sizeData = getSize();
var sizeList = sizeData.sizeList;
var typefaceList = sizeData.typefaceList;



//Vue实例
var vm = new Vue({
    el:'#details',
    data: {
        data:details,
        sizeList:sizeList,
        typefaceList:typefaceList,
        comboList:{
        },
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
        },
        getCombo:function () {

        }
    }
})

//根据楹联类型和尺寸size获取套餐combo
function getCoupletsCombo(sizeId){
    $.ajax({
        type:'post',
        url:comboUrl,
        data:{
            'type':type,
            'sizeId':sizeId,
        },
        dataType:'json',
        // async:false,
        success:function (result) {
            data = result.body;
            if(!result.success){
                layer.msg(result.msg);
                return;
            }
            vm.comboList = data.comboList;
        }
    })
}
//根据楹联类型和尺寸size获取套餐combo
function getLexiconCombo(sizeId,typefaceId){
    $.ajax({
        type:'post',
        url:comboUrl,
        data:{
            'type':type,
            'sizeId':sizeId,
            'typefaceId':typefaceId,
        },
        dataType:'json',
        // async:false,
        success:function (result) {
            data = result.body;
            if(!result.success){
                layer.msg(result.msg);
                return;
            }
            vm.comboList = data.comboList;
        }
    })
}
