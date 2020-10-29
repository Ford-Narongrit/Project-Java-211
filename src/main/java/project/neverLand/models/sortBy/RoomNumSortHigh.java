package project.neverLand.models.sortBy;

import project.neverLand.models.Mail;

import java.util.Comparator;

public class RoomNumSortHigh implements Comparator<Mail> {
    @Override
    public int compare(Mail o1, Mail o2) {
        return o1.getReceiverLocation().compareTo(o2.getReceiverLocation());
    }
}
