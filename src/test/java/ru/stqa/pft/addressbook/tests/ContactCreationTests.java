package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test (enabled = true)
    public void testContactCreation() throws Exception {
        app.goTo().gotoHomePage();
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData().withFirstname("Ivan").withLastname("Dishenko");
        app.contact().create(contact);
        app.goTo().gotoHomePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getContactId(), c2.getContactId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }

}
