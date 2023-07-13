package org.upriser.hitcsecs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.upriser.hitcsecs.entity.Certificate;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;
import org.upriser.hitcsecs.error.CustomException;
import org.upriser.hitcsecs.service.CertificateService;
import org.upriser.hitcsecs.utils.CertificateResponse;

import java.io.IOException;
import java.util.List;

@RestController
public class CertificateController {

    private final CertificateService certificateService;
    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping(value = "/add-certificate")
    public ResponseEntity<?> addCertificate(
            @RequestParam("studentId") String studentId,
            @RequestParam("certificateType") CertificateType certificateType,
            @RequestParam("activityOrCourseId") String activityOrCourseId,
            @RequestParam("file") MultipartFile file
    ) throws IOException, CustomException {

        if(certificateType!=CertificateType.MAR && certificateType!=CertificateType.MOOCS){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Certificate type should be either 'MAR' or 'MOOCS'.");
        }

        Certificate certificate = certificateService.addCertificate(
                studentId, certificateType, activityOrCourseId, file);

        CertificateResponse certificateResponse = new CertificateResponse(
                file.getOriginalFilename(),
                certificate.getDownloadUrl(),
                file.getContentType(),
                file.getSize()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(certificateResponse);
    }

    @DeleteMapping("/delete-certificate")
    public ResponseEntity<?> deleteCertificate(@RequestParam("certificateId") String certificateId) throws CustomException {
        certificateService.deleteCertificate(certificateId);
        return  ResponseEntity.ok()
                .body("Certificate successfully deleted with id: "+certificateId);
    }

    @PostMapping("/verify-certificate")
    public ResponseEntity<?> verifyCertificate(@RequestParam("certificateId") String certificateId) throws CustomException {
        certificateService.verifyCertificate(certificateId);
        return  ResponseEntity.ok()
                .body("Certificate successfully verified with id: "+certificateId);
    }

    @GetMapping("/download-certificate/{certificateId}")
    public ResponseEntity<?> downloadCertificate(@PathVariable("certificateId") String certificateId) throws Exception {
        Certificate certificate = certificateService.fetchCertificate(certificateId);
        return  ResponseEntity.ok()
                .contentType(MediaType.valueOf(certificate.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + certificate.getFileName()
                                + "\"")
                .body(new ByteArrayResource(certificate.getData()));
    }

    @GetMapping("/fetch-certificate")
    public ResponseEntity<?> fetchCertificate(@RequestParam("certificateId") String certificateId) throws CustomException {
        Certificate certificate = certificateService.fetchCertificate(certificateId);
        return  ResponseEntity.ok()
                .body(certificate);
    }

    @GetMapping("/fetch-certificate-by-year")
    public ResponseEntity<?> fetchCertificatesByYear(
            @RequestParam("studentId") String studentId,
            @RequestParam("yearType") YearType yearType) throws CustomException {
        List<Certificate> certificateList = certificateService.fetchCertificatesByYear(
                studentId, yearType);
        return  ResponseEntity.ok()
                .body(certificateList);
    }

    @GetMapping("/fetch-certificate-by-mar-moocs")
    public ResponseEntity<?> fetchCertificatesByMarMoocs(
            @RequestParam("studentId") String studentId,
            @RequestParam("certificateType") CertificateType certificateType) throws CustomException {
        List<Certificate> certificateList = certificateService.fetchCertificatesByMarMoocs(
                studentId, certificateType);
        return  ResponseEntity.ok()
                .body(certificateList);
    }

    @GetMapping("/fetch-certificate-by-year-and-mar-moocs")
    public ResponseEntity<?> fetchCertificatesByYearAndMarMoocs(
            @RequestParam("studentId") String studentId,
            @RequestParam("yearType") YearType yearType,
            @RequestParam("certificateType") CertificateType certificateType
    ) throws CustomException {
        List<Certificate> certificateList = certificateService.fetchCertificatesByYearAndMarMoocs(
                studentId, yearType, certificateType);
        return  ResponseEntity.ok()
                .body(certificateList);
    }


}
