package myapp;

import com.google.appengine.api.utils.SystemProperty;
import com.google.cloud.NoCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.http.HttpTransportOptions;

final class Config {
  private Config() {}
 
  private static DatastoreOptions datastoreOptions = null;

  public static boolean isTest() {
    return SystemProperty.environment.value() != SystemProperty.Environment.Value.Production;
  }

  public static synchronized DatastoreOptions getDatastoreOptions() {
    if (datastoreOptions == null) {
      if (isTest()) {
        // Use local development server.
        // You can start one by using the following command:
        // gcloud beta emulators datastore start --project=north-castle-veterans-site --host-port=localhost:9999
        datastoreOptions = DatastoreOptions.newBuilder()
          .setHost("localhost:9999")
          .setProjectId("north-castle-veterans-site")
          .setTransportOptions(HttpTransportOptions.newBuilder().build())
          .setCredentials(NoCredentials.getInstance())
          .build();
      } else {
        // Production
        datastoreOptions = DatastoreOptions.getDefaultInstance();
      }
    }
    return datastoreOptions;
  }
  
  public static Datastore getDatastore() {
    return getDatastoreOptions().getService();
  }
}
