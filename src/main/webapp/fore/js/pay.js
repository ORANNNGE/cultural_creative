//微信支付

function payReq(type,orderId,price){
    var appId,timeStamp,nonceStr,package,signType,paySign;
    var url = '../wechat/wxpay';
    // layer.msg(orderId+'，'+type+','+price);
    var payParam = {
        'type':type,
        'price':price*100,
        'orderId':orderId
    }

    $.ajax({
        url:url,
        type:'post',
        data:payParam,
        dataType:'json',
        success:function (result) {
            // alert.log(result);
            var payData = result.body.payData;

            appId = payData.appId;
            timeStamp = payData.timeStamp;
            nonceStr = payData.nonceStr;
            package = payData.package;
            signType = payData.signType;
            paySign = payData.paySign;
            //
            location.href =
                'pay.jsp?' +
                'appId='+appId+'&'+
                'timeStamp='+timeStamp+'&'+
                'nonceStr='+nonceStr+'&'+
                'package='+package+'&'+
                'signType='+signType+'&'+
                'paySign='+paySign;
        }
    })
}
