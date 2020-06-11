package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static ca.jrvs.apps.twitter.util.JSONParser.toObjectFromJson;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao mockDao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet() throws Exception {
        String hashTag = "#Test";
        String text = "Hello " + hashTag + " - " + System.currentTimeMillis();
        float latitude = 12.1267F;
        float longitude = -75.1251F;

        //Test failure path
        when(mockDao.create(isNotNull())).thenThrow(new RuntimeException("Mock failure"));
        try {
            Tweet tweet = new Tweet(text, latitude, longitude);
            service.postTweet(tweet);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test working path
        Tweet testTweet = service.postTweet(new Tweet(text, latitude, longitude));
        TwitterService spyService = Mockito.spy(service);
        doReturn(testTweet).when(spyService).postTweet(any());
        Tweet actualTweet = spyService.postTweet(testTweet);

        assertNotNull(actualTweet);
        assertNotNull(actualTweet.getText());
        assertEquals(2, actualTweet.getCoordinates().getCoordinates().length);
        assertEquals(latitude, actualTweet.getCoordinates().getCoordinates()[0],0);
        assertEquals(longitude, actualTweet.getCoordinates().getCoordinates()[1], 0);
    }

    @Test
    public void showTweet() throws Exception {
        String idStr = "1270174965879504896";
        String[] fields = {"id", "text"};

        //Test failure path
        when(mockDao.findById(anyString())).thenThrow(new RuntimeException("Mock failure"));
        try {
            service.showTweet(idStr,fields);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test working path
        String tweetJsonStr = "{\n"
                +"    \"created_at\":\"Wed Jun 10 14:38:39 +0000 2020\",\n"
                +"    \"id\":1270727081501118465,\n"
                +"    \"id_str\":\"1270727081501118465\",\n"
                +"    \"text\":\"Hello #Test - 1591799917526\",\n"
                +"    \"entities\":{\n"
                +"       \"hashtags\": [{\n"
                +"       \"text\":\"Test\",\n"
                +"       \"indices\": [\n"
                +"            6, 11\n"
                +"      ]}]},\n"
                +"    \"coordinates\":{\n"
                +"       \"type\":\"Point\",\n"
                +"       \"coordinates\": [\n"
                +"            -75.1251, 12.1267\n"
                +"      ]}]},\n"
                +"    \"retweet_count\":0,\n"
                +"    \"favorite_count\":0,\n"
                +"    \"favorited\":false,\n"
                +"    \"retweeted\":false\n"
                +"}";
        Tweet expectedTweet = toObjectFromJson(tweetJsonStr,Tweet.class);
        TwitterService spyService = Mockito.spy(service);
        doReturn(expectedTweet).when(spyService).showTweet(anyString(), any());
        Tweet actualTweet = spyService.showTweet(idStr, fields);

        assertNotNull(actualTweet);
        assertNotNull(actualTweet.getText());
        assertEquals(idStr, actualTweet.getId_str());
    }

    @Test
    public void deleteTweets() throws Exception {
        List<Tweet> testTweetList = new ArrayList<>();
        String[] idList = {"1270191389129785351",
                "1270174965879504896"};

        //Test failure path
        when(mockDao.deleteById(isNotNull())).thenThrow(new RuntimeException("Mock failure"));
        try {
            service.deleteTweets(idList);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test working path
        String jsonStr1 = "{\n"
                +"    \"created_at\":\"Wed Jun 10 11:32:11 +0000 2020\",\n"
                +"    \"id\":1270191389129785351,\n"
                +"    \"id_str\":\"1270191389129785351\",\n"
                +"    \"text\":\"Hello #Test - 1591799917125\",\n"
                +"    \"entities\":{\n"
                +"       \"hashtags\": [{\n"
                +"       \"text\":\"Test\",\n"
                +"       \"indices\": [\n"
                +"            6, 11\n"
                +"      ]}]},\n"
                +"    \"coordinates\":{\n"
                +"       \"type\":\"Point\",\n"
                +"       \"coordinates\": [\n"
                +"            -75.1251, 12.1267\n"
                +"      ]}]},\n"
                +"    \"retweet_count\":0,\n"
                +"    \"favorite_count\":0,\n"
                +"    \"favorited\":false,\n"
                +"    \"retweeted\":false\n"
                +"}";
        String jsonStr2 = "{\n"
                +"    \"created_at\":\"Wed Jun 10 11:35:27 +0000 2020\",\n"
                +"    \"id\":1270174965879504896,\n"
                +"    \"id_str\":\"1270174965879504896\",\n"
                +"    \"text\":\"Hello #Test - 1591799912918\",\n"
                +"    \"entities\":{\n"
                +"       \"hashtags\": [{\n"
                +"       \"text\":\"Test\",\n"
                +"       \"indices\": [\n"
                +"            6, 11\n"
                +"      ]}]},\n"
                +"    \"coordinates\":{\n"
                +"       \"type\":\"Point\",\n"
                +"       \"coordinates\": [\n"
                +"            -75.1251, 12.1267\n"
                +"      ]}]},\n"
                +"    \"retweet_count\":0,\n"
                +"    \"favorite_count\":0,\n"
                +"    \"favorited\":false,\n"
                +"    \"retweeted\":false\n"
                +"}";
        Tweet expectedTweet1 = toObjectFromJson(jsonStr1, Tweet.class);
        Tweet expectedTweet2 = toObjectFromJson(jsonStr2, Tweet.class);
        testTweetList.add(expectedTweet1); testTweetList.add(expectedTweet2);

        TwitterService spyService = Mockito.spy(service);
        doReturn(testTweetList).when(spyService).deleteTweets(any());
        List<Tweet> resultTweetList = spyService.deleteTweets(idList);

        assertNotNull(resultTweetList);
        assertEquals(idList[0], resultTweetList.get(0).getId_str());
        assertEquals(idList[1], resultTweetList.get(1).getId_str());
    }
}