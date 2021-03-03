package me.dwliu.ebase.sample.es;

import com.alibaba.fastjson.JSON;
import me.dwliu.ebase.sample.es.model.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

/**
 * Elasticsearch 测试
 *
 * @author liudw
 * @date 2021/3/1 09:27
 **/
@SpringBootTest
public class ElasticsearchTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private final String indexName = "es_index_test2";

    @Test
    public void testCreateIndex() throws IOException {

        /*
        {
  "es_index_test2" : {
    "aliases" : { },
    "mappings" : { },
    "settings" : {
      "index" : {
        "creation_date" : "1614742385687",
        "number_of_shards" : "1",
        "number_of_replicas" : "1",
        "uuid" : "D_dNU3X_SNODXyVDjDXycw",
        "version" : {
          "created" : "7090399"
        },
        "provided_name" : "es_index_test2"
      }
    }
  }
}*/

        //创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        //客户端请求，获取返回结果
        CreateIndexResponse createIndexResponse = restHighLevelClient
                .indices().create(createIndexRequest, RequestOptions.DEFAULT);

        System.out.println(createIndexResponse);
    }


    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    @Test
    public void testDeleteIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (!exists) {
            return;
        }

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    @Test
    public void testAddDocument() throws IOException {

        String uuid = UUID.randomUUID().toString().replace("-", "");
        User user = new User();
        user.setId(uuid);
        user.setUsername("test");
        user.setAge(11);


        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.id(uuid);
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);


        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        System.out.println(index.status());


    }


    @Test
    public void testIsExists() throws IOException {
        String uuid = "e8c7a750eff04707a85d3ffd23b60740";
        GetRequest getRequest = new GetRequest(indexName, uuid);
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);

    }

    @Test
    public void testGetDocument() throws IOException {
        String uuid = "e8c7a750eff04707a85d3ffd23b60740";
        GetRequest getRequest = new GetRequest(indexName, uuid);
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields);
        System.out.println(documentFields.getSourceAsString());

    }


    @Test
    public void testUpdateRequest() throws IOException {
        String uuid = "e8c7a750eff04707a85d3ffd23b60740";
        UpdateRequest updateRequest = new UpdateRequest(indexName, uuid);
        User user = new User(uuid + "qqqq", "dddd", 23);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);

        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.status());
        System.out.println(update.toString());
    }

    @Test
    public void testDeleteRequest() throws IOException {
        String uuid = "e8c7a750eff04707a85d3ffd23b60740";
        DeleteRequest deleteRequest = new DeleteRequest(indexName, uuid);
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());

    }


    @Test
    public void bathInsert() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueSeconds(2));

        for (int i = 0; i < 10; i++) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            User user = new User(uuid, "test" + i, 20 + 1);
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.id(uuid).source(JSON.toJSONString(user), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
    }

}
