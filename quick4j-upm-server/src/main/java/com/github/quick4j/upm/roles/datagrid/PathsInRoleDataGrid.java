package com.github.quick4j.upm.roles.datagrid;

import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.plugin.datagrid.DataGrid;
import com.github.quick4j.plugin.datagrid.entity.DynamicColumnDataGrid;
import com.github.quick4j.plugin.datagrid.meta.*;
import com.github.quick4j.upm.actions.entity.Action;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
@Component
public class PathsInRoleDataGrid extends DynamicColumnDataGrid{
    @Resource
    private MyBatisRepository myBatisRepository;

    public PathsInRoleDataGrid() {
        super("pathsInRole", "com.github.quick4j.upm.paths.entity.Path");
        newToolbar()
                .addToolbutton("Apply", "icon-ok", "doApplyEditingRow")
                .addToolbutton("Cancel Edit", "icon-cancel", "doCancelEditRow");
    }

    public PathsInRoleDataGrid(String name, String entity, MyBatisRepository myBatisRepository) {
        super(name, entity);
        this.myBatisRepository = myBatisRepository;
    }

    @Override
    public List<Header> getColumns() {
        List<Action> list = myBatisRepository.findAll(Action.class);
        List<Header> columns = new ArrayList<Header>();
        Header header = new Header();
        columns.add(header);

        for(Action action : list){
            Column column = new Column(action.getName(), action.getCode(), 80);
            column.setAlign("center");
            column.setFormatter("onFormatterHandler");
            Editor editor = new Editor("checkbox");
            editor.addOption("on", "check");
            editor.addOption("off", "cross");
            editor.addOption("field", action.getCode());
            column.setEditor(editor);
            header.addColumn(column);
        }

        return columns;
    }

    @Override
    public List<Header> getFrozenColumns() {
        List<Header> columns = new ArrayList<Header>();
        Header header = new Header();
        header.addColumn("资源", "name", 200);
        columns.add(header);
        return columns;
    }

    @Override
    public DataGrid copySelf() {
        try{
            PathsInRoleDataGrid dataGrid = new PathsInRoleDataGrid(getName(), getEntity(), myBatisRepository);

            if(isExistToolbar()){
                Toolbar toolbar = dataGrid.newToolbar();
                for (Toolbutton toolbutton : getToolbar()){
                    toolbar.add(toolbutton.clone());
                }
            }

            if(isSupportPostProcess()){
                dataGrid.setPostProcessor(getPostProcessor());
            }

            return dataGrid;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
