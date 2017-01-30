
/* global layer */

function fadeOut(id, time) {
    fade(id, time, 100, 0);
}

function fadeIn(id, time) {
   // alert("aki");
    fade(id, time, 0, 100);
}

function fade(id, time, ini, fin) {
//    var target = document.getElementById(id);
//    alert(target);
    var alpha = ini;
    var inc;
    if (fin >= ini) { 
        inc = 2; 
    } else {
        inc = -2;
    }
    timer = (time * 1000) / 50;
    var i = setInterval(
        function() {
            if ((inc > 0 && alpha >= fin) || (inc < 0 && alpha <= fin)) {
                clearInterval(i);
            }
            setAlpha(target, alpha);
            alpha += inc;
        }, timer);
        
}

function setAlpha(target, alpha) {
    target.style.filter = "alpha(opacity="+ alpha +")";
    target.style.opacity = alpha/100;
}