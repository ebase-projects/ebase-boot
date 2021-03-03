package me.dwliu.ebase.sample.es.model;

import org.elasticsearch.search.aggregations.Aggregations;

import java.util.List;

public class EsResult<T> {

    private  List<T> res;
    private long count;
    private Aggregations aggCount;

    public List<T> getRes() {
        return res;
    }

    public void setRes(List<T> res) {
        this.res = res;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Aggregations getAggCount() {
        return aggCount;
    }

    public void setAggCount(Aggregations aggCount) {
        this.aggCount = aggCount;
    }
}
