
//O txt, que tiver essa class, irá PERMITIR apenas LETRAS e ESPAÇOS
$(".somenteLetras").on("input", function(){
    var regexp = /[^a-zA-Z\u00C0-\u00FF ]/g;
    if(this.value.match(regexp)){
      $(this).val(this.value.replace(regexp,''));
    }
});

//O txt, que tiver essa class, irá PERMITIR apenas NÚMEROS
$(".somenteNumeros").on("input", function(){
    var regexp = /[^0-9]/g;
    if(this.value.match(regexp)){
      $(this).val(this.value.replace(regexp,''));
    }
});