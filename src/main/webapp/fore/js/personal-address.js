//获取到用户信息

var url = 'getAddressList';
function getAddressList(){
    var data;
    $.ajax({
        type:'post',
        dataType:'json',
        url:url,
        async:false,
        success:function (result) {
            if(!result.success){
                layer.msg(result.msg);
                return;
            }
            data = result.body;
            console.log(result.msg);
            console.log(data);
        }
    })
    return data;
}

var data = getAddressList();
var addressList = data.data;
new Vue({
    el:'#addressList',
    data:{
        data:addressList,
    }
});