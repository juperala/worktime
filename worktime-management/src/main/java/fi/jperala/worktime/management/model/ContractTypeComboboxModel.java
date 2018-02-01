package fi.jperala.worktime.management.model;

import fi.jperala.worktime.model.AbstractWorktimeComboboxModel;
import fi.jperala.worktime.jpa.domain.ContractType;

/**
 * Combobox model for ContractType enum.
 */
public class ContractTypeComboboxModel extends AbstractWorktimeComboboxModel<ContractType> {

    public int getSize() {
        return ContractType.values().length;
    }

    public ContractType getElementAt(int index) {
        return ContractType.values()[index];
    }
}
