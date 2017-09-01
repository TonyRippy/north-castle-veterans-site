package myapp;

import com.google.appengine.api.utils.SystemProperty;
import com.google.cloud.NoCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.http.HttpTransportOptions;

class Config {
  private static DatastoreOptions datastoreOptions = null;
  
  public static synchronized DatastoreOptions getDatastoreOptions() {
    if (datastoreOptions == null) {
      if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
        // Production
        datastoreOptions = DatastoreOptions.getDefaultInstance();
      } else {
        // Local development server
        datastoreOptions = DatastoreOptions.newBuilder()
          .setHost("localhost:9999")
          .setProjectId("north-castle-veterans-site")
          .setTransportOptions(HttpTransportOptions.newBuilder().build())
          .setCredentials(NoCredentials.getInstance())
          .build();
      }
    }
    return datastoreOptions;
  }
  
  public static Datastore getDatastore() {
    return getDatastoreOptions().getService();
  }
}
