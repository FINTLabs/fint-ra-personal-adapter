package no.fint.ra.data.service;

import lombok.extern.slf4j.Slf4j;
import no.fint.arkiv.p360.contact.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.WebServiceException;
import java.util.List;

@Slf4j
@Service
public class P360ContactServiceP360 extends P360AbstractService {

    private IContactService contactServicePort;
    private ObjectFactory objectFactory;

    public P360ContactServiceP360() {
        super("http://software-innovation.com/SI.Data", "ContactService");
    }

    @PostConstruct
    private void init() {

        contactServicePort = new ContactService(ContactService.WSDL_LOCATION, serviceName).getBasicHttpBindingIContactService();
        super.addAuthentication(contactServicePort);

        objectFactory = new ObjectFactory();
    }

    public boolean ping() {

        try {
            contactServicePort.ping();
        } catch (WebServiceException e) {
            log.warn(e.getMessage());
            return false;
        }

        return true;
    }

    public void getContactPersons() {
        GetContactPersonsParameter getContactPersonsParameter = objectFactory.createGetContactPersonsParameter();
        getContactPersonsParameter.setRecno(objectFactory.createGetContactPersonsParameterRecno(100003));
        GetContactPersonsResult contactPersons = contactServicePort.getContactPersons(getContactPersonsParameter);
    }

    public ContactPersonResult getContactPerson(int recno) {
        GetContactPersonsParameter getContactPersonsParameter = objectFactory.createGetContactPersonsParameter();
        getContactPersonsParameter.setRecno(objectFactory.createGetContactPersonsParameterRecno(recno));
        List<ContactPersonResult> contactPersonResult = contactServicePort.getContactPersons(getContactPersonsParameter).getContactPersons().getValue().getContactPersonResult();

        if (contactPersonResult.size() == 1) {
            return contactPersonResult.get(0);
        }

        return null;
    }

    public PrivatePersonResult getPrivatePerson(String name) {
        GetPrivatePersonsParameter privatePersonsParameter = objectFactory.createGetPrivatePersonsParameter();
        privatePersonsParameter.setName(objectFactory.createGetPrivatePersonsParameterName(name));
        GetPrivatePersonsResult privatePersons = contactServicePort.getPrivatePersons(privatePersonsParameter);
        List<PrivatePersonResult> privatePersonResult = privatePersons.getPrivatePersons().getValue().getPrivatePersonResult();

        if (privatePersonResult.size() == 1) {
            return privatePersonResult.get(0);
        }

        return null;
    }
}
