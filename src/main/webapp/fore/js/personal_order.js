
function getOrderList() {
    var url = 'getOrderList';
    var data;
    $.ajax({
        type:'post',
        url:url,
        dataType:'json',
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}

var allList = getOrderList();
var coupletsOrderList = allList.coupletsOrderList;
// var coupletsPrice = coupletsOrderList.coupletsPrice;
var lexiconOrderList = allList.lexiconOrderList;
var finishedOrderList = allList.finishedOrderList;
console.log(coupletsOrderList);
console.log(lexiconOrderList);
console.log(finishedOrderList);
var vm = new Vue({
    el:'#orderList',
    data:{
        'coupletsOrderList':coupletsOrderList,
        'lexiconOrderList':lexiconOrderList,
        'finishedOrderList':finishedOrderList,
    }
})
//显示所有订单
function allOrder() {
    $('#allOrder').attr('class','on');
    $('#nonPayment').attr('class','');
    $('#nonInstall').attr('class','');
    $('#hadDone').attr('class','');
    $('.order-div ul li').each(function () {
        $(this).show();
    })
}
//待付款订单
function nonPayment() {
    $('#allOrder').attr('class','');
    $('#nonPayment').attr('class','on');
    $('#nonInstall').attr('class','');
    $('#hadDone').attr('class','');
    $('.order-div ul li').each(function () {
        $(this).show();
        var status = $(this).attr('data-status');
        if(status != 1){
            $(this).hide();
        }
    })
}
//待安装订单
function nonInstall() {
    $('#allOrder').attr('class','');
    $('#nonPayment').attr('class','');
    $('#nonInstall').attr('class','on');
    $('#hadDone').attr('class','');
    $('.order-div ul li').each(function () {
        $(this).show();
        var status = $(this).attr('data-status');
        if(status != 2){
            $(this).hide();
        }
    })

}
//已完成订单
function hadDone() {
    $('#allOrder').attr('class','');
    $('#nonPayment').attr('class','');
    $('#nonInstall').attr('class','');
    $('#hadDone').attr('class','on');
    $('.order-div ul li').each(function () {
        $(this).show();
        var status = $(this).attr('data-status');
        if(status != 3){
            $(this).hide();
        }
    })
}

//跳转到此页面
var status = getParaFromURL('status');
console.log(status);
switch (status) {
    case '1': nonPayment();break;
    case '2': nonInstall();break;
    case '3': hadDone();break;
    default: allOrder();
}
//付款
function toPay(object) {
    var order = $(object).parent('div').parent('li');
    // var orderId = order.attr('data-orderId');
    var orderId = $(order).attr('data-id');
    var orderType = $(order).attr('data-type');
    var orderPrice = $(order).attr('data-price');
    //发起付款请求
    payReq(orderType,orderId,orderPrice);
}
//删除订单
function delOrder(object){
    //获取orderId
    //弹出是否删除按钮
    layer.open({
        content:'是否删除该订单',
        yes:function (index,layero) {
            var order = $(object).parent('div').parent('li');
            var orderId = $(order).attr('data-id');//订单id
            var orderType = $(order).attr('data-type');//订单类型
            //请求参数
            var param={
                'orderId':orderId,
                'orderType':orderType
            }
            $.ajax({
                url:'delOrder',
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

//确认订单
function ensureOrder(object){
    //获取orderId
    //弹出是否完成按钮
    layer.open({
        content:'确认该订单已完成',
        yes:function (index,layero) {
            var order = $(object).parent('div').parent('li');
            var orderId = $(order).attr('data-id');//订单id
            var orderType = $(order).attr('data-type');//订单类型
            //请求参数
            var param={
                'orderId':orderId,
                'orderType':orderType
            }
            $.ajax({
                url:'ensureOrder',
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