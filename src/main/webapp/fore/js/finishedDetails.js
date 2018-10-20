/*
查询价格
 */


//添加订单
function addFinishedOrder(){
    //
    var finishedId = details.id;
    var price = details.price;
    var url = 'addFinishedOrder';
    //总价
    // var totalPrice = vm.totalPrice;
    //购买数量
    // var num = vm.num;
    // if(!coupletsPriceId || !sizeId || !frameId || !craftId){
    //     layer.msg('请选择规格');
    //     return;
    // }
    // if(num == 0){
    //     layer.msg('请选择数量');
    //     return;
    // }

    console.log('*******');

    console.log(finishedId);
    console.log(finishedType);
    console.log(price);
    console.log('*******');

    // console.log(num);
    // console.log(totalPrice);
    // console.log(coupletsId);
    // console.log(coupletsPriceId);
    var param = {
        'type':finishedType,
        'finishedId':finishedId,
        'finishedType':finishedType,
        'price':price,
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
            layer.open({
                content: '订单已生成，是否立即付款？',
                yes: function (index, layero) {
                    //do something
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            })


        }
    })
}