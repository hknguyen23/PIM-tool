package vn.elca.training.model.dto;

import java.time.LocalDate;

public class EmployeeDto extends AbstractEntityDto {
    private String visa;
    private String fullName;
    private LocalDate birthDate;

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String firstName, String lastName) {
        this.fullName = firstName + " " + lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
