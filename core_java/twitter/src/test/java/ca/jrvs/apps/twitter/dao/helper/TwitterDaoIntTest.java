package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.practice.JSONParser;
import com.google.gdata.util.common.base.PercentEscaper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static ca.jrvs.apps.twitter.practice.JSONParser.toJson;
import static ca.jrvs.apps.twitter.practice.JSONParser.toObjectFromJson;

public class TwitterDaoIntTest {
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
        float longitude = -75.1251F;
        float latitude = 12.1267F;
        Tweet postTweet = new Tweet(text,longitude,latitude);
        System.out.println(toJson(postTweet,true,true));
        Tweet tweet = dao.create(postTweet);
/*
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(longitude, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(latitude, tweet.getCoordinates().getCoordinates().get(1));
*/  }
/*
    @Test
    public void findById() throws Exception {
        Tweet tweet = dao.findById("1270034859562602496");
        System.out.println(toJson(tweet,true,true));
    }

    @Test
    public void deleteById() throws Exception {
        Tweet tweet = dao.deleteById("1270035258222747651");
        System.out.println(toJson(tweet,true,true));
    }
*/
}
