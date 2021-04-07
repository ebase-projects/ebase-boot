package me.dwliu.ebase.sample.es;

import com.alibaba.fastjson.JSONArray;
import me.dwliu.ebase.sample.es.service.EsService;
import org.elasticsearch.search.SearchHit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@SpringBootTest
class EbaseBootSampleEsApplicationTests {
	@Autowired
	private EsService esService;

	@Test
	public void Test2() {
		JSONArray result = esService.searchAgg("dg_location_latest", EsUtil.buildGeoHashGridSearch(true, false, false, false));
		System.out.println(result.toJSONString());
		System.out.println(result.size());
	}

	@Test
	public void Test3() {
		boolean beidou = true;
		boolean satellite = false;
		boolean ais = true;
		boolean mmsi = false;
		SearchHit[] esData = this.esService.searchHits("dg_location_latest", EsUtil.buildGeoBoundingBoxSearch(null, null, null, null, beidou, satellite, ais, mmsi));
		System.out.println(esData);

		Arrays.stream(esData).forEach(item -> {
			Map<String, Object> map = item.getSourceAsMap();
			System.out.println(item);
		});
	}

}
