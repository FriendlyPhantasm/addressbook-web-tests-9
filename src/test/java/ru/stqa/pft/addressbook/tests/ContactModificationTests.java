package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstname("Ivan").withMiddlename("Sergeevich")
                    .withLastname("Sozdanko").withNickname("Creatic").withCompany("Best Company").withTitle("World")
                    .withAddress("New York").withMobile("+79007773333"));
        }
    }

    @Test (enabled = true)
    public void testContactModification() {
        app.goTo().gotoHomePage();
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData().withContactId(before.get(index).getContactId()).withFirstname("Ivan")
                .withMiddlename("Sergeevich").withLastname("Modifikenko").withNickname("Dishisv").withCompany("Best Company")
                .withTitle("World").withAddress("New York").withMobile("+79007773333");
        app.contact().modify(index, contact);
        app.goTo().gotoHomePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getContactId(), c2.getContactId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);
    }
}
