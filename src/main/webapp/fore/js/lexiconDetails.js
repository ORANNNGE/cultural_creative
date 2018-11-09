/*
查询价格
 */
var sizeId;
var comboId;
var typefaceId;
var lexiconId = details.id;
var lexiconPriceId;
function getLexiconPrice(object) {
    var spec = $(object).attr('data-type');

    if(spec == 'typeface'){
        $(object).parent().children('p').removeClass('checked');
        $(object).attr('class','checked');
        typefaceId = $(object).attr('data-id');


    }
    if(spec == 'size'){
        $('.thsm-prod').children('p').removeClass('checked');
        $(object).parent().children('p').removeClass('checked');
        $(object).attr('class','checked');
        comboId="";
        sizeId = $(object).attr('data-id');
        if(typefaceId){
            getLexiconCombo(sizeId,typefaceId);
        }
    }
    if(spec == 'combo'){
        $(object).parent().children('p').removeClass('checked');
        $(object).attr('class','checked');
        comboId = $(object).attr('data-id');

    }

    if(sizeId && comboId && typefaceId){
        console.log('*****');
        console.log('sizeId'+sizeId);
        console.log('typefaceId'+typefaceId);
        console.log('comboId'+comboId);
        console.log('type'+type);
        console.log('*****');
        var url = 'getLexiconPrice';
        var param = {
            'sizeId':sizeId,
            'comboId':comboId,
            'typefaceId':typefaceId,
            'type':type,
        };

        $.ajax({
            type:'post',
            url:url,
            dataType:'json',
            data:param,
            async:false,
            success:function (result) {
                vm.thePrice = result.body;
                lexiconPriceId = result.body.price.id;
            }
        })
    }
}


//添加订单
function addLexiconOrder(){
    //
    var url = 'addLexiconOrder';
    //总价
    var totalPrice = vm.totalPrice;
    //
    var orderId;
    //购买数量
    var num = vm.num;
    if( !sizeId  || !comboId || !typefaceId){
        layer.msg('请选择规格');
        return;
    }
    if(num == 0){
        layer.msg('请选择数量');
        return;
    }

    console.log('------');
    console.log(num);
    console.log(totalPrice);
    console.log(sizeId);
    console.log(comboId);
    console.log(typefaceId);
    console.log(lexiconPriceId);
    var param = {
        'comboId':comboId,
        'typefaceId':typefaceId,
        'sizeId':sizeId,
        'type':type,
        'lexiconId':lexiconId,
        'lexiconPriceId':lexiconPriceId,
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
            orderId = result.body.orderId;
            layer.open({
                content: '是否立即付款',
                yes: function (index, layero) {
                    //do something
                    payReq(orderType,orderId,totalPrice);
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            })


        }
    })
}