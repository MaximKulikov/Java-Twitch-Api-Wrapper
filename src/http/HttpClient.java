package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpClient {

    private int connectionTimeout = 20000; // Connection timeout in ms
    private int dataRetrievalTimeout = 20000; // Data retrieval timeout in ms

    /**
     * Make a GET Request
     * @param requestURL Resource URL
     * @return HttpResponse
     */
    public HttpResponse get(String requestURL, Map<String, String> headers) throws IOException {

        HttpResponse response = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(requestURL);
            urlConnection = (HttpURLConnection) url.openConnection();

            // Settings
            urlConnection.setConnectTimeout(connectionTimeout);
            urlConnection.setReadTimeout(dataRetrievalTimeout);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            // Headers
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    String key = header.getKey();
                    String value = header.getValue();
                    urlConnection.addRequestProperty(key, value);
                }
            }

            urlConnection.connect();

            // Response
            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();
            response = new HttpResponse(responseCode, responseMessage);

            if (responseCode != HttpURLConnection.HTTP_NOT_FOUND) {
                String content = readStream(urlConnection.getInputStream());
                response.setContent(content);
            }

        } catch (IOException e) {
            throw e;
        }

        return response;
    }

    public HttpResponse get(String requestURL) throws IOException {
        return get(requestURL, null);
    }

    /**
     * Read the input stream and convert to a string
     * @param inputStream InputStream to read
     * @return String representing entire input stream contents
     */
    private static String readStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder text = new StringBuilder();

        try {
            String line = reader.readLine();
            while (line != null) {
                text.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Failed to read line
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text.toString();
    }

    /**
     * Get connection timeout value (in milliseconds)
     * @return Connection timeout value (in milliseconds)
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Set connection timeout value (in milliseconds)
     * @param connectionTimeout New timeout value in milliseconds
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Get Data Retrieval timeout value
     * @return Data Retrieval timeout value
     */
    public int getDataRetrievalTimeout() {
        return dataRetrievalTimeout;
    }

    /**
     * Set Data Retrieval timeout
     * @param dataRetrievalTimeout New timeout value in milliseconds
     */
    public void setDataRetrievalTimeout(int dataRetrievalTimeout) {
        this.dataRetrievalTimeout = dataRetrievalTimeout;
    }
}
