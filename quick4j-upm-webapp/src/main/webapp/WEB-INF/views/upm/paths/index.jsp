
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
            <table class="quick4j-treegrid" id="treegrid"
                   data-options="name: 'paths',fit: true, striped: true, idField: 'id', treeField: 'name', customAttr: {iconField: 'icon', parentField: 'pid'}"></table>
        </div>


        <script src="static/js/vender/jquery-1.11.1.min.js"></script>
        <script src="static/js/vender/easyui/jquery.easyui.min.js"></script>
        <script src="static/js/vender/easyui/locale/easyui-lang-zh_CN.js"></script>
        <script src="static/js/jquery.easyui.extend.min.js"></script>
        <script src="static/js/vender/jquery.json-2.3.js"></script>
        <script src="static/js/quick4j.parser.js"></script>
        <script src="static/js/quick4j.util.js"></script>
        <script src="static/js/quick4j.datagrid.js"></script>
        <script src="static/js/quick4j.treegrid.js"></script>
        <script src="static/js/quick4j.combogrid.js"></script>
        <script>
            function doNew(){
                var target = $('#treegrid');
                var node = target.treegrid("getSelected");
                var pid = node ? node.id : null;

                $.showModalDialog({
                    title: '新建',
                    content: 'url:upm/paths/new',
                    data: {operation: 'new', treegrid: target, pid: pid},
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
                        quick4j.parse(body.contentDocument);
                        var pid = win.getData('pid');
                        if(pid) $('#pid').val(pid);
                    }
                });
            }

            function doEdit(){
                var target = $('#treegrid');
                var node = target.treegrid('getSelected');

                if(!node){
                    $.messager.alert("警告", "请选择要编辑的资源!", "warning");
                    return;
                }

                $.showModalDialog({
                    title: '新建',
                    content: 'url:upm/paths/'+node.id+'/edit',
                    data: {operation: 'edit', treegrid: target, node: node},
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
                        record = win.getData('node');
                        $('#id').val(record.id);
                        $('#name').val(record.name);
                        $('#path').val(record.path);
                        $('#icon').val(record.icon);
                        $('#index').numberspinner('setValue', record.index);
                        $('#pid').val(record.pid);
                    }
                });
            }

            function doDelete(){
                var target = $('#treegrid');
                var node = target.treegrid('getSelected');

                if(!node){
                    $.messager.alert("警告", "请选择要删除的数据!", "warning");
                    return;
                }

                $.messager.confirm('确认', '确认删除选中的数据?', function(r){
                    if(r){
                        $.ajax({
                            url: 'upm/paths/' + node.id + '/delete',
                            success: function(data){
                                if(data.status == 200){
                                    target.treegrid('reload');
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

            function doAssignButtons(){
                var target = $('#treegrid');
                var node = target.treegrid('getSelected');

                if(!node){
                    $.messager.alert("警告", "请选择要分配按钮的资源!", "warning");
                    return;
                }
                $.showModalDialog({
                    title: '分配按钮',
                    content: 'url:upm/paths/'+node.id+'/assign/actions',
                    data: {node: node},
                    useiframe: true,
                    showMask: true,
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
                        if(body) body.doInit(win);
                    }
                });
            }
        </script>
    </body>
</html>
