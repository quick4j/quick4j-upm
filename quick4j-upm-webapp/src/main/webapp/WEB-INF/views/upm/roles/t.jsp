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
        <div data-options="region: 'center', border: false">
            <input type="hidden" id="roleId">
            <table class="quick4j-treegrid" id="pathsInRole_treegrid"
                   data-options="
                       name: 'pathsInRole',
                       fit: true,
                       striped: true,
                       border: false,
                       idField: 'id',
                       treeField: 'name',
                       queryParams:{roleId: $('#roleId').val()},
                       customAttr: {
                            iconField: 'icon',
                            parentField: 'pid',
                            contextMenu:{
                                isShow: true,
                                isMerge: false,
                                items:[{
                                    text: '编辑',
                                    iconCls: 'icon-edit',
                                    onclick: doEditRow
                                },{
                                    text: '编辑并全选',
                                    onclick: doEditRowAndCheckedAll
                                },'-',{
                                    text: '全选',
                                    onclick: doCheckedAllForEditingRow
                                },{
                                    text: '取消全选',
                                    onclick: doUnCheckedAllForEditingRow
                                },'-',{
                                    text: '取消编辑',
                                    onclick: doCancelEditRow
                                },{
                                    text: '应用',
                                    onclick: doApplyEditingRow
                                }]
                            }
                       },
                       onBeginEdit: onBeforeEditHandle,
                       onAfterEdit: onAfterEditHandle
                       ">
            </table>
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
        <script type="text/javascript">
            //重写checkbox editor 以满足资源授权
            $.extend($.fn.datagrid.defaults.editors, {
                checkbox: {
                    init: function(container, options){
                        var input = $("<input type=\"checkbox\">").appendTo(container);
                        input.val(options.on)
                                .attr('offval', options.off)
                                .attr('field', options.field);
                        return input;
                    },
                    getValue: function(target){
                        if($(target).is(':checked')){
                            return $(target).val();
                        }else{
                            var field = $(target).attr('field');
                            if(editingRow.data[field]){
                                return $(target).attr("offval");
                            }else{
                                return editingRow.data[field]
                            }
                        }
                    },
                    setValue: function(target, value){
                        var checked = false;
                        if($(target).val() == value){
                            checked = true;
                        }
                        $(target)._propAttr('checked', checked);
                    }
                }
            });

            var editingRow;
            function onFormatterHandler(value, row, index){
                switch (value){
                    case 'cross':
                        var html = "<span>&#10008</span>";
                        break;
                    case 'check':
                        var html = "<span style='color:#00BB00;'>&#10004</span>";
                        break;
                    default :
                        var html = "<span>&#8211</span>";
                }

                return html;
            }

            function doEditRow(item, row, target){
                if(!row.editable) return;
                if(editingRow) return;
                setEditingRow(row, target);
                $(target).treegrid('beginEdit', row.id);

            }

            function doCheckedAllForEditingRow(item, row, target){
                var editors = $(target).treegrid('getEditors', row.id);
                $.each(editors, function(i, edit){
                    if(row[edit.field]){
                        $(edit.target).prop('checked', true);
                    }
                });
            }

            function doUnCheckedAllForEditingRow(item, row, target){
                var editors = $(target).treegrid('getEditors', row.id);
                $.each(editors, function(i, edit){
                    if(row[edit.field]){
                        $(edit.target).prop('checked', false);
                    }
                });
            }

            function doEditRowAndCheckedAll(item, row, target){
                doEditRow(item, row, target);
                doCheckedAllForEditingRow(item, row, target);
            }

            function doCancelEditRow(item, row, target){
                $(target).treegrid('cancelEdit', row.id);
                clearEditingRow();
            }

            function doApplyEditingRow(item, row, target){
                if(editingRow){
                    $(editingRow.target).treegrid('endEdit', editingRow.data.id);

                    if(!editingRow.applyState){
                        alert('faile');
                        $(editingRow.target).treegrid('update', {id: editingRow.data.id, row: editingRow.data});
                    }

                    clearEditingRow();
                }
            }

            function onBeforeEditHandle(e, row){
                var editors = $(this).treegrid('getEditors', row.id);
                $.each(editors, function(i, edit){
                    if(!row[edit.field]){
                        $(edit.target).hide();
                    }
                });
            }

            function onAfterEditHandle(row, changes){
                editingRow.changes = changes;
                console.log(changes);

                var getAction = function(code){
                    return $.grep(editingRow.data.actions, function(action, i){
                        return action.code == code;
                    })[0];
                };

                if(!$.isEmptyObject(editingRow.changes)){
                    var permission = {
                        inserted: [],
                        deleted: []
                    };
                    for(var code in editingRow.changes){
                        switch (editingRow.changes[code]){
                            case 'check':
                                permission.inserted.push({
                                    pathId: editingRow.data.id,
                                    actionCode: code,
                                    actionId: getAction(code).id
                                });
                                break;
                            case 'cross':
                                permission.deleted.push({
                                    pathId: editingRow.data.id,
                                    actionCode: code,
                                    actionId: getAction(code).id
                                });
                                break;
                        }
                    }

                    if(permission.inserted.length > 0 || permission.deleted.length > 0){
                        doSavePathsPermission(permission);
                    }
                }
            }

            function clearEditingRow(){
                editingRow = null;
            }

            function setEditingRow(row, target){
                editingRow = {
                    target: target,
                    data: $.parseJSON($.toJSON(row)),
                    changes: {},
                    applyState: false
                };
            }

            function doSavePathsPermission(permission){
                $.ajax({
                    type: 'post',
                    async: false,
                    url: 'upm/roles/'+roleId+'/paths',
                    data: {inserted: $.toJSON(permission.inserted), deleted: $.toJSON(permission.deleted)},
                    success: function(reponse){
                        if(reponse.status != 200){
                            $.messager.alert('错误', reponse.status + '<br>' + reponse.message, 'error');
                        }else{
                            editingRow.applyState = true;
                        }
                    },
                    error: function(){
                        $.messager.alert('错误', '操作过程中发生错误。', 'error');
                    }
                });
            }

            var roleId;
            function pathsWindowInit(win){
                roleId = win.getData('row').id;
                $('#roleId').val(roleId);
            }

        </script>
    </body>
</html>
