/*
查询价格
 */


//添加订单
function addFinishedOrder(){
    //其他成品id
    var finishedId = details.id;
    var totalPrice = details.price;
    var url = 'addFinishedOrder';
    //订单id
    var orderId;
    console.log('*******');
    console.log(finishedId);
    console.log(finishedType);
    console.log(totalPrice);
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
        // 'num':num,
    }

    $.ajax({
        type:'post',
        url:url,
        dataType:'json',
        data:param,
        async:false,
        success:function (result) {
            if(!result.success){
                layer.msg(result.msg);
                return;
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