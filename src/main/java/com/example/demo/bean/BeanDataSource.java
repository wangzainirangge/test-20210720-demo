package com.example.demo.bean;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

public class BeanDataSource {
    private String dbName;

    private Connection connection;

    private String driver;

    private String url;

    private String username;

    private String password;

    private Integer modNumber;

    private Integer count;

    private List<String> tableNames;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getModNumber() {
        return modNumber;
    }

    public void setModNumber(Integer modNumber) {
        this.modNumber = modNumber;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    @Override
    public String toString() {
        return "BeanDataSource{" +
                "dbName='" + dbName + '\'' +
                ", connection=" + connection +
                ", driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", modNumber=" + modNumber +
                ", count=" + count +
                ", tableNames=" + tableNames +
                '}';
    }
}
