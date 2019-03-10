package personal.jeremyxu2010.mybatis.plugins;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;

/**
 * @Description:
 * @Author: jeremyxu
 * @Created Date: 2017/3/4
 * @Created Time: 19:29
 * @Version:1.0
 */
public class RenameProperties {

    private static final String DOT = ".";

    private boolean enabled;

    private String subPpackage;
    private String classSuffix;

    private String originalType;
    private String newType;

    public void validate(String theSubPpackage, String theClassSuffix) {
        enabled = theSubPpackage != null || theClassSuffix != null;

        if (enabled) {
            if (theSubPpackage == null) {
                subPpackage = "";
            } else if (!theSubPpackage.startsWith(DOT)) {
                subPpackage = DOT + theSubPpackage;
            } else {
                subPpackage = theSubPpackage;
            }

            classSuffix = theClassSuffix == null ? "" : theClassSuffix;
        }
    }

    public String setTypes(String theOriginalType) {
        if (enabled) {
            this.originalType = theOriginalType;
            int lastDot = originalType.lastIndexOf(DOT);
            newType = originalType.substring(0, lastDot) + subPpackage + originalType.substring(lastDot) + classSuffix;
            return newType;
        }

        return theOriginalType;
    }

    public FullyQualifiedJavaType renameType(FullyQualifiedJavaType theJavaType) {
        if (enabled && theJavaType.getFullyQualifiedName().contains(newType)) {
            return new FullyQualifiedJavaType(theJavaType.getFullyQualifiedName().replace(newType, originalType));
        } else {
            return theJavaType;
        }
    }

    public Attribute renameAttribute(Attribute attribute) {
        if (newType != null && newType.equals(attribute.getValue())) {
            return new Attribute(attribute.getName(), originalType);
        } else {
            return attribute;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSubPpackage() {
        return subPpackage;
    }

    public void setSubPpackage(String subPpackage) {
        this.subPpackage = subPpackage;
    }

    public String getClassSuffix() {
        return classSuffix;
    }

    public void setClassSuffix(String classSuffix) {
        this.classSuffix = classSuffix;
    }

    public String getOriginalType() {
        return originalType;
    }

    public void setOriginalType(String originalType) {
        this.originalType = originalType;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }
}
