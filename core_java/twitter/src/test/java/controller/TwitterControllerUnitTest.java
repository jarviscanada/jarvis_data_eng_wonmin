package controller;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

import static ca.jrvs.apps.twitter.util.JSONParser.toObjectFromJson;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    Service mockService;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() {
        String hashTag = "#Test";
        String text = "Hello " + hashTag + " - " + System.currentTimeMillis();
        float latitude = 12.1267F;
        float longitude = -75.1251F;
        String coords = Float.toString(latitude) + ":" + Float.toString(longitude);
        String[] args = {"post", text, coords};

        //Test failure path
        when(mockService.postTweet(isNotNull())).thenThrow(new RuntimeException("Mock failure"));
        try {
            controller.postTweet(args);
            fail();
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        //Test working path
        TwitterController spyController = Mockito.spy(controller);
        Tweet testTweet = new Tweet(text, latitude, longitude);
        doReturn(testTweet).when(spyController).postTweet(any());
        Tweet actualTweet = spyController.postTweet(args);

        assertNotNull(actualTweet);
        assertNotNull(actualTweet.getText());
        assertEquals(2, actualTweet.getCoordinates().getCoordinates().length);
        assertEquals(latitude, actualTweet.getCoordinates().getCoordinates()[0],0);
        assertEquals(longitude, actualTweet.getCoordinates().getCoordinates()[1], 0);
    }

    @Test
    public void showTweet() throws Exception {
        String idStr = "1270174965879504896";
        String fields = "id,text";
        String[] args = {"show", idStr, fields};

        //Test failure path
        when(mockService.showTweet(anyString(),any())).thenThrow(new RuntimeException("Mock failure"));
        try {
            controller.showTweet(args);
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
        TwitterController spyController = Mockito.spy(controller);
        doReturn(expectedTweet).when(spyController).showTweet(any());
        Tweet actualTweet = spyController.showTweet(args);

        assertNotNull(actualTweet);
        assertNotNull(actualTweet.getText());
        assertEquals(idStr, actualTweet.getId_str());
    }

    @Test
    public void deleteTweets() throws Exception {
        List<Tweet> testTweetList = new ArrayList<>();
        String idList = "1270191389129785351,1270174965879504896";
        String[] args = {"delete", idList};

        //Test failure path
        when(mockService.deleteTweets(isNotNull())).thenThrow(new RuntimeException("Mock failure"));
        try {
            controller.deleteTweet(args);
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

        TwitterController spyController = Mockito.spy(controller);
        doReturn(testTweetList).when(spyController).deleteTweet(any());
        List<Tweet> resultTweetList = spyController.deleteTweet(args);

        assertNotNull(resultTweetList);
        assertEquals("1270191389129785351", resultTweetList.get(0).getId_str());
        assertEquals("1270174965879504896", resultTweetList.get(1).getId_str());
    }
}
