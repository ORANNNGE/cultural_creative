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

    default:
        url = "";
}

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

var data = getDetails();
//详情
var details = data.data;
//Vue实例
new Vue({
    el:'#details',
    data: {
        data:details,
    },
    computed: {
        details:function () {
            var text = this.data.details;
            return parseBlob(text);
        }
    }
})
