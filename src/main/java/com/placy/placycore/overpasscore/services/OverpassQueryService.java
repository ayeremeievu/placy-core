package com.placy.placycore.overpasscore.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.placy.placycore.core.exceptions.HttpRequestResultException;
import com.placy.placycore.overpasscore.adapter.SimpleOverpassQueryAdapter;
import com.placy.placycore.overpasscore.data.ElementData;
import com.placy.placycore.overpasscore.data.OverpassResponseData;
import com.placy.placycore.overpasscore.mappers.OverpassResponseMapper;
import com.placy.placycore.overpasscore.query.SimpleOverpassQuery;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = buildHttpRequest(simpleOverpassQuery);

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value()) {
                String body = response.body();

                result = objectMapper.readValue(body, OverpassResponseData.class);
            } else {
                throw new HttpRequestResultException(
                    String.format("Overpass responded not successfully. Status : %s, body : %s", response.statusCode(), response.body())
                );
            }
        } catch (IOException | InterruptedException ex) {
            rethrow(ex);
        }

        return result;
    }

    public <T> List<T> mapElements(OverpassResponseData overpassResponseData, OverpassResponseMapper<T> overpassResponseMapper) {
        List<ElementData> features = overpassResponseData.getElements();

        return features.stream()
                       .map(overpassResponseMapper::map)
                       .collect(Collectors.toList());
    }

    private HttpRequest buildHttpRequest(SimpleOverpassQuery simpleOverpassQuery) {
        String overpassQuery = simpleOverpassQueryAdapter.resolveOverpassQuery(simpleOverpassQuery);
        return HttpRequest.newBuilder()
                          .uri(URI.create(OVERPASS_BASE_URI))
                          .setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                          .POST(HttpRequest.BodyPublishers.ofString(overpassQuery))
                          .build();
    }

    public SimpleOverpassQueryAdapter getSimpleOverpassQueryAdapter() {
        return simpleOverpassQueryAdapter;
    }

    public void setSimpleOverpassQueryAdapter(SimpleOverpassQueryAdapter simpleOverpassQueryAdapter) {
        this.simpleOverpassQueryAdapter = simpleOverpassQueryAdapter;
    }
}
