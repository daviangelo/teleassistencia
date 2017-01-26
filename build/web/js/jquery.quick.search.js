$(function(){
	$(".input-search").keyup(function(){
		//pega o css da tabela 
		var tabela = $(this).attr('alt');
		console.log(tabela);
		if( $(this).val() != ""){
			$("."+tabela+" tbody tr").hide();
			$("."+tabela+" td:contains-ci('" + $(this).val() + "')").parent("tr").show();
		} else{
			$("."+tabela+" tbody tr").show();
		}
	});	
});
$.extend($.expr[":"], {
	"contains-ci": function(elem, i, match, array) {
		return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
	}
});	

var url = window.location.href;
var urlName = url.split('/')[1].split('.xhtml')[0];
console.log(urlName)