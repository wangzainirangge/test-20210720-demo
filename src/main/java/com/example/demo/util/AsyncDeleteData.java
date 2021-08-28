package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.example.demo.bean.BeanDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.nacos.api.common.Constants.DEFAULT_GROUP;

@Component
public class AsyncDeleteData {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Integer CommitCount = 100000;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.name}")
    private String dataId;

    @Autowired
    private DeleteDataUtil deleteDataUtil;

    public void deleteData(){
        JSONObject jsonObject = getJson();
        List<BeanDataSource> list = null;
        if (jsonObject!=null){
            JSONObject dataSourcesJson = jsonObject.getJSONObject("weaversphere");
            if (dataSourcesJson!=null){
                list = getDataSources(dataSourcesJson);
                for (int i=0;i<list.size();i++){
                    deleteDataUtil.loadDbTables(list.get(i));
                }
            } else {
                logger.error("dataSourcesJson为null......");
            }
        } else {
            logger.error("jsonObject为null......");
        }
    }

    public JSONObject getJson(){
        JSONObject jsonObject = null;
        ConfigService configService = null;
        try {
            configService = NacosFactory.createConfigService(serverAddr);
            String content = configService.getConfig(dataId, DEFAULT_GROUP, 3000L);
            Yaml yaml = new Yaml();
            jsonObject = yaml.loadAs(content, JSONObject.class);
        } catch (NacosException e) {
            e.printStackTrace();
        }finally {
            return jsonObject;
        }
    }

    public List<BeanDataSource> getDataSources(JSONObject dataSourcesJson){
        List<BeanDataSource> list = new ArrayList<>();
        try {
            //Yaml yaml = new Yaml();
            //JSONObject jsonObject = yaml.loadAs(new FileInputStream(new File("./src/main/resources/application.yaml")), JSONObject.class);
            int count = dataSourcesJson.size();
            for (int i=0; i<count; i++){
                JSONObject dataSourceBeanJson = dataSourcesJson.getJSONObject("datasource-0" + (i+1));
                BeanDataSource beanDataSource = new BeanDataSource();
                beanDataSource.setModNumber(Integer.valueOf(dataSourceBeanJson.getString("modNumber")));
                String driver = dataSourceBeanJson.getString("driver-class-name");
                String url = dataSourceBeanJson.getString("url");
                String username = dataSourceBeanJson.getString("username");
                String password = dataSourceBeanJson.getString("password");
                String[] tmps = url.substring(0,url.indexOf("?")).split("/");
                beanDataSource.setDbName(tmps[tmps.length-1]);
                beanDataSource.setDriver(driver);
                beanDataSource.setUrl(url);
                beanDataSource.setUsername(username);
                beanDataSource.setPassword(password);
                beanDataSource.setCount(count);
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(url, username, password);
                beanDataSource.setConnection(connection);
                list.add(beanDataSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return list;
        }
    }
}
