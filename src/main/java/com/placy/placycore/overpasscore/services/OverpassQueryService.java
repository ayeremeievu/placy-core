package com.placy.placycore.overpasscore.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.placy.placycore.core.exceptions.HttpRequestResultException;
import com.placy.placycore.overpasscore.adapter.SimpleOverpassQueryAdapter;
import com.placy.placycore.overpasscore.data.ElementData;
import com.placy.placycore.overpasscore.data.OverpassResponseData;
import com.placy.placycore.overpasscore.mappers.OverpassResponseMapper;
import com.placy.placycore.overpasscore.query.SimpleOverpassQuery;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class OverpassQueryService {

    private final static String OVERPASS_BASE_URI = "http://overpass-api.de/api/interpreter";
    private final static ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private SimpleOverpassQueryAdapter simpleOverpassQueryAdapter;

    public OverpassResponseData queryOverpassSync(SimpleOverpassQuery simpleOverpassQuery) {
        OverpassResponseData result = null;

        CloseableHttpClient client = HttpClients.createDefault();

        try {
            HttpPost request = buildHttpRequest(simpleOverpassQuery);

            try (CloseableHttpResponse response = client.execute(request)) {
                String body = EntityUtils.toString(response.getEntity());
                if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

                    result = objectMapper.readValue(body, OverpassResponseData.class);
                } else {
                    throw new HttpRequestResultException(
                            String.format("Overpass responded not successfully. Status : %s, body : %s",
                                    response.getStatusLine().getStatusCode(), body)
                    );
                }
            }
        } catch (IOException ex) {
            rethrow(ex);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                rethrow(ex);
            }
        }

        return result;
    }

    public <T> List<T> mapElements(OverpassResponseData overpassResponseData, OverpassResponseMapper<T> overpassResponseMapper) {
        List<ElementData> features = overpassResponseData.getElements();

        return features.stream()
                       .map(overpassResponseMapper::map)
                       .collect(Collectors.toList());
    }

    private HttpPost buildHttpRequest(SimpleOverpassQuery simpleOverpassQuery) throws UnsupportedEncodingException {
        String overpassQuery = simpleOverpassQueryAdapter.resolveOverpassQuery(simpleOverpassQuery);
        HttpPost httpPost = new HttpPost(OVERPASS_BASE_URI);

        httpPost.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        StringEntity body = new StringEntity(overpassQuery);

        httpPost.setEntity(body);

        return httpPost;
    }

    public SimpleOverpassQueryAdapter getSimpleOverpassQueryAdapter() {
        return simpleOverpassQueryAdapter;
    }

    public void setSimpleOverpassQueryAdapter(SimpleOverpassQueryAdapter simpleOverpassQueryAdapter) {
        this.simpleOverpassQueryAdapter = simpleOverpassQueryAdapter;
    }
}
