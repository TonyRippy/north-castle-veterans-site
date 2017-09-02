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


public class Veteran {
  public final String id;
  public final String cemeteryId;
  public String firstName;
  public String middleName;
  public String lastName;
  public String biography;
  public String references;
  public String born;
  public String died;
  public String eyes;
  public String height;
  public String sex;
  
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

  private Key buildKey(KeyFactory keyFactory) {
    return keyFactory
      .addAncestor(PathElement.of("Cemetery", cemeteryId))
      .setKind("Veteran")
      .newKey(id);
  }

  private static String getString(Entity e, String propertyName) {
    if (e.contains(propertyName)) {
      return e.getString(propertyName);
    } else {
      return null;
    }
  }
  
  private static void setString(Entity.Builder e, String propertyName, String value) {
    if (value != null) {
      e.set(propertyName, value);
    }
  }
  
  boolean readSummary(Entity e) {
    firstName = getString(e, "firstName");
    middleName = getString(e, "middleName");
    lastName = getString(e, "lastName");
    return true;
  }

  boolean readFull(Entity e) {
    firstName = getString(e, "firstName");
    middleName = getString(e, "middleName");
    lastName = getString(e, "lastName");
    biography = getString(e, "biography");
    references = getString(e, "references");
    born = getString(e, "born");
    died = getString(e, "died");
    eyes = getString(e, "eyes");
    height = getString(e, "height");
    sex = getString(e, "sex");
    return true;
  }
  
  private Entity toEntity(KeyFactory keyFactory) {
    Entity.Builder e = Entity.newBuilder(buildKey(keyFactory));
    setString(e, "firstName", firstName);
    setString(e, "middleName", middleName);
    setString(e, "lastName", lastName);
    setString(e, "biography", biography);
    setString(e, "references", references);
    setString(e, "born", born);
    setString(e, "died", died);
    setString(e, "eyes", eyes);
    setString(e, "height", height);
    setString(e, "sex", sex);
    return e.build();
  }

  public boolean readFromDatastore() {
    Datastore datastore = getDatastore();
    Key key = buildKey(datastore.newKeyFactory());
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
}
