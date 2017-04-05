package abak;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.log4j.Logger;
import org.bson.Document;
import pro.mojo.mongo.dao.IMongoAdapter;
import pro.mojo.mongo.dao.MongoHost;
import pro.mojo.mongo.dao.MongoManager;
import pro.mojo.mongo.dao.Page;
import pro.mojo.mongo.query.*;
import pro.mojo.type.DateFromTo;

import java.util.LinkedList;
import java.util.List;

/**
 * FindList的不同实现
 * Created by steven on 2016/9/23.
 */
public class FindList {
    private final static Logger log = Logger.getLogger(FindList.class);

    private IMongoAdapter adapter;
    private MongoHost host;
    private MongoCollection<Document> collection;
    private MongoCursor<Document> cursor;
    private FindIterable<Document> finder;

    private IQuery iquery;
    private IQuery projectionFields,sortFields;
    private IQueryFactory iqueryFactory;
    private Class<?> clazz;
    private Integer limitCount;
    private Integer skipCount;
    private Long totalCount;

    public <T> FindList(IMongoAdapter mongoAdapter, T modelQuery, IQueryFactory iqueryFactory) throws Exception {
        this.clazz = modelQuery.getClass();
        init(mongoAdapter, iqueryFactory.createQuery(modelQuery),iqueryFactory);
    }

    public <T> FindList(IMongoAdapter mongoAdapter, String jsonQuery, IQueryFactory iqueryFactory) throws Exception {
        init(mongoAdapter, iqueryFactory.createQuery(jsonQuery),iqueryFactory);
    }

    private void init(IMongoAdapter mongoAdapter, IQuery iquery, IQueryFactory iqueryFactory) throws Exception {
        this.iqueryFactory = iqueryFactory;
        this.iquery = iquery;

        this.adapter = mongoAdapter;
        this.host = new MongoHost(adapter, MongoManager.getMongoConfig(adapter.getId()));
        this.collection = host.getDatabase().getCollection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        this.finder = collection.find(iquery.toDocument());
    }

    public <T> Page<T> page(int _indexPage, int _perPageCount){
        List<T> list = new LinkedList<>();
        totalCount = collection.count(iquery.toDocument());

        if (_indexPage > 0)
            finder.skip(_indexPage * _perPageCount);
        finder.limit(_perPageCount);
        cursor = finder.iterator();
        while(cursor.hasNext())
            list.add((T)pro.cg.convert.JsonToModel(cursor.next().toJson(),clazz));

        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        if (host != null)
            host.release();

        Page<T> pg = new Page<T>(_indexPage, _perPageCount, totalCount, list);
        return pg;
    }

    public <T> List<T> get(){
        if (clazz != null)
            return as(clazz);
        return null;
    }

    public <T> List<T> as(final Class<?> clz){
        List<T> list = new LinkedList<>();
        cursor = finder.iterator();
        while(cursor.hasNext())
            list.add((T)pro.cg.convert.JsonToModel(cursor.next().toJson(),clz));
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        if (host != null){
            host.release();
        }
        return list;
    }

    public FindList fields(String jsonFields){
        this.projectionFields = iqueryFactory.createQuery(jsonFields);
        this.finder.projection(this.projectionFields.toDocument());
        return this;
    }

    public FindList sort(String jsonFields){
        this.sortFields = iqueryFactory.createQuery(jsonFields);
        this.finder.sort(this.sortFields.toDocument());
        return this;
    }

    public FindList limit(int limitCount){
        this.limitCount = limitCount;
        this.finder.limit(limitCount);
        return this;
    }

    public FindList skip(int skipCount){
        this.skipCount = skipCount;
        this.finder.skip(skipCount);
        return this;
    }

    public FindList dateFromTo(DateFromTo dateFromTo){

        return this;
    }
}
