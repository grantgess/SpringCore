package org.example.models;


import javax.persistence.*;

@Entity
@Table(name="Item")
public class Item {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="item_name")
    private String itemName;
    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person owner;

    public Item() {}

    public Item(String item_name) {
        this.itemName = item_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getItem_name() {
        return itemName;
    }

    public void setItem_name(String item_name) {
        this.itemName = item_name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", item_name='" + itemName + '\'' +
                '}';
    }
}
