package ca.jrvs.apps.twitter.dao.helper;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class TwitterHttpHelperTest {
    final Logger logger = LoggerFactory.getLogger(TwitterHttpHelper.class);

    @Test
    public void httpPost() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        logger.info(consumerKey + "\n" + consumerSecret + "\n" + accessToken + "\n" + tokenSecret);

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        URI postUri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=Hello,World");
        HttpResponse httpResponse = httpHelper.httpPost(postUri);

        logger.info(EntityUtils.toString(httpResponse.getEntity()));
    }

    @Test
    public void httpGet() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        logger.info(consumerKey + "\n" + consumerSecret + "\n" + accessToken + "\n" + tokenSecret);

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        URI getUri = new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Wonmin36844607");
        HttpResponse httpResponse = httpHelper.httpGet(getUri);

        logger.info(EntityUtils.toString(httpResponse.getEntity()));
    }
}
