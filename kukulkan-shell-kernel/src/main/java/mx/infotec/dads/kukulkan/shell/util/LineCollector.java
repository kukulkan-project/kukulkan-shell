package mx.infotec.dads.kukulkan.shell.util;

import java.util.ArrayList;
import java.util.List;

import mx.infotec.dads.kukulkan.shell.domain.Line;

public class LineCollector implements BufferCollector<List<Line>> {

    private List<Line> lines = new ArrayList<>();

    private LineValuedProcessor processor;

    public LineCollector(LineValuedProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void collect(String toCollect) {
        processor.process(toCollect).ifPresent(lines::add);
    }

    @Override
    public List<Line> getCollection() {
        return lines;
    }
}
