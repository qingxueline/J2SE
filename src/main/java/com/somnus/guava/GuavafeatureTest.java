package com.somnus.guava;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.net.InternetDomainName;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedInts;
import com.google.common.reflect.ClassPath;
import org.junit.Test;

/**
 * Created by lyl on 2018/3/22 0022.
 */
public class GuavaFeatureTest {

    @Test
    public void dataTest() {
        //UnsignedInts与UnsignedLongs还支持compare, divide, min, max等方法。
        int notReallyInt = UnsignedInts.parseUnsignedInt("4294967295");
        System.out.println("notReallyInt: " + notReallyInt);

        String maxUnsigned = UnsignedInts.toString(notReallyInt); // We’re legit!
        System.out.println("maxUnsigned:" + maxUnsigned);

        //包装类型
        UnsignedInteger newType = UnsignedInteger.valueOf(maxUnsigned);
        newType = newType.plus(UnsignedInteger.valueOf("1")); // Increment
        System.out.println("newType:" + newType);


        /***
         * <p>
         *
         *
         *  关于域名有几个概念是比较容易混淆的：publicSuffix()返回的是对应着公共后缀列表中的独立实体的顶级域名。
         *  因此返回的可能会有co.uk, .com, .cool这样的结果（没错，.cool是一个真实的后缀，比如javais.cool, scalais.cool以及cppis.cool）。
         *  而topPrivateDomain()，这是对应公共后缀列表的一个独立实体的私有域名。在blog.takipi.com上调用这个方法会返回takipi.com，
         *  但如果你把它用于某个github主页，比如username.github.io的话则会返回username.github.io，因为这在PSL上是一个单独的实体。
         *  当你需要校验域名的时候这个功能就派上用场了，比如我们最近给将JIRA集成进Takipi的时候，首先我们要检查你的JIRA域名，然后才能连接到Takipi的生产环境的错误分析工具中。
         *  <p/>
         */
        InternetDomainName owner = InternetDomainName.from("blog.takipi.com").topPrivateDomain();

        System.out.println("owner: " + owner);// returns takipi.com

        //判断是否是有效果域名
        System.out.println("isValid：" + InternetDomainName.isValid("takipi.monsters")); // returns false


//        ClassPath classpath = ClassPath.from(classloader);
//        for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses("com.mycomp.mypackage")) {
//            System.out.println(classInfo.getName());
//        }


    }

}
