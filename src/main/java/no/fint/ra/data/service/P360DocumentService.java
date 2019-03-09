package no.fint.ra.data.service;

import no.fint.arkiv.p360.document.DocumentService;
import no.fint.arkiv.p360.document.IDocumentService;
import no.fint.ra.data.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

@Service
public class P360DocumentService {


    private static final QName SERVICE_NAME = new QName("http://software-innovation.com/SI.Data", "DocumentService");

    private IDocumentService documentServicePort;

    @Autowired
    private RequestService requestService;

    @PostConstruct
    private void init() {

        DocumentService ss = new DocumentService(DocumentService.WSDL_LOCATION, SERVICE_NAME);
        documentServicePort = ss.getBasicHttpBindingIDocumentService();

        BindingProvider bp = (BindingProvider) documentServicePort;
        requestService.addAuthentication(bp.getRequestContext());

    }

    public boolean ping() {

        try {
            documentServicePort.ping();
        } catch (WebServiceException e) {
            return false;
        }

        return true;
    }
}
