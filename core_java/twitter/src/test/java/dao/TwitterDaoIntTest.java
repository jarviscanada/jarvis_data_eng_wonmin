package dao;


import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ca.jrvs.apps.twitter.util.JSONParser.toJson;
import static org.junit.Assert.*;

public class TwitterDaoIntTest {
    final Logger logger = LoggerFactory.getLogger(TwitterDaoIntTest.class);
    private TwitterDao dao;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws Exception {
        String hashTag = "#Test";
        String text = "Hello " + hashTag + " - " + System.currentTimeMillis();
        float latitude = 12.1267F;
        float longitude = -75.1251F;
        Tweet postTweet = new Tweet(text,latitude,longitude);
        logger.info(toJson(postTweet,true,true));
        Tweet tweet = dao.create(postTweet);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
        assertEquals(latitude, tweet.getCoordinates().getCoordinates()[0],0);
        assertEquals(longitude, tweet.getCoordinates().getCoordinates()[1],0);
    }

    @Test
    public void findById() throws Exception {
        Tweet tweet = dao.findById("1270727081501118465");
        logger.info(toJson(tweet,true,true));

        assertNotNull(tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    }

    @Test
    public void deleteById() throws Exception {
        Tweet tweet = dao.deleteById("1270731418537209862");
        logger.info(toJson(tweet,true,true));

        assertNotNull(tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    }
}