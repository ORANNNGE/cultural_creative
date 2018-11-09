//获取url里的参数
function getParaFromURL(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]);
    return null;
}

//转译富文本
function parseBlob(text){
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    console.log(output);
    temp = null;
    return output;
}

function dateFormat(date) {
    var myDate = new Date(date);
    var month = myDate.getMonth() + 1;
    var day = myDate.getDate();
    month = (month.toString().length == 1) ? ("0" + month) : month;
    day = (day.toString().length == 1) ? ("0" + day) : day;
    var result = myDate.getFullYear() + '-' + month + '-' + day; //当前日期
    return result;
}