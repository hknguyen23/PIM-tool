package vn.elca.training.model.enumerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProjectStatuses {
    @Value("${en.NEW}")
    NEW("NEW", "New"),

    @Value("${en.PLA}")
    PLA("PLA", "Planned"),

    @Value("${en.INP}")
    INP("INP", "In Process"),

    @Value("${en.FIN}")
    FIN("FIN", "Finished");

    public final String value;
    public final String title;

    ProjectStatuses(String value, String title) {
        this.value = value;
        this.title = title;
    }

}
