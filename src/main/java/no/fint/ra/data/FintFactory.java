package no.fint.ra.data;

import no.fint.model.administrasjon.personal.Personalressurs;
import no.fint.model.felles.Person;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.felles.kompleksedatatyper.Personnavn;
import no.fint.model.resource.Link;
import no.fint.model.resource.administrasjon.personal.PersonalressursResource;
import no.fint.model.resource.felles.PersonResource;
import no.fint.arkiv.p360.contact.ContactPersonBase;
import no.fint.arkiv.p360.contact.ContactPersonResult;
import no.fint.arkiv.p360.contact.PrivatePersonBase;
import no.fint.arkiv.p360.contact.PrivatePersonResult;

public enum  FintFactory {
    ;

    public static PersonResource createPerson(PrivatePersonBase privatePerson, ContactPersonBase contactPerson) {
        PersonResource personResource = new PersonResource();
        Identifikator nin = FintFactory.createIdentifikator(privatePerson.getExternalID().getValue());
        personResource.setFodselsnummer(nin);
        Personnavn personnavn = new Personnavn();
        personnavn.setFornavn(contactPerson.getFirstName().getValue());
        personnavn.setEtternavn(contactPerson.getLastName().getValue());
        personResource.setNavn(personnavn);
        personResource.addSelf(Link.with(Person.class, "fodeselsnummer", nin.getIdentifikatorverdi()));

        return personResource;
    }

    public static PersonalressursResource createPeronalressurs(PrivatePersonResult privatePerson, ContactPersonResult contactPerson) {
        PersonalressursResource personalressursResource = new PersonalressursResource();
        Identifikator ansattnummer = FintFactory.createIdentifikator(FintFactory.maskId(privatePerson.getExternalID().getValue()));
        Identifikator systemId = FintFactory.createIdentifikator(contactPerson.getRecno().toString());

        personalressursResource.setAnsattnummer(ansattnummer);
        personalressursResource.setSystemId(systemId);

        personalressursResource.addPerson(Link.with(Person.class, "fodselsnummer", privatePerson.getExternalID().getValue()));
        personalressursResource.addSelf(Link.with(Personalressurs.class, "ansattnummer", ansattnummer.getIdentifikatorverdi()));
        personalressursResource.addSelf(Link.with(Personalressurs.class, "systemId", systemId.getIdentifikatorverdi()));

        return personalressursResource;
    }

    public static Identifikator createIdentifikator(String value) {
        Identifikator identifikator = new Identifikator();
        identifikator.setIdentifikatorverdi(value);
        return identifikator;
    }

    public static String maskId(String nin) {
        return Long.toString((Long.parseLong(nin) / 100), 36);
    }
}
