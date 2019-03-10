package no.fint.ra.data.service;

import lombok.extern.slf4j.Slf4j;
import no.fint.arkiv.p360.user.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.WebServiceException;
import java.util.List;

@Slf4j
@Service
public class P360UserService extends P360AbstractService {


    private IUserService userServicePort;

    private ObjectFactory objectFactory;


    public P360UserService() {
        super("http://software-innovation.com/SI.Data", "UserService");
    }

    @PostConstruct
    private void init() {
        userServicePort = new UserService(UserService.WSDL_LOCATION, serviceName).getBasicHttpBindingIUserService();
        super.addAuthentication(userServicePort);
        objectFactory = new ObjectFactory();
    }

    public boolean ping() {

        try {
            userServicePort.ping();
        } catch (WebServiceException e) {
            log.warn(e.getMessage());
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
