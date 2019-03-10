package no.fint.ra.data.service;

import no.fint.ra.data.RequestService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

public abstract class P360AbstractService {

    @Autowired
    protected RequestService requestService;

    final QName serviceName;

    public P360AbstractService(String namespaceURI, String localPart) {
        serviceName = new QName(namespaceURI, localPart);
    }

    void addAuthentication(Object port) {
        BindingProvider bp = (BindingProvider) port;
        requestService.addAuthentication(bp.getRequestContext());
    }
}