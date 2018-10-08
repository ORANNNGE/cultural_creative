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
    <title>基本信息</title>
</head>
<body>
<div class="personal-base" id="customerInfo">
    <div class="base-name">
        <span class="base-span">昵称</span>
        <p v-text="data.nickname"></p>
    </div>
    <div class="base-tou">
        <span class="base-span">头像</span>
        <div class="base-img"><img :src="data.headimg"/></div>
    </div>
    <div class="base-phone">

        <span class="base-span" v-if="data.phonenum">手机号</span>
        <a href="###">绑定</a>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/customerInfo.js"></script>
</html>
