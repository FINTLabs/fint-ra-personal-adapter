package no.fint.ra.data.service;

import no.fint.arkiv.p360.file.FileService;
import no.fint.arkiv.p360.file.IFileService;
import no.fint.ra.data.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

@Service
public class P360FileService {


    private static final QName SERVICE_NAME = new QName("http://software-innovation.com/SI.Data", "FileService");

    private IFileService fileServicePort;

    @Autowired
    private RequestService requestService;

    @PostConstruct
    private void init() {

        FileService ss = new FileService(FileService.WSDL_LOCATION, SERVICE_NAME);
        fileServicePort = ss.getBasicHttpBindingIFileService();

        BindingProvider bp = (BindingProvider) fileServicePort;
        requestService.addAuthentication(bp.getRequestContext());

    }

    public boolean ping() {

        try {
            fileServicePort.ping();
        } catch (WebServiceException e) {
            return false;
        }

        return true;
    }
}
