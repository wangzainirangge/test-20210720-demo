package com.example.demo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Arrays;
import java.util.Collection;

public class MyRangeTableShardingAlgorithm implements RangeShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        //select * from course where cid between 4 and 7
        //Integer lowerValue = (Integer) rangeShardingValue.getValueRange().lowerEndpoint();//4
        //Integer upperValue = (Integer) rangeShardingValue.getValueRange().upperEndpoint();//7
        String logicTableName = rangeShardingValue.getLogicTableName();
        //实现course_$->{cid%2 + 1}     course_1    course_2
        return Arrays.asList(logicTableName+"_1",logicTableName+"_2");
    }


}
