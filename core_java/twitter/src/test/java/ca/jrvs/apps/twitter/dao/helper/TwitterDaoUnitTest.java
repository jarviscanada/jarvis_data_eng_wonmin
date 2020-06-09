package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.test.context.TestPropertySource;

import static ca.jrvs.apps.twitter.practice.JSONParser.toJson;
import static ca.jrvs.apps.twitter.practice.JSONParser.toObjectFromJson;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockhelper;

    @InjectMocks
    TwitterDao dao;

    @Test
    public void create() throws Exception {
        String hashTag = "#Test";
        String text = "Hello " + hashTag + " - " + System.currentTimeMillis();
        double longitude = -75.1251;
        double latitude = 12.1267;

        //Test failure path
        when(mockhelper.httpPost(isNotNull())).thenThrow(new RuntimeException("Mock"));
        try {
            Tweet tweet = new Tweet(text, longitude, latitude);
            dao.create(tweet);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test working path - Create a spy dao
        String tweetJsonStr = "{\n"
                +"    \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                +"    \"id\":1270155265481064289,\n"
                +"    \"id_str\":\"1270155265481064289\",\n"
                +"    \"text\":\"Hello #Test - 1591663586895\",\n"
                +"    \"entities\":null,\n"
                +"    \"coordinates\":null,\n"
                +"    \"retweet_count\":0,\n"
                +"    \"favorite_count\":0,\n"
                +"    \"favorited\":false,\n"
                +"    \"retweeted\":false\n"
                +"}";
        when(mockhelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(spyDao).create(any());
        Tweet tweet = new Tweet(text, longitude, latitude);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void findById() throws Exception {
        //Test fail Path
        when(mockhelper.httpPost(isNotNull())).thenThrow(new RuntimeException("Mock"));
        try {
            dao.findById("1270155265481064454");
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test working path
        when(mockhelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        String tweetJsonStr = "{\n"
                +"    \"created_at\":\"Tue Jun 09 00:46:28 +0000 2020\",\n"
                +"    \"id\":1270155265481064454,\n"
                +"    \"id_str\":\"1270155265481064454\",\n"
                +"    \"text\":\"Hello #Test - 1591663586895\",\n"
                +"    \"entities\":{\n"
                +"       \"hashtags\": [{\n"
                +"       \"text\":\"Test\",\n"
                +"       \"indices\": [\n"
                +"            6, 11\n"
                +"      ]}]},\n"
                +"    \"coordinates\":null,\n"
                +"    \"retweet_count\":0,\n"
                +"    \"favorite_count\":0,\n"
                +"    \"favorited\":false,\n"
                +"    \"retweeted\":false\n"
                +"}";
        Tweet expectedTweet = toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(spyDao).findById(anyString());
        assertNotNull(expectedTweet);
        assertNotNull(expectedTweet.getText());
    }

    @Test
    public void deleteById() throws Exception {
        //Test fail Path
        when(mockhelper.httpPost(isNotNull())).thenThrow(new RuntimeException("Mock"));
        try {
            dao.deleteById("1270155265481064454");
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test working path
        when(mockhelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        String tweetJsonStr = "{\n"
                +"    \"created_at\":\"Tue Jun 09 00:46:28 +0000 2020\",\n"
                +"    \"id\":1270155265481064454,\n"
                +"    \"id_str\":\"1270155265481064454\",\n"
                +"    \"text\":\"Hello #Test - 1591663586895\",\n"
                +"    \"entities\":{\n"
                +"       \"hashtags\": [{\n"
                +"       \"text\":\"Test\",\n"
                +"       \"indices\": [\n"
                +"            6, 11\n"
                +"      ]}]},\n"
                +"    \"coordinates\":null,\n"
                +"    \"retweet_count\":0,\n"
                +"    \"favorite_count\":0,\n"
                +"    \"favorited\":false,\n"
                +"    \"retweeted\":false\n"
                +"}";
        Tweet expectedTweet = toObjectFromJson(tweetJsonStr, Tweet.class);
        doReturn(expectedTweet).when(spyDao).deleteById(anyString());
        assertNotNull(expectedTweet);
        assertNotNull(expectedTweet.getText());

    }

}
