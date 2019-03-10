package no.fint.ra.service;

import lombok.extern.slf4j.Slf4j;
import no.fint.adapter.event.EventResponseService;
import no.fint.adapter.event.EventStatusService;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.event.model.Status;
import no.fint.event.model.health.Health;
import no.fint.event.model.health.HealthStatus;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.relation.FintResource;
import no.fint.ra.data.service.P360ContactServiceP360;
import no.fint.ra.data.service.P360UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Slf4j
@Service
public class EventHandlerService {

    @Autowired
    private EventResponseService eventResponseService;

    @Autowired
    private EventStatusService eventStatusService;

    @Autowired
    private P360ContactServiceP360 p360ContactService;

    @Autowired
    private P360UserService p360UserService;


    public void handleEvent(Event event) {
        if (event.isHealthCheck()) {
            postHealthCheckResponse(event);
        } else {
            if (eventStatusService.verifyEvent(event).getStatus() == Status.ADAPTER_ACCEPTED) {
                Event<FintResource> responseEvent = new Event<>(event);
                try {

                    createEventResponse(event, responseEvent);

                } catch (Exception e) {
                    log.error("Error handling event {}", event, e);
                    responseEvent.setResponseStatus(ResponseStatus.ERROR);
                    responseEvent.setMessage(e.getMessage());
                } finally {
                    responseEvent.setStatus(Status.ADAPTER_RESPONSE);
                    eventResponseService.postResponse(responseEvent);
                }
            }
        }
    }


    private void createEventResponse(Event event, Event<FintResource> responseEvent) {
        switch (PersonalActions.valueOf(event.getAction())) {

        }
    }


    public void postHealthCheckResponse(Event event) {
        Event<Health> healthCheckEvent = new Event<>(event);
        healthCheckEvent.setStatus(Status.TEMP_UPSTREAM_QUEUE);

        if (healthCheck()) {
            healthCheckEvent.addData(new Health("adapter", HealthStatus.APPLICATION_HEALTHY.name()));
        } else {
            healthCheckEvent.addData(new Health("adapter", HealthStatus.APPLICATION_UNHEALTHY));
            healthCheckEvent.setMessage("The adapter is unable to communicate with the application.");
        }

        eventResponseService.postResponse(healthCheckEvent);
    }


    private boolean healthCheck() {

        return p360ContactService.ping() && p360UserService.ping();

    }


    @PostConstruct
    void init() {

    }
}
