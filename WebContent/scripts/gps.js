/**
 * Script para navegacao
 */

var id, options;

function success(pos) {

AtualizacaoAcompanhamento.atualizarAcompanhamento(pos.coords.latitude,pos.coords.longitude, pos.coords.heading, pos.coords.speed, function(){
 desenhaAcompanhamentos();
});


//TODO atualiza bean posicao
//TODO atualiza bean velocidade
//TODO atualiza bean rumo

//TODO chama método atualizar acomp (se não possuir ele cria um novo);
}

function error(err) {
  alert('ERRO(' + err.code + '): ' + err.message);
  
}

options = {
  enableHighAccuracy: true,
  timeout: 5000,
  maximumAge: 0
};

id = navigator.geolocation.watchPosition(success, error, options);