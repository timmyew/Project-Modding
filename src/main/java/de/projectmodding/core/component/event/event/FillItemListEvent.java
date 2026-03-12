package de.projectmodding.core.component.event.event;

import de.projectmodding.core.component.event.Event;

import java.util.List;

public class FillItemListEvent implements Event<List<String>> {

    private final List<String> listData;
    public FillItemListEvent(List<String> list) {
        listData = list;
    }

    @Override
    public List<String> getData() {
        return listData;
    }
}
