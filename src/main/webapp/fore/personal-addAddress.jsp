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
    <title>添加新地址</title>
</head>
<body>
<div class="personal-addrenew">
    <div class="addnew-form" data-toggle="distpicker" id="addressForm">
        <form>
            <ul>
                <li><label>
                    <span>姓名</span>
                    <input type="text" placeholder="请输入姓名" id="name" v-model="name"/>
                </label></li>
                <li><label>
                    <span>手机号</span>
                    <input type="text" placeholder="请输入手机号" id="phoneNum" v-model="phoneNum"/>
                </label></li>

                <li>
                    <label>
                        <span>省份</span>
                        <select  id="province" v-model="province"></select>
                    </label>
                </li>
                <li>
                    <label>
                        <span>城市</span>
                        <select id="city" v-model="city"></select>
                    </label>
                </li>
                <li>
                    <label>
                        <span>区县</span>
                        <select id="district" v-model="district"></select>
                    </label>
                </li>

                <li><label>
                    <span>详细地址</span>
                    <textarea placeholder="请输入详细地址" id="details" v-model="details"></textarea>
                </label></li>
            </ul>
        </form>
    </div>
    <div class="addre-a">
        <a href="###" onclick="addAddress()">保存</a>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/distpicker.data.js"></script>
<script src="js/distpicker.js"></script>
<script src="js/personal-addAddress.js"></script>
</html>

