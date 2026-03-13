package de.projectmodding.core.component.event.event;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.model.event.FillItemEventModel;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;

import java.util.List;

public class FillItemListEvent implements Event<FillItemEventModel> {

    private final FillItemEventModel fillItemEventModel;
    public FillItemListEvent(FillItemEventModel list) {
        fillItemEventModel = list;
    }

    @Override
    public FillItemEventModel getData() {
        return fillItemEventModel;
    }
}
