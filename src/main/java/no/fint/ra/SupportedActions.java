package no.fint.ra;

import no.fint.adapter.AbstractSupportedActions;
import no.fint.model.administrasjon.personal.PersonalActions;
import no.fint.model.felles.FellesActions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SupportedActions extends AbstractSupportedActions {

    @PostConstruct
    public void addSupportedActions() {
        add(PersonalActions.GET_ALL_PERSONALRESSURS);
        add(FellesActions.GET_ALL_PERSON);
    }

}
