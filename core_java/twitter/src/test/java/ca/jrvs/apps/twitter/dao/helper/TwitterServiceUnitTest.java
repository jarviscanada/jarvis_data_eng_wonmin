package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet() {
        when(dao.create(any())).thenReturn(new Tweet());
        service.postTweet(new Tweet("Hello",20,19));
    }

    @Test
    public void showTweet() {
        when(dao.findById(anyString())).thenReturn(new Tweet());
        service.showTweet("1270174965879504896", new String[] {"text", "id"});
    }

    @Test
    public void deleteTweets() {
        when(dao.deleteById(anyString())).thenReturn(new Tweet());
        service.deleteTweets(new String[] {
                "1270191389129785351",
                "1270174965879504896"});
    }
}
