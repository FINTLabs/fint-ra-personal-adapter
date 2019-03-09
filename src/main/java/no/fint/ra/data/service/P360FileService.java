package no.fint.ra.data.service;

import com.sun.xml.internal.ws.client.ClientTransportException;
import no.fint.ra.data.RequestService;
import no.fint.arkiv.p360.file.FileService;
import no.fint.arkiv.p360.file.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

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
        } catch (ClientTransportException e) {
            return false;
        }

        return true;
    }
}
