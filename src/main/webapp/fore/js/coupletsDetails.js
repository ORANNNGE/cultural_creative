/*
查询价格
 */
var sizeId;
var sizeName;
var comboId;
var comboName;
var coupletsId = details.id;
var coupletsPriceId
function getCoupletsPrice(object) {
    var spec = $(object).attr('data-type');

    if(spec == 'size'){
        $(object).parent().children('p').removeClass('checked');
        $(object).attr('class','checked');
        $('.norm-patent').children('p').removeClass('checked');
        sizeId = $(object).attr('data-id');
        comboId = '';
        getCoupletsCombo(sizeId);
    }
    if(spec == 'combo'){
        $(object).parent().children('p').removeClass('checked');
        $(object).attr('class','checked');
        comboId = $(object).attr('data-id');
    }
    if(sizeId && comboId && coupletsId){
        console.log(sizeId);
        console.log(comboId);
        console.log(coupletsId);
        var url = 'getCoupletsPrice';
        var param = {
            'sizeId':sizeId,
            'comboId':comboId,
            'type':type
        };

        $.ajax({
            type:'post',
            url:url,
            dataType:'json',
            data:param,
            async:false,
            success:function (result) {
                vm.thePrice = result.body;
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
    var orderId;
    //购买数量
    var num = vm.num;
    if(!coupletsPriceId || !sizeId || !comboId){
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
            if(result.success == false){
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
            orderId = result.body.orderId;
            layer.open({
                content: '订单已生成，是否立即付款？',
                yes: function (index, layero) {
                    //do something
                    payReq(orderType,orderId,totalPrice);
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            })


        }
    })
}

