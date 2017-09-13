package myapp;

import static myapp.Config.getDatastore;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

public class Cemetery extends DataObject<Cemetery> {
  public final String id;
  public String name;
  public List<Veteran> veterans;

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

  @Override
  protected String getKind() {
    return "Cemetery";
  }

  @Override
  protected Key buildKey(KeyFactory keyFactory) {
    if (id == null || id.isEmpty()) {
      return null;
    }
    return keyFactory
      .setKind("Cemetery")
      .newKey(id);
  }

  @Override
  protected boolean readSummaryFields(Entity e) {
    name = getString(e, "cemeteryName");
    return true;
  }

  @Override
  protected boolean readAllFields(Entity e) {
    name = getString(e, "cemeteryName");
    return true;
  }

  @Override
  protected boolean writeAllFields(Entity.Builder e) {
    setString(e, "cemeteryName", name);
    return true;
  }

  @Override
  protected boolean readFromDatastore(Datastore datastore) {
    // Load this record's fields.
    if (!super.readFromDatastore(datastore)) {
      return false;
    }
    // Also load the list of veterans that are buried in this cemetery.
    // TODO: This could be factored out into a Veterans.readAllForParent().
    Query<Entity> query = Query.newEntityQueryBuilder()
      .setKind("Veteran")
      .setFilter(PropertyFilter.hasAncestor(
          buildKey(datastore.newKeyFactory())))
      .build();
    QueryResults<Entity> results = datastore.run(query);
    veterans = new ArrayList<>();
    while (results.hasNext()) {
      Entity e = results.next();
      Veteran veteran = new Veteran(e.getKey().getName(), id);
      if (veteran.readSummaryFields(e)) {
        veterans.add(veteran);
      }
    }
    return true;
  }

  // TODO: Figure out how to move this into DataObject
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
      if (cemetery.readSummaryFields(e)) {
        all.add(cemetery);
      }
    }
    return all;
  }

}
