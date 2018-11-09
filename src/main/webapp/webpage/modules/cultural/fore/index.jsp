<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <!--适配当前屏幕-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <link rel="stylesheet" href="css/base.css" />
    <link rel="stylesheet" href="css/style.css" />
    <title>天下诗联·楹联</title>
</head>
<body>
<div class="index-body" id="index">
    <!--轮播图-->
    <div class="lunbo" id="lunbo">
        <div class="bd">
            <ul>
                <li><a href="#"><img src="images/index-bg.png" /></a></li>
                <li><a href="#"><img src="images/index-bg.png" /></a></li>
                <li><a href="#"><img src="images/index-bg.png" /></a></li>
            </ul>
        </div>
        <div class="hd">
            <ul class="clearfix"></ul>
        </div>
    </div>
    <div class="middle" >
        <!--单位成品楹联-->
        <div class="mid-tit">
            <span></span><h1>单位成品楹联</h1><span></span>
        </div>
        <div class="unit">
            <div class="hd">
                <ul>
                    <li id="governmentCouplets" class="on" @click="governmentCouplets">机关事业</li><li id="companyCouplets" @click="companyCouplets">企业商户</li>
                </ul>
            </div>
            <div class="bd">
                <ul >
                    <li  v-for="item in gvmtAndCpnData">
                        <a class="unit-i-a" href="###"><div class="unit-img"><img :src="item.picture" /></div></a>
                        <a class="unit-a" href="###" v-text="item.name"></a>
                        <div class="unit-txt">
                            <span v-text="'￥'+item.price"></span>
                            <p v-if="item.lexicon.isOriginal == '1'">原创</p>
                            <p v-else>常用</p>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="midd-more-a"><a :href="govAndComp">查看更多>></a></div>
        </div>
        <!--家庭成品楹联-->
        <div class="mid-tit home-tit">
            <span></span><h1>家庭成品楹联</h1><span></span>
        </div>
        <div class="home" id="homeCouplets">
            <div class="home-ul">
                <ul>
                    <li v-for="item in homeData">
                        <a class="home-i-a" href="###"><div class="home-img"><img :src="item.picture" /></div></a>
                        <a class="home-a" href="###" v-text="item.name"></a>
                        <div class="home-txt">
                            <span v-text=" '￥' + item.price"></span>
                            <p v-if="item.lexicon.isOriginal == '1'">原创</p>
                            <p v-else>常用</p>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="midd-more-a"><a href="home">查看更多>></a></div>
        </div>
        <!--年画作品-->
        <div class="mid-tit year-tit">
            <span></span><h1>年画作品</h1><span></span>
        </div>
        <div class="year-pictures" >
            <div class="year-ul" id="newYearPics">
                <ul>
                    <li v-for="item in newYearPicsData">
                        <div class="year-img"><img :src="'../' + item.picture" /></div>
                        <div class="year-txt">
                            <p class="year-p" v-text="item.title"></p>
                            <div class="year-a clearfix">
                                <span v-text=" '￥' + item.price"></span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="midd-more-a"><a href="newYearPic">查看更多>></a></div>
        </div>
        <!--书画作品展示-->
        <div class="mid-tit year-tit">
            <span></span><h1>书画作品展示</h1><span></span>
        </div>
        <div class="calligraphy">
            <div class="hd">
                <ul><li class="on" id="calligraphies" @click="calligraphies">书法作品</li><li id="paintings" @click="paintings">美术作品</li></ul>
            </div>
            <div class="bd">
                <div class="igra-ul">
                    <ul>
                        <li v-for="(item,index) in clgpAndPntData">
                            <div v-if="index%2 != 1">
                                <div class="igra-bg"><img src="images/index-shbg.png" /> </div>
                                <div class="igra-div">
                                    <div class="igra-img"><img :src="item.picture" /></div>
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
                                <div class="igra-bg"><img src="images/index-shbg.png" /> </div>
                                <div class="igra-div igras">
                                    <div class=  "igra-txt">
                                        <h3 v-text="item.author.name"></h3>
                                        <h3>作品展示：</h3>
                                        <p v-html="item.intro"></p>
                                        <div class="igra-a clearfix">
                                            <span v-text=" '￥' + item.price"></span><a href="###">立即购买</a>
                                        </div>
                                    </div>
                                    <div class="igra-img"><img :src="item.picture" /></div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="midd-more-a"><a href="clgrafAndPaint">查看更多>></a></div>
        </div>
        <!--室内装饰、工艺品-->
        <div class="mid-tit year-tit">
            <span></span><h1>室内装饰、工艺品</h1><span></span>
        </div>
        <div class="interior year-pictures" id="decorations">
            <div class="year-ul">
                <ul>
                    <li v-for="item in decorationsData">
                        <div class="year-img"><img :src="item.picture" /></div>
                        <div class="year-txt">
                            <p class="year-p" v-text="item.details">室内装饰品</p>
                            <div class="year-a clearfix">
                                <span v-text=" '￥' + item.price"></span><a href="###">立即购买</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="midd-more-a"><a href="decoration">查看更多>></a></div>
        </div>
    </div>
    <!--底部Tab-->
    <jsp:include page="include/footerNavigation.jsp"></jsp:include>
</div>
</body>
<script type="text/javascript" src="js/jquery.1.8.2.min.js" ></script>
<script type="text/javascript" src="js/TouchSlide.1.1.js" ></script>
<script src="js/footerNavigation.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="js/index.js"></script>
<script type="text/javascript">
    TouchSlide({
        slideCell:"#lunbo",
        titCell:".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
        mainCell:".bd ul",
        effect:"left",
        autoPlay:true,//自动播放
        autoPage:true, //自动分页
    });
</script>
</html>

