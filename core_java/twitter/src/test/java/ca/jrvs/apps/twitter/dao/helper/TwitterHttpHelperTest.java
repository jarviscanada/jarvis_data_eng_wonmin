package ca.jrvs.apps.twitter.dao.helper;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import java.net.URI;

import static org.junit.Assert.*;

public class TwitterHttpHelperTest {

    @Test
    public void httpPost() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        System.out.println(consumerKey + "\n" + consumerSecret + "\n" + accessToken + "\n" + tokenSecret);

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        URI postUri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=Hello,World");
        HttpResponse httpResponse = httpHelper.httpPost(postUri);
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));

    }

    @Test
    public void httpGet() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        System.out.println(consumerKey + "\n" + consumerSecret + "\n" + accessToken + "\n" + tokenSecret);

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        URI getUri = new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Wonmin36844607");
    }
}