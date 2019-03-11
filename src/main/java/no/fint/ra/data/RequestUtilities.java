package no.fint.ra.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.BindingProvider;
import java.util.Map;

@Component
public class RequestUtilities {

    @Autowired
    private AppProps appProps;

    public void addAuthentication(Map<String, Object> map) {
        map.put(BindingProvider.USERNAME_PROPERTY, appProps.getP360User());
        map.put(BindingProvider.PASSWORD_PROPERTY, appProps.getP360Password());
    }
}
