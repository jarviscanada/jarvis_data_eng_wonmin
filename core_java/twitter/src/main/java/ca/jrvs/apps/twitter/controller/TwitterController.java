package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import java.util.List;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

    private final String COORD_SEP = ":";
    private final String COMMA = ",";
    private Service service;

    @Autowired
    public TwitterController(Service service) {this.service = service;}

    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Please verify the app usage! \n" +
                    "Usage: Util.TwitterCLIApp post \"text\" \"latitude:longitude\"");
        }
        String text = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        float myLat = 0;
        float myLong = 0;

        if (coordArray.length != 2) {
            throw new IllegalArgumentException("Please specify a valid latitude:longitude");
        }
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Tweet body cannot be empty!");
        }

        try {
            myLat = Float.parseFloat(coordArray[0]);
            myLong = Float.parseFloat(coordArray[1]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid location information!", e);
        }
        Tweet tweet = new Tweet(text,myLat,myLong);
        return service.postTweet(tweet);
    }

    @Override
    public Tweet showTweet(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Please verify the app usage! \n" +
                    "Usage: Util.TwitterCLIApp show \"tweet_id\" \"[optional_fields]\"");
        }
        String idStr = args[1];
        String field = args[2];
        String[] fieldList = field.split(COMMA);

        return service.showTweet(idStr, fieldList);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Please verify the app usage! \n" +
                    "Usage: Util.TwitterCLIApp delete \"[id_list]\"");
        }
        String id = args[1];
        String[] idList = id.split(COMMA);

        return service.deleteTweets(idList);
    }
}