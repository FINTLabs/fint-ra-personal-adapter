package no.fint.ra.data.service;

import com.sun.xml.internal.ws.client.ClientTransportException;
import no.fint.ra.data.RequestService;
import no.fint.arkiv.p360.caze.CaseService;
import no.fint.arkiv.p360.caze.ICaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

@Service
public class P360CaseService {


    private static final QName SERVICE_NAME = new QName("http://software-innovation.com/SI.Data", "CaseService");

    private ICaseService caseServicePort;

    @Autowired
    private RequestService requestService;

    @PostConstruct
    private void init() {

        CaseService ss = new CaseService(CaseService.WSDL_LOCATION, SERVICE_NAME);
        caseServicePort = ss.getBasicHttpBindingICaseService();

        BindingProvider bp = (BindingProvider) caseServicePort;
        requestService.addAuthentication(bp.getRequestContext());

    }

    public boolean ping() {

        try {
            caseServicePort.ping();
        } catch (ClientTransportException e) {
            return false;
        }

        return true;
    }
}
