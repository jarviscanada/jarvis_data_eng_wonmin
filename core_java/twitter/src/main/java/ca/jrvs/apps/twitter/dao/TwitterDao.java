package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.practice.JSONParser;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.gdata.util.common.base.PercentEscaper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ca.jrvs.apps.twitter.practice.JSONParser.toJson;
import static ca.jrvs.apps.twitter.practice.JSONParser.toObjectFromJson;

public class TwitterDao implements CrdDao<Tweet, String> {
    private Logger logger = LoggerFactory.getLogger(TwitterDao.class);

    //URI Constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy/";
    //URI Symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";
    //Response Code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao (HttpHelper httpHelper) {this.httpHelper = httpHelper;}

    @Override
    public Tweet create(Tweet entity) {
        Tweet tweet = null;
        try {
            PercentEscaper percentEscaper = new PercentEscaper("", false);
            String escapedStr = percentEscaper.escape(entity.getText());
            URI postUri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUALS + escapedStr
                                + AMPERSAND + "lat" + EQUALS + entity.getCoordinates().getCoordinates()[0]
                                + AMPERSAND + "long" + EQUALS + entity.getCoordinates().getCoordinates()[1]);
            HttpResponse httpResponse = httpHelper.httpPost(postUri);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status != HTTP_OK) {
                try {
                    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                throw new RuntimeException("Unexpected HTTP Response!");
            }
            if (httpResponse.getEntity() == null) {
                throw new RuntimeException("Empty Response Body");
            }
            String jsonstr;
            try {
                 jsonstr = EntityUtils.toString(httpResponse.getEntity());
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("Failed to Convert Entity to String");
            }
            try {
                tweet = toObjectFromJson(jsonstr, Tweet.class);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("Failed to Convert JSON String to Tweet Object");
            }
        }
        catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
        return tweet;
    }

    @Override
    public Tweet findById(String s) {
        Tweet tweet = null;
        try {
            URI getUri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUALS + s);
            HttpResponse httpResponse = httpHelper.httpGet(getUri);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status != HTTP_OK) {
                try {
                    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                throw new RuntimeException("Unexpected HTTP Response!");
            }
            if (httpResponse.getEntity() == null) {
                throw new RuntimeException("Empty Response Body");
            }
            String jsonstr;
            try {
                jsonstr = EntityUtils.toString(httpResponse.getEntity());
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("Failed to Convert Entity to String");
            }
            try {
                tweet = toObjectFromJson(jsonstr, Tweet.class);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("Failed to Convert JSON String to Tweet Object");
            }
        }
        catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
        return tweet;
    }

    @Override
    public Tweet deleteById(String s) {
        Tweet tweet = null;
        try {
            URI deleteUri = new URI(API_BASE_URI + DELETE_PATH + s + ".json");
            HttpResponse httpResponse = httpHelper.httpPost(deleteUri);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status != HTTP_OK) {
                try {
                    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                throw new RuntimeException("Unexpected HTTP Response!");
            }
            if (httpResponse.getEntity() == null) {
                throw new RuntimeException("Empty Response Body");
            }
            String jsonstr;
            try {
                jsonstr = EntityUtils.toString(httpResponse.getEntity());
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("Failed to Convert Entity to String");
            }
            try {
                tweet = toObjectFromJson(jsonstr, Tweet.class);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("Failed to Convert JSON String to Tweet Object");
            }
        }
        catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
        return tweet;
    }

/*
    @Override
    public Tweet create(Tweet tweet) {
        URI postUri;
        try {
            PercentEscaper percentEscaper = new PercentEscaper("", false);
            String escapedStr = percentEscaper.escape(tweet.getText());
            postUri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUALS + escapedStr);
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid");
        }
        HttpResponse httpResponse = httpHelper.httpPost(postUri);
        return parseResponseBody(httpResponse, HTTP_OK);
    }
    private Tweet parseResponseBody(HttpResponse httpResponse, Integer expectedStatusCode) {
        Tweet tweet = null;
        int status = httpResponse.getStatusLine().getStatusCode();

        if (status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(httpResponse.getEntity()));
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            throw new RuntimeException("Unexpected HTTP Response!");
        }

        if (httpResponse.getEntity() == null) {
            throw new RuntimeException("Empty Response Body");
        }

        String jsonstr;
        try {
            jsonstr = EntityUtils.toString(httpResponse.getEntity());
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to Convert Entity to String");
        }

        try {
            tweet = toObjectFromJson(jsonstr, Tweet.class);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to Convert JSON String to Tweet Object");
        }
       return tweet;
    }
    */
}
