package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service {
    private CrdDao dao;
    private static int charMAX = 140;
    private static int idMAX = 19;
    private static float latMAX = 90F;
    private static float latMIN = -90F;
    private static float longMAX = 180F;
    private static float longMIN = -180F;

    @Autowired
    public TwitterService(CrdDao dao) {this.dao = dao;}

    @Override
    public Tweet postTweet(Tweet tweet) throws IllegalArgumentException{
        float myLong = tweet.getCoordinates().getCoordinates()[0];
        float myLat = tweet.getCoordinates().getCoordinates()[1];

        if (tweet.getText().length() > charMAX) {
            throw new IllegalArgumentException("The number of character exceeds 140!");
        }
        if (myLong > longMAX || myLong < longMIN) {
            throw new IllegalArgumentException("The longitude is out of range!");
        }
        if (myLat > latMAX || myLat < latMIN) {
            throw new IllegalArgumentException("The latitude is out of range!");
        }
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        if (id.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("id should only consist of digits!");
        }
        if (id.length() > idMAX) {
            throw new IllegalArgumentException("id length out of range!");
        }
         /*Optional Field Parser - To be implemented later if time permits
        if (fields.length == 0) {
            return (Tweet) dao.findById(id);
        }
        */
        return (Tweet) dao.findById(id);
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List deletedList = new ArrayList();
        for (String s: ids) {
            if (s.matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("id should only consist of digits");
            }
            if (s.length() > idMAX) {
                throw new IllegalArgumentException("id length out of range");
            }
            deletedList.add((Tweet) dao.deleteById(s));
        }
        return deletedList;
    }
}
