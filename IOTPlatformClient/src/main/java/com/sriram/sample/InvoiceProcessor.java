package com.sriram.sample;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by srirkumar on 9/7/2015.
 */
public class InvoiceProcessor {
    private static Map<String, String> headerMap = new HashMap<String, String>();

    private static final String PAYPAL_USERID = "X-PAYPAL-SECURITY-USERID";

    private static final String PAYPAL_PASSWORD = "X-PAYPAL-SECURITY-PASSWORD";

    private static final String PAYPAL_SIGNATURE = "X-PAYPAL-SECURITY-SIGNATURE";

    private static final String SANDBOX_ID = "X-PAYPAL-APPLICATION-ID";

    private static final String REQUEST_TYPE = "X-PAYPAL-REQUEST-DATA-FORMAT";

    private static final String RESPONSE_TYPE = "X-PAYPAL-RESPONSE-DATA-FORMAT";

    public static void main(String [] args) {
        InvoiceProcessor invoiceProcessor = new InvoiceProcessor();
        invoiceProcessor.createInvoice();
    }
    static {
        headerMap.put(PAYPAL_USERID, "ksriram_leo-facilitator_api1.rediffmail.com");
        headerMap.put(PAYPAL_PASSWORD, "UYNXXY4N6LMYHV62");
        headerMap.put(PAYPAL_SIGNATURE, "AIKR3jEl4..kZrGA6Nsu5aKQ2ctrATtTn2NgXrWHnZjn3Z2OUCssYkVb");
        headerMap.put(SANDBOX_ID, "APP-80W284485P519543T");
        headerMap.put(REQUEST_TYPE, "JSON");
        headerMap.put(RESPONSE_TYPE, "JSON");
    }
    public void createInvoice() {
        try {

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost("https://svcs.sandbox.paypal.com/Invoice/CreateInvoice");

            post.addHeader(PAYPAL_USERID, headerMap.get(PAYPAL_USERID));
            post.addHeader(PAYPAL_PASSWORD, headerMap.get(PAYPAL_PASSWORD));
            post.addHeader(PAYPAL_SIGNATURE, headerMap.get(PAYPAL_SIGNATURE));
            post.addHeader(SANDBOX_ID, headerMap.get(SANDBOX_ID));
            post.addHeader(REQUEST_TYPE, headerMap.get(REQUEST_TYPE));
            post.addHeader(RESPONSE_TYPE, headerMap.get(RESPONSE_TYPE));
            StringEntity input = new StringEntity(pullResponseFromFile("CreateInvoiceJSON.json"));
            post.setEntity(input);
            HttpResponse response;

            response = client.execute(post);

            BufferedReader rd = null;
            if (response != null) {
                rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            }
            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pull from the response file
     *
     * @param resourcePath resource path
     * @return response from file
     * @throws IOException IOException
     */
    private String pullResponseFromFile(String resourcePath) throws IOException {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext();
        StringBuffer response = new StringBuffer();
        Resource resource =
                appContext.getResource("classpath:" + resourcePath);

        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        return response.toString();
    }
}

