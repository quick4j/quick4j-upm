<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <%@include file="/static/global.inc"%>
        <title></title>
        <link rel="stylesheet" href="static/css/upm.css">
        <link rel="stylesheet" href="static/js/vender/easyui/themes/metro/easyui.css">
        <link rel="stylesheet" href="static/js/vender/easyui/themes/icon.css">
    </head>
    <body class="easyui-layout">
        <div data-options="region:'center',border:false" style="padding: 2px;">
            <table class="quick4j-datagrid" id="datagrid"
                    data-options="
                    name: 'users',
                    fit: true,
                    striped: true,
                    pagination: true,
                    singleSelect: true"></table>
        </div>

        <script src="static/js/vender/jquery-1.11.1.min.js"></script>
        <script src="static/js/vender/easyui/jquery.easyui.min.js"></script>
        <script src="static/js/vender/easyui/locale/easyui-lang-zh_CN.js"></script>
        <script src="static/js/jquery.easyui.extend.min.js"></script>
        <script src="static/js/vender/jquery.json-2.3.js"></script>
        <script src="static/js/quick4j.parser.js"></script>
        <script src="static/js/quick4j.datagrid.js"></script>
        <script src="static/js/quick4j.treegrid.js"></script>
        <script src="static/js/quick4j.util.js"></script>
        <script>
            function doNew(){
                $.showModalDialog({
                    title: '新建',
                    content: 'url:upm/users/new',
                    data: {operation: 'new', datagrid: $('#datagrid')},
                    buttons:[{
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: 'doSave'
                    },{
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function(win){
                            win.close();
                        }
                    }]
                });
            }

            function doDelete(){
                var target = $('#datagrid');
                var row = target.datagrid('getSelected');

                if(!row){
                    $.messager.alert("警告", "请选择要删除的记录!", "warning");
                    return;
                }

                $.messager.confirm('确认', '确认删除此条记录?', function(r){
                    if(r){
                        $.ajax({
                            url: 'upm/users/' + row.id + '/delete',
                            success: function(data){
                                if(data.status == 200){
                                    target.datagrid('reload');
                                }else{
                                    $.messager.alert('错误', data.status + '<br>' + data.message, 'error');
                                }
                            },
                            error: function(){
                                $.messager.alert('错误', '操作过程中发生错误。', 'error');
                            }
                        })
                    }
                });
            }
        </script>
    </body>
</html>
