package no.fint.ra.data.service;

import no.fint.model.resource.felles.PersonResources;
import no.fint.ra.data.FintFactory;
import no.fint.arkiv.p360.contact.ContactPersonBase;
import no.fint.arkiv.p360.contact.PrivatePersonBase;
import no.fint.arkiv.p360.user.UserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService extends FintServiceAbstract {


    @Autowired
    private P360UserService p360UserService;

    @Autowired
    private P360ContactService p360ContactService;

    public PersonResources getPersonResources() {

        PersonResources personResources = new PersonResources();

        List<UserBase> users = p360UserService.getUsers();

        users.forEach(userBase -> {

            ContactPersonBase contactPerson = getContactPerson(userBase, p360ContactService);
            PrivatePersonBase privatePerson = getPrivatePerson(contactPerson, p360ContactService);

            if (privatePerson != null) {
                personResources.addResource(FintFactory.createPerson(privatePerson, contactPerson));
            }

        });

        return personResources;
    }


}
