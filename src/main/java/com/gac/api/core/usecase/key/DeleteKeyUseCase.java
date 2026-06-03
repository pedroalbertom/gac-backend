package com.gac.api.core.usecase.key;

import com.gac.api.core.domain.ItemStatus;
import com.gac.api.core.domain.Key;
import com.gac.api.core.gateway.KeyGateway;

public class DeleteKeyUseCase {

    private final KeyGateway keyGateway;

    public DeleteKeyUseCase(KeyGateway keyGateway) {
        this.keyGateway = keyGateway;
    }

    public void execute(Long id) {
        Key key = keyGateway.findById(id)
                .orElseThrow(() -> new RuntimeException("Key not found for the given id."));

        if (key.getStatus() == ItemStatus.ON_LOAN) {
            throw new RuntimeException("Business rule: keys on loan cannot be removed.");
        }

        keyGateway.deleteById(id);
    }
}
