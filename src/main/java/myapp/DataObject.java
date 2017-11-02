package myapp;

import static myapp.Config.getDatastore;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * A Base class for all objects that are read from and written to Google Datastore.
 */
abstract class DataObject<T> {

  /** Returns the Datastore "Kind" used to persist this object. */ 
  abstract protected String getKind();
  
  /** Reads a string value from a Datastore entity, treating missing fields as null. */ 
  public static String getString(Entity e, String propertyName) {
    if (e.contains(propertyName)) {
      return e.getString(propertyName);
    } else {
      return null;
    }
  }

  /** Saves a string value to a Datastore Entity, omitting nulls or empty strings. */  
  public static void setString(Entity.Builder e, String propertyName, String value) {
    setString(e, propertyName, value, true);
  }

  public static void setString(Entity.Builder e, String propertyName, String value, boolean index) {
    if (value != null && !value.isEmpty()) {
      StringValue v = StringValue.newBuilder(value)
        .setExcludeFromIndexes(!index)
        .build();
      e.set(propertyName, v);
    }
  }

  /** Reads a text field from a Datastore entity, treating missing fields as null. */ 
  public static String getText(Entity e, String propertyName) {
    return getString(e, propertyName);
  }

  /** Saves a text field to a Datastore entity, omitting nulls or empty strings. */
  public static void setText(Entity.Builder e, String propertyName, String value) {
    setString(e, propertyName, value, false);
  }
  
  /** Reads a numeric value from a Datastore entity, treating missing fields as null. */ 
  public static Number getNumber(Entity e, String propertyName) {
    if (e.contains(propertyName)) {
      return new Long(e.getLong(propertyName));
    } else {
      return null;
    }
  }
  
  /** Saves a numeric value to a Datastore Entity, omitting nulls. */  
  public static void setNumber(Entity.Builder e, String propertyName, Number value) {
    if (value != null) {
      e.set(propertyName, value.longValue());
    }
  }  

  /** Reads a list of strings from a Datastore entity, treating missing fields as null. */ 
  public static List<String> getStringList(Entity e, String propertyName) {
    if (!e.contains(propertyName)) {
      return null;
    }
    List<StringValue> values = e.getList(propertyName);
    ArrayList<String> out = new ArrayList<>(values.size());
    for (StringValue value : values) {
      String s = value.get();
      if (s != null && !s.isEmpty()) {
        out.add(s);
      }
    }
    if (out.isEmpty()) {
      return null;
    }
    return out;
  }

  /** Saves a list of strings to a Datastore Entity, omitting nulls or empty lists. */  
  public static void setStringList(Entity.Builder e, String propertyName, List<String> list) {
    if (list == null || list.isEmpty()) {
      return;
    }
    ListValue.Builder builder = ListValue.newBuilder();
    for (String s : list) {
      if (s != null && !s.isEmpty()) {
        builder.addValue(s);
      }
    }
    ListValue value = builder.build();
    if (!value.get().isEmpty()) {
      e.set(propertyName, value);
    }
  }
  
  /**
   * Method to create a Datastore Key from the current values.
   * 
   * @returns a Key for this object if there is enough information 
   *     to create one, null otherwise.
   */
  abstract protected Key buildKey(KeyFactory keyFactory);

  /**
   * Reads only those fields needed for a list of objects.
   *
   * @param entity a Datastore Entity that contains information about this object. 
   * @returns true if all the required fields were read, false otherwise.
   */
  abstract protected boolean readSummaryFields(Entity entity);

  /**
   * Reads all fields for this object.
   *
   * @param entity a Datastore Entity that contains information about this object. 
   * @returns true if all the required fields were read, false otherwise.
   */
  abstract protected boolean readAllFields(Entity entity);

  /**
   * Writes all fields for this object.
   *
   * @param entity the Datastore Entity that will be used to store the object.
   * @returns true if all the required fields were written, false otherwise.
   */
  abstract protected boolean writeAllFields(Entity.Builder entity);

  /**
   * Reads the data object's fields from Datastore.
   * 
   * Assumes that the data object has enough information to build a Key. 
   *
   * @retutns true if the object was read fully, false otherwise.
   */
  public boolean readFromDatastore() {
    return readFromDatastore(getDatastore());
  }
  
  protected boolean readFromDatastore(Datastore datastore) {
    Key key = buildKey(datastore.newKeyFactory());
    if (key == null) {
      return false;
    }
    Query<Entity> query = Query.newEntityQueryBuilder()
      .setKind(getKind())
      .setFilter(PropertyFilter.eq("__key__", key))
      .build();
    QueryResults<Entity> results = datastore.run(query);
    if (results.hasNext()) {
      return readAllFields(results.next());
    } else {
      return false;
    }
  }

  /**
   * Writes the data object's fields to Datastore.
   * 
   * @returns true if the object was written, false otherwise.
   */
  public boolean writeToDatastore() {
    return writeToDatastore(getDatastore());
  }

  protected boolean writeToDatastore(Datastore datastore) {
    Key key = buildKey(datastore.newKeyFactory());
    if (key == null) {
      return false;
    }
    Entity.Builder e = Entity.newBuilder(key);
    if (!writeAllFields(e)) {
      return false;
    }
    try {
      datastore.put(e.build());
      return true;
    } catch (DatastoreException ex) {
      // TODO: Log the error?
      return false;
    }
  }

}
