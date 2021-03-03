package me.dwliu.ebase.sample.es;

import com.alibaba.fastjson.JSONArray;
import me.dwliu.ebase.sample.es.service.EsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EbaseBootSampleEsApplicationTests {
    @Autowired
    private EsService esService;

    @Test
    public void Test2() {
        JSONArray result = esService.searchAgg("dg_location_latest", EsUtil.buildGeoHashGridSearch(true, false, false, false));
        System.out.println(result.toJSONString());
    }

}
