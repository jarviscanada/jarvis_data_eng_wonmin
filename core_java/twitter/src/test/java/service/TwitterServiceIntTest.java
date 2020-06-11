package service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Before;
import org.junit.Test;
import javax.validation.constraints.PastOrPresent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        float latitude = 12.1267F;
        float longitude = -90.1251F;
        Tweet postTweet = new Tweet(text, latitude, longitude);
        Tweet tweet = service.postTweet(postTweet);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(latitude, tweet.getCoordinates().getCoordinates()[0],0);
        assertEquals(longitude, tweet.getCoordinates().getCoordinates()[1],0);
    }

    @Test
    public void showTweet() throws Exception {
        String idStr = "1270034859562602496";
        Tweet shownTweet = service.showTweet(idStr, new String[]{"id", "text"});


        assertNotNull(shownTweet.getText());
        assertNotNull(shownTweet.getCoordinates());
        assertEquals(idStr, shownTweet.getId_str());
        assertEquals(2, shownTweet.getCoordinates().getCoordinates().length);
    }

    @Test
    public void deleteTweets() throws Exception {
        List deletedTweets = new ArrayList();
        String[] idList = {"1270191389129785351",
                "1270174965879504896"};
        deletedTweets = service.deleteTweets(idList);
        deletedTweets.forEach(System.out::println);

        assertNotNull(deletedTweets);
        assertEquals(idList.length,deletedTweets.size(),0);
    }
}
