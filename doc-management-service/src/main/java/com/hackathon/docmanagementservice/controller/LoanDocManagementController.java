package com.hackathon.docmanagementservice.controller;

import com.hackathon.docmanagementservice.entity.LoanDocumentDetails;
import com.hackathon.docmanagementservice.modal.ResponseData;
import com.hackathon.docmanagementservice.serviceImpl.LoanDocManagementServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.hackathon.docmanagementservice.service.LoanDocManagementService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/loandocs")
@Log4j2
public class LoanDocManagementController {
    @Autowired
    LoanDocManagementServiceImpl loanDocManagementServiceImpl;

    @PostMapping("/upload/{customerId}/{loanId}")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("customerId") long customerId,
                                   @PathVariable("loanId") long loanId){
        LoanDocumentDetails loanDocumentDetails = null;
        loanDocumentDetails = loanDocManagementServiceImpl.saveFileData(file, customerId, loanId);
        log.info("*** Details are : ", loanDocumentDetails);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/loandocs/download/")
                .path(loanDocumentDetails.getFileId())
                .toUriString();

        return new ResponseData(loanDocumentDetails.getFileName(),
                downloadUrl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) throws Exception {
        log.info("File ID to download: ",fileId);
        LoanDocumentDetails loanDocumentDetails = loanDocManagementServiceImpl.getLoanDocuments(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loanDocumentDetails.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "loanDocumentDetails; filename=\""+ loanDocumentDetails.getFileName()
                +"\"")
                .body(new ByteArrayResource(loanDocumentDetails.getFileData()));
    }
}
