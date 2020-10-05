package project.neverLand.services.hardCodeSource;

import project.neverLand.models.*;
import project.neverLand.models.Package;

public class InboxDataBase {
    public InboxList getInboxData(){
        InboxList inboxList = new InboxList();

        Mail a = new Mail(new Person("sender1","A"),"177 ถนน งามวงศ์วาน (ปากซอยงามวงศ์วาน 58) ลาดยาว จตุจักร 10900",new Person("Sasuke","Ushiwa"),"แขวง คลองสิบสอง เขต หนองจอก กรุงเทพมหานคร 10530");
        inboxList.addInbox(a);

        a = new Mail(new Person("sender2","B"),"ตำบลบ้านใหม่ อำเภอปากเกร็ด นนทบุรี 11120",new Person("naruto","Uzumaki"),"ตำบล โป่งน้ำร้อน อำเภอคลองลาน กำแพงเพชร 62180");
        inboxList.addInbox(a);

        a = new Mail(new Person("sender3","C"),"เขต หนองจอก กรุงเทพมหานคร 10530",new Person("Moka","Aoba"),"อำเภอ บ้านม่วง สกลนคร 47140");
        inboxList.addInbox(a);

        a = new Mail(new Person("sender4","d"),"ตำบล วะตะแบก อำเภอ เทพสถิต ชัยภูมิ 36230",new Person("Kirigaya","Kasuto"),"แขวง คลองสิบสอง เขต หนองจอก กรุงเทพมหานคร 10530");
        inboxList.addInbox(a);

        a = new Package(new Person("sender5","e"), "แขวง หัวหมาก เขตบางกะปิ กรุงเทพมหานคร 10240", new Person("Hunter","Monter"), "40 ซอย รามคำแหง 43/1 แขวง พลับพลา เขตวังทองหลาง กรุงเทพมหานคร 10310", "kerry", "1231231234234");
        inboxList.addInbox(a);

        a = new Document(new Person("sender6","f"), "13-17 สุคนธสวัสดิ์ 17 แขวง ลาดพร้าว เขตลาดพร้าว กรุงเทพมหานคร 10230", new Person("Lee","sin"), "ซอยรามอินทรา 14 (มัยลาภ) แขวง จรเข้บัว จรเข้บัว กรุงเทพมหานคร 10230","4");
        inboxList.addInbox(a);
        return inboxList;
    }
}
