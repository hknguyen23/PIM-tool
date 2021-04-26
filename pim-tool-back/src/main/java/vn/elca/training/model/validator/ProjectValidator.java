package vn.elca.training.model.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import vn.elca.training.model.entity.Project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ProjectValidator implements Validator {
    @Value("${project.id.empty}")
    private String projectIdEmpty;

    @Value("${project.id.lessThan}")
    private String projectIdLessThan;

    @Override
    public boolean supports(Class clazz) {
        return Project.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project)o;
        Long id = project.getId();

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String finishingDate = project.getFinishingDate().format(dtf);

//        if (id < 2) {
//            errors.rejectValue("id", String.format(projectIdLessThan, 2));
//        }

        ValidationUtils.rejectIfEmpty(errors, "id", projectIdEmpty);
//        ValidationUtils.rejectIfEmpty(errors, "name", "${project.name.empty}");
//        ValidationUtils.rejectIfEmpty(errors, "finishingDate", "${project.finishingDate.empty}");
//        ValidationUtils.rejectIfEmpty(errors, "customer", "${project.customer.empty}");
    }


}
