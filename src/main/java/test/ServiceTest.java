package test;

import com.lql.dao.AcDataInfoMapper;
import com.wbl.modal.MultipleDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by LQL on 2016/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ServiceTest {

    @Autowired
    private AcDataInfoMapper dataInfoMapper;

    @Test
    public void test() throws  Exception{
        MultipleDataSource.setDataSourceKey("accessControlDataSource");
        System.out.println(dataInfoMapper.getDataInfoById("data1"));

    }
}
