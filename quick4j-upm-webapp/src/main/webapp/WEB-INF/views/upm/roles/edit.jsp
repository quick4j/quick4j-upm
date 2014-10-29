<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .columns{}
    .row{}
    .row .columns{
        display: inline;
    }
</style>
<div style="padding: 10px;">
    <form id="editFrm" method="post">
        <input type="hidden" name="id" id="id">
        <div class="row">
            <div class="columns">
                <label>名称</label>
            </div>
            <div class="columns">
                <input class="textbox easyui-validatebox" type="text" id="name"
                       name="name" data-options="required:true">
            </div>
        </div>
        <div class="row">
            <div class="columns">
                <label>描述</label>
            </div>
            <div class="columns">
                <input class="textbox" type="text" id="desc"
                       name="desc">
            </div>
        </div>
    </form>
</div>
<script>
    $(document).ready(function(){
        $('#code').focus();
    })

    function doSave(win){
        var id = $('#id').val();
        $('#editFrm').form('submit', {
            url: 'upm/roles/'+id+'/edit',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(data){
                var data = eval('(' + data + ')');
                if(data.status == 200){
                    win.getData('datagrid').datagrid('reload');
                    win.close();
                }else{
                    alert(data.message)
                }
            }
        });
    }
</script>
