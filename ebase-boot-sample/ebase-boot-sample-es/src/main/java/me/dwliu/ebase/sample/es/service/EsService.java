package me.dwliu.ebase.sample.es.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.dwliu.ebase.sample.es.EsUtil;
import me.dwliu.ebase.sample.es.model.ElasticEntity;
import me.dwliu.ebase.sample.es.model.EsResult;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGrid;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class EsService {
    @Autowired
    RestHighLevelClient restHighLevelClient;


    //待删除监管渔船
    public static Map<String, String> managerShipDel = new HashMap<>();

    /**
     * 插入数据
     *
     * @param idxName
     * @param entity
     */
    public void insertOrUpdateOne(String idxName, ElasticEntity entity) {
        IndexRequest request = new IndexRequest(idxName);
        request.id(entity.getId());
        request.source(entity.getData(), XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     *
     * @param idxName
     * @param idList  待删除列表
     * @param <T>
     */
    public <T> void deleteBatch(String idxName, Collection<T> idList) {
        BulkRequest request = new BulkRequest();
        idList.forEach(item -> request.add(new DeleteRequest(idxName, item.toString())));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> EsResult<T> search(String idxName, SearchSourceBuilder builder, Class<T> c) {
        EsResult esResult = new EsResult();
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                JSONObject hitObj = JSONObject.parseObject(hit.getSourceAsString());
                if (hitObj.containsKey("UPDATE_BY")) {
                    hitObj.remove("UPDATE_BY");
                }
                res.add(JSON.parseObject(hitObj.toString(), c));
            }
            esResult.setCount(response.getHits().getTotalHits().value);
            Aggregations aggregations = response.getAggregations();
            esResult.setAggCount(aggregations);
            esResult.setRes(res);
            return esResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SearchHit[] searchHits(String idxName, SearchSourceBuilder builder) {
        EsResult esResult = new EsResult();
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            return hits;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject search(String idxName, SearchSourceBuilder builder) {
        JSONObject json = new JSONObject();
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            JSONArray array = new JSONArray();
            for (SearchHit hit : hits) {
                array.add(JSON.parseObject(hit.getSourceAsString()));
            }
            json.put("data", array);
            json.put("total", response.getHits().getTotalHits().value);
            json.put("success", true);
            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray searchAgg(String idxName, SearchSourceBuilder builder) {
        JSONArray result = new JSONArray();
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            GeoGrid agg = response.getAggregations().get("agg");
            for (GeoGrid.Bucket entry : agg.getBuckets()) {
                JSONObject jsonObj = new JSONObject();
                GeoPoint point = (GeoPoint) entry.getKey();
                jsonObj.put("lon", point.getLon());
                jsonObj.put("lat", point.getLat());
                jsonObj.put("count", entry.getDocCount());
                result.add(jsonObj);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByQuery(String idxName, QueryBuilder builder) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(idxName);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long searchCount(String idxName, QueryBuilder builder) {
        try {
            CountRequest countRequest = new CountRequest(idxName);
            countRequest.query(builder);
            CountResponse countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            return countResponse.getCount();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    @Async("threadPoolTaskExecutor")
    public void unManager(String id) {
        try {
            Thread.sleep(5 * 61 * 1000);
            if (null == managerShipDel.get(id)) {
                return;
            }
            QueryBuilder queryBuilder = QueryBuilders.termQuery("ID", id);
            SearchHit[] hits = searchHits("dg_location_latest", EsUtil.buildSearch(0, 100, queryBuilder));
            for (SearchHit hit : hits) {
                UpdateRequest request = new UpdateRequest("dg_location_latest", hit.getId());
                request.doc(XContentType.JSON, "MANAG", "1");
                restHighLevelClient.update(request, RequestOptions.DEFAULT);
            }
            managerShipDel.remove(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
