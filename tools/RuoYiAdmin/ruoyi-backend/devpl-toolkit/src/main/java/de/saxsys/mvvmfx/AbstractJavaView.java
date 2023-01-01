package de.saxsys.mvvmfx;

import org.openjdk.jol.vm.VM;

public abstract class AbstractJavaView<T extends ViewModel> implements JavaView<T> {

    @InjectViewModel
    private VM vm;

    public final VM vm() {
        return this.vm;
    }
}
