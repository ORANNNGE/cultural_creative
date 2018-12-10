/*
查询价格
 */


//添加订单
function addFinishedOrder(){
    //其他成品id
    var finishedId = details.id;
    var totalPrice = vm.totalPrice;
    var url = 'addFinishedOrder';
    var num = vm.num;
    //订单id
    var orderId;
    console.log('*******');
    console.log(finishedId);
    console.log(finishedType);
    console.log(totalPrice);
    console.log(num);
    console.log('*******');

    // console.log(num);
    // console.log(totalPrice);
    // console.log(coupletsId);
    // console.log(coupletsPriceId);
    var param = {
        'type':finishedType,
        'finishedId':finishedId,
        // 'finishedType':finishedType,
        'price':totalPrice,
        'num':num,
    }

    $.ajax({
        type:'post',
        url:url,
        dataType:'json',
        data:param,
        async:false,
        success:function (result) {
            if(!result.success){
                if(result.errorCode != -1){
                    layer.msg(result.msg);
                    return;
                }
                layer.open({
                    type:2,
                    title:'请设置收货地址',
                    area: ['380px', '80%'],
                    shadeClose: true,
                    shade: 0.5,
                    content: 'personal-address.jsp',
                })
            }
            //订单id
            orderId = result.body.orderId;
            layer.open({
                content: '订单已生成，是否立即付款？',
                yes: function (index, layero) {
                    //do something
                    payReq(orderType,orderId,totalPrice)
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            })
        }
    })
}