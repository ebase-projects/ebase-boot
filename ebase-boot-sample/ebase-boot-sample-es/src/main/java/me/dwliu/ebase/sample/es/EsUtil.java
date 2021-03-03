package me.dwliu.ebase.sample.es;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.turf.TurfConstants;
import com.mapbox.turf.TurfTransformation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class EsUtil {

    private EsUtil() {
    }

    public static Class<?> getClazz(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param queryBuilder 设置查询对象
     * @param from         设置from选项，确定要开始搜索的结果索引。 默认为0
     * @param size         设置大小选项，确定要返回的搜索匹配数。 默认为10
     * @param timeout
     * @return
     */
    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder, int from, int size, int timeout) {

        //使用默认选项创建 SearchSourceBuilder 。
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置查询对象。可以使任何类型的 QueryBuilder
        sourceBuilder.query(queryBuilder);
        //设置from选项，确定要开始搜索的结果索引。 默认为0。
        sourceBuilder.from(from);
        //设置大小选项，确定要返回的搜索匹配数。 默认为10。
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(timeout, TimeUnit.SECONDS));
        return sourceBuilder;
    }

    public static SearchSourceBuilder initSearchSourceBuilder(QueryBuilder queryBuilder) {
        return initSearchSourceBuilder(queryBuilder, 0, 10, 60);
    }

    /**
     * 查询符合多边形内的数据
     *
     * @param field
     * @param points
     * @return
     * @throws Exception
     */
    public static GeoPolygonQueryBuilder polygonQuery(String field, List<GeoPoint> points) {
        if (points == null || points.size() <= 0) {
            return null;
        }
        GeoPolygonQueryBuilder qb = QueryBuilders.geoPolygonQuery(field, points);
        return qb;
    }

    /**
     * 查找在给定的中心点确定范围内的数据
     *
     * @param field
     * @param distance
     * @param point
     * @return
     * @throws Exception
     */
    public static GeoDistanceQueryBuilder distanceQuery(String field, double distance, GeoPoint point) {
        if (point == null) {
            return null;
        }
        GeoDistanceQueryBuilder qb = QueryBuilders.geoDistanceQuery(field)
                .point(point)
                .distance(distance, DistanceUnit.KILOMETERS);
        return qb;
    }

    /**
     * 查找在给定的中心点确定范围内的数据
     *
     * @param field
     * @param distance
     * @param point
     * @return
     * @throws Exception
     */
    public static GeoDistanceQueryBuilder distanceQueryMeters(String field, double distance, GeoPoint point) {
        if (point == null) {
            return null;
        }
        GeoDistanceQueryBuilder qb = QueryBuilders.geoDistanceQuery(field)
                .point(point)
                .distance(distance, DistanceUnit.METERS);
        return qb;
    }

    /**
     * 多条件查询条件AND
     *
     * @param mustMap
     * @return
     */
    public static BoolQueryBuilder multiQuery(Map<String, Object> mustMap) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : mustMap.entrySet()) {
            boolQueryBuilder.must(QueryBuilders
                    .matchQuery(entry.getKey(), entry.getValue()));
        }
        return boolQueryBuilder;
    }

    /**
     * 查询字段等于且不等于
     *
     * @param filed   字段名称
     * @param must    字段等于匹配值
     * @param notMust 字段不等于匹配值
     * @return
     */
    public static BoolQueryBuilder multiAndNotQuery(String filed, Object[] must, Object[] notMust) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (null != must && must.length > 0) {
            for (Object value : must) {
                boolQueryBuilder.should(QueryBuilders
                        .matchQuery(filed, value));
            }
        }
        if (null != notMust && notMust.length > 0) {
            BoolQueryBuilder notBoolQueryBuilder = QueryBuilders.boolQuery();
            for (Object value : notMust) {
                notBoolQueryBuilder.mustNot(QueryBuilders
                        .matchQuery(filed, value));
            }
            boolQueryBuilder.should(notBoolQueryBuilder);
        }
        return boolQueryBuilder;
    }

    /**
     * 多条件查询条件OR
     *
     * @param mustMap
     * @return
     */
    public static BoolQueryBuilder multiOrQuery(Map<String, Object> mustMap) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : mustMap.entrySet()) {
            boolQueryBuilder.should(QueryBuilders
                    .termQuery(entry.getKey(), entry.getValue()));
        }
        return boolQueryBuilder;
    }

    /**
     * 多条件查询条件OR
     *
     * @param filed
     * @param values
     * @return
     */
    public static BoolQueryBuilder multiOrOneQuery(String filed, Object[] values) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Object value : values) {
            boolQueryBuilder.should(QueryBuilders
                    .termQuery(filed, value));
        }
        return boolQueryBuilder;
    }

    /**
     * 多条件不等于
     *
     * @param filed
     * @param values
     * @return
     */
    public static BoolQueryBuilder mustNotQuery(String filed, Object[] values) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Object value : values) {
            boolQueryBuilder.mustNot(QueryBuilders
                    .matchQuery(filed, value));
        }
        return boolQueryBuilder;
    }

    /**
     * 根据时间段查询
     *
     * @param field
     * @param from
     * @param to
     * @return
     */
    public static RangeQueryBuilder dateQuery(String field, Date from, Date to) {

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field)
                .from(DateFormatUtils.format(from, "yyyy-MM-dd HH:mm:ss"))
                .to(DateFormatUtils.format(to, "yyyy-MM-dd HH:mm:ss"));
        return rangeQueryBuilder;
    }


    /**
     * 模糊查找
     *
     * @param name
     * @param query
     * @return
     */
    public static WildcardQueryBuilder fuzzyQuery(String name, String query) {
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(name, query);
        return wildcardQueryBuilder;
    }


    public static SearchSourceBuilder buildSearch(int form, int size, QueryBuilder... queryBuilders) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (QueryBuilder queryBuilder : queryBuilders) {
            boolQueryBuilder.must(queryBuilder);
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(size);
        searchSourceBuilder.from(form);
        return searchSourceBuilder;
    }

    /*public static SearchSourceBuilder buildSearch(QueryBuilder... queryBuilders) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (QueryBuilder queryBuilder : queryBuilders) {
            boolQueryBuilder.must(queryBuilder);
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(10000);
        searchSourceBuilder.from(0);
        return searchSourceBuilder;
    }*/

    public static SearchSourceBuilder buildSearch(int form, int size, List<QueryBuilder> andQueryList, List<QueryBuilder> orQueryList) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        for (QueryBuilder andQuery : andQueryList) {
            boolQueryBuilder.must(andQuery);
        }
        for (QueryBuilder orQuery : orQueryList) {
            boolQueryBuilder.should(orQuery);
        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(size);
        searchSourceBuilder.from(form);
        return searchSourceBuilder;
    }


    public static SearchSourceBuilder buildGeoHashGridSearch(boolean beidou, boolean satellite, boolean ais, boolean mmsi) {
        AggregationBuilder aggregation = AggregationBuilders
                .geohashGrid("agg")
                .field("LOCATION")
                .precision(7);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (beidou || satellite || ais || mmsi) {
            boolQueryBuilder.must(buildGeoQuery(beidou, satellite, ais, mmsi, -1, -1, -1));
        } else {
            boolQueryBuilder.must(QueryBuilders.existsQuery("null"));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregation);
        searchSourceBuilder.size(0);
        return searchSourceBuilder;
    }

    public static SearchSourceBuilder buildGeoBoundingBoxSearch(Double topLeftLon, Double topLeftlat, Double bottomRightLon, Double bottomRightLat, boolean beidou, boolean satellite, boolean ais, boolean mmsi) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(buildGeoQuery(beidou, satellite, ais, mmsi, -1, -1, -1));

        if (null != topLeftLon && null != topLeftlat && null != bottomRightLon && null != bottomRightLat) {
            boolQueryBuilder.must(QueryBuilders.geoBoundingBoxQuery("LOCATION").setCorners(new GeoPoint(topLeftlat, topLeftLon), new GeoPoint(bottomRightLat, bottomRightLon)));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.collapse(new CollapseBuilder("C0010007_CM"));
        searchSourceBuilder.sort(getSortBuilder());
        searchSourceBuilder.sort("UTC", SortOrder.DESC);
        searchSourceBuilder.fetchSource(new String[]{"ID", "LON", "LAT", "C0010007_CM", "TERMINAL_TYPE", "BEIDOU", "MMSI", "SATELLITE", "AIS", "C0010010_CC", "SHIP_DISTRICT", "UTC", "COURSE", "DISTRESS_TYPE", "DISTRESS", "ASSIGNMENT_TYPE", "SHIP_TOWN", "SHIP_VILLAGE"}, null);
        searchSourceBuilder.size(100000);
        return searchSourceBuilder;
    }

    public static SearchSourceBuilder buildOtherSearch() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort("UTC", SortOrder.DESC);
        searchSourceBuilder.size(100000);
        return searchSourceBuilder;
    }

    public static SearchSourceBuilder buildGeoPolygonSearch(String shipName, List<GeoPoint> points, boolean beidou, boolean satellite, boolean ais, boolean mmsi) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(buildGeoQuery(beidou, satellite, ais, mmsi, -1, -1, -1));
        boolQueryBuilder.must(QueryBuilders.geoPolygonQuery("LOCATION", points));
        boolQueryBuilder.must(QueryBuilders.wildcardQuery("C0010007_CM", "*" + shipName + "*"));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.collapse(new CollapseBuilder("C0010007_CM"));
        searchSourceBuilder.sort(getSortBuilder());
        searchSourceBuilder.sort("UTC", SortOrder.DESC);
        searchSourceBuilder.size(5);
        return searchSourceBuilder;
    }

    /**
     * 自定义排序按北斗 > 插卡式AIS > 海事卫星 > AIS顺序排序
     *
     * @return
     */
    private static SortBuilder getSortBuilder() {
        //定义传入script的参数
        // 自定义评分规则
        Map<String, Float> groupMap = new HashMap<>();
        groupMap.put("11", 12.0f);
        groupMap.put("21", 9.0f);
        groupMap.put("10", 6.0f);
        groupMap.put("12", 3.0f);

        Map<String, Object> params = new HashMap<>();
        params.put("groupMap", groupMap);

        String field = "TERMINAL_TYPE";
        // String activeProfile = SpringContextHolder.getApplicationContext().getEnvironment().getActiveProfiles()[0];
        // if (StringUtils.equals(activeProfile, "dev")) {
        //     field = "TERMINAL_TYPE.keyword";
        // } else {
        //     field = "TERMINAL_TYPE";
        // }
        // 定义脚本
        String scriptText = " def groupScore = params.groupMap[doc['" + field + "'].value];" +
                "groupScore != null?groupScore:0;";
        // 定义script 注意不同的es版本参数顺序不一样
        Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, scriptText, params);

        // 定义ScriptSortBuilder
        ScriptSortBuilder builder = new ScriptSortBuilder(script, ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC);
