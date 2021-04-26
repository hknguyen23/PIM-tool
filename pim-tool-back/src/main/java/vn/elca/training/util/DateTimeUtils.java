package vn.elca.training.util;

import java.util.ArrayList;
import java.util.List;

public class DateTimeUtils {
    public final static List<String> formats = new ArrayList<>();
    static {
        formats.add("dd/MM/yyyy");
        formats.add("yyyy-MM-dd");
    }
}
