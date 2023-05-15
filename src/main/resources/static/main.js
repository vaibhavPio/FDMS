



$('document').ready(function(){

$('.table .btn').on('click',function(event)){
event.preventDefault();
var href= $(this).attr('href');

//$.get(href, function(baseLoginEntity, status){
//})
$('table #calculate').on('click',function(event){
event.preventDefault();
var href = $(this).attr('href');
$('#calculate #delRef').attr('href', href);
$('#calculateModel').modal();

});

});