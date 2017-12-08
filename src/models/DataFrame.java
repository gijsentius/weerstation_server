package models;

import interfaces.DBItem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DataFrame implements Iterable{
    private int size;
    private LinkedList items;
    /**
     * Class resembles a piece of data which can be as big as the user needs it to be.
     */
    public DataFrame() {
        this.size = 0;
    }

    public DataFrame(DBItem[] items) {
        this.size = items.length;
        this.items = new LinkedList<DBItem>();
        for (DBItem item : items) {
            addItem(item);
        }
    }

    public void addItem(DBItem item) {
        this.items.add(item);
        this.size++;
    }

    public int getSize() {
        return size;
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
