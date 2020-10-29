package project.neverLand.models.sortBy;

import project.neverLand.models.Mail;

import java.util.Comparator;

public class DateSortLast implements Comparator<Mail> {
    @Override
    public int compare(Mail o1, Mail o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
