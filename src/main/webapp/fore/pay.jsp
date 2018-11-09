<%--
  Created by IntelliJ IDEA.
  User: 52555
  Date: 2018/11/2
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支付</title>
</head>
<body>
</body>
<script src="js/jquery.1.8.2.min.js"></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="js/myUtils.js"></script>
<script>
    function onBridgeReady(){
        //获取参数
        var appId =getParaFromURL('appId');
        var nonceStr =getParaFromURL('nonceStr');
        var prepayId =getParaFromURL('prepayId');
        var package =getParaFromURL('package');
        var paySign =getParaFromURL('paySign');
        var signType =getParaFromURL('signType')
        var timeStamp =getParaFromURL('timeStamp')

        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId":appId,
                "timeStamp":timeStamp,
                "nonceStr":nonceStr,
                "package":package,
                "signType":signType,
                "paySign":paySign
            },
            function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    location.href="index.jsp";
                }else {//这里支付失败和支付取消统一处理
                    alert("支付取消");
                    location.href="index.jsp";
                }
            }
        );
    }
    $(document).ready(function () {
        if (typeof WeixinJSBridge == "undefined"){
            if (document.addEventListener){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else {
            onBridgeReady();
        }
    });
</script>
</html>
