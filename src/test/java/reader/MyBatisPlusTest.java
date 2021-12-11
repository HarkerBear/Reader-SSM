package reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reader.entity.Test;
import reader.mapper.TestMapper;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MyBatisPlusTest {
    @Resource
    private TestMapper testMapper;

    @org.junit.Test
    public void testInsert(){
        Test test = new Test();
        test.setContent("MyBatis-plus");
        testMapper.insert(test);
    }

    @org.junit.Test
    public void testUpdate(){
        Test test=testMapper.selectById(30);
        test.setContent("new-30");
        testMapper.updateById(test);
    }
    @org.junit.Test
    public void testSelect(){
        QueryWrapper<Test> queryWrapper=new QueryWrapper<Test>();
        queryWrapper.gt("id",30);
        List<Test> list=testMapper.selectList(queryWrapper);
        System.out.println(list.get(0));
    }
}
