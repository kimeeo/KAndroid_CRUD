package com.kimeeo.kAndroid.crud;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by BhavinPadhiyar on 07/07/16.
 */
public class Query {
    public Query() {
        filters = new HashMap<>();
        columnsMap = new HashMap<>();
        includeMap= new HashMap<>();
    }

    private int pageIndex=-1;
    private int pageSize=30;
    private boolean transform;
    private boolean orderByAsc=true;
    private String satisfy;
    private String orderBy;
    private Map<String,String> filters;
    private Map<String,String> columnsMap;
    private Map<String,String> includeMap;


    public String[] getFilters() {
        Set<Map.Entry<String,String>> set= filters.entrySet();
        String[] data= new String[set.size()];
        int count=0;
        for (Map.Entry<String, String> entry : set) {
            data[count]=entry.getValue();
            count++;
        }
        return data;
    }
    public String[] getColumns() {
        Set<Map.Entry<String,String>> set= columnsMap.entrySet();
        String[] data= new String[set.size()];
        int count=0;
        for (Map.Entry<String, String> entry : set) {
            data[count]=entry.getValue();
            count++;
        }
        return data;
    }
    public String[] getInclude() {
        Set<Map.Entry<String,String>> set= includeMap.entrySet();
        String[] data= new String[set.size()];
        int count=0;
        for (Map.Entry<String, String> entry : set) {
            data[count]=entry.getValue();
            count++;
        }
        return data;
    }
    public boolean isTransform() {
        return transform;
    }
    public int getPageIndex() {
        return pageIndex;
    }
    public int getPageSize() {
        return pageSize;
    }
    public boolean isOrderByDesc() {
        return !orderByAsc;
    }
    public boolean isOrderByAsc() {
        return orderByAsc;
    }
    public String getSatisfy() {
        return satisfy;
    }
    public String getOrderBy() {
        return orderBy;
    }

    public Query transform(boolean transform) {
        this.transform = transform;
        return this;
    }
    public Query filterContain(String selector,String value){
        String val=selector+",cs,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterStartWith(String selector,String value) {
        String val=selector+",sw,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterEndWith(String selector,String value) {
        String val=selector+",ew,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterEqual(String selector,String value) {
        String val=selector+",eq,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterNotEqual(String selector,String value) {
        String val=selector+",ne,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterEqual(String selector,int value) {
        String val=selector+",eq,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterNotEqual(String selector,int value) {
        String val=selector+",ne,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterLowerThan(String selector,int value) {
        String val=selector+",lt,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterLowerOrEqual(String selector,int value) {
        String val=selector+",le,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterGreaterThan(String selector,int value) {
        String val=selector+",gt,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterGreaterOrEqual(String selector,int value) {
        String val=selector+",ge,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterIn(String selector,String value) {
        String val=selector+",in,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterIn(String selector,int... value) {
        String val=selector+",in,"+value.toString();
        filters.put(val,val);
        return this;
    }
    public Query filterNotIn(String selector,String value) {
        String val=selector+",ni,"+value;
        filters.put(val,val);
        return this;
    }
    public Query filterNotIn(String selector,int... value) {
        String val=selector+",ni,"+value.toString();
        filters.put(val,val);
        return this;
    }
    public Query filterIsNull(String selector) {
        String val=selector+",is";
        filters.put(val,val);
        return this;
    }
    public Query filterIsNotNull(String selector) {
        String val=selector+",no";
        filters.put(val,val);
        return this;
    }
    public Query satisfyAll() {
        satisfy="all";
        return this;
    }
    public Query satisfyAny() {
        satisfy="any";
        return this;
    }
    public Query columns(String columns) {
        columnsMap.put(columns,columns);
        return this;
    }
    public Query include(String table) {
        includeMap.put(table,table);
        return this;
    }
    public Query orderByDesc(String selector) {
        orderBy = selector;
        orderByAsc=false;
        return this;
    }
    public Query orderByAsc(String selector) {
        orderBy = selector;
        orderByAsc=true;
        return this;
    }
    public Query orderBy(String selector) {
        orderBy = selector;
        orderByAsc=true;
        return this;
    }
    public Query page(int index,int size) {
        pageIndex = index;
        pageSize=size;
        return this;
    }


    public Map<String, String> buildQueryMap() {
        Map<String, String> queryMap = new HashMap<>();
        if(filters!=null && filters.size()!=0) {
            String filterParam = "filter[]";
            if (filters.entrySet().size() == 1)
                filterParam = "filter";

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                queryMap.put(filterParam, entry.getValue());
            }

            if(filters.entrySet().size() != 1) {
                if (satisfy != null)
                    queryMap.put("satisfy", satisfy);
                else
                    queryMap.put("satisfy", "all");
            }
        }
        if(columnsMap!=null && columnsMap.size()!=0) {
            String columns = getColumns().toString();
            queryMap.put("columns",columns);
        }
        if(orderBy!=null && !orderBy.equals(""))
        {
            if(isOrderByDesc())
                queryMap.put("order",orderBy+",desc");
            else
                queryMap.put("order",orderBy+",asc");

            if(pageIndex!=-1)
                queryMap.put("page",pageIndex+","+pageSize);
        }

        if(includeMap!=null && includeMap.size()!=0) {
            String include = getInclude().toString();
            queryMap.put("include",include);
        }

        if(transform)
            queryMap.put("transform","1");


        return queryMap;
    }

    public String buildQueryString()
    {
        Set<Map.Entry<String,String>> set= buildQueryMap().entrySet();
        String[] data= new String[set.size()];
        int count=0;
        for (Map.Entry<String, String> entry : set) {
            data[count]=entry.getKey()+"="+entry.getValue();
            count++;
        }
        String queryString="";
        for (int i = 0; i < data.length; i++) {
            queryString +=data[i];
            if(i<data.length-2)
                queryString += "&";
        }
        return queryString;
    }
}
