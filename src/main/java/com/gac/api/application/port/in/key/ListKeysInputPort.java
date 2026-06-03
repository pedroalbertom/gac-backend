package com.gac.api.application.port.in.key;

import com.gac.api.domain.model.Key;
import java.util.List;

public interface ListKeysInputPort {
    List<Key> execute();
}
