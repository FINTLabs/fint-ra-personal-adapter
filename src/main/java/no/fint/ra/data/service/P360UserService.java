package no.fint.ra.data.service;

import no.fint.arkiv.p360.user.*;
import no.fint.ra.data.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import java.util.List;

@Service
public class P360UserService {

    private static final QName SERVICE_NAME = new QName("http://software-innovation.com/SI.Data", "UserService");

    private IUserService userServicePort;

    private ObjectFactory objectFactory;

    @Autowired
    private RequestService requestService;

    @PostConstruct
    private void init() {

        UserService userService = new UserService(UserService.WSDL_LOCATION, SERVICE_NAME);
        userServicePort = userService.getBasicHttpBindingIUserService();

        BindingProvider bp = (BindingProvider) userServicePort;
        requestService.addAuthentication(bp.getRequestContext());

        objectFactory = new ObjectFactory();


    }

    public boolean ping() {

        try {
            userServicePort.ping();
        } catch (WebServiceException e) {
            return false;
        }
        return true;

    }

    public List<UserBase> getUsers() {

        GetUsersParameter getUsersParameter = objectFactory.createGetUsersParameter();
        GetUsersResult users = userServicePort.getUsers(getUsersParameter);
        return users.getUsers().getValue().getUserBase();
    }


}
