package de.projectmodding.core.component.event.event;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.model.event.FillItemEventModel;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;

import java.util.List;

public class FillItemListEvent extends AbstractEvent<FillItemEventModel> {


    public FillItemListEvent(FillItemEventModel dataModel) {
        super(dataModel);
    }
}
