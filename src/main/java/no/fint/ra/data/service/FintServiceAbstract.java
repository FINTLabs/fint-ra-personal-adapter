package no.fint.ra.data.service;

import no.fint.arkiv.p360.contact.ContactPersonBase;
import no.fint.arkiv.p360.contact.ContactPersonResult;
import no.fint.arkiv.p360.contact.PrivatePersonBase;
import no.fint.arkiv.p360.contact.PrivatePersonResult;
import no.fint.arkiv.p360.user.UserBase;

abstract class FintServiceAbstract {


    boolean hasNin(PrivatePersonBase privatePerson) {
        return !privatePerson.getExternalID().getValue().equals("");
    }

    String getFullname(ContactPersonBase contactPerson) {
        return String.format("%s %s",
                contactPerson.getFirstName().getValue(),
                contactPerson.getLastName().getValue());
    }

    int getRecNo(String recNoValue) {

        String[] recnoString = recNoValue.split(":");

        if (recnoString.length == 2) {
            return Integer.valueOf(recnoString[1]);
        }
        return -1;

    }

    ContactPersonResult getContactPerson(UserBase userBase, P360ContactService p360ContactService) {

        int recNo = getRecNo(userBase.getContactExternalId().getValue());

        if (recNo > 0) {
            return p360ContactService.getContactPerson(recNo);
        }
        return null;
    }

    PrivatePersonResult getPrivatePerson(ContactPersonBase contactPerson, P360ContactService p360ContactService) {

        if (contactPerson != null) {
            PrivatePersonResult privatePerson = p360ContactService.getPrivatePerson(getFullname(contactPerson));

            if (privatePerson != null) {
                if (hasNin(privatePerson)) {
                    return privatePerson;
                }
            }
        }

        return null;
    }
}
