package org.upriser.hitcsecs.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponse {
    private String fileName;
    private String downloadUrl;
    private String fileType;
    private Long fileSize;
}
