package com.gac.api.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Movement {

    private Long id;
    private MovementType type;
    private MovementStatus status;
    private String professorRegistrationNumber;
    private Long attendantId;
    private AssetType assetType;
    private Long assetId;
    private String confirmationCode;
    private String academicPurpose;
    private String room;
    private String defectDescription;
    private LocalDateTime checkedOutAt;
    private LocalDateTime returnedAt;
    private LocalDateTime createdAt;
    private List<String> loanedAccessories = new ArrayList<>();
    private List<String> returnedAccessories = new ArrayList<>();

    public Movement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public MovementStatus getStatus() {
        return status;
    }

    public void setStatus(MovementStatus status) {
        this.status = status;
    }

    public String getProfessorRegistrationNumber() {
        return professorRegistrationNumber;
    }

    public void setProfessorRegistrationNumber(String professorRegistrationNumber) {
        this.professorRegistrationNumber = professorRegistrationNumber;
    }

    public Long getAttendantId() {
        return attendantId;
    }

    public void setAttendantId(Long attendantId) {
        this.attendantId = attendantId;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getAcademicPurpose() {
        return academicPurpose;
    }

    public void setAcademicPurpose(String academicPurpose) {
        this.academicPurpose = academicPurpose;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }

    public LocalDateTime getCheckedOutAt() {
        return checkedOutAt;
    }

    public void setCheckedOutAt(LocalDateTime checkedOutAt) {
        this.checkedOutAt = checkedOutAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getLoanedAccessories() {
        return loanedAccessories;
    }

    public void setLoanedAccessories(List<String> loanedAccessories) {
        this.loanedAccessories = loanedAccessories != null ? loanedAccessories : new ArrayList<>();
    }

    public List<String> getReturnedAccessories() {
        return returnedAccessories;
    }

    public void setReturnedAccessories(List<String> returnedAccessories) {
        this.returnedAccessories = returnedAccessories != null ? returnedAccessories : new ArrayList<>();
    }

    public LocalDateTime eventAt() {
        if (returnedAt != null) {
            return returnedAt;
        }
        if (checkedOutAt != null) {
            return checkedOutAt;
        }
        return createdAt;
    }
}
