package com.example.demo.util;

import com.example.demo.bean.BeanDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.alibaba.nacos.api.common.Constants.DEFAULT_GROUP;

@Component
public class DeleteDataUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Integer CommitCount = 200000;

    @Async
    public void loadDbTables(BeanDataSource beanDataSource){
        PreparedStatement pst01 = null;
        PreparedStatement pst02 = null;
        ResultSet rs01 = null;
        ResultSet rs02 = null;
        List<String> listTableNames = new ArrayList<>();
        String dbName = beanDataSource.getDbName();
        Connection connection = beanDataSource.getConnection();
        String sqlGetAllTableName = "select table_name from information_schema.tables where table_schema=?";
        try {
            pst01 = connection.prepareStatement(sqlGetAllTableName);
            pst01.setString(1, dbName);
            rs01 = pst01.executeQuery();
            while (rs01.next()){
                String tableName = rs01.getString("table_name");
                String sqlGetTableColumnName = "SELECT GROUP_CONCAT(COLUMN_NAME SEPARATOR \",\") columnNames FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
                pst02 = connection.prepareStatement(sqlGetTableColumnName);
                pst02.setString(1,dbName);
                pst02.setString(2,tableName);
                rs02 = pst02.executeQuery();
                while (rs02.next()){
                    String columnNames = rs02.getString("columnNames");
                    if (columnNames.contains("TENANT_KEY")){
                        List<String> deleteIdList = getDeleteId(beanDataSource.getModNumber(),beanDataSource.getCount(),tableName,connection);
                        if (deleteIdList.size()>0&&deleteIdList!=null){
                            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "------>" + Thread.currentThread().getName() + "开始删除" + dbName + "." + tableName + "......");
                            deleteIdList(deleteIdList,tableName,connection);
                            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "------>" + Thread.currentThread().getName() + "删除成功......");
                        }

                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            beanDataSource.setTableNames(listTableNames);
        }
    }


    public void loadDbTablesbak(List<BeanDataSource> list){
        for (int i=0; i<list.size(); i++){
            PreparedStatement pst01 = null;
            PreparedStatement pst02 = null;
            ResultSet rs01 = null;
            ResultSet rs02 = null;
            BeanDataSource beanDataSource = list.get(i);
            List<String> listTableNames = new ArrayList<>();
            String dbName = beanDataSource.getDbName();
            Connection connection = beanDataSource.getConnection();
            String sqlGetAllTableName = "select table_name from information_schema.tables where table_schema=?";
            try {
                pst01 = connection.prepareStatement(sqlGetAllTableName);
                pst01.setString(1, dbName);
                rs01 = pst01.executeQuery();
                while (rs01.next()){
                    String tableName = rs01.getString("table_name");
                    String sqlGetTableColumnName = "SELECT GROUP_CONCAT(COLUMN_NAME SEPARATOR \",\") columnNames FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
                    pst02 = connection.prepareStatement(sqlGetTableColumnName);
                    pst02.setString(1,dbName);
                    pst02.setString(2,tableName);
                    rs02 = pst02.executeQuery();
                    while (rs02.next()){
                        String columnNames = rs02.getString("columnNames");
                        if (columnNames.contains("TENANT_KEY")){
                            List<String> deleteIdList = getDeleteId(beanDataSource.getModNumber(),beanDataSource.getCount(),tableName,connection);
                            if (deleteIdList.size()>0&&deleteIdList!=null){
                                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "开始删除" + dbName + "." + tableName + "......");
                                deleteIdList(deleteIdList,tableName,connection);
                                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"删除成功......");
                            }

                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                beanDataSource.setTableNames(listTableNames);
            }
        }
    }


    public void deleteIdList(List<String> list,String tableName,Connection connection){
        PreparedStatement pst = null;
        String sqlDeleteData = "delete from " + tableName + " where REQUESTID = ?";
        try {
            connection.setAutoCommit(false);
            pst = connection.prepareStatement(sqlDeleteData);
            int count = 0;
            for (String id:list){
                pst.setString(1,id);
                pst.addBatch();
                count++;
                if (count%CommitCount==0){
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "------>" + Thread.currentThread().getName() + "------>" + "开始执行事务111");
                    pst.executeBatch();
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "------>" + Thread.currentThread().getName() + "------>" + "执行事务结束222");
                }
            }
            if (count%CommitCount>0){
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "------>" + Thread.currentThread().getName() + "------>" + "最终提交事务333");
                pst.executeBatch();
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "------>" + Thread.currentThread().getName() + "------>" + "最终提交结束444");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }


    public List<String> getDeleteId(int modNumber,int count,String tableName,Connection connection){
        List<String> list = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sqlGetDeleteIds = "select REQUESTID from " + tableName + " where ";
        if (count>1){
            for (int i=0; i<count; i++){
                if (modNumber!=i){
                    sqlGetDeleteIds = sqlGetDeleteIds + "REQUESTID mod " + count + " = " + i + " or ";
                }
            }
            sqlGetDeleteIds = sqlGetDeleteIds.substring(0,sqlGetDeleteIds.lastIndexOf("or"));
            try {
                pst = connection.prepareStatement(sqlGetDeleteIds);
                rs = pst.executeQuery();
                while (rs.next()){
                    list.add(rs.getString("REQUESTID"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            logger.error("count不大于1......");
        }
        return list;
    }




}
