package project.neverLand.models.sortBy;

import project.neverLand.models.Mail;

import java.util.Comparator;

public class RoomNumSortLower implements Comparator<Mail> {
    @Override
    public int compare(Mail o1, Mail o2) {
        return o2.getReceiverLocation().compareTo(o1.getReceiverLocation());
    }
}
