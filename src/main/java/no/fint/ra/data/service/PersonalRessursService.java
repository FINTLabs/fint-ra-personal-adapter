package no.fint.ra.data.service;

import no.fint.arkiv.p360.contact.ContactPersonResult;
import no.fint.arkiv.p360.contact.PrivatePersonResult;
import no.fint.arkiv.p360.user.UserBase;
import no.fint.model.resource.administrasjon.personal.PersonalressursResources;

import no.fint.ra.data.FintFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalRessursService extends FintAbstractService {


    @Autowired
    private P360UserService p360UserService;

    @Autowired
    private P360ContactServiceP360 p360ContactService;

    public PersonalressursResources getPersonalressurs() {

        PersonalressursResources personalressursResources = new PersonalressursResources();

        List<UserBase> users = p360UserService.getUsers();

        users.forEach(userBase -> {
            ContactPersonResult contactPerson = getContactPerson(userBase, p360ContactService);
            PrivatePersonResult privatePerson = getPrivatePerson(contactPerson, p360ContactService);

            if (privatePerson != null) {
                personalressursResources.addResource(FintFactory.createPeronalressurs(privatePerson, contactPerson));
            }
        });

        return personalressursResources;
    }
}
