package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * MarketDataDao is responsible for getting Quotes from IEX
 */
@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private final String IEX_BATCH_URL;

    private static final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }

    /**
     * Get an IexQuote (Helper method for method findAllById)
     *
     * @param ticker
     * @throws IllegalArgumentException if a given ticket is invalid
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0) {
            return Optional.empty();
        } else if (quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        } else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }
        return iexQuote;
    }

    /**
     * Get quotes from IEX
     *
     * @param tickers list of tickers
     * @return a list of IexQuote object
     * @throws IllegalArgumentException if any ticker is invalid of tickers is empty
     * @throws DataRetrievalFailureException if HTTP request is failed
     */
    @Override
    public List<IexQuote> findAllById(Iterable<String> tickers) {
        List<IexQuote> quotes = new ArrayList();
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<String> jsonResponse = null;

        if (StreamSupport.stream(tickers.spliterator(),false).count() == 0) {
            throw new IllegalArgumentException("List of tickers is empty");
        }
        String ticketList = StreamSupport
                .stream(tickers.spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining(","));
        String newURI = IEX_BATCH_URL.replaceAll("%s",ticketList);

        try {
            jsonResponse = executeHttpGet(newURI);
        } catch (DataRetrievalFailureException e) {
            logger.error("Failed to parse Http Response", e);
        }

        JSONObject jsonQuote = new JSONObject(jsonResponse.get());

        tickers.forEach(ind -> {
            try {
                quotes.add(objectMapper
                        .readValue(jsonQuote.getJSONObject(ind).getJSONObject("quote").toString(),
                                IexQuote.class));
            } catch (IOException e) {
                logger.error("Failed to parse Json string", e);
                throw new RuntimeException();
            }
        });
        return quotes;
    }

    /**
     * Execute a get and return HTTP entity/body as a String
     *
     * @param url resource URL
     * @return http response body or Optional.empty for 404 Response
     * @throws DataRetrievalFailureException if Http failed or status code is unexpected
     */
    private Optional<String> executeHttpGet(String url) {
        Optional<String> response = null;
        try {
            CloseableHttpClient httpClient = getHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                try {
                    logger.info(EntityUtils.toString(httpResponse.getEntity()));
                } catch (IOException e) {
                    logger.error("Could not parse Http response body", e);
                    throw new RuntimeException();
                }
                return Optional.empty();
            }
            if (httpResponse.getEntity() == null) {
                throw new RuntimeException("Empty response body");
            }
            response = Optional.of(EntityUtils.toString(httpResponse.getEntity()));
        } catch (IOException e) {
            logger.error("Invalid URL!", e);
            throw new RuntimeException();
        }
        return response;
    }

    /**
     * Borrow a HTTP client from the httpClientConnectionManager
     * @return httpClient
     */
    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true) //Prevent connectionManager shutdown when calling httpClient.close()
                .build();
    }






    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not Implemented");
    }
}
