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
    <script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
    <script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
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
   <div class="base-phone" v-if="isPhoneNum">
        <span class="base-span" ></span>
        <span class="base-span">手机号</span>
        <a class="pop-a" v-text="data.phonenum" style="color: #000000"></a>
    </div>
   <div class="base-phone" v-else>
        <span class="base-span">手机号</span>
        <a class="pop-a" href="###">绑定</a>
    </div>
    <div class="person-pop-more">
        <div class="person-pop">
            <form>
                <label>
                    <div class="pop-inp">
                        <input id="phoneNum" type="text" placeholder="请输入手机号码" />
                        <i><img src="images/base-phone.png"/></i>
                    </div>
                    <span class="pop-span" onclick="getVerifyCode()">获取验证码</span>
                </label>
                <label>
                    <div class="pop-inp">
                        <input id="verifyCode" type="text" placeholder="请输入验证码" />
                        <i><img src="images/base-yzm.png"/></i>
                    </div>
                </label>
                <label>
                    <input class="pop-subm" type="submit" value="提交" onclick="bindPhoneNum()"/>
                </label>
            </form>
        </div>
    </div>
</div>
</body>
<script>
    /*
        绑定手机号输入框
     */
    $(function(){
        //隐藏层
        function hideLayer(){
            $('.person-pop-more').fadeOut();
        }
        $('.person-pop-more').click(function(event) {
            if (event.target == this){
                hideLayer();
            }
        });
        $('.pop-a').click(function(){
            var clickText = $('.pop-a').html();
            if(clickText != '绑定') return;
            $('.person-pop-more').fadeIn();
        })
    })
</script>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script src="../static/plugin/layui/layer/layer.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/customerInfo.js"></script>
<script>
    //发送验证码
    function getVerifyCode() {
        var phoneNum = $('#phoneNum').val();
        var validatePhoneNum = /^1[356789]\d{9}$/;
        console.log(phoneNum);
        console.log(validatePhoneNum.test(phoneNum));
        if(!validatePhoneNum.test(phoneNum)){
            layer.msg("手机号格式错误，请重新输入");
            return;
        }
        var url = 'getVerifyCode';
        $.ajax({
            type:'post',
            data:{'phoneNum':phoneNum},
            dataType:'json',
            url:url,
            success:function(result) {
                // var data = result.body;
                if(result.success){
                    $('.pop-span').html('已发送');
                    $('.pop-span').attr('onclick','');
                    $('.pop-span').css('background','#696969');
                    $('#phoneNum').attr('disabled','disabled');
                    layer.msg(result.msg);
                }
            }
        })
    }
    //绑定手机号
    function bindPhoneNum() {
        var verifyCode = $('#verifyCode').val();
        var validateVerifyCode = /^\d{6}$/;
        console.log(verifyCode);
        console.log(validateVerifyCode.test(verifyCode));
        if(!validateVerifyCode.test(verifyCode)){
            layer.msg("验证码格式错误，请重新输入");
            return;
        }
        var url = 'bindPhoneNum';
        $.ajax({
            type:'post',
            data:{'verifyCode':verifyCode},
            dataType:'json',
            url:url,
            success:function(result) {
                if(result.success){
                    //隐藏手机号绑定框
                    $('.person-pop-more').fadeOut();
                    layer.msg(result.msg);
                    window.location.reload();
                    return;
                }else{
                    layer.msg(result.msg);
                }
            }
        })
    }

</script>
</html>
