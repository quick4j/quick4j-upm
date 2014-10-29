<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/static/global.inc"%>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <title></title>
        <link rel="stylesheet" href="static/css/upm.css">
        <link rel="stylesheet" href="static/js/vender/easyui/themes/metro/easyui.css">
        <link rel="stylesheet" href="static/js/vender/easyui/themes/icon.css">
    </head>
    <body class="easyui-layout">
        <div data-options="region:'center', border: false" style="padding: 2px;">
            <table class="quick4j-datagrid" id="datagrid"
                   data-options="
                    name: 'roles',
                    fit: true,
                    striped: true,
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
                    content: 'url:upm/roles/new',
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

            function doEdit(){
                var target = $('#datagrid');
                var row = target.datagrid('getSelected');

                if(!row){
                    $.messager.alert("警告", "请选择要编辑的角色!", "warning");
                    return;
                }

                $.showModalDialog({
                    title: '新建',
                    content: 'url:upm/roles/'+row.id+'/edit',
                    data: {operation: 'edit', datagrid: target, row: row},
                    locate: 'document',
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
                    }],
                    onLoad: function(win, body){
                        record = win.getData('row');
                        $('#id').val(record.id);
                        $('#name').val(record.name);
                        $('#desc').val(record.desc);
                    }
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
                            url: 'upm/roles/' + row.id + '/delete',
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

            function doAssignResource(){
                var target = $('#datagrid');
                var row = target.datagrid('getSelected');

                if(!row){
                    $.messager.alert("警告", "请选择要授权的角色!", "warning");
                    return;
                }

                $.showModalDialog({
                    title: '分配权限',
                    content: 'url:upm/roles/'+row.id+'/paths',
                    data: {row: row},
                    locate: 'document',
                    useiframe: true,
                    buttons:[{
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function(win){
                            win.close();
                        }
                    }],
                    onLoad: function(win, body){
                        if(body) body.doInit(win);
                    }
                });
            }
        </script>
    </body>
</html>
