/*
 * Copyright (c) 2019. Florian Herborn & Sebastian Quast
 */

$(document).ready(function () {
    $(".section-link").on("click", function (e) {
        e.preventDefault();
        const target = $(this).attr('href');
        const targetElement = $(target);

        const headerHeight = $('#navbar').outerHeight();

        $('html, body').animate({
            scrollTop: targetElement.offset().top - headerHeight
        }, 500);
    });

});