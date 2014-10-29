/**
 * @author zhaojh
 */
;(function($){
    if(typeof quick4j.util === 'undefined'){
        $.namespace("quick4j.util");
    }

    var string2IamgeForDataGrid = function(value, row, index){
        return '<div class="'+ value+ '" style="height:16px; height: 16px;"></div>'
    }

    $.extend(quick4j.util, {
        string2Image: string2IamgeForDataGrid
    });
})(jQuery);
