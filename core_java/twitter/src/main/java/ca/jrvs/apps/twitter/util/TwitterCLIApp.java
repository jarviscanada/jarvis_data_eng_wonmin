package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static ca.jrvs.apps.twitter.util.JSONParser.toJson;


@Component
public class TwitterCLIApp {

    public static final String USAGE = "Usage: Util.TwitterCLIApp post|show|delete [options]";

    private Controller controller;

    @Autowired
    public TwitterCLIApp (Controller controller) {this.controller = controller;}

    public static void main(String[] args) {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        CrdDao dao = new TwitterDao(httpHelper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

        app.usage(args);
    }

    public void usage(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Improper arguments!\n" + USAGE);
        }
        switch (args[0].toLowerCase()) {
            case "post":
                printTweet(controller.postTweet(args));
                break;

            case "show":
                printTweet(controller.showTweet(args));
                break;

            case "delete":
                controller.deleteTweet(args).forEach(this::printTweet);
                break;

            default:
                throw new IllegalArgumentException("Invalid usage\n" + USAGE);
        }
    }

    public void printTweet(Tweet tweet) {
        try {
            System.out.println(toJson(tweet,true,true));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse tweet object!", e);
        }
    }
}