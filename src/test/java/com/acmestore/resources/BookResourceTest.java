package com.acmestore.resources;

import com.acmestore.StoreBackendApplication;
import com.acmestore.StoreBackendConfiguration;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.dropwizard.testing.FixtureHelpers;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 5/16/17.
 */
public class BookResourceTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(9090));

    @ClassRule
    public static final DropwizardAppRule<StoreBackendConfiguration> app = new DropwizardAppRule<>(
            StoreBackendApplication.class,
            ResourceHelpers.resourceFilePath("config.yml"));
    private Client client = app.client();

    private String hostUrl = String.format("http://localhost:%d", app.getLocalPort());

    @Test
    public void testGetBook() {
        wireMockRule.stubFor(get(urlPathEqualTo("/api/books"))
            .withHeader("Accept", containing("json"))
            .withQueryParam("bibkeys", equalTo("ISBN:0201558025"))
            .withQueryParam("format", equalTo("json"))
            .withQueryParam("jscmd", equalTo("data"))
            .willReturn(aResponse()
                    .withFixedDelay(200)
                    .withHeader("Content-Type", "text/json")
                    .withStatus(200)
                    .withBody(FixtureHelpers.fixture("fixtures/open_library_get_book.json"))));


        Response response = client.target(hostUrl + "/book/get")
                .queryParam("isbn", "0201558025")
                .request().buildGet().invoke();
        Map<String, String> data = response.readEntity(Map.class);
        System.out.println(data);
        assertThat(data.get("viewCount")).isEqualTo("1");
    }
}
