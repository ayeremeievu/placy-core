package com.placy.placycore.core.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author a.yeremeiev@netconomy.net
 */
public abstract class AbstractSimpleMapper<FROM, TO> implements SimpleMapper<FROM, TO> {

    @Override
    public List<TO> mapAll(List<FROM> fromList) {
        if(fromList == null || fromList.isEmpty()) {
            return new ArrayList<>();
        }

        return fromList.stream()
            .map(this::map).collect(Collectors.toList());
    }
}
