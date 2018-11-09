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

function setDefaultAddr(object){
    $('input:checked').attr('checked',false);
    $(object).attr('checked',true);
    var addr = $(object).parent('label').parent('div').parent('li');
    var id = $(addr).attr('data-id');//地址id
    //请求参数
    var param={
        'id':id,
    }
    $.ajax({
        url:'setDefaultAddr',
        data:param,
        dataType:'json',
        type:'post',
        success:function (result) {
            if(result.success == true){
                layer.msg(result.msg)
                window.location.reload();
                return;
            }
            layer.msg(result.msg);
        }
    })
}

//删除收货地址
function delAddr(object) {
    layer.open({
        content:'是否删除',
        yes:function (index,layero) {
            var addr = $(object).parent('div').parent('li');
            var id = $(addr).attr('data-id');//地址id
            //请求参数
            var param={
                'id':id,
            }
            $.ajax({
                url:'delAddr',
                data:param,
                dataType:'json',
                type:'post',
                success:function (result) {
                    if(result.success == true){
                        layer.msg(result.msg)
                        window.location.reload();
                        return;
                    }
                    layer.msg(result.msg);
                }
            })
        }
    })

}