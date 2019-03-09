package no.fint.ra.data.service;

import com.sun.xml.internal.ws.client.ClientTransportException;
import no.fint.arkiv.p360.contact.*;
import no.fint.ra.data.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import java.util.List;

@Service
public class P360ContactService {


    private static final QName SERVICE_NAME = new QName("http://software-innovation.com/SI.Data", "ContactService");

    private IContactService contactServicePort;
    private ObjectFactory objectFactory;

    @Autowired
    private RequestService requestService;

    @PostConstruct
    private void init() {

        ContactService ss = new ContactService(ContactService.WSDL_LOCATION, SERVICE_NAME);
        contactServicePort = ss.getBasicHttpBindingIContactService();

        BindingProvider bp = (BindingProvider) contactServicePort;
        requestService.addAuthentication(bp.getRequestContext());

        objectFactory = new ObjectFactory();
    }

    public boolean ping() {

        try {
            contactServicePort.ping();
        } catch (ClientTransportException e) {
            return false;
        }

        return true;
    }

    public void getContactPersons() {
        GetContactPersonsParameter getContactPersonsParameter = objectFactory.createGetContactPersonsParameter();
        getContactPersonsParameter.setRecno(objectFactory.createGetContactPersonsParameterRecno(100003));
        GetContactPersonsResult contactPersons = contactServicePort.getContactPersons(getContactPersonsParameter);
        System.out.println("hei");
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
