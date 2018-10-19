/*
查询价格
 */
var sizeId;
var frameId;
var craftId;
var typefaceId;
var authorId;
var lexiconId = details.id;
var lexiconPriceId;
function getLexiconPrice(object) {
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
    if(type == 'author'){
        authorId = $(object).attr('data-id');
        typefaceId = '';
    }
    if(type == 'typeface'){
        typefaceId = $(object).attr('data-id');
        authorId = '';
    }

    if(sizeId && frameId && craftId && lexiconId && (authorId || typefaceId)){
        console.log('*****');
        console.log('sizeId'+sizeId);
        console.log('frameId'+frameId);
        console.log('craftId'+craftId);
        console.log('authorId'+authorId);
        console.log('typefaceId'+typefaceId);
        console.log('*****');
        var url = 'getLexiconPrice';
        var param = {
            'sizeId':sizeId,
            'frameId':frameId,
            'craftId':craftId,
            'lexiconId':lexiconId,
            'authorId':authorId,
            'typefaceId':typefaceId
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
    //购买数量
    var num = vm.num;
    if(!lexiconPriceId || !sizeId || !frameId || !craftId  ){
        if(!authorId || !typefaceId){
            layer.msg('请选择规格');
            return;
        }
    }
    if(num == 0){
        layer.msg('请选择数量');
        return;
    }

    console.log('*******');
    console.log(num);
    console.log(totalPrice);
    console.log(lexiconId);
    console.log(lexiconPriceId);
    var param = {
        'lexiconPriceId':lexiconPriceId,
        'lexiconId':lexiconId,
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