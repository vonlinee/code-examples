package io.devpl.sdk.api.service.impl;

import io.devpl.sdk.api.entity.Model;
import io.devpl.sdk.api.service.InternalConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalConfigServiceImpl implements InternalConfigService {

    @Autowired
    Model model;
}
