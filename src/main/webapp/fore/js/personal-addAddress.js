//新增收货地址

//修改收货地址
var id = getParaFromURL('id');
var vm = new Vue({
    el:'#addressForm',
    data:{
        name:'',
        phoneNum:'',
        province:'',
        city:'',
        district:'',
        details:'',
        isDefault:'',
    },
    created:function () {
        $(function () {
            if(!id) return;
            var param = {
                'id':id
            }
            $.ajax({
                url:'getAddr',
                data:param,
                dataType:'json',
                type:'post',
                success:function (result) {
                    if(result.success == false){
                        layer.msg(result.msg);
                        location.href='personal-address.jsp'
                        return;
                    }
                    var data = result.body;
                    vm.name = data.address.name;
                    vm.phoneNum = data.address.phonenum;
                    vm.isDefault = data.address.isDefault;
                }
            })
        })


    }
});

function addAddress() {
    var name = vm.name;
    var phoneNum = vm.phoneNum;
    var district =
        vm.province+'/'+
        vm.city+'/'+
        vm.district;
    var details = vm.details;
    //是否默认收货地址，只有在修改是才会传到后台
    var isDefault = vm.isDefault;
    if(!name || !phoneNum || !details){
       layer.msg('请填写完整信息');
       return;
    }
    //若url里没有id,则是新增收货地址
    var param;
    if(!id){
        param = {
            'name': name,
            'phonenum': phoneNum,
            'district': district,
            'details': details,
        }
    }else{
        param = {
            'id': id,
            'name': name,
            'phonenum': phoneNum,
            'district': district,
            'details': details,
            'isDefault': isDefault,
        }
    }

    var url = 'addAddress';

    $.ajax({
        type:'post',
        data:param,
        dataType:'json',
        url:url,
        success:function(result) {
            if(result.success == false){
                layer.msg(result.msg);
                return;
            }
            layer.msg(result.msg);
            location.href="personal-address.jsp";
        }
    })
}
