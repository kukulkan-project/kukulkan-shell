package mx.infotec.dads.kukulkan.shell.util;

import java.util.ArrayList;
import java.util.List;

public class CharSequenceCollector implements BufferCollector<List<CharSequence>> {

    List<CharSequence> charSequenceList = new ArrayList<>();

    @Override
    public void collect(String toCollect) {
        charSequenceList.add(toCollect);
    }

    @Override
    public List<CharSequence> getCollection() {
        return charSequenceList;
    }
}
