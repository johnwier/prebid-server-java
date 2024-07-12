package org.prebid.server.it;

import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.prebid.server.model.Endpoint;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static java.util.Collections.singletonList;

public class InvibesTest extends IntegrationTest {

    @Test
    public void openrtb2AuctionShouldRespondWithBidsFromInvibes() throws IOException, JSONException {
        // given
        WIRE_MOCK_RULE.stubFor(post(urlPathEqualTo("/invibes-exchange"))
                .withRequestBody(equalToJson(jsonFrom("openrtb2/invibes/test-invibes-bid-request.json")))
                .willReturn(aResponse().withBody(jsonFrom("openrtb2/invibes/test-invibes-bid-response.json"))));

        // when
        final Response response = responseFor("openrtb2/invibes/test-auction-invibes-request.json",
                Endpoint.openrtb2_auction);

        // then
        assertJsonEquals("openrtb2/invibes/test-auction-invibes-response.json", response, singletonList("invibes"));
    }
}
