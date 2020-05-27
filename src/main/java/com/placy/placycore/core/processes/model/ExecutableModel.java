package com.placy.placycore.core.processes.model;

import java.util.Date;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface ExecutableModel {
    Date getStartDate();

    void setStartDate(Date startDate);

    Date getFinishDate();

    void setFinishDate(Date finishDate);
}
