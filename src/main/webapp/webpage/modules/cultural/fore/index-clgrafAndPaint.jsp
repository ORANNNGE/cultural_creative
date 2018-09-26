<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <!--适配当前屏幕-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <link rel="stylesheet" href="../webpage/modules/cultural/fore/css/base.css" />
    <link rel="stylesheet" href="../webpage/modules/cultural/fore/css/style.css" />
    <link rel="stylesheet" href="../webpage/modules/cultural/fore/css/more-style.css" />
    <title>书画作品展示</title>
</head>
<body>
<div class="index-body" id="clgrafAndPaint">
    <!--图片-->
    <div class="lunbo" id="lunbo">
        <img src="../webpage/modules/cultural/fore/images/index-bg.png" />
    </div>
    <div class="middle">
        <div class="calligraphy">
            <div class="hd">
                <ul><li class="on" id="calligraphies" @click="calligraphies">书法作品</li><li id="paintings" @click="paintings">美术作品</li></ul>
            </div>
            <div class="bd">
                <div class="igra-ul">
                    <ul>
                        <li v-for="(item,index) in list">
                            <div v-if="index%2 != 1">
                                <div class="igra-bg"><img src="../webpage/modules/cultural/fore/images/index-shbg.png" /> </div>
                                <div class="igra-div">
                                    <div class="igra-img"><img :src=" '../' + item.picture" /></div>
                                    <div class="igra-txt">
                                        <h3 v-text="item.author.name"></h3>
                                        <h3>作品展示：</h3>
                                        <p v-html="item.intro"></p>
                                        <div class="igra-a clearfix">
                                            <span v-text=" '￥' + item.price"></span><a href="###">立即购买</a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div v-else>
                                <div class="igra-bg"><img src="../webpage/modules/cultural/fore/images/index-shbg.png" /> </div>
                                <div class="igra-div igras">
                                    <div class=  "igra-txt">
                                        <h3 v-text="item.author.name"></h3>
                                        <h3>作品展示：</h3>
                                        <p v-html="item.intro"></p>
                                        <div class="igra-a clearfix">
                                            <span v-text=" '￥' + item.price"></span><a href="###">立即购买</a>
                                        </div>
                                    </div>
                                    <div class="igra-img"><img :src="'../'+item.picture" /></div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--底部Tab-->
    <jsp:include page="include/footerNavigation.jsp"></jsp:include>
</div>
</body>
<script type="text/javascript" src="../webpage/modules/cultural/fore/js/jquery.1.8.2.min.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="../webpage/modules/cultural/fore/js/index-clgrafAndPaint.js"></script>
<script src="../webpage/modules/cultural/fore/js/footerNavigation.js"></script>
</html>

