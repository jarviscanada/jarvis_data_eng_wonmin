package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.PastOrPresent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TwitterServiceIntTest {
    private TwitterDao dao;
    private TwitterService service;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);
        this.service = new TwitterService(dao);
    }

    @Test
    public void postTweet() throws Exception {
        String hashTag = "#Test";
        String text = "Hello " + hashTag + " - " + System.currentTimeMillis();
        float longitude = -100.1251F;
        float latitude = 12.1267F;
        Tweet postTweet = new Tweet(text, longitude, latitude);
        Tweet tweet = service.postTweet(postTweet);
    }

    @Test
    public void showTweet() throws Exception {
        Tweet shownTweet = service.showTweet("1270034859562602496", new String[]{"text", "id"});
    }

    @Test
    public void deleteTweets() throws Exception {
        List deletedTweets = new ArrayList();
        deletedTweets = service.deleteTweets(new String[] {
                "1270191389129785351",
                "1270174965879504896" });
        deletedTweets.forEach(System.out::println);
        }
}

