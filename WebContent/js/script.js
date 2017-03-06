var fns = {
    lightboxOverlay: $('<div class="lightboxOverlay"></div>'),
    lightboxBlock: $('<div class="lightboxOverlayInner"></div><div class="lightboxBlock"><a href="#" class="lightboxClose"></a><div class="lightboxInner"></div></div>'),
    lightbox: function (content, width, height) {
        $(content).appendTo(".lightboxInner");

        var pageHeight = $(window).height();
        var pageWidth = $(window).width();
        var contentWidth = width;
        var contentHeight = height;
        var maxWidthContent = pageWidth * 0.8;
        var maxHeightContent = pageHeight * 0.8;

        if (contentWidth > maxWidthContent) {
            var contentWidthFinal = maxWidthContent;
        } else {
            var contentWidthFinal = contentWidth;
        }

        if (contentHeight > maxHeightContent) {
            var contentHeightFinal = maxHeightContent;
        } else {
            var contentHeightFinal = contentHeight;
        }

        $('.lightboxOverlay').css({
            'position': 'fixed',
            'top': '0',
            'left': '0',
            'background-color': 'rgba(0,0,0,0.6)',
            'height': '100%',
            'width': '100%',
            'z-index': '5005'
        });

        $('.lightboxOverlay').addClass("open");

        console.log(contentHeightFinal);
        console.log(contentWidthFinal);

        $('.lightboxBlock').css("position", "fixed");
        $('.lightboxBlock').css("top", Math.max(0, ((pageHeight - contentHeightFinal) / 2)) + "px");
        $('.lightboxBlock').css("left", Math.max(0, ((pageWidth - contentWidthFinal) / 2) + $(window).scrollLeft()) + "px");

        $(".lightboxInner").css({'max-height': contentHeightFinal, 'max-width': contentWidthFinal});
        // $(".lightboxInner " + content).css({'max-height': contentHeightFinal});

        $('.lightboxClose').click(function () {
            $('.lightboxOverlay').removeClass("open");
            $(".lightboxInner").empty();
        });

        $('.lightboxOverlayInner').click(function () {
            $('.lightboxOverlay').removeClass("open");
            $(".lightboxInner").empty();
        });
    },
}

var global = {
    profileHeader: function () {
        $('.boxContent').on('click', function () {
            $(this).next('.submenu-profile').toggleClass('active');
        });
    },
    clouseLight: function () {
        $('.boxBtn .btnAlterar').on('click', function (event) {
            event.preventDefault();
            $('.contentDialogForm .boxClouse').addClass('active');
            $('.ui-widget-overlay').remove();
            $('.ui-dialog').removeClass('active');
        });

        $('.ui-button.btn-associar').on('click', function (event) {
            event.preventDefault();
            $('.boxClouse').addClass('active');
            $('.ui-widget-overlay').remove();
            $('.ui-dialog').removeClass('active');
        });

        $('.contentDialogForm .boxClouse, .btn-picklist-cancelar, .ui-dialog-titlebar-close, .btn-cancelar').on('click', function (event) {
            event.preventDefault();
            $('.contentDialogForm .boxClouse').removeClass('active');
            $('.ui-dialog').addClass('active');
            $('#excluir').removeClass('active');
        });
    },
    sessionStore: function () {
        function includStorage(nameCidade) {
            sessionStorage.setItem('cidade', nameCidade);
        }

        $('.login .ui-button').on('click', function (event) {
            var nameUser = $('input#j_idt8:nomeUsuario').val();
            includStorage(nameUser);
            if (!$('input#j_idt8:nomeUsuario') == "" && !$('input#j_idt8:senha') == "") {
            }
        });
    },
    init: function () {
        global.profileHeader();
        global.sessionStore();
    }
}

var lightboxClient = {
    cadClient: function () {
        $('.newClient').on('click', function (event) {
            event.preventDefault();
            $('.lightboxOverlay ').addClass('open');
        });

        $('.lightboxClose.cadClient').on('click', function (event) {
            event.preventDefault();
            $('.lightboxOverlay ').removeClass('open');
        });

        $('.lightboxOverlayInner.cadClient').on('click', function (event) {
            event.preventDefault();
            $('.lightboxOverlay ').removeClass('open');
        });
    },
    cadPrestador: function () {
        $('.newPrestador').on('click', function (event) {
            event.preventDefault();

            var optifast = $('#boxPrestador').html();

            fns.lightboxOverlay.appendTo('body');
            fns.lightboxBlock.appendTo('.lightboxOverlay');
            fns.lightbox(optifast, 835, 1000);
        });
    },
    alert: function () {
        $('.ui-growl-item-container').on('click', function (event) {
            event.preventDefault();

            $(this).each(function () {
                $(this).remove();
            });
        });
    }
}

var picklist = {
    searchpicklist: function () {
        var campSearch = '<input type="text" id="search" class="searchTop" placeholder="Busca..."> ';
        $(campSearch).insertBefore('.ui-picklist-list');
         
       setTimeout(function() {
            $('#search').keyup(function () {
            console.log('teste') 
            _this = this;
            $.each($('.ui-picklist-source').find('li'), function () {               
                if ($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) == -1) {
                    $(this).hide();
                } else {
                    $(this).show();
                }
            });
        });
        }, 3000);
    },
}

$(document).ready(function () {
    global.init();
    lightboxClient.cadClient();
    lightboxClient.cadPrestador();
    picklist.searchpicklist();

    $('.btnDelete').on('click', function (event) {
        event.preventDefault();
        $('#excluir').addClass('active');
        $('.ui-confirm-dialog').css('display', 'block');
    });

    $('.ui-dialog .ui-dialog-buttonpane button').on('click', function (event) {
        event.preventDefault();
        $('#excluir').removeClass('active');
        $('.ui-widget-overlay').css('display', 'none');
    });

    $(".pageNavigator ul li a").each(function () {
        var link = $(this).attr('href');
        var url = window.location.pathname;
        if (link == url) {
            $(this).parent().addClass("current");
        }
    });
});

$(document).mousemove(function (event) {
    lightboxClient.alert();
    global.clouseLight();
});
