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
    <title>地址管理</title>
</head>
<body style="background: #eee;">
<div class="personal-address" id="addressList">
    <div class="addre-ul">
        <ul>
            <li v-for="item in data">
                <div class="addre-up">
                    <h3 v-text="item.name"> <span v-text="item.phonenum"></span></h3>
                    <p v-text="item.details"></p>
                </div>
                <div class="addre-label">
                    <label><input type="checkbox" value="默认" /><span>默认</span></label>
                    <a href="###">编辑</a>
                    <p class="addre-delete">删除</p>
                </div>
            </li>
        </ul>
        <div class="addre-a">
            <a href="personal-addAddress.jsp">添加新地址</a>
        </div>
    </div>

</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/personal-address.js"></script>
</html>