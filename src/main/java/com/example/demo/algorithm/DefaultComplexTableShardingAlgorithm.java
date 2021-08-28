package com.example.demo.algorithm;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class DefaultComplexTableShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        String tableName = complexKeysShardingValue.getLogicTableName();
        System.out.println(collection.toString());
        Map<String, Collection<Long>> map =  complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        LinkedList cidList = (LinkedList) map.get("cid");
        LinkedList userIdList = (LinkedList) map.get("user_id");
        Long cid = (Long) cidList.getFirst();
        String key = "test_" + (cid%2 + 1);
        return Arrays.asList(key);
    }

}
