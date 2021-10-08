package com.marchsoft.secondkill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.marchsoft.secondkill.common.util.StringUtils;

import java.util.Scanner;

public class MysqlGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java")
                .setAuthor("Wangmingcan")
                .setOpen(false)
                .setIdType(IdType.AUTO)  //主键策略
                .setFileOverride(false) // 文件覆盖
                .setActiveRecord(true)  //设置支持ActiveRecord   继承Model
                .setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/study?useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("12345");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.marchsoft.secondkill");
        mpg.setPackageInfo(pc);

        //配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略（下划线处理）
                .setColumnNaming(NamingStrategy.underline_to_camel)  // 数据库字段映射到实体的命名策略（下划线处理）
                .setCapitalMode(true)  // 全局大写命名
                .setEntityLombokModel(true)
                .setRestControllerStyle(true);

        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplate(templateConfig);
        mpg.execute();
    }
}
