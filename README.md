# mybatis项目构建步骤
1. 配置数据库连接信息(db.properties)
   
    ```java
    jdbc.driverName=com.mysql.jdbc.Driver
    jdbc.url=jdbc:mysql://localhost:3307/elm
    jdbc.user=root
    jdbc.password=root
    ```
    
2. 配置mybatis核心配置文件(SqlMapConfig.xml)

    + 加载属性文件

    ```java
    <properties resource="db.properties"></properties>
    ```

    + 配置类的别名

    ```java
        <typeAliases>
    		<!--配置单个类名的别名-->
            <typeAlias type="com.ctgu.pojo.User" alias="User"></typeAlias>
    		<!-- 表示包下所有的类的全限定类名的别名就是类名-->
            <package name="com.ctgu.pojo"/>
        </typeAliases>
    ```

    + 配置mybatis环境

    ```java
    <!--    mybatis中的环境配置，和spring整合后，该配置就被弃用-->
        <environments default="development">
            <environment id="development">
                <transactionManager type="JDBC"></transactionManager>
                <dataSource type="POOLED">
    				 <!--可以写成静态的，也可以引用db.properties中配置的值-->
        			<!--使用${字段名}引用-->
                    <property name="driver" value="${jdbc.driverName}"/>
                    <property name="url" value="${jdbc.url}"/>
                    <property name="username" value="${jdbc.user}"/>
                    <property name="password" value="${jdbc.password}"/>
                </dataSource>
            </environment>
        </environments>
    ```

    + 加载Mapper文件

    ```java
        <mappers>
    			<!-- 可以自己配mapper文件的包路径，那么在pom.xml文件中必须指定xml所在资源路径也可以直接配置mapper文件所在具体位置，但对多mapper文件不利，此种方法要求mapper接口称和mapper映射文件名称相同，且放在同一个目录中。-->
    		<!-- <package name="com/ctgu/dao"/>-->
            <mapper resource="com/ctgu/dao/UserDaoMapper.xml"></mapper>
        </mappers>
            
    ```

3. 编写Bean实体类

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userId;
    private String password;
    private String userName;
    private int userSex;
    private int delTag;

}
由于我使用了lombook插件，因此可以省略get/set方法。此处应该额外注意变量名与数据库字段保持相同，如果不相同，则需要使用resultMap。
    例如：
    <!-- resultMap最终还是要将结果映射到pojo上，type就是指定映射到哪一个pojo -->
    <!-- id：设置ResultMap的id -->
    <resultMap type="User" id="userMap">
        <!-- 定义主键 ,非常重要。如果是多个字段,则定义多个id -->
        <!-- property：主键在pojo中的属性名 -->
        <!-- column：主键在数据库中的列名 -->
        <id property="id" column="userId" />

        <!-- 定义普通属性 -->
        <result property="password" column="password" />
        <result property="userName" column="userName" />
        <result property="userSex" column="userSex" />
        <result property="delTag" column="delTag" />
    </resultMap>

	<!--    查询全部信息-->
    <select id="getUserList" resultMap="userMap">
        SELECT * FROM user
    </select>
```

4. 编写查询接口和mapper配置文件

```
public interface UserDaoMapper {
    List<User> getUserList();
}
```

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ctgu.dao.UserDaoMapper">

namespace的值应该是对应接口的全类名

<!--    查询全部信息-->
    <select id="getUserList" resultType="User">
        SELECT * FROM user
    </select>
</mapper>

批量配置时mapper文件应该和interface接口保持同名
mapper文件中的sql标签中的ID必须与接口类中的方法名完全一致
接口方法中的参数类型、返回值类型与mapper配置文件中的sql标签的传入、返回值类型要一致。


```

5. 测试类开发

   ```java
   //加载配置文件
   InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
   //创建会话工厂
   SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
   //获取sqlsession对象
   SqlSession sqlSession = factory.openSession();
   //获取dao对象
   UserDaoMapper userDao = sqlSession.getMapper(UserDaoMapper.class);
   List<User> userList = userDao.getUserList();
   for(User user :userList){
       System.out.println(user.getUserName());
   }
   //关闭资源
   sqlSession.close();
   ```

6. 注意事项

```java
1. mybatis核心配置文件应该放置在资源文件中(resources),否则会造成无法加载问题。
2. mapper.xml文件无法加载时，在pom.xml中加入以下配置：
	<resources>
      <resource>
        <directory>src/main/java</directory>
        <includes>
          <include>**/*.xml</include>
        </includes>
      </resource>
    </resources>
   造成原始便是工程不会默认编译src目录下的资源文件，需要手动配置。
3. parameterType属性那些情况下要写？哪些情况下不用写？
	如果是基本类型，或者是java自身的引用类型，在mybatis 运行时，会自动的进行匹配，
	如果是你自己声明的一个类型，因为可能在引入的jar包中有同名的类，所以你需要制定，这个时候的类型是什么
```

