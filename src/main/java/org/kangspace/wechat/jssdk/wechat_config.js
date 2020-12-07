/**
 *  wx jssdk call
 *  API:https://mp.weixin.qq.com/wiki?id=mp1445241432&lang=zh_CN
 *  must import (or https)http://res.wx.qq.com/open/js/jweixin-1.0.0.js
 */


/**
 * 分享信息对象
 */
var shareInfo={
    title:"微信jdsdk分享Title",
    desc:"微信jdsdk分享desc",
    shareUrl:document.URL,
    shareImgUrl:$(".banner img")[0].src
};
/**
 * 加载微信配置
 * 需请求接口获取 appId,timestamp,nonceStr,signature后加载微信配置
 *
 */
function loadWxConfig(appId,timestamp,nonceStr,signature){
    // TODO 删除 debug
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: appId, // 必填，公众号的唯一标识
        timestamp: timestamp, // 必填，生成签名的时间戳
        nonceStr: nonceStr, // 必填，生成签名的随机串
        signature: signature,// 必填，签名，见附录1
        jsApiList: ["onMenuShareTimeline","onMenuShareAppMessage","onMenuShareQQ","onMenuShareWeibo","onMenuShareQZone"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
}

wx.ready(function () {
    // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
    wx.onMenuShareTimeline({
        title: shareInfo.title, // 分享标题
        link: shareInfo.shareUrl ? shareInfo.shareUrl : location.href,
        imgUrl: shareInfo.shareImgUrl
    });
    // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
    wx.onMenuShareAppMessage({
        title: shareInfo.title, // 分享标题
        desc: shareInfo.desc, // 分享描述
        link: shareInfo.shareUrl ? shareInfo.shareUrl : location.href,
        imgUrl: shareInfo.shareImgUrl
    });
    // 获取“分享给QQ”按钮点击状态及自定义分享内容接口
    wx.onMenuShareQQ({
        title: shareInfo.title, // 分享标题
        desc: shareInfo.desc, // 分享描述
        link: shareInfo.shareUrl ? shareInfo.shareUrl : location.href,
        imgUrl: shareInfo.shareImgUrl
    });
    //隐藏右上角菜单接口
    wx.hideOptionMenu();
    //显示右上角菜单接口
    wx.showOptionMenu();
    //关闭当前网页窗口接口
    wx.closeWindow();
    //批量隐藏功能按钮接口
    wx.hideMenuItems({
        menuList: [] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
    });
    //批量显示功能按钮接口
    wx.showMenuItems({
        menuList: [] // 要显示的菜单项，所有menu项见附录3
    });
    //隐藏所有非基础按钮接口
    wx.hideAllNonBaseMenuItem();// “基本类”按钮详见附录3
    //显示所有功能按钮接口
    wx.showAllNonBaseMenuItem();
});

/*
 * 浏览器版本信息:
 */
var browser={
    versions:function(){
        var u = navigator.userAgent, app = navigator.appVersion;
        return {//移动终端浏览器版本信息
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
}

var mobileAgent =  ["iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
    "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
    "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
    "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
    "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
    "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
    "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
    "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
    "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
    "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
    "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
    "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
    "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
    "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
    "Googlebot-Mobile"];

var isAndroid= function(){
    if(browser.versions.mobile && browser.versions.android){
        return true;
    }
    return false;
}

var isMobile = function(){
    var userAgent = navigator.userAgent.toLowerCase();
    for(var i=0;i<mobileAgent.length;i++){
        if(userAgent.indexOf(mobileAgent[i].toLowerCase())!=-1)
            return true;
    }
    return false;
}