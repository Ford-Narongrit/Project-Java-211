package project.neverLand.services.hardCodeSource;

import project.neverLand.models.Address;
import project.neverLand.models.AddressList;
import project.neverLand.models.Person;

public class AddressDataBase {
    public AddressList getAddressList(){
        AddressList addressList = new AddressList();

        Address a = new Address("1","5","15/11","big");
        a.addPersonToRoom(new Person("naruto","Uzumaki"));
        addressList.addAddress(a);

        a = new Address("2","2","22/01","small");
        a.addPersonToRoom(new Person("Kirigaya","Kasuto"));
        addressList.addAddress(a);

        a = new Address("1","2","12/08","big");
        a.addPersonToRoom(new Person("Moka","Aoba"));
        addressList.addAddress(a);

        a = new Address("2","4","24/03","big");
        a.addPersonToRoom(new Person("Hunter","Monter"));
        addressList.addAddress(a);

        return addressList;
    }

}
