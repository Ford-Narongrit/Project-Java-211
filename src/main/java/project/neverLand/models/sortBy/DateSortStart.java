package project.neverLand.models.sortBy;

import project.neverLand.models.Mail;

import java.util.Comparator;

public class DateSortStart implements Comparator<Mail> {
    @Override
    public int compare(Mail o1, Mail o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
