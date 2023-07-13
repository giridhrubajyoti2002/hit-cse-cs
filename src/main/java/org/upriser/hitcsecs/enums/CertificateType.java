package org.upriser.hitcsecs.enums;

public enum CertificateType {
    MAR("MAR"),
    MOOCS("MOOCS")
    ;

    private final String certificateType;
    CertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    @Override
    public String toString() {
        return certificateType;
    }
}
