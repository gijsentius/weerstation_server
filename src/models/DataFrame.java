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

    public DataFrame(DataItem[] items) {
        this.size = items.length;
        this.items = new LinkedList<DataItem>();
        for (DataItem item : items) {
            addItem(item);
        }
    }

    public void addItem(DataItem item) {
        this.items.add(item);
        this.size++;
    }

    public int getSize() {
        return size;
    }

    public DataItem[] getItems() {
        return (DataItem[]) items.toArray();
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
