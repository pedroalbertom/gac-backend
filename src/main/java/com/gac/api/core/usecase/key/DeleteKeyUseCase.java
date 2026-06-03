package com.gac.api.core.usecase.key;

import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.exception.BusinessRuleException;
import com.gac.api.core.exception.NotFoundException;
import com.gac.api.core.gateway.KeyGateway;

public class DeleteKeyUseCase {

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
