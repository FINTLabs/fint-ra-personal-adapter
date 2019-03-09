package no.fint.ra.data.service;

import no.fint.arkiv.p360.caze.CaseService;
import no.fint.arkiv.p360.caze.ICaseService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.WebServiceException;

@Service
public class P360CaseService extends AbstractService {

    private ICaseService caseServicePort;

    public P360CaseService() {
        super("http://software-innovation.com/SI.Data", "CaseService");
    }

    @PostConstruct
    public void init() {
        caseServicePort = new CaseService(CaseService.WSDL_LOCATION, serviceName).getBasicHttpBindingICaseService();
        super.addAuthentication(caseServicePort);
    }

    public boolean ping() {
        try {
            caseServicePort.ping();
        } catch (WebServiceException e) {
            return false;
        }

        return true;
    }
}
