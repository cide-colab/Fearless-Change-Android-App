/*
 * Copyright (c) 2019. Florian Herborn & Sebastian Quast
 */

$(document).ready(function () {
    $(".section-link").on("click", function (e) {
        e.preventDefault();
        const section = $(this).data('section');
        const target = $('#' + section);

        const headerHeight = $('#navbar').outerHeight();

        $('html, body').animate({
            scrollTop: target.offset().top - headerHeight
        }, 500);
    });
});