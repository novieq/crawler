package com.novieq.learning;

import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

public class Crawler implements Runnable {

    protected static final Logger logger = Logger.getLogger(Crawler.class.getName());

    protected List<URI> assignedURLs;

    public void init(List<URI> arrayList) {
        this.assignedURLs = arrayList;
    }

    @Override
    public void run() {
        for (URI currentURL : assignedURLs) {
            if (currentURL != null) {
                processPage(currentURL);
            }

        }
    }

    private void processPage(URI url) {
        HttpParams params = new BasicHttpParams();
        params.setBooleanParameter("http.protocol.handle-redirects", false);

        HttpGet httpGet = new HttpGet("http://tracking.lengow.com/shortUrl/2883-47759-5841445897777/?cm_mmc=shoppingcom-MEN-Underwear_Onesies-5841445897777");
    //    HttpGet httpGet = new HttpGet("http://click.linksynergy.com/link?id=EX2wQWS0Dkk&offerid=275536.432200&type=15&murl=http%3A%2F%2Fwww.net-a-porter.com%2Fintl%2Fproduct%2F432200%3Fcm_mmc%3DProductFeed-_-Dolce_and_Gabbana");
    //    HttpGet httpGet = new HttpGet("http://www.joowel.com");
        HttpClient httpClient = new DefaultHttpClient(params);
        httpGet.addHeader("Accept-Encoding", "gzip");
        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    Header header = response.getFirstHeader("Location");
                    String movedToUrl = header.getValue();
                    System.out.println(movedToUrl);
             //       logger.info(movedToUrl);
                }
            }
            //System.out.println(statusCode);
            Thread.sleep(10000);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

}