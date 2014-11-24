$(function(){
    $('li')
        .css('pointer','default')
        .css('list-style-image','none');
    $('li:has(ul)')
        .click(function(event){
            if (this == event.target) {
                $(this).css('list-style-image',
                    (!$(this).children().is(':hidden')) ? 'url(/images/right-arrow-bullet.png)' : 'url(/images/down-arrow-bullet.png)');
                $(this).children().toggle();
            }
            return false;
        })
        .css({cursor:'pointer', 'list-style-image':'url(/images/right-arrow-bullet.png)'})
        .children().hide();
    $('li:not(:has(ul))').css({cursor:'default', 'list-style-image':'none'});
 
});
