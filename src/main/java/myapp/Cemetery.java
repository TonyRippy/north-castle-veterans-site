package myapp;

import static myapp.Config.getDatastore;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

public class Cemetery {
  final String id;
  String name;
  List<Veteran> veterans;

  public Cemetery(String id) {
    this.id = id;
  }

  static public Cemetery forPath(String path) {
    // Extract the cemeteryId from the URL path.
    // Format is "/CEMETERY_ID"
    if (path == null || path.length() < 2 || path.charAt(0) != '/') {
      return null;
    }
    // Remove the leading slash from the path.
    String cemeteryId = path.substring(1); 
    return new Cemetery(cemeteryId);
  }

  private boolean readSummary(Entity e) {
    name = e.getString("cemeteryName");
    return true;
  }
  
  private boolean readFull(Entity e) {
    name = e.getString("cemeteryName");
    return true;
  }

  private Entity toEntity(KeyFactory keyFactory) {
    Key key = keyFactory.setKind("Cemetery").newKey(id);
    return Entity.newBuilder(key)
      .set("cemeteryName", name)
      .build();
  }

  public boolean readFromDatastore() {
    Datastore datastore = getDatastore();
    Key key = datastore.newKeyFactory()
      .setKind("Cemetery")
      .newKey(id);
    Query<Entity> query = Query.newEntityQueryBuilder()
      .setKind("Cemetery")
      .setFilter(PropertyFilter.eq("__key__", key))
      .build();
    QueryResults<Entity> results = datastore.run(query);
    if (!(results.hasNext() && readFull(results.next()))) {
      return false;
    }
    // Also load the list of veterans that are buried in this cemetery.
    query = Query.newEntityQueryBuilder()
      .setKind("Veteran")
      .setFilter(PropertyFilter.hasAncestor(
           datastore.newKeyFactory().setKind("Cemetery").newKey(id)))
      .build();
    results = datastore.run(query);
    veterans = new ArrayList<>();
    while (results.hasNext()) {
      Entity e = results.next();
      Veteran veteran = new Veteran(e.getKey().getName(), id);
      if (veteran.readSummary(e)) {
        veterans.add(veteran);
      }
    }
    return true;
  }

  public boolean writeToDatastore() {
    Datastore datastore = getDatastore();
    Entity entity = toEntity(datastore.newKeyFactory());
    try {
      datastore.put(entity);
      return true;
    } catch (DatastoreException ex) {
      // TODO: Log the error?
      return false;
    }
  }
  
  public static List<Cemetery> listAll() {
    Datastore datastore = getDatastore();
    Query<Entity> query = Query.newEntityQueryBuilder()
        .setKind("Cemetery")
        .build();
    QueryResults<Entity> results = datastore.run(query);
    List<Cemetery> all = new ArrayList<>();
    while (results.hasNext()) {
      Entity e = results.next();
      Cemetery cemetery = new Cemetery(e.getKey().getName());
      if (cemetery.readSummary(e)) {
        all.add(cemetery);
      }
    }
    return all;
  }
}
