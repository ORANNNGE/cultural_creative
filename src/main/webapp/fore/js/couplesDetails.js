/**
 * 楹联详情页
 */
var url = 'getCoupletsById';
var param = MyUtils('id');

function getCoupletsDetails(){
    var data;
    var params = {
        id:param
    }
    $.ajax({
        type:'post',
        url:url,
        dataType:'json',
        data:params,
        async:false,
        success:function (result) {
            data = result.body;
        }
    })
    return data;
}

//楹联详情
var data = getCoupletsDetails();
var details = data.data;
//Vue实例
new Vue({
    el:'#coupletsDetails',
    data: {
        data:details,
    },
    computed: {
        details:function () {
            var text = this.data.details;
            return blob = parseBlob(text);
        }
    }
})
