package abak;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.log4j.Logger;
import org.bson.Document;
import pro.mojo.mongo.dao.IMongoAdapter;
import pro.mojo.mongo.dao.MongoHost;
import pro.mojo.mongo.dao.MongoManager;
import pro.mojo.mongo.query.IQuery;
import pro.mojo.mongo.query.IQueryFactory;
import pro.mojo.mongo.query.QueryUtil;

/**
 * FindOne的不同实现
 * Created by steven on 2016/12/15.
 */
public class FindOne {
    private final static Logger log = Logger.getLogger(FindOne.class);

    private IMongoAdapter adapter;
    private MongoHost host;
    private MongoCollection<Document> collection;
    private MongoCursor<Document> cursor;
    private FindIterable<Document> finder;

    private IQuery iquery;
    private IQuery projectionFields,sortFields;
    private IQueryFactory iqueryFactory;
    private Class<?> clazz;

    public <T> FindOne(IMongoAdapter mongoAdapter, T modelQuery, IQueryFactory iqueryFactory) throws Exception {
        init(mongoAdapter, iqueryFactory.createQuery(modelQuery),iqueryFactory);
        clazz = modelQuery.getClass();
    }

    public <T> FindOne(IMongoAdapter mongoAdapter, String jsonQuery, IQueryFactory iqueryFactory) throws Exception {
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

    public <T> T as(final Class<?> clz){
        clazz = clz;
        this.collection = host.getDatabase().getCollection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));

        Document document = null;
        finder = collection.find(iquery.toDocument());
        document = finder.first();
        if (host != null)
            host.release();
        if (document == null)
            return null;
        return (T)pro.tools.convert.JsonToModel(document.toJson(),clz);
    }

    public FindOne fields(String jsonFields){
        this.projectionFields = iqueryFactory.createQuery(jsonFields);
        this.finder.projection(this.projectionFields.toDocument());
        return this;
    }

    public FindOne sort(String jsonFields){
        this.sortFields = iqueryFactory.createQuery(jsonFields);
        this.finder.sort(this.sortFields.toDocument());
        return this;
    }
}
