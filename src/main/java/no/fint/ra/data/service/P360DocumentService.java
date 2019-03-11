package no.fint.ra.data.service;

import no.fint.arkiv.p360.document.DocumentService;
import no.fint.arkiv.p360.document.IDocumentService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.WebServiceException;

@Service
public class P360DocumentService extends P360AbstractService {

    private IDocumentService documentServicePort;

    public P360DocumentService() {
        super("http://software-innovation.com/SI.Data", "DocumentService");
    }

    @PostConstruct
    private void init() {

        documentServicePort = new DocumentService(DocumentService.WSDL_LOCATION, serviceName).getBasicHttpBindingIDocumentService();
        super.addAuthentication(documentServicePort);

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
