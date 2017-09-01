package myapp;

import static myapp.Config.getDatastore;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;


public class Veteran {
  final String id;
  final String cemeteryId;
  String givenName;

  public Veteran(String id, String cemeteryId) {
    this.id = id;
    this.cemeteryId = cemeteryId;
  }

  static public Veteran forPath(String path) {
    // Extract the cemeteryId and veteranId from the URL path.
    // Format is "/CEMETERY_ID/VETERAN_ID"
    if (path == null || path.length() == 0 || path.charAt(0) != '/') {
      return null;
    }
    // Find the second slash, ignoring the first.
    int index = path.indexOf('/', 1);
    if (index < 0) {
      return null;
    }
    String cemeteryId = path.substring(1, index);
    String veteranId = path.substring(index + 1);
    return new Veteran(veteranId, cemeteryId);
  }

  boolean readSummary(Entity e) {
    givenName = e.getString("givenName");
    return true;
  }
  
  boolean readFull(Entity e) {
    givenName = e.getString("givenName");
    return true;
  }
  
  public boolean readFromDatastore() {
    Datastore datastore = getDatastore();
    Key key = datastore.newKeyFactory()
      .addAncestor(PathElement.of("Cemetery", cemeteryId))
      .setKind("Veteran")
      .newKey(id);
    Query<Entity> query = Query.newEntityQueryBuilder()
      .setKind("Veteran")
      .setFilter(PropertyFilter.eq("__key__", key))
      .build();
    QueryResults<Entity> results = datastore.run(query);
    if (results.hasNext()) {
      return readFull(results.next());
    } else {
      return false;
    }
  }
}
