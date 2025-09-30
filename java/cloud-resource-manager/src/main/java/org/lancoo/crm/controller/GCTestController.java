package org.lancoo.crm.controller;

import org.lancoo.crm.domain.JVMInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gc")
public class GCTestController {

    @GetMapping("/V1.0/ygc")
    public void testYgc() {
        byte[] array1 = new byte[1024 * 1024];
        array1 = new byte[1024 * 1024];
        array1 = new byte[1024 * 1024];
        array1 = null;
        byte[] array2 = new byte[2 * 1024 * 1024];
    }

    @GetMapping("/current")
    public JVMInfo getJVMInfo() {
        return JVMInfoExample.getJVMInfo();
    }
}
