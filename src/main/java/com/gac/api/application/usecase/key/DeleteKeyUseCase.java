package com.gac.api.application.usecase.key;

import com.gac.api.application.port.in.key.DeleteKeyInputPort;

import com.gac.api.domain.model.ItemStatus;
import com.gac.api.domain.model.Key;
import com.gac.api.domain.exception.BusinessRuleException;
import com.gac.api.domain.exception.NotFoundException;
import com.gac.api.domain.port.KeyGateway;

public class DeleteKeyUseCase implements DeleteKeyInputPort {

    private final KeyGateway keyGateway;

    public DeleteKeyUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public void execute(Long id) {
        Key key = keyGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Key not found."));

        if (key.getStatus() == ItemStatus.ON_LOAN || key.getStatus() == ItemStatus.RESERVED) {
            throw new BusinessRuleException("Reserved or on-loan keys cannot be removed.");
        }

        keyGateway.deleteById(id);
    }
}