//        ScriptSortBuilder builder = new ScriptSortBuilder(script, ScriptSortBuilder.ScriptSortType.NUMBER);

        return builder;
    }

    public static SearchSourceBuilder buildSurroundShipSearch(boolean beidou, boolean satellite, boolean ais, boolean mmsi, double lon, double lat, double radius) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(buildGeoQuery(beidou, satellite, ais, mmsi, lon, lat, radius));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.fetchSource(new String[]{"ID", "LON", "LAT", "C0010007_CM", "C0010009_CBZL", "SHIP_DISTRICT", "C0010010_CC", "UTC", "SHIP_INFO_ID", "SPEED"}, null);
        searchSourceBuilder.size(10000);
        return searchSourceBuilder;
    }

    public static BoolQueryBuilder buildGeoQuery(boolean beidou, boolean satellite, boolean ais, boolean mmsi, double lon, double lat, double radius) {
        BoolQueryBuilder devBoolQueryBuilder = QueryBuilders.boolQuery();
        if (beidou || satellite || ais || mmsi) {
            if (beidou) {
                devBoolQueryBuilder.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery("BEIDOU", "")).must(QueryBuilders.existsQuery("BEIDOU")));
            }
            if (satellite) {
                devBoolQueryBuilder.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery("SATELLITE", "")).must(QueryBuilders.existsQuery("SATELLITE")));
            }
            if (ais) {
                devBoolQueryBuilder.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery("AIS", "")).must(QueryBuilders.existsQuery("AIS")));
            }
            if (mmsi) {
                devBoolQueryBuilder.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery("MMSI", "")).must(QueryBuilders.existsQuery("MMSI")));
            }
        }
        BoolQueryBuilder result = QueryBuilders.boolQuery();
        result.must(devBoolQueryBuilder);
        //查询监管渔船
        result.must(QueryBuilders.termQuery("MANAG", "0"));
        if (radius > 0) {
            Polygon circle = TurfTransformation.circle(Point.fromLngLat(lon, lat), radius, 128, TurfConstants.UNIT_NAUTICAL_MILES);
            List<GeoPoint> points = new ArrayList<>();
            circle.coordinates().stream().forEach(coord -> {
                coord.stream().forEach(item -> {
                    points.add(new GeoPoint(item.latitude(), item.longitude()));
                });
            });
            result.must(QueryBuilders.geoPolygonQuery("LOCATION", points));
        }

        return result;
    }
}
