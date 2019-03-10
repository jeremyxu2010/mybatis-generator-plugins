package personal.jeremyxu2010.mybatis.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @Description:
 * @Author: jeremyxu
 * @Created Date: 2017/3/20
 * @Created Time: 9:31
 * @Version:1.0
 */
public class ModelExampleBuilderPlugin extends PluginAdapter {
    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        for (Method method : topLevelClass.getMethods()) {
            if("createCriteriaInternal".equals(method.getName())){
                method.getBodyLines().clear();
                method.addBodyLine("Criteria criteria = new Criteria(this);"); //$NON-NLS-1$
                method.addBodyLine("return criteria;"); //$NON-NLS-1$
            }
        }
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {
            if(new FullyQualifiedJavaType("Criteria").equals(innerClass.getType())){
                Field filed = new Field("topCriteria", topLevelClass.getType());
                filed.setVisibility(JavaVisibility.PRIVATE);
                innerClass.addField(filed);

                Method constructMethod = new Method();
                constructMethod.setVisibility(JavaVisibility.PROTECTED);
                constructMethod.setName("Criteria"); //$NON-NLS-1$
                constructMethod.addParameter(new Parameter(topLevelClass.getType(), "topCriteria"));
                constructMethod.setConstructor(true);
                constructMethod.addBodyLine("super();"); //$NON-NLS-1$
                constructMethod.addBodyLine("this.topCriteria = topCriteria;"); //$NON-NLS-1$
                innerClass.addMethod(constructMethod);

                Method getMethod = new Method();
                getMethod.setVisibility(JavaVisibility.PUBLIC);
                getMethod.setName("getTopCriteria"); //$NON-NLS-1$
                getMethod.setReturnType(topLevelClass.getType());
                getMethod.setConstructor(false);
                getMethod.addBodyLine("return this.topCriteria;"); //$NON-NLS-1$
                innerClass.addMethod(getMethod);
            }
        }
        return true;
    }
}
