import java.util.Iterator;
import java.util.NoSuchElementException;

public class OHRequest {
    public String description;
    public String name;
    public OHRequest next;

    public OHRequest(String description, String name, OHRequest next) {
        this.description = description;
        this.name = name;
        this.next = next;
    }
}

class OHIterator implements Iterator<OHRequest> {
    OHRequest curr;

    public OHIterator(OHRequest queue) {
        curr = queue;
    }

    public boolean isGood(String description) {
        return true;
    }


    @Override
    public boolean hasNext() {
        while (curr != null && !isGood(curr.description)) {
            curr = curr.next;
        }
        return curr != null;
    }

    @Override
    public OHRequest next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        OHRequest currRequest = curr;
        curr = curr.next;
        return currRequest;
    }
}

class OHQueue implements Iterable<OHRequest> {
    OHRequest queue;

    public OHQueue(OHRequest queue) {
        this.queue = queue;
    }

    @Override
    public Iterator<OHRequest> iterator() {
        return new OHIterator(queue);
    }
}

class TYIterator extends OHIterator {
    public TYIterator(OHRequest queue) {
        super(queue);
    }

    @Override
    public OHRequest next() {
        OHRequest result = super.next();
        if (result.description.contains("thank u")) {
            result = super.next();
        }
        return result;
    }
}