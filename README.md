# In all

Use version 1.0.4.

# mybatis-generator-plugins
这是一个将工作中很有用的mybatis generator插件打包到一起的项目。

这些插件来自于[dcendents's maven plugin](https://github.com/dcendents/mybatis-generator-plugins) 和 [叉烧包's maven plugin](https://github.com/wucao/mybatis-generator-limit-plugin)

## 用法

Add the plugin `<plugin></plugin>` into MyBatis Generator configuration file.

```
<generatorConfiguration>
    <context id="default" targetRuntime="MyBatis3">
    	<plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin" />
		<plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
		<plugin type="org.mybatis.generator.plugins.RenameExampleClassPlugin">
			<property name="searchString" value="Example$" />
			<property name="replaceString" value="Criteria" />
		</plugin>
		<plugin type="personal.jeremyxu2010.mybatis.plugins.RenameExampleClassAndMethodsPlugin">
			<property name="classMethodSearchString" value="Example" />
			<property name="classMethodReplaceString" value="Criteria" />
			<property name="parameterSearchString" value="example" />
			<property name="parameterReplaceString" value="criteria" />
		</plugin>
		<plugin type="personal.jeremyxu2010.mybatis.plugins.MySQLLimitPlugin" />
		<plugin type="personal.jeremyxu2010.mybatis.plugins.CreateSubPackagePlugin">
                <property name="exampleSubPackage" value="filters" />
                <property name="exampleClassSuffix" value="" />
		</plugin>
		<plugin type="personal.jeremyxu2010.mybatis.plugins.ModelExampleBuilderPlugin"></plugin>
    	...
    </context>
</generatorConfiguration>
```

### Running with Maven

pom.xml


```
<build>
	<plugins>
		<plugin>
			<groupId>org.mybatis.generator</groupId>
			<artifactId>mybatis-generator-maven-plugin</artifactId>
			<version>1.3.5</version>
			<dependencies>
				<dependency>
					<groupId>mysql</groupId>
					<artifactId>mysql-connector-java</artifactId>
					<version>5.1.38</version>
				</dependency>
				<dependencies>
					<dependency>
						<groupId>personal.jeremyxu2010</groupId>
						<artifactId>mybatis-generator-plugins</artifactId>
						<version>1.0.4</version>
					</dependency>
				</dependencies>
			</dependencies>
			<configuration>
				<overwrite>true</overwrite>
			</configuration>
		</plugin>
	</plugins>
</build>
```

Run `mvn mybatis-generator:generator` to generate java and xml files.

### Using the Generated Objects

```
XxxCriteria criteria = new XxxCriteria();
...
criteria.setLimit(10); // page size limit
criteria.setOffset(20); // offset
List<Xxx> list = xxxMapper.selectByCriteria(criteria);
```

If you use xml mapper, the SQL will be:
`select ... limit 20, 10`

If you use annotation mapper, the SQL will be:
`select ... limit 10 offset 20`


```
XxxCriteria criteria = new XxxCriteria();
...
criteria.setLimit(10); // limit
List<Xxx> list = xxxMapper.selectByCriteria(criteria);
```
If you use xml mapper, the SQL will be:
`select ... limit 10`

If you use annotation mapper, the SQL will be:
`select ... limit 10`
