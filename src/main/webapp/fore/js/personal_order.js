
function getOrderList() {
    var url = 'getOrderList';
    var data;
    $.ajax({
        type:'post',
        url:url,
        dataType:'json',
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}

var allList = getOrderList();
var coupletsOrderList = allList.coupletsOrderList;
// var coupletsPrice = coupletsOrderList.coupletsPrice;
var lexiconOrderList = allList.lexiconOrderList;
var finishedOrderList = allList.finishedOrderList;
console.log(coupletsOrderList);
console.log(lexiconOrderList);
console.log(finishedOrderList);
var vm = new Vue({
    el:'#orderList',
    data:{
        'coupletsOrderList':coupletsOrderList,
        'lexiconOrderList':lexiconOrderList,
        'finishedOrderList':finishedOrderList,
    }
})
