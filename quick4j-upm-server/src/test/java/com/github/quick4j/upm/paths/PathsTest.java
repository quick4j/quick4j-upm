package com.github.quick4j.upm.paths;

import static org.hamcrest.CoreMatchers.*;

import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.upm.actions.entity.Action;
import com.github.quick4j.upm.paths.entity.ActionInPath;
import com.github.quick4j.upm.paths.entity.Path;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author zhaojh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "/config/spring-config.xml",
    "/config/spring-config-mybatis.xml"
})
public class PathsTest {

    @Resource
    private Repository repository;

    @Test
    public void test(){
        Action action = repository.find(Action.class, "2C4E478F84194F1EB2DB69BF00425415");
        Assert.assertThat(action.getCode(), is("new"));
    }

//    @Test
//    @Transactional
    public void batchInsertPaths(){
        for (int i=1; i<501; i++){
            Path path = new Path();
            path.setName("path-" + i);
            path.setPath("/"+path.getName());
            path.setPid("0");
            path.setIndex(i);

            repository.insert(path);
            for (int j=1; j<4; j++){
                Path sub = new Path();
                sub.setPid(path.getId());
                sub.setName("sub-" + (10) * i + j);
                sub.setPath(path.getPath() + '/' + sub.getName());
                sub.setIndex(j);
                repository.insert(sub);

                Random random = new Random();
                int randomNum = random.nextInt(4) + 1 ;
                System.out.println("count: " + i + ", randomNum: " + randomNum);
                if(randomNum % 2 == 0){
                    for (int k=1; k<randomNum; k++){
                        Path third = new Path();
                        third.setPid(sub.getId());
                        third.setName("third-" + (10) * ((10) * i + j) + k);
                        third.setPath(sub.getPath() +'/' + third.getName());
                        third.setIndex(k);
                        repository.insert(third);
                    }
                }
            }
        }
    }

//    @Test
//    @Transactional
    public void batchBuildActionPathRelation(){
        List<Path> pathList = repository.findAll(Path.class);
        List<Action> actionList = repository.findAll(Action.class);
        String[] actions = new String[]{"new", "edit", "delete", "save"};
        List<String> list = Arrays.asList(actions);

        for (Path path : pathList){

            for (Action action : actionList){
                if(list.contains(action.getCode())){
                    ActionInPath actionInPath = new ActionInPath(action, path);
                    repository.insert(actionInPath);
                }
            }

        }
    }
}
