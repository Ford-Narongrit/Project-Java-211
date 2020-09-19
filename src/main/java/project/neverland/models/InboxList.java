package project.neverland.models;

import java.util.ArrayList;

public class InboxList {
    private ArrayList<Mail> inboxList;

    public InboxList() {
        inboxList = new ArrayList<>();
    }

    public void addInbox(Mail mail){
        inboxList.add(mail);
    }

    public ArrayList<Mail> toList() {
        return inboxList;
    }

    @Override
    public String toString() {
        return "InboxList{" +
                "inboxList=" + inboxList +
                '}';
    }
}
