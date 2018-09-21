var pathName = window.location.pathname;
console.log('pathname:'+pathName)
if( pathName.indexOf('index') >= 0){
    $('#toIndex').attr('class','foot-a on-a');
}
if(pathName.indexOf('couplets') >= 0){
    $('#toCouplets').attr('class','foot-b on-b');
}
if(pathName.indexOf('lexicon') >= 0){
    $('#toLexicon').attr('class','foot-c on-c');
}
// if(pathName == 'to/index'){
//     $('#toIndex').attr('class','foot-a on-a');
// }
// if(pathName == 'to/index'){
//     $('#toIndex').attr('class','foot-a on-a');
// }
//跳转到主页
function toIndex(){
    if(pathName == '/to/index') return;
    location.href = 'index';
}

function toCouplets(){
    if(pathName == '/to/couplets') return;
    location.href = 'couplets';
}
function toLexicon(){
    if(pathName == '/to/lexicon') return;
    location.href = 'lexicon';
}