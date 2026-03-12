package de.projectmodding.core.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefinitionLoaderContext {
    String path;
    String prevAttributeName;
}
