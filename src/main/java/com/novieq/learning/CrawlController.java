package com.novieq.learning;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class CrawlController {
    protected static final Logger logger = Logger.getLogger(CrawlController.class.getName());

    public static void main(String[] args)  {
        try {
        List<URI> arrayList = new ArrayList<>(50);
        URI uri = new URI("www.google.com");
        for(int i=0;i<15000;i++) {
            arrayList.add(uri);
        }
        start(Crawler.class,500,arrayList);
        } catch(URISyntaxException ex) {
            
        }
    }
    protected static <T extends Crawler> void start(final Class<T> _c, final int numberOfCrawlers, List<URI> arrayList) {
        try {
            final List<Thread> threads = new ArrayList<>();
            final List<T> crawlers = new ArrayList<>();
            int perThreadSize = arrayList.size()/numberOfCrawlers;
            List<List<URI>> perThreadData = new ArrayList<List<URI>>(numberOfCrawlers);
            for(int i=1; i<=numberOfCrawlers;i++) {
                perThreadData.add(new ArrayList<URI>(perThreadSize));
                for(int j=0;j <perThreadSize;j++){
                    perThreadData.get(i-1).add(arrayList.get(((i-1)*perThreadSize+j)));
                }
            }

            for (int i = 1; i <= numberOfCrawlers; i++) {
                T crawler = _c.newInstance();
                Thread thread = new Thread(crawler, "Crawler " + i);
                //crawler.setThread(thread);
                crawler.init(perThreadData.get(i-1));
                thread.start();
                crawlers.add(crawler);
                threads.add(thread);
                logger.info("Crawler " + i + " started.");
            }
        }catch(Exception e) {
            
        }
    }
}
