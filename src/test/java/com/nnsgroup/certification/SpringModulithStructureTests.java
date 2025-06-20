package com.nnsgroup.certification;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class SpringModulithStructureTests {

    @Test
    void shouldListAllModules() {
        ApplicationModules modules = ApplicationModules.of(CertificationRepositoryApplication.class);
        modules.forEach(module -> System.out.println("Detected module: " + module.getName()));
    }
}