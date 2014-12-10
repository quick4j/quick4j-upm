<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/static/global.inc"%>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <title></title>
        <link rel="stylesheet" href="static/js/vender/easyui/themes/metro/easyui.css">
        <link rel="stylesheet" href="static/js/vender/easyui/themes/icon.css">
    </head>
    <body class="easyui-layout">
        <input type="hidden" id="pathid">
        <div data-options="region: 'west', border:false" style="width: 45%; padding: 3px;">
            <table class="quick4j-datagrid" id="left"
                   data-options="
                       name: 'actions',
                       title: '所有按钮',
                       fit: true,
                       striped: true,
                       singleSelect: true,
                       rownumbers: true,
                       columns:[[
                        {field: 'icon', title: '图标', width: 40, formatter: quick4j.util.string2Image},
                        {field: 'name', title: '名称', width: 100}
                       ]],
                       onDblClickRow: addButton
                    "></table>
        </div>
        <div data-options="region: 'east',border:false" style="width: 45%; padding: 3px;">
            <table class="quick4j-datagrid" id="right"
                   data-options="
                       fit: true,
                       title: '可选按钮',
                       method: 'get',
                       striped: true,
                       singleSelect: true,
                       rownumbers: true,
                       columns:[[
                        {field: 'icon', title: '图标', width: 40, formatter: quick4j.util.string2Image},
                        {field: 'name', title: '名称', width: 100}
                       ]],
                       onDblClickRow: removeButton
                  "></table>
        </div>
        <div data-options="region: 'center',border:false">
            <div style="width: 30px; height: 100px; position: relative; top: 25%; left: 30%">

            </div>
        </div>


        <script src="static/js/vender/jquery-1.11.1.min.js"></script>
        <script src="static/js/vender/easyui/jquery.easyui.min.js"></script>
        <script src="static/js/vender/easyui/locale/easyui-lang-zh_CN.js"></script>
        <script src="static/js/jquery.easyui.extend.min.js"></script>
        <script src="static/js/vender/jquery.json-2.3.js"></script>
        <script src="static/js/quick4j.parser.js"></script>
        <script src="static/js/quick4j.util.js"></script>
        <script src="static/js/quick4j.datagrid.js"></script>
        <script>
            function doInit(win){
                var pathid = win.getData("node").id;
                $('#right').datagrid('options').url = 'upm/paths/' + pathid + '/actions';
            }

            function addButton(rowIndex, rowData){
                $(this).datagrid('unselectRow', rowIndex);
                $('#right').datagrid('appendRow', rowData);
            }

            function removeButton(rowIndex, rowData){
                $(this).datagrid('unselectRow', rowIndex);
                $('#right').datagrid('deleteRow', rowIndex);
            }

            function doSave(win){
                var inserted = $('#right').datagrid('getChanges', 'inserted');
                var deleted = $('#right').datagrid('getChanges', 'deleted');

                var id = win.getData('node').id;
                $.ajax({
                    type: 'post',
                    url: 'upm/paths/' + id + '/bound/actions',
                    data: {inserted: $.toJSON(inserted), deleted: $.toJSON(deleted)},
                    dataType: 'json',
                    success: function(result){
                        if(result.status == 200){
                            alert('ok')
                        }else{
                            alert('faile')
                        }
                    },
                    error: function(){
                        $.messager.alert("警告", "数据保存失败!", "warning");
                    }
                })
            }
        </script>
    </body>
</html>
