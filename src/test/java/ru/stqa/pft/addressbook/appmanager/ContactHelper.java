package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }
    public void submitAddContact() {
      click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
      type(By.name("firstname"),contactData.getFirstname());
      type(By.name("middlename"),contactData.getMiddlename());
      type(By.name("lastname"),contactData.getLastname());
      type(By.name("nickname"),contactData.getNickname());
      type(By.name("title"),contactData.getTitle());
      type(By.name("company"),contactData.getCompany());
      type(By.name("address"),contactData.getAddress());
      type(By.name("mobile"),contactData.getMobile());

      if (creation)
        {
            Select groupSelection = new Select(wd.findElement(By.name("new_group")));
            if (groupSelection.isMultiple())
                new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    }

    public void initAddContact() {
      click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        click(By.xpath("//table[@id='maintable']/tbody/tr[" +  String.valueOf(index) + "]/td[8]/a/img"));
        //click((By.tagName("input")).toString(index));
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void updateContact() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void create(ContactData contact) {
        initAddContact();
        fillContactForm(contact, true);
        submitAddContact();
    }

    public void modify(int index, ContactData contact) {
        selectContact(index + 2);
        fillContactForm(contact, false);
        updateContact();
    }

    public void delete(int index) {
        selectContact(index);
        deleteContact();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//img[@alt='Edit']"));
    };

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.xpath("//table/tbody/tr"));
        int i = 2;
        for (WebElement element : elements.subList(1, elements.size())) {
            int id = Integer.parseInt(element.findElement(By.xpath("//tr[" + String.valueOf(i) + "]/td[1]/input")).getAttribute("value"));
            String lastName = element.findElement(By.xpath("//tr[" + String.valueOf(i) + "]/td[2]")).getText();
            String firstName = element.findElement(By.xpath("//tr[" + String.valueOf(i) + "]/td[3]")).getText();
            ContactData contact = new ContactData().withContactId(id).withFirstname(firstName).withLastname(lastName);
            contacts.add(contact);
            i++;
        }
        return contacts;
    }
}
