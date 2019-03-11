package no.fint.ra.data.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.administrasjon.personal.PersonalressursResources;
import no.fint.model.resource.felles.PersonResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Getter
@Service
public class DataService {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonalRessursService personalRessursService;

    private PersonResources personResources;
    private PersonalressursResources personalressursResources;

    private transient boolean ready = false;

    @PostConstruct
    public void init() {
        ready = false;
    }

    @Scheduled(initialDelay = 10000, fixedRateString = "${fint.ra.adapter.p360.rate:3600000}")
    public void update() {

        log.info("Start updating data...");
        long startTime = System.currentTimeMillis();
        ready = false;
        personResources = personService.getPersonResources();
        personalressursResources = personalRessursService.getPersonalressurs();
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        ready = true;
        log.info("Finished updating data in {} seconds", timeElapsed / 1000);

    }


}
