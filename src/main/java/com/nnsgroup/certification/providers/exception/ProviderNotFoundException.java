package com.nnsgroup.certification.providers.exception;

import java.util.UUID;

public class ProviderNotFoundException extends RuntimeException {
    public ProviderNotFoundException(UUID id) {
        super("Provider not found: " + id);
    }
}
