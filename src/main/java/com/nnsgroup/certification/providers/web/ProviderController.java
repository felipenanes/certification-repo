package com.nnsgroup.certification.providers.web;

import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.providers.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping("/{providerId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Provider getProvider(@PathVariable UUID providerId) {
        return providerService.getById(providerId);
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public Provider createProvider(@RequestBody Provider provider) {
        return providerService.create(provider);
    }
}