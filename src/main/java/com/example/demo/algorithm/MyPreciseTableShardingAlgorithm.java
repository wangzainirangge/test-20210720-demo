package com.example.demo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class MyPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        //select * from course cid = '' or cid in ('','')

        String logicTableName = preciseShardingValue.getLogicTableName();
        String ColumnName = preciseShardingValue.getColumnName();
        Long ColumnValue = preciseShardingValue.getValue();
        //System.out.println(ColumnName+"----->"+ColumnValue);
        //实现course_$->{cid%2 + 1}     course_1    course_2
        Long res = ColumnValue%2 + 1;
        String key = logicTableName + "_" + res;
        if (collection.contains(key)){
            return key;
        }else {
            throw new UnsupportedOperationException(key + "在数据库找不到相应表！");
        }

    }

}
