package vn.elca.training.util;

import java.util.ArrayList;
import java.util.List;

public class DateTimeUtils {
    public final static List<String> FORMATS = new ArrayList<>();
    static {
        FORMATS.add("dd/MM/yyyy");
        FORMATS.add("yyyy-MM-dd");
    }
}
