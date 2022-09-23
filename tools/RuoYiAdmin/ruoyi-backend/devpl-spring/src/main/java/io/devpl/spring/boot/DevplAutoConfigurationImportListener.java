package io.devpl.spring.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;
import org.springframework.boot.autoconfigure.AutoConfigurationImportListener;

import java.util.List;
import java.util.Set;

@Slf4j
public class DevplAutoConfigurationImportListener implements AutoConfigurationImportListener {
    @Override
    public void onAutoConfigurationImportEvent(AutoConfigurationImportEvent event) {
        List<String> candidateConfigurations = event.getCandidateConfigurations();
        Set<String> exclusions = event.getExclusions();
        Object source = event.getSource();

        log.info("DevplAutoConfigurationImportListener => {}", event.getSource());
    }
}
