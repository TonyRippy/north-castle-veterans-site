package myapp;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.PathElement;


public class Veteran extends DataObject<Veteran> {
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
    return "Veteran";
  }

  @Override
  protected Key buildKey(KeyFactory keyFactory) {
    if (id == null || id.isEmpty() || cemeteryId == null || cemeteryId.isEmpty()) {
      return null;
    }
    return keyFactory
      .addAncestor(PathElement.of("Cemetery", cemeteryId))
      .setKind("Veteran")
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
    biography = getString(e, "biography");
    references = getString(e, "references");
    birthDate = getString(e, "birthDate");
    birthLocation = getString(e, "birthLocation");
    deathDate = getString(e, "deathDate");
    deathLocation = getString(e, "deathLocation");
    eyes = getString(e, "eyes");
    height = getString(e, "height");
    sex = getString(e, "sex");
    pageNumber = getNumber(e, "pageNumber");
    return true;
  }

  @Override
  protected boolean writeAllFields(Entity.Builder e) {
    setString(e, "firstName", firstName);
    setString(e, "middleName", middleName);
    setString(e, "lastName", lastName);
    setString(e, "biography", biography);
    setString(e, "references", references);
    setString(e, "birthDate", birthDate);
    setString(e, "birthLocation", birthLocation);
    setString(e, "deathDate", deathDate);
    setString(e, "deathLocation", deathLocation);
    setString(e, "eyes", eyes);
    setString(e, "height", height);
    setString(e, "sex", sex);
    setNumber(e, "pageNumber", pageNumber);
    return true;
  }

}
