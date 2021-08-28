package com.example.demo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class MyPreciseDSShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        String logicTableName = preciseShardingValue.getLogicTableName();
        String ColumnName = preciseShardingValue.getColumnName();
        Long ColumnValue = preciseShardingValue.getValue();
        System.out.println(ColumnName+"----->"+ColumnValue);
        Long res = ColumnValue%2 + 1;
        String key = "ds" + res;
        if (collection.contains(key)){
            return key;
        }else {
            throw new UnsupportedOperationException(key + "找不到该数据库！");
        }
    }

}
