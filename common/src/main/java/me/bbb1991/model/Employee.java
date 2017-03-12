package me.bbb1991.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@XmlRootElement
public class Employee implements Serializable {

    private String surname;

    private String firstname;

    private Integer age;

    private String address;

    private Position position;

    public Employee(String surname, String firstname, Integer age, String address, Position position) {
        this.surname = surname;
        this.firstname = firstname;
        this.age = age;
        this.address = address;
        this.position = position;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", position=" + position +
                '}';
    }
}