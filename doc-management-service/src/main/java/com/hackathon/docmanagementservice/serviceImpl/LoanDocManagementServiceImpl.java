package com.hackathon.docmanagementservice.serviceImpl;

import com.hackathon.docmanagementservice.entity.LoanDocumentDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.hackathon.docmanagementservice.repository.LoanDocDetailsRepository;
import com.hackathon.docmanagementservice.service.LoanDocManagementService;

@Service
@Log4j2
public class LoanDocManagementServiceImpl implements LoanDocManagementService {

    @Autowired
    LoanDocDetailsRepository loanDocDetailsRepository;

    @Override
    public LoanDocumentDetails saveFileData(MultipartFile file, long customerId, long loanId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(null != fileName && fileName.contains("..")){
                throw new Exception("File Name contains invalid path sequence: "+ fileName);
            }
            LoanDocumentDetails loanDocumentDetails = new LoanDocumentDetails(fileName,
                    file.getContentType(),
                    file.getBytes(),
                    customerId,loanId);
            log.info("** Before saving doc");
            return loanDocDetailsRepository.save(loanDocumentDetails);

        }catch(Exception e){
            e.printStackTrace();
          log.error("Error while saving file: ", e);
        }
        return null;
    }

    @Override
    public LoanDocumentDetails getLoanDocuments(String fileId) throws Exception {
        return loanDocDetailsRepository.findById(fileId)
                .orElseThrow(() -> new Exception("File is not found with Id:"+ fileId));
    }
}
