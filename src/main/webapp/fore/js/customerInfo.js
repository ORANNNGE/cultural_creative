//获取到用户信息

var url = 'getCustomerInfo';
function getCustomerInfo(){
    var data;
    $.ajax({
        type:'post',
        dataType:'json',
        url:url,
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}

var data = getCustomerInfo();
var customerInfo = data.data;
new Vue({
    el:'#customerInfo',
    data:{
        data:customerInfo,
    }
});