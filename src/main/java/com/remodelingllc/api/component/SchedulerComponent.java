package com.remodelingllc.api.component;

import com.remodelingllc.api.entity.Feature;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Log4j2
public class SchedulerComponent {

    @Value("${com.remodelingllc.api.uri}")
    private String apiUri;

    @Scheduled(cron = "0 */25 * * * *")
    public void refreshApi() {
        var restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUri + "/feature");
        try {
            ResponseEntity<Feature[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    null, Feature[].class);
            log.info("api recargada, status = {}", response.getStatusCode());
        } catch (RestClientException | NullPointerException e) {
            log.error("Error al recargar el api, {}", e.getMessage());
        }
    }
}
