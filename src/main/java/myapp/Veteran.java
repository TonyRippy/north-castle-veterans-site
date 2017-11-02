package myapp;

import static myapp.Config.getDatastore;
import static com.google.cloud.datastore.StructuredQuery.OrderBy.asc;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;

import java.util.ArrayList;
import java.util.List;

public class Veteran extends DataObject<Veteran> {
  public static final String KIND = "Veteran";
  
  public String id;
  public String cemeteryId;
  public String firstName;
  public String middleName;
  public String lastName;
  public String biography;
  public String references;
  public String birthDate;
  public String birthLocation;
  public String deathDate;
  public String deathLocation;
  public String eyes;
  public String height;
  public String sex;
  public Number pageNumber;
  public List<String> images;

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

  @Override
  protected String getKind() {
    return KIND;
  }

  @Override
  protected Key buildKey(KeyFactory keyFactory) {
    if (id == null || id.isEmpty() || cemeteryId == null || cemeteryId.isEmpty()) {
      return null;
    }
    return keyFactory
      .addAncestor(PathElement.of("Cemetery", cemeteryId))
      .setKind(KIND)
      .newKey(id);
  }

  @Override
  protected boolean readSummaryFields(Entity e) {
    firstName = getString(e, "firstName");
    middleName = getString(e, "middleName");
    lastName = getString(e, "lastName");
    return true;
  }

  @Override
  protected boolean readAllFields(Entity e) {
    firstName = getString(e, "firstName");
    middleName = getString(e, "middleName");
    lastName = getString(e, "lastName");
    biography = getText(e, "biography");
    references = getText(e, "references");
    birthDate = getString(e, "birthDate");
    birthLocation = getString(e, "birthLocation");
    deathDate = getString(e, "deathDate");
    deathLocation = getString(e, "deathLocation");
    eyes = getString(e, "eyes");
    height = getString(e, "height");
    sex = getString(e, "sex");
    pageNumber = getNumber(e, "pageNumber");
    images = getStringList(e, "images");
    return true;
  }

  @Override
  protected boolean writeAllFields(Entity.Builder e) {
    setString(e, "firstName", firstName);
    setString(e, "middleName", middleName);
    setString(e, "lastName", lastName);
    setText(e, "biography", biography);
    setText(e, "references", references);
    setString(e, "birthDate", birthDate);
    setString(e, "birthLocation", birthLocation);
    setString(e, "deathDate", deathDate);
    setString(e, "deathLocation", deathLocation);
    setString(e, "eyes", eyes);
    setString(e, "height", height);
    setString(e, "sex", sex);
    setNumber(e, "pageNumber", pageNumber);
    setStringList(e, "images", images);
    return true;
  }

  // TODO: Figure out how to move this into DataObject
  public static List<Veteran> listAll() {
    Datastore datastore = getDatastore();
    Query<Entity> query = Query.newEntityQueryBuilder()
        .setKind(KIND)
        .setOrderBy(asc("lastName"), asc("firstName"))
        .build();
    QueryResults<Entity> results = datastore.run(query);
    List<Veteran> all = new ArrayList<>();
    while (results.hasNext()) {
      Entity e = results.next();
      String id = e.getKey().getName();
      List<PathElement> ancestors = e.getKey().getAncestors();
      if (ancestors.size() != 1) {
        throw new RuntimeException("Unexpected number of ancestors: " + e.getKey().toString());
      }
      PathElement ancestor = ancestors.get(0);
      if (!ancestor.getKind().equals(Cemetery.KIND)) {
        throw new RuntimeException("Encountered ancestor that isn't a Cemetery: " + ancestor.toString());
      }
      String cemeteryId = ancestor.getName();
      Veteran veteran = new Veteran(id, cemeteryId);
      if (veteran.readSummaryFields(e)) {
        all.add(veteran);
      }
    }
    return all;
  }

}
