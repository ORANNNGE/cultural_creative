/*
查询价格
 */
var sizeId;
var frameId;
var craftId;
var coupletsId = details.id;
var coupletsPriceId
function getCoupletsPrice(object) {
    var type = $(object).attr('data-type');

    if(type == 'size'){
        sizeId = $(object).attr('data-id');
    }
    if(type == 'frame'){
        frameId = $(object).attr('data-id');
    }
    if(type == 'craft'){
        craftId = $(object).attr('data-id');
    }
    if(sizeId && frameId && craftId && coupletsId){
        console.log(sizeId);
        console.log(frameId);
        console.log(craftId);
        var url = 'getCoupletsPrice';
        var param = {
            'sizeId':sizeId,
            'frameId':frameId,
            'craftId':craftId,
            'coupletsId':coupletsId
        };

        $.ajax({
            type:'post',
            url:url,
            dataType:'json',
            data:param,
            async:false,
            success:function (result) {
                vm.coupletsPrice = result.body;
                coupletsPriceId = result.body.price.id;
            }
        })
    }
}


//添加订单
function addCoupletsOrder(){
    //
    var url = 'addCoupletsOrder';
    //总价
    var totalPrice = vm.totalPrice;
    //购买数量
    var num = vm.num;
    if(!coupletsPriceId && !sizeId && !frameId && !craftId){
        layer.msg('请选择规格');
        return;
    }
    if(num == 0){
        layer.msg('请选择数量');
        return;
    }

    console.log('*******');
    console.log(num);
    console.log(totalPrice);
    console.log(coupletsId);
    console.log(coupletsPriceId);
    var param = {
        'coupletsPriceId':coupletsPriceId,
        'coupletsId':coupletsId,
        'totalPrice':totalPrice,
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
                layer.msg(result.msg);
                return;
            }
            layer.open({
                content: '是否立即付款',
                yes: function (index, layero) {
                    //do something
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            })


        }
    })
}
// $(function () {
//
//     layer.open({
//         content: '是否立即付款',
//         yes: function (index, layero) {
//             //do something
//             layer.close(index); //如果设定了yes回调，需进行手工关闭
//         }
//     })
// })