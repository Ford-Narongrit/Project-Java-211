package project.neverland.models;

import java.util.ArrayList;

public class  InboxList {
    private ArrayList<Mail> inboxList;

    public InboxList() {
        inboxList = new ArrayList<>();
    }

    public void addInbox(Mail mail){
        inboxList.add(mail);
    }
    public void removeInbox(Mail mail){
        inboxList.remove(mail);
    }

    public ArrayList<Mail> toList() {
        return inboxList;
    }

    public ArrayList<Mail> toNotReceivedList(){
        ArrayList<Mail> notReceivedList = new ArrayList<>();
        for(Mail mail: inboxList){
            if(!mail.isReceived()){
                notReceivedList.add(mail);
            }
        }
        return notReceivedList;
    }

}
