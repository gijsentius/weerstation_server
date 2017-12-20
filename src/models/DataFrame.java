package models;

import interfaces.DataItem;

import java.util.Iterator;
import java.util.LinkedList;

public class DataFrame implements Iterable{
    private int size;
    private LinkedList items;
    /**
     * Class resembles a piece of data which can be as big as the user needs it to be.
     */
    public DataFrame() {
        this.size = 0;
        this.items = new LinkedList<DataItem>();
    }

    public DataFrame(LinkedList<DataItem> items) {
        this.items = items;
        this.size = items.size();  //Check this way of reading the size
    }

    public void addItem(DataItem item) {
        this.items.add(item);
        this.size++;
    }

    public int getSize() {
        return size;
    }

    public LinkedList<DataItem> getItems() {
        return items;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            private final Iterator list = items.iterator();

            @Override
            public boolean hasNext() {
                return list.hasNext();
            }

            @Override
            public Object next() {
                return list.next();
            }
        };
    }
}
