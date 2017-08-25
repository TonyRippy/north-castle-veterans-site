# North Castle Veterans Site

A web site dedicated to honoring North Castle's deceased veterans.

## Before you begin

1.  Download and install the [Google Cloud
    SDK](https://cloud.google.com/sdk/docs/).
1.  [Install and configure Apache Maven](http://maven.apache.org/index.html).
1.  [Create a new Google Cloud Platform project, or use an existing
		one](https://console.cloud.google.com/project).
1.  Initialize the Cloud SDK.

        gcloud init

1.  Install the Cloud SDK `app-engine-java` component.

        gcloud components install app-engine-java

## Deploying to App Engine

To run the application locally, use the [Maven App Engine
plugin](https://cloud.google.com/appengine/docs/java/tools/using-maven).

    mvn clean appengine:run

View the app at [localhost:8080](http://localhost:8080).

To deploy the app to App Engine, run

    mvn clean appengine:deploy

After the deploy finishes, you can view the application at
https://north-castle-veterans-site.appspot.com.  You can see the new
version deployed on the [App Engine section of the Google Cloud
Console](https://console.cloud.google.com/appengine/versions).

## Next steps

1.  Read the [App Engine
    documentation](https://cloud.google.com/appengine/docs/java/).
1.  Explore the other [Google Cloud Platform sample
    applications](https://github.com/GoogleCloudPlatform/java-docs-sample) for
    Java™.

## Contributing changes

* See [CONTRIBUTING.md](CONTRIBUTING.md)

## Licensing

* See [LICENSE](LICENSE)

Java is a registered trademark of Oracle Corporation and/or its affiliates.
