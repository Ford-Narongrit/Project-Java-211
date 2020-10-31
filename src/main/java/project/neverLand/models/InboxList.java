package project.neverLand.models;

import java.util.ArrayList;
import java.util.Comparator;

public class InboxList {
    private ArrayList<Mail> inboxList;

    public InboxList() {
        inboxList = new ArrayList<>();
    }

    public void addInbox(Mail mail) {
        inboxList.add(mail);
    }

    public ArrayList<Mail> toList() {
        return inboxList;
    }

    public ArrayList<Mail> toNotReceivedList() {
        ArrayList<Mail> notReceivedList = new ArrayList<>();
        for (Mail mail : inboxList) {
            if (!mail.isReceived()) {
                notReceivedList.add(mail);
            }
        }
        return notReceivedList;
    }

    public ArrayList<Mail> toPersonList(Person person) {
        ArrayList<Mail> personList = new ArrayList<>();
        for (Mail mail : inboxList) {
            if (mail.getReceiver().isThisPerson(person)) {
                personList.add(mail);
            }
        }
        return personList;
    }


    public ArrayList<Mail> toRoomNumber(String roomNumber) {
        ArrayList<Mail> inboxList = new ArrayList<>();
        for (Mail mail : toNotReceivedList()) {
            if (mail.getReceiverLocation().contains(roomNumber)) {
                inboxList.add(mail);
            }
        }
        return inboxList;
    }

    public void sortBy(Comparator comparator) {
        inboxList.sort(comparator);
    }

}
