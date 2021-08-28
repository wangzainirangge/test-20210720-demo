package com.example.demo.algorithm;


import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class DefaultComplexDSShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        System.out.println(collection.toString());
        System.out.println(complexKeysShardingValue.getLogicTableName());
        System.out.println(complexKeysShardingValue.getColumnNameAndShardingValuesMap().toString());
        Map<String, Collection<Long>> map =  complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        LinkedList cidList = (LinkedList) map.get("cid");
        LinkedList userIdList = (LinkedList) map.get("user_id");
        System.out.println(cidList);
        System.out.println(userIdList);
        System.out.println("-------------------------------------");
        if (userIdList!=null){
            Long userId = (Long) userIdList.getFirst();
            String key = "ds" + (userId%2 + 1);
            return Arrays.asList(key);
        } else {
            return Arrays.asList("ds1","ds2");
        }
    }

}
