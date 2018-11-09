<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <!--适配当前屏幕-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <link rel="stylesheet" href="css/base.css" />
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/more-style.css" />
    <title>联系客服</title>
</head>
<body>
<div class="personal-call" id="customerServ">
    <ul>
        <li>
            <span>客服电话</span>
            <p v-text="customerServ.tel"></p>
        </li>
        <li>
            <span><i><img src="images/per-weixin.png"/></i></span>
            <p v-text="customerServ.wechat"></p>
        </li>
        <li>
            <span><i><img src="images/per-qq.png"/></i></span>
            <p v-text="customerServ.qq"></p>
        </li>
    </ul>
    <div class="call-txt">
        <p>留言</p>
        <textarea placeholder="请输入您的留言" v-model="leaveMsg"></textarea>
        <input type="submit" value="提交" @click="addLeaveMsg"/>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/myUtils.js"></script>
<script src="js/personal-customer-service.js"></script>
</html>

