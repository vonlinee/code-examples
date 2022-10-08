package codegen.xml.render;

import codegen.xml.DocTypeVisitor;
import codegen.xml.PublicDocType;
import codegen.xml.SystemDocType;

public class DocTypeRenderer implements DocTypeVisitor<String> {

    @Override
    public String visit(PublicDocType docType) {
        return "PUBLIC \"" //$NON-NLS-1$
                + docType.getDtdName()
                + "\" \"" //$NON-NLS-1$
                + docType.getDtdLocation()
                + "\""; //$NON-NLS-1$
    }

    @Override
    public String visit(SystemDocType docType) {
        return "SYSTEM \"" //$NON-NLS-1$
                + docType.getDtdLocation()
                + "\""; //$NON-NLS-1$
    }
}
