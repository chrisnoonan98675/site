$(function(){
    $('li')
        .css('pointer','default')
        .css('list-style-image','none');
    $('li:has(ul)')
        .click(function(event){
            if (this == event.target) {
                $(this).css('list-style-image',
                    (!$(this).children().is(':hidden')) ? 'url(/images/plusbox.png)' : 'url(/images/minusbox.png)');
                $(this).children().toggle();
            }
            return false;
        })
        .css({cursor:'pointer', 'list-style-image':'url(/images/plusbox.png)'})
        .children().hide();
    $('li:not(:has(ul))').css({cursor:'default', 'list-style-image':'none'});
 
});
