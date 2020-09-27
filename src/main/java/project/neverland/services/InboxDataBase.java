package project.neverland.services;

import project.neverland.models.InboxList;
import project.neverland.models.Mail;
import project.neverland.models.Person;

import java.util.ArrayList;

public class InboxDataBase {
    public InboxList getInboxData(){
        InboxList inboxList = new InboxList();
        Mail a = new Mail(new Person("ford","za"),"111/751 ",new Person("a","b"),"School","S");
        inboxList.addInbox(a);
        a = new Mail(new Person("jui","oooo"),"kaset",new Person("a","b"),"barmai","M");
        inboxList.addInbox(a);
        a = new Mail(new Person("Dommm","za"),"bodin",new Person("a","b"),"ssss","L");
        inboxList.addInbox(a);
        a = new Mail(new Person("pora","ss"),"bodin",new Person("aeee","brrr"),"ssss","L");
        inboxList.addInbox(a);

        return inboxList;
    }
}
