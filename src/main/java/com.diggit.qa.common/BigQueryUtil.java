package com.diggit.qa.common;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Datasets;
import com.google.api.services.bigquery.Bigquery.Jobs.Insert;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.DatasetList;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationQuery;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
/**
 * Created by yoosufm on 1/17/17.
 */
public class BigQueryUtil {

    // [START credentials]
    /////////////////////////
    // CHANGE ME!
    // USER GENERATED VALUES: you must fill in values specific to your application.
    //
    // Visit https://cloud.google.com/console to create a Project and generate an
    // OAuth 2.0 Client ID and Secret.
    // See the README for more info.
    // Then, add the Project ID below, and point the CLIENTSECRETS_LOCATION file
    // to the file you downloaded.
    /////////////////////////
    private static final String PROJECT_ID = "diggit-1266";
    private static final String CLIENTSECRETS_LOCATION = "/home/yoosufm/wp/Diggit-d05a5d7c7140.json";

    static GoogleClientSecrets clientSecrets = loadClientSecrets();

    // Static variables for API scope, callback URI, and HTTP/JSON functions
  //  private static final List<String> SCOPES = Arrays.asList(BigqueryScopes.);
  //  private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    /** Global instances of HTTP transport and JSON factory objects. */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static GoogleAuthorizationCodeFlow flow = null;

    /** Directory to store user credentials. */
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".store/bq_sample");

    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    private static FileDataStoreFactory dataStoreFactory;

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Create a new BigQuery client authorized via OAuth 2.0 protocol
        // dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        Bigquery bigquery = createAuthorizedClient();

        // Print out available datasets in the "publicdata" project to the console
        listDatasets(bigquery, "diggit-1266");

        // Start a Query Job
        String querySql = "SELECT count(*) FROM [diggit-1266:diggit_hist.Diggit_IP_US] where date > '2017-01-16' and date < '2017-01-17' LIMIT 1000;";
        JobReference jobId = startQuery(bigquery, PROJECT_ID, querySql);

        // Poll for Query Results, return result output
        Job completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);

        // Return and display the results of the Query Job
        displayQueryResults(bigquery, PROJECT_ID, completedJob);

    }

    /** Authorizes the installed application to access user's protected data. */
    private static Credential buildService() throws IOException, GeneralSecurityException {
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleCredential credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);

        // Depending on the environment that provides the default credentials (for
        // example: Compute Engine, App Engine), the credentials may require us to
        // specify the scopes we need explicitly.  Check for this case, and inject
        // the Cloud Storage scope if required.
        if (credential.createScopedRequired()) {
            Collection<String> scopes = StorageScopes.all();
            credential = credential.createScoped(scopes);
        }

       return credential;
    }
    // [END credentials]

    /**
     * Creates an authorized BigQuery client service using the OAuth 2.0 protocol
     *
     * This method first creates a BigQuery authorization URL, then prompts the
     * user to visit this URL in a web browser to authorize access. The
     * application will wait for the user to paste the resulting authorization
     * code at the command line prompt.
     *
     * @return an authorized BigQuery client
     * @throws IOException
     */
    public static Bigquery createAuthorizedClient() throws IOException {

        Credential credential = null;
        try {
            credential = buildService();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return new Bigquery(TRANSPORT, JSON_FACTORY, credential);
    }

    /**
     * Display all BigQuery datasets associated with a project
     *
     * @param bigquery  an authorized BigQuery client
     * @param projectId a string containing the current project ID
     * @throws IOException
     */
    public static void listDatasets(Bigquery bigquery, String projectId)
            throws IOException {
        Datasets.List datasetRequest = bigquery.datasets().list(projectId);
        DatasetList datasetList = datasetRequest.execute();
        if (datasetList.getDatasets() != null) {
            List<DatasetList.Datasets> datasets = datasetList.getDatasets();
            System.out.println("Available datasets\n----------------");
            System.out.println(datasets.toString());
            for (DatasetList.Datasets dataset : datasets) {
                System.out.format("%s\n", dataset.getDatasetReference().getDatasetId());
            }
        }
    }

    // [START start_query]
    /**
     * Creates a Query Job for a particular query on a dataset
     *
     * @param bigquery  an authorized BigQuery client
     * @param projectId a String containing the project ID
     * @param querySql  the actual query string
     * @return a reference to the inserted query job
     * @throws IOException
     */
    public static JobReference startQuery(Bigquery bigquery, String projectId,
                                          String querySql) throws IOException {
      //  System.out.format("\nInserting Query Job: %s\n", querySql);

        Job job = new Job();
        JobConfiguration config = new JobConfiguration();
        JobConfigurationQuery queryConfig = new JobConfigurationQuery();
        config.setQuery(queryConfig);

        job.setConfiguration(config);
        queryConfig.setQuery(querySql);

        Insert insert = bigquery.jobs().insert(projectId, job);
        insert.setProjectId(projectId);
        JobReference jobId = insert.execute().getJobReference();

        //System.out.format("\nJob ID of Query Job is: %s\n", jobId.getJobId());

        return jobId;
    }

    /**
     * Polls the status of a BigQuery job, returns Job reference if "Done"
     *
     * @param bigquery  an authorized BigQuery client
     * @param projectId a string containing the current project ID
     * @param jobId     a reference to an inserted query Job
     * @return a reference to the completed Job
     * @throws IOException
     * @throws InterruptedException
     */
    public static Job checkQueryResults(Bigquery bigquery, String projectId, JobReference jobId)
            throws IOException, InterruptedException {
        // Variables to keep track of total query time
        long startTime = System.currentTimeMillis();
        long elapsedTime;

        while (true) {
            Job pollJob = bigquery.jobs().get(projectId, jobId.getJobId()).execute();
            elapsedTime = System.currentTimeMillis() - startTime;
//            System.out.format("Job status (%dms) %s: %s\n", elapsedTime,
//                    jobId.getJobId(), pollJob.getStatus().getState());
            if (pollJob.getStatus().getState().equals("DONE")) {
                return pollJob;
            }
            // Pause execution for one second before polling job status again, to
            // reduce unnecessary calls to the BigQUery API and lower overall
            // application bandwidth.
            Thread.sleep(1000);
        }
    }
    // [END start_query]

    // [START display_result]
    /**
     * Makes an API call to the BigQuery API
     *
     * @param bigquery     an authorized BigQuery client
     * @param projectId    a string containing the current project ID
     * @param completedJob to the completed Job
     * @throws IOException
     */
    public static List<TableRow> displayQueryResults(Bigquery bigquery,
                                           String projectId, Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(
                        projectId, completedJob
                                .getJobReference()
                                .getJobId()
                ).execute();
        List<TableRow> rows = queryResult.getRows();
       // System.out.print("\nQuery Results:\n------------\n");

        return rows;
    }
    // [END display_result]

    /**
     * Helper to load client ID/Secret from file.
     *
     * @return a GoogleClientSecrets object based on a clientsecrets.json
     */
    private static GoogleClientSecrets loadClientSecrets() {
        try {
            InputStream inputStream = new FileInputStream(CLIENTSECRETS_LOCATION);
            Reader reader =
                    new InputStreamReader(inputStream);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(),
                    reader);
            return clientSecrets;
        } catch (Exception e) {
            System.out.println("Could not load client secrets file " + CLIENTSECRETS_LOCATION);
            e.printStackTrace();
        }
        return null;
    }
}
