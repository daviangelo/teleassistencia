/**
 * Script para navegacao
 */

var id, options;

function success(pos) {

alert('latitude: ' + pos.coords.latitude  + ' longitude:' + pos.coords.longitude);
 
 
}

function error(err) {
  console.warn('ERRO(' + err.code + '): ' + err.message);
}

options = {
  enableHighAccuracy: true,
  timeout: 5000,
  maximumAge: 0
};

id = navigator.geolocation.watchPosition(success, error, options);