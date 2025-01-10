
### **1. Платформа Java EE. Спецификации и их реализации**  

**Java EE (Enterprise Edition)** — это платформа для разработки корпоративных приложений на языке Java. Она предоставляет разработчикам набор спецификаций для работы с различными задачами: веб-приложения, бизнес-логика, интеграция с базами данных, управление транзакциями, обмен сообщениями, разработка веб-сервисов и др.  

#### Основные спецификации:  
1. **Servlet API**  
   - Обработка HTTP-запросов и управление веб-компонентами.  
   - Создание динамических страниц, редиректов, управление сессиями.  
   - Пример реализации: Apache Tomcat, Jetty.  

2. **JSP (JavaServer Pages)**  
   - Упрощает создание динамических веб-страниц через внедрение Java-кода в HTML.  
   - Позволяет использовать теги JSP и библиотеки тегов.  

3. **JSF (JavaServer Faces)**  
   - Фреймворк для построения компонентных пользовательских интерфейсов.  
   - Применяет шаблоны и модели MVC.  

4. **EJB (Enterprise JavaBeans)**  
   - Обеспечивает поддержку бизнес-логики, управление транзакциями, безопасность.  
   - Включает Stateless, Stateful и Singleton компоненты.  

5. **JPA (Java Persistence API)**  
   - Стандартизирует работу с базами данных через объектно-реляционное отображение (ORM).  
   - Основные аннотации: `@Entity`, `@Table`, `@Column`, `@Query`.  
   - Примеры реализации: Hibernate, EclipseLink.  

6. **JAX-RS (Java API for RESTful Web Services)**  
   - Создание RESTful веб-сервисов.  
   - Поддерживает HTTP методы (GET, POST, PUT, DELETE).  

7. **JAX-WS (Java API for XML Web Services)**  
   - Инструмент для создания SOAP веб-сервисов.  
   - Генерация WSDL для описания сервисов.  

#### Реализации Java EE:  
- **WildFly** — легковесный и производительный сервер приложений.  
- **GlassFish** — референсная реализация Java EE от Oracle (позже передана Eclipse Foundation).  
- **Apache TomEE** — расширение Apache Tomcat, добавляющее поддержку спецификаций Java EE.  

---

### **2. Принципы IoC, CDI и Location Transparency. Компоненты и контейнеры**

#### **Инверсия управления (IoC — Inversion of Control)**  
IoC — это архитектурный принцип, при котором создание и управление объектами передаётся внешнему контейнеру или фреймворку. Программист не создаёт объекты вручную, а описывает зависимости, а контейнер обеспечивает их создание и связывание.  

##### **Основные аспекты IoC**:  
1. **Dependency Injection (DI, внедрение зависимостей)**  
   - При DI объект получает свои зависимости (другие объекты) извне, а не создаёт их самостоятельно.  
   - Реализация через конструктор, сеттеры или поля.  

   ```java
   public class MyService {
       private final Dependency dependency;

       @Inject // CDI аннотация
       public MyService(Dependency dependency) {
           this.dependency = dependency;
       }
   }
   ```

2. **Event-Driven IoC**  
   - Контейнер реагирует на события внутри приложения (например, запуск или остановка компонента).  

##### **Преимущества IoC**:  
- Упрощённое тестирование через мок-объекты.  
- Ослабленная связность между компонентами.  
- Централизованное управление зависимостями.  

---

#### **CDI (Context and Dependency Injection)**  
**CDI** — стандарт Java EE для управления контекстами и внедрения зависимостей.  

##### **Особенности CDI**:  
1. **Контексты (Contexts)**  
   - Контекст отвечает за управление временем жизни объектов.  
   - Примеры:  
     - **RequestScoped**: объект живёт в рамках HTTP-запроса.  
     - **SessionScoped**: объект живёт в рамках сессии.  
     - **ApplicationScoped**: объект живёт в рамках всего приложения.  

2. **Dependency Injection (DI)**  
   - Внедрение зависимостей через аннотации.  
   - Пример аннотаций: `@Inject`, `@Produces`.  

3. **Event System**  
   - Поддержка событийного взаимодействия между компонентами (`@Observes`).  

4. **Альтернативы (Alternatives)**  
   - Позволяет заменять компоненты (например, для тестирования).  

##### **Пример CDI**:  
```java
@RequestScoped
public class GreetingService {
    public String getGreeting() {
        return "Hello, CDI!";
    }
}

@Inject
private GreetingService service;

public void greet() {
    System.out.println(service.getGreeting());
}
```

---

#### **Location Transparency (Прозрачность местоположения)**  
**Прозрачность местоположения** означает, что компоненты могут быть вызваны без знания их физического расположения (локально или удалённо).  

##### **Как это достигается?**  
1. **EJB**  
   - Объекты, реализованные как EJB, могут вызываться локально или через удалённый интерфейс (RMI).  

   ```java
   @Stateless
   public class MyBean implements MyRemoteInterface {
       public String sayHello() {
           return "Hello!";
       }
   }
   ```

2. **JNDI (Java Naming and Directory Interface)**  
   - Используется для поиска и вызова удалённых объектов.  

3. **JMS (Java Messaging Service)**  
   - Позволяет организовать обмен сообщениями между приложениями независимо от их местоположения.  

##### **Преимущества Location Transparency**:  
- Гибкость: компоненты можно перемещать между серверами.  
- Масштабируемость: сервисы могут обрабатываться распределённо.  

---

#### **Компоненты и контейнеры**  
1. **Компоненты**  
   - Единицы функциональности приложения, такие как:  
     - Веб-компоненты (сервлеты, JSP).  
     - Бизнес-компоненты (EJB).  
     - Веб-сервисы (JAX-RS, JAX-WS).  

2. **Контейнеры**  
   - Среда выполнения для компонентов. Контейнер управляет их жизненным циклом, конфигурацией и взаимодействием.  
   - Примеры:  
     - **Web Container** — для сервлетов, JSP (Tomcat).  
     - **EJB Container** — для EJB (WildFly, GlassFish).  

##### **Что обеспечивает контейнер?**  
- Управление временем жизни объектов.  
- Управление зависимостями (DI).  
- Безопасность и управление транзакциями.  
- Прозрачность взаимодействия.  

---

### **3. Управление жизненным циклом компонентов. Дескрипторы развёртывания**

#### **Управление жизненным циклом компонентов**  

Компоненты Java EE (например, EJB, сервлеты, REST-контроллеры) работают в контейнерах, которые берут на себя ответственность за их создание, инициализацию, управление состоянием, уничтожение и вызовы методов.  

##### **Жизненный цикл сервлетов**  
1. **Создание и инициализация**  
   - При запуске веб-приложения контейнер создаёт экземпляры сервлетов, используя метод `init()`.  
   - В этот момент происходит загрузка ресурсов и настройка.  
   
   ```java
   @WebServlet("/example")
   public class ExampleServlet extends HttpServlet {
       @Override
       public void init() throws ServletException {
           System.out.println("Servlet initialized");
       }
   }
   ```

2. **Обработка запросов**  
   - Каждый HTTP-запрос обрабатывается методом `service()`, который вызывает соответствующий метод (`doGet()`, `doPost()` и т.д.).  

3. **Уничтожение**  
   - Контейнер вызывает метод `destroy()` перед завершением работы приложения или удалением сервлета из памяти.  

##### **Жизненный цикл EJB (Enterprise JavaBeans)**  
**Stateless Session Bean**  
1. Создаётся при необходимости контейнером.  
2. Пул объектов используется для обработки запросов.  
3. Уничтожается, когда объект больше не нужен.  

**Stateful Session Bean**  
1. Создаётся контейнером для каждого клиента.  
2. Сохраняет состояние между вызовами методов.  
3. Уничтожается после окончания сессии клиента или по таймауту.  

```java
@Stateful
public class MyStatefulBean {
    public void businessMethod() {
        System.out.println("Stateful bean method called");
    }
}
```

---

#### **Дескрипторы развёртывания (Deployment Descriptors)**  
Дескрипторы развёртывания — это конфигурационные файлы, которые определяют поведение и настройку компонентов Java EE.  

##### **Формат файлов дескрипторов**  
1. **web.xml**  
   - Конфигурирует веб-компоненты (сервлеты, фильтры, слушатели).  
   - Находится в каталоге `WEB-INF`.  

   **Пример:**
   ```xml
   <web-app xmlns="http://java.sun.com/xml/ns/javaee" version="3.1">
       <servlet>
           <servlet-name>ExampleServlet</servlet-name>
           <servlet-class>com.example.ExampleServlet</servlet-class>
       </servlet>
       <servlet-mapping>
           <servlet-name>ExampleServlet</servlet-name>
           <url-pattern>/example</url-pattern>
       </servlet-mapping>
   </web-app>
   ```

2. **ejb-jar.xml**  
   - Определяет EJB-компоненты, их типы и конфигурацию.  
   - Используется для описания транзакций, безопасности и других аспектов.  

   **Пример:**
   ```xml
   <ejb-jar xmlns="http://java.sun.com/xml/ns/javaee" version="3.2">
       <enterprise-beans>
           <session>
               <ejb-name>MyEJB</ejb-name>
               <ejb-class>com.example.MyEJB</ejb-class>
               <session-type>Stateless</session-type>
           </session>
       </enterprise-beans>
   </ejb-jar>
   ```

3. **application.xml**  
   - Определяет модульные приложения (EAR).  
   - Указывается состав модулей: веб-приложения, EJB, модули ресурсов.  

   **Пример:**
   ```xml
   <application xmlns="http://java.sun.com/xml/ns/javaee" version="7">
       <module>
           <web>
               <web-uri>example-web.war</web-uri>
               <context-root>/example</context-root>
           </web>
       </module>
       <module>
           <ejb>example-ejb.jar</ejb>
       </module>
   </application>
   ```

4. **glassfish-web.xml** (или дескрипторы для других серверов)  
   - Сервероспецифичные настройки, такие как обработка ресурсов JNDI, настройки сессий и безопасности.  

---

#### **Особенности использования дескрипторов**  
1. **Переопределение аннотаций**  
   - Конфигурация в XML может дополнять или переопределять настройки, указанные через аннотации.  

2. **Назначение ролей и безопасность**  
   - Управление доступом через `<security-role>` и `<security-constraint>`.  

3. **Гибкость**  
   - XML-описание может быть легко изменено без необходимости перекомпиляции кода.  

##### **Пример безопасности в web.xml**:  
```xml
<security-constraint>
    <web-resource-collection>
        <web-resource-name>SecureServlet</web-resource-name>
        <url-pattern>/secure/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>admin</role-name>
    </auth-constraint>
</security-constraint>
<security-role>
    <role-name>admin</role-name>
</security-role>
```

---

### **4. Java EE API. Виды компонентов. Профили платформы Java EE**

#### **Java EE API**  
Java EE (Jakarta EE) предоставляет стандартный набор API для разработки корпоративных приложений. Эти API поддерживают управление компонентами, безопасность, транзакции, веб-сервисы и т.д.  

##### **Основные модули Java EE API**  
1. **Веб-компоненты**  
   - **Servlet API**: Обработка HTTP-запросов и создание веб-страниц.  
   - **JSP (JavaServer Pages)**: Генерация HTML с использованием Java-кода.  
   - **JSF (JavaServer Faces)**: Фреймворк для создания пользовательского интерфейса.  

2. **Бизнес-логика**  
   - **EJB (Enterprise JavaBeans)**: Компоненты для реализации бизнес-логики, работы с транзакциями и распределёнными системами.  

3. **Персистентность данных**  
   - **JPA (Java Persistence API)**: ORM для работы с базами данных.  

4. **Сообщения**  
   - **JMS (Java Message Service)**: Обмен сообщениями между компонентами.  

5. **Веб-сервисы**  
   - **JAX-RS**: RESTful веб-сервисы.  
   - **JAX-WS**: SOAP веб-сервисы.  

6. **Безопасность**  
   - **JAAS (Java Authentication and Authorization Service)**: Аутентификация и авторизация.  

7. **Транзакции**  
   - **JTA (Java Transaction API)**: Управление транзакциями.  

8. **Управление ресурсами**  
   - **JNDI (Java Naming and Directory Interface)**: Доступ к ресурсам (базам данных, удалённым объектам).  

---

#### **Виды компонентов Java EE**  
Компоненты — это основные строительные блоки приложений Java EE.  

##### **Типы компонентов**  
1. **Веб-компоненты**  
   - **Servlet**: Обрабатывает HTTP-запросы, реализует бизнес-логику на серверной стороне.  
   - **JSP**: Шаблонные страницы для генерации HTML.  
   - **WebSocket**: Обеспечивает двустороннюю связь между клиентом и сервером.  

2. **Бизнес-компоненты**  
   - **EJB**: Реализуют сложную бизнес-логику, поддерживают транзакции и безопасность.  

3. **Управление ресурсами**  
   - **Message-Driven Beans (MDB)**: Асинхронная обработка сообщений из JMS-очередей.  

4. **Веб-сервисы**  
   - Компоненты, предоставляющие REST или SOAP API.  

##### **Пример сервлета**:  
```java
@WebServlet("/example")
public class ExampleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Hello, Java EE!");
    }
}
```

---

#### **Профили платформы Java EE**  
Java EE поддерживает различные профили, чтобы адаптироваться к приложениям разного масштаба.  

##### **Основные профили**  
1. **Full Profile (Полный профиль)**  
   - Включает все спецификации Java EE.  
   - Используется для крупных корпоративных приложений.  

2. **Web Profile (Веб-профиль)**  
   - Упрощённая версия, включающая только спецификации для веб-приложений.  
   - Подходит для небольших приложений и микросервисов.  

##### **Сравнение Full Profile и Web Profile**  
| Спецификация      | Full Profile | Web Profile |  
|--------------------|--------------|-------------|  
| Servlet API        | ✔            | ✔           |  
| JSP/JSF            | ✔            | ✔           |  
| JPA                | ✔            | ✔           |  
| EJB (Stateless)    | ✔            | ✔           |  
| EJB (Stateful)     | ✔            | ✘           |  
| JMS                | ✔            | ✘           |  
| JAX-WS             | ✔            | ✘           |  
| JAX-RS             | ✔            | ✔           |  

---

#### **Особенности профилей**  
- **Web Profile** используется для облегчённых серверов, таких как Tomcat или Jetty, которые поддерживают только веб-технологии.  
- **Full Profile** предназначен для серверов приложений, таких как WildFly или GlassFish, поддерживающих полный набор API.  

### **5. Компоненты EJB. Stateless & Stateful Session Beans. EJB Lite и EJB Full**

#### **Enterprise JavaBeans (EJB)**  
EJB — это серверные компоненты Java EE, предназначенные для реализации бизнес-логики, управления транзакциями и обеспечения распределённости. Они работают в контейнере, который берет на себя управление их жизненным циклом, безопасностью и другими аспектами.  

---

#### **Типы EJB-компонентов**  

1. **Session Beans (Сессионные бины)**  
   Используются для реализации бизнес-логики.  
   - **Stateless Session Beans**: Не хранят состояние между вызовами.  
   - **Stateful Session Beans**: Хранят состояние, уникальное для каждого клиента.  
   - **Singleton Beans**: Один экземпляр на приложение, используется для общей логики.  

2. **Message-Driven Beans (MDB)**  
   Обрабатывают сообщения из JMS-очередей (асинхронная работа).  

---

#### **Stateless Session Beans**  
Stateless Session Bean (SLSB) используется для задач, не требующих сохранения состояния между вызовами. Пример: расчёт налогов, проверка доступности продукта.  

##### **Характеристики:**  
- Не сохраняют состояние клиента.  
- Поддерживаются пулами для обработки нескольких запросов.  
- Лёгкие и быстрые.  

##### **Пример Stateless Session Bean**  
```java
import javax.ejb.Stateless;

@Stateless
public class TaxCalculatorBean {
    public double calculateTax(double amount) {
        return amount * 0.2;
    }
}
```

##### **Вызов Stateless Session Bean из клиента**  
```java
@EJB
private TaxCalculatorBean taxCalculator;

public void execute() {
    double tax = taxCalculator.calculateTax(1000);
    System.out.println("Tax: " + tax);
}
```

---

#### **Stateful Session Beans**  
Stateful Session Bean (SFSB) используется, когда необходимо сохранять состояние клиента между вызовами. Пример: корзина покупок.  

##### **Характеристики:**  
- Поддерживают состояние между методами в рамках одной сессии.  
- Уникальны для каждого клиента.  
- Уничтожаются после завершения сессии.  

##### **Пример Stateful Session Bean**  
```java
import javax.ejb.Stateful;

@Stateful
public class ShoppingCartBean {
    private List<String> items = new ArrayList<>();

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getItems() {
        return items;
    }
}
```

##### **Вызов Stateful Session Bean из клиента**  
```java
@EJB
private ShoppingCartBean shoppingCart;

public void execute() {
    shoppingCart.addItem("Laptop");
    System.out.println("Cart items: " + shoppingCart.getItems());
}
```

---

#### **EJB Lite и EJB Full**  
EJB Lite — это упрощённая версия EJB, ориентированная на лёгкие приложения, например, микросервисы.  
EJB Full — полный набор возможностей EJB для корпоративных приложений.  

##### **Сравнение EJB Lite и EJB Full**  

| Возможность                   | EJB Lite        | EJB Full       |  
|--------------------------------|-----------------|----------------|  
| Stateless Session Beans        | ✔               | ✔              |  
| Stateful Session Beans         | ✔               | ✔              |  
| Singleton Beans                | ✔               | ✔              |  
| Message-Driven Beans (MDB)     | ✘               | ✔              |  
| Таймеры (Timers)               | ✘               | ✔              |  
| Распределённые вызовы          | ✘               | ✔              |  
| Расширенное управление транзакциями | ✘         | ✔              |  

##### **Использование EJB Lite**  
EJB Lite чаще всего применяется в Web Profile серверах, таких как Tomcat.  

##### **Использование EJB Full**  
EJB Full необходим для крупных приложений с распределёнными транзакциями и очередями сообщений.  

---

### **6. Работа с электронной почтой в Java EE. JavaMail API**

#### **JavaMail API**  
JavaMail API — это стандартная библиотека для работы с электронной почтой в приложениях Java. Она позволяет отправлять, получать и обрабатывать сообщения электронной почты с использованием различных протоколов, таких как SMTP, IMAP и POP3.

---

#### **Основные возможности JavaMail API**  
1. **Отправка электронных писем**  
   - Работа с протоколом **SMTP**.  
   - Поддержка вложений, HTML и текста.  

2. **Получение и чтение писем**  
   - Использование протоколов **IMAP** и **POP3**.  

3. **Обработка сообщений**  
   - Парсинг заголовков, тела письма и вложений.  

4. **Управление почтовыми ящиками**  
   - Подключение к почтовым серверам.  
   - Работа с папками (входящие, черновики, отправленные).  

---

#### **Основные классы JavaMail API**  
1. **javax.mail.Session**  
   - Представляет сессию для взаимодействия с почтовым сервером.  

2. **javax.mail.Message**  
   - Описывает электронное письмо.  

3. **javax.mail.Transport**  
   - Используется для отправки сообщений.  

4. **javax.mail.Store**  
   - Для подключения к почтовому ящику и работы с папками.  

5. **javax.mail.Folder**  
   - Описывает папку (например, входящие сообщения).  

---

#### **Отправка электронной почты с использованием JavaMail API**

##### **Пример отправки текста с использованием SMTP**
```java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void main(String[] args) {
        String to = "recipient@example.com"; // Адрес получателя
        String from = "sender@example.com"; // Адрес отправителя
        String host = "smtp.example.com";   // SMTP-сервер

        // Настройки свойств
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Порт
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Аутентификация
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your_username", "your_password");
            }
        });

        try {
            // Создание сообщения
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Test Email");
            message.setText("This is a test email from JavaMail API!");

            // Отправка сообщения
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
```

---

#### **Получение электронной почты с использованием JavaMail API**

##### **Пример чтения писем через IMAP**
```java
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailReader {
    public static void main(String[] args) {
        String host = "imap.example.com"; // IMAP-сервер
        String username = "your_username";
        String password = "your_password";

        // Настройки свойств
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");

        try {
            // Подключение к почтовому ящику
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            // Работа с папкой "Входящие"
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Чтение сообщений
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Content: " + message.getContent());
            }

            // Закрытие соединения
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

#### **Особенности работы с вложениями**

Для отправки и получения писем с вложениями используется класс **MimeBodyPart**.  

##### **Пример отправки письма с вложением**
```java
MimeMessage message = new MimeMessage(session);
MimeBodyPart textPart = new MimeBodyPart();
textPart.setText("This is the message body.");

// Добавление вложения
MimeBodyPart attachmentPart = new MimeBodyPart();
attachmentPart.attachFile("path/to/file.txt");

// Объединение частей
Multipart multipart = new MimeMultipart();
multipart.addBodyPart(textPart);
multipart.addBodyPart(attachmentPart);

// Установка содержимого сообщения
message.setContent(multipart);
Transport.send(message);
```

---

#### **Использование JavaMail API в Java EE**

Java EE позволяет использовать JavaMail API непосредственно в EJB или сервлетах.  

##### **Интеграция в EJB**  
```java
@Stateless
public class EmailService {
    @Resource(name = "java:jboss/mail/Default")
    private Session mailSession;

    public void sendEmail(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
```

### **7. JMS. Реализация очередей сообщений. Способы доставки сообщений до клиента. Message-Driven Beans**

#### **JMS (Java Message Service)**  
Java Message Service — это API Java для обмена сообщениями между компонентами распределённого приложения. JMS позволяет реализовать асинхронное взаимодействие и гарантированную доставку сообщений.  

---

#### **Основные понятия JMS**  

1. **Сообщение (Message)**  
   Основная единица передачи данных в JMS.  
   - **Текстовые сообщения** (`TextMessage`) — содержат строку текста.  
   - **Объектные сообщения** (`ObjectMessage`) — содержат Java-объекты.  
   - **Потоковые сообщения** (`StreamMessage`) — содержат примитивные данные.  
   - **Картографические сообщения** (`MapMessage`) — содержат пары ключ-значение.  

2. **Пункт назначения (Destination)**  
   Область для отправки и получения сообщений. Включает:  
   - **Очереди (Queues)** — каждое сообщение доставляется только одному получателю.  
   - **Темы (Topics)** — сообщения доставляются всем подписчикам.  

3. **Производители (Producers)**  
   Отправляют сообщения в очередь или тему.  

4. **Потребители (Consumers)**  
   Получают сообщения из очереди или темы.  

5. **Поставщики (Providers)**  
   Реализации JMS, например, Apache ActiveMQ, RabbitMQ, JBoss Messaging.  

---

#### **Реализация очередей сообщений**

**Пример использования JMS API для работы с очередями:**  

##### **Отправка сообщения в очередь**
```java
import javax.jms.*;
import javax.naming.InitialContext;

public class MessageProducerExample {
    public static void main(String[] args) {
        try {
            // Получение контекста JNDI
            InitialContext ctx = new InitialContext();

            // Поиск ConnectionFactory и очереди
            ConnectionFactory factory = (ConnectionFactory) ctx.lookup("jms/ConnectionFactory");
            Queue queue = (Queue) ctx.lookup("jms/Queue");

            // Установка соединения и сессии
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Создание сообщения и отправка
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage("Hello, JMS!");
            producer.send(message);

            System.out.println("Message sent successfully!");
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

##### **Получение сообщения из очереди**
```java
import javax.jms.*;
import javax.naming.InitialContext;

public class MessageConsumerExample {
    public static void main(String[] args) {
        try {
            // Получение контекста JNDI
            InitialContext ctx = new InitialContext();

            // Поиск ConnectionFactory и очереди
            ConnectionFactory factory = (ConnectionFactory) ctx.lookup("jms/ConnectionFactory");
            Queue queue = (Queue) ctx.lookup("jms/Queue");

            // Установка соединения и сессии
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Подписка на очередь
            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();

            // Чтение сообщения
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received: " + textMessage.getText());
            }

            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

#### **Способы доставки сообщений до клиента**

1. **Синхронная доставка**  
   Потребитель явно запрашивает сообщение с использованием методов `receive()`.  
   
   Пример:  
   ```java
   Message message = consumer.receive();
   ```

2. **Асинхронная доставка**  
   Потребитель регистрирует обработчик сообщений (`MessageListener`), который вызывается автоматически при получении сообщения.  

   Пример:  
   ```java
   consumer.setMessageListener(message -> {
       if (message instanceof TextMessage) {
           TextMessage textMessage = (TextMessage) message;
           System.out.println("Asynchronous received: " + textMessage.getText());
       }
   });
   ```

---

#### **Message-Driven Beans (MDB)**

MDB — это EJB-компонент, предназначенный для обработки сообщений из JMS-очередей и тем. Он является асинхронным и автоматически вызывается при поступлении сообщения.  

##### **Характеристики MDB:**  
- Реализует интерфейс `javax.jms.MessageListener`.  
- Работает на сервере приложений.  
- Упрощает интеграцию с JMS.  

##### **Пример MDB**
```java
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Queue")
    }
)
public class MyMessageDrivenBean implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("MDB received: " + textMessage.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

#### **Типы доставки сообщений**
1. **P2P (Point-to-Point)**  
   - Использует очереди.  
   - Каждое сообщение доставляется только одному потребителю.  

2. **Pub/Sub (Publish/Subscribe)**  
   - Использует темы.  
   - Сообщение доставляется всем подписчикам.  

##### **Пример Pub/Sub**
```java
Topic topic = (Topic) ctx.lookup("jms/Topic");
MessageProducer producer = session.createProducer(topic);
```

---

### **8. Понятие транзакции. Управление транзакциями в Java EE. JTA**

#### **Что такое транзакция?**  

Транзакция — это последовательность операций, которые либо успешно завершаются все вместе (коммит), либо полностью отменяются (откат), чтобы система оставалась в согласованном состоянии.  

Транзакции имеют четыре ключевых свойства, описываемых как **ACID**:
1. **Atomicity (атомарность):** Транзакция либо выполняется целиком, либо не выполняется вовсе.  
2. **Consistency (согласованность):** Транзакция переводит систему из одного согласованного состояния в другое.  
3. **Isolation (изоляция):** Транзакции не влияют друг на друга, даже если выполняются одновременно.  
4. **Durability (долговечность):** Результаты завершённой транзакции сохраняются даже при сбоях.  

---

#### **Управление транзакциями в Java EE**  

Java EE использует **JTA (Java Transaction API)** для работы с транзакциями. JTA предоставляет стандартный API для управления транзакциями на уровне Java-приложений.  

##### **Архитектура транзакций в Java EE**  
- **Transaction Manager** — управляет транзакциями.  
- **Resource Manager** — управляет ресурсами, такими как базы данных, очереди JMS.  
- **Application Server** — координирует работу `Transaction Manager` и компонентов приложения.  

---

#### **Типы транзакционного управления**  

1. **Контейнерное управление транзакциями (Container-Managed Transactions, CMT)**  
   - Транзакции автоматически обрабатываются сервером приложений.  
   - Разработчику нужно только указать аннотации или использовать дескрипторы.  

##### **Пример CMT с использованием аннотаций**
```java
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AccountService {
    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferFunds(Long fromAccountId, Long toAccountId, Double amount) {
        Account from = em.find(Account.class, fromAccountId);
        Account to = em.find(Account.class, toAccountId);

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        em.merge(from);
        em.merge(to);
    }
}
```

2. **Программное управление транзакциями (Bean-Managed Transactions, BMT)**  
   - Разработчик управляет транзакциями вручную через API `UserTransaction`.  

##### **Пример BMT**
```java
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.transaction.UserTransaction;

@Stateless
public class AccountService {
    @Resource
    private UserTransaction utx;

    public void transferFunds(Long fromAccountId, Long toAccountId, Double amount) {
        try {
            utx.begin();

            // Логика перевода средств
            Account from = em.find(Account.class, fromAccountId);
            Account to = em.find(Account.class, toAccountId);

            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            em.merge(from);
            em.merge(to);

            utx.commit();
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }
}
```

---

#### **Аннотации для работы с транзакциями**  

1. **@TransactionAttribute**  
   Указывает уровень транзакции для метода EJB.  
   - **REQUIRED** — метод должен работать в рамках существующей транзакции или начать новую.  
   - **REQUIRES_NEW** — всегда создаётся новая транзакция.  
   - **MANDATORY** — требуется существующая транзакция, иначе выбрасывается исключение.  
   - **SUPPORTS** — метод может работать как внутри транзакции, так и без неё.  
   - **NOT_SUPPORTED** — метод выполняется вне транзакции.  
   - **NEVER** — метод не должен работать в рамках транзакции.  

2. **@Transactional** (Java EE 7 и выше)  
   Упрощает указание транзакционного поведения на уровне CDI-бинов.  

---

#### **JTA API**  

Ключевые интерфейсы JTA:  
1. **javax.transaction.UserTransaction**  
   Используется для программного управления транзакциями.  

   Пример:  
   ```java
   UserTransaction utx = InitialContext.doLookup("java:comp/UserTransaction");
   utx.begin();
   // транзакционные операции
   utx.commit();
   ```

2. **javax.transaction.TransactionManager**  
   Управляет состоянием транзакции, используется сервером приложений.  

---

#### **Работа с транзакциями и ресурсами**  

Для управления ресурсами, такими как базы данных или JMS, используются следующие элементы:
1. **JDBC Connections** — управляются через пул соединений.  
2. **EntityManager (JPA)** — автоматически участвует в транзакциях.  
3. **JMS** — операции отправки/получения сообщений можно выполнять в рамках транзакции.  

Пример объединённой транзакции JPA и JMS:
```java
@Stateless
public class OrderService {
    @PersistenceContext
    private EntityManager em;

    @Resource(mappedName = "jms/QueueConnectionFactory")
    private ConnectionFactory factory;

    @Resource(mappedName = "jms/Queue")
    private Queue queue;

    public void placeOrder(Order order) {
        try {
            // Сохранение заказа
            em.persist(order);

            // Отправка сообщения
            Connection connection = factory.createConnection();
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage("Order placed: " + order.getId());
            producer.send(message);

            session.commit();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

---

### **9. Веб-сервисы. Технологии JAX-RS и JAX-WS**

#### **Что такое веб-сервисы?**  
Веб-сервисы — это компоненты, которые позволяют приложениям обмениваться данными и функциональностью через интернет или локальную сеть. Они обеспечивают взаимодействие между различными платформами и языками программирования, используя стандартные протоколы, такие как HTTP, SOAP и REST.

---

#### **JAX-RS и JAX-WS: Обзор технологий**

- **JAX-WS (Java API for XML Web Services):** предназначен для разработки SOAP-веб-сервисов. SOAP — это протокол, основанный на XML, который поддерживает строгую типизацию данных и сложные операции.
- **JAX-RS (Java API for RESTful Web Services):** предназначен для разработки RESTful веб-сервисов. REST (Representational State Transfer) — это архитектурный стиль, основанный на использовании стандартных HTTP-операций.

---

### **JAX-WS: Разработка SOAP-веб-сервисов**

#### **Особенности JAX-WS**  
- SOAP-ориентированная архитектура.
- Операции описываются с помощью **WSDL (Web Services Description Language)**.
- Поддерживает сложные операции, транзакции и безопасность.

##### **Ключевые аннотации JAX-WS**  
1. **@WebService:** используется для создания веб-сервиса.  
2. **@WebMethod:** обозначает метод, доступный через веб-сервис.  
3. **@SOAPBinding:** управляет стилем SOAP-сообщений (RPC или документный).  

##### **Пример SOAP-веб-сервиса**
```java
import javax.jws.WebService;

@WebService
public class CalculatorService {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }
}
```

##### **Деплой веб-сервиса в Java EE**
- Класс должен быть размещён в WAR-файле.
- Сервис автоматически доступен по адресу, заданному сервером приложений.

##### **Клиент для SOAP-веб-сервиса**
Создание клиента с использованием WSDL:
```java
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class CalculatorClient {
    public static void main(String[] args) throws Exception {
        URL wsdlUrl = new URL("http://localhost:8080/CalculatorService?wsdl");
        QName qname = new QName("http://service.example/", "CalculatorService");

        Service service = Service.create(wsdlUrl, qname);
        Calculator calculator = service.getPort(Calculator.class);

        System.out.println("Sum: " + calculator.add(10, 20));
    }
}
```

---

### **JAX-RS: Разработка RESTful веб-сервисов**

#### **Особенности JAX-RS**  
- Поддержка стандартных HTTP-методов: `GET`, `POST`, `PUT`, `DELETE`.
- Простота в использовании благодаря аннотациям.
- Легковесность и высокая производительность.

##### **Ключевые аннотации JAX-RS**  
1. **@Path:** определяет URI-адрес ресурса.  
2. **@GET, @POST, @PUT, @DELETE:** обозначают методы, соответствующие HTTP-операциям.  
3. **@Produces:** указывает формат ответа (`application/json`, `application/xml` и др.).  
4. **@Consumes:** указывает формат входных данных.  
5. **@QueryParam, @PathParam, @HeaderParam:** для передачи параметров.  

---

#### **Пример RESTful веб-сервиса**
```java
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/calculator")
public class CalculatorResource {
    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@QueryParam("a") int a, @QueryParam("b") int b) {
        return "Sum: " + (a + b);
    }

    @POST
    @Path("/subtract")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Result subtract(Numbers numbers) {
        int result = numbers.getA() - numbers.getB();
        return new Result(result);
    }
}

class Numbers {
    private int a;
    private int b;

    // Getters and setters
}

class Result {
    private int result;

    public Result(int result) {
        this.result = result;
    }

    // Getters and setters
}
```

##### **Деплой RESTful веб-сервиса в Java EE**
1. Включить библиотеку JAX-RS в проект.
2. Создать файл конфигурации `web.xml` или использовать аннотацию `@ApplicationPath`.

```java
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
}
```

##### **Тестирование RESTful API**
Инструменты:
- **Postman**
- **curl**
- Встроенные возможности браузеров для GET-запросов.

---

#### **Сравнение JAX-WS и JAX-RS**

| Особенность       | JAX-WS                      | JAX-RS                  |
|--------------------|-----------------------------|--------------------------|
| Протокол           | SOAP                       | HTTP                    |
| Формат данных      | XML                        | JSON, XML, текст и др.  |
| Описание сервиса   | WSDL                       | Не требуется            |
| Простота           | Более сложен               | Легковесный             |
| Производительность | Относительно медленнее     | Быстрее                 |

---

### **10. Платформа Spring. Сходства и отличия с Java EE**

#### **Что такое Spring?**
Spring — это популярный фреймворк для создания корпоративных приложений на языке Java. Он предоставляет широкий набор инструментов для работы с базами данных, транзакциями, веб-приложениями, интеграциями, а также удобную систему для создания приложений с использованием принципов инверсия управления (IoC) и аспектно-ориентированного программирования (AOP).

---

#### **Основные компоненты Spring:**
1. **Spring Core** — основной контейнер для внедрения зависимостей (IoC).
2. **Spring AOP** — аспектно-ориентированное программирование.
3. **Spring Data** — поддержка работы с базами данных.
4. **Spring MVC** — модуль для разработки веб-приложений.
5. **Spring Security** — средства для защиты приложений.
6. **Spring Boot** — инструмент для упрощения разработки и развертывания приложений.
7. **Spring Batch** — для обработки больших объемов данных и выполнения фоновых задач.

---

#### **Сходства между Spring и Java EE**
- **Инверсия управления (IoC):** и Java EE, и Spring используют подход инверсии управления для внедрения зависимостей и управления жизненным циклом объектов.
- **Модульность:** обе платформы предлагают модульный подход для разработки приложений, позволяя интегрировать различные компоненты, такие как базы данных, веб-сервисы и транзакции.
- **Транзакции:** обе платформы поддерживают управление транзакциями через JTA или собственные средства.
- **Web-разработка:** и в Spring, и в Java EE поддерживаются веб-приложения с использованием сервлетов, JSP, и технологий для построения RESTful API.

---

#### **Основные отличия Spring от Java EE**

1. **Легкость и настройка**
   - **Spring:** предоставляет более легковесный подход, автоматизируя конфигурацию через аннотации и XML-конфигурации, и избавляя от необходимости в большом количестве дескрипторов развертывания.
   - **Java EE:** использует более традиционные подходы и требует настройки через дескрипторы развертывания (например, `web.xml`).

2. **Контейнеры и интеграция**
   - **Spring:** использует контейнер IoC для управления зависимостями и созданием объектов, позволяя интегрировать сторонние библиотеки и технологии без привязки к конкретному серверу приложений.
   - **Java EE:** имеет контейнеры, такие как EJB, для создания и управления компонентами, но ограничен выбором серверов приложений, таких как WildFly, GlassFish.

3. **Модульность**
   - **Spring:** можно подключать только те компоненты, которые действительно нужны для конкретного проекта, что позволяет снизить избыточность.
   - **Java EE:** включает в себя целый набор компонентов, таких как EJB, JPA, JMS, которые могут быть полезны, но не всегда требуются.

4. **Web-разработка**
   - **Spring:** через Spring MVC предлагает гибкую архитектуру для построения веб-приложений с возможностью кастомизации на уровне контроллеров, сервисов и представлений.
   - **Java EE:** использует более традиционный подход с сервлетами и JSP, но также предоставляет JAX-RS для разработки RESTful веб-сервисов.

5. **Аспектно-ориентированное программирование (AOP)**
   - **Spring:** активно использует AOP для внедрения поведения в компоненты без изменения их кода (например, для реализации логирования, кэширования и транзакций).
   - **Java EE:** не имеет встроенной поддержки AOP, хотя возможности аспектно-ориентированного программирования можно реализовать с помощью внешних библиотек.

---

### **11. Модули Spring. Архитектура Spring Runtime. Spring Security и Spring Data**

#### **Модули Spring:**
1. **Spring Core:** предоставляет базовые возможности для внедрения зависимостей и управления жизненным циклом объектов.
2. **Spring AOP:** добавляет поддержку аспектно-ориентированного программирования, позволяя обрабатывать кросс-функциональные задачи (например, логирование, транзакции) отдельно от основной бизнес-логики.
3. **Spring MVC:** используется для создания веб-приложений с разделением модели, представления и контроллера. Обеспечивает удобную работу с RESTful сервисами.
4. **Spring Boot:** предоставляет возможность быстро и просто создать, настроить и развернуть Spring-приложение с минимальной конфигурацией.
5. **Spring Security:** модуль для защиты приложений. Предоставляет функциональность аутентификации и авторизации, защиту от атак и конфиденциальность.
6. **Spring Data:** упрощает работу с базами данных, поддерживает работу с JPA, MongoDB, Cassandra и другими технологиями хранения данных.

---

#### **Архитектура Spring Runtime:**
1. **Контейнер IoC (Inversion of Control):** основа Spring. Контейнер управляет жизненным циклом объектов и внедрением зависимостей.
2. **BeanFactory и ApplicationContext:** это интерфейсы контейнера Spring, где `ApplicationContext` является более мощным и функциональным, поддерживающим международные настройки и возможность обработки событий.
3. **Автоконфигурация (Spring Boot):** автоматическая настройка компонентов приложения в зависимости от присутствующих библиотек и классов на пути.

---

#### **Spring Security:**
Spring Security — это мощный и гибкий фреймворк для защиты приложений, обеспечивающий:
1. **Аутентификацию:** проверку подлинности пользователей.
2. **Авторизацию:** разграничение доступа к различным частям приложения.
3. **Защиту от атак:** таких как CSRF, XSS и SQL-инъекции.

##### **Пример настройки безопасности в Spring Security**
```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password("{noop}password").roles("USER");
    }
}
```

---

#### **Spring Data:**
Spring Data позволяет упростить взаимодействие с базами данных, скрывая детали реализации и обеспечивая мощные механизмы для работы с различными хранилищами данных (SQL, NoSQL).

1. **JPA (Java Persistence API):** интеграция с ORM для работы с реляционными базами данных.
2. **MongoDB, Cassandra, Elasticsearch:** интеграция с популярными NoSQL решениями.
3. **Spring Data Repositories:** автоматическое создание репозиториев для работы с базами данных.

##### **Пример репозитория с JPA**
```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

---
### **12. Реализация IoC и CDI в Spring. Сходства и отличия с Java EE**

#### **Инверсия управления (IoC) в Spring и CDI в Java EE**
Инверсия управления (IoC) — это принцип, при котором управление созданием объектов и их зависимостями передается от программного кода к контейнеру. IoC способствует улучшению гибкости и уменьшению связности между компонентами, так как компоненты не создают свои зависимости напрямую, а получают их от контейнера.

1. **Spring IoC:**
   - Spring использует контейнер IoC для управления жизненным циклом объектов. Он создаёт и управляет объектами с помощью конфигураций (через XML или аннотации).
   - Spring поддерживает концепцию **внедрения зависимостей (DI)** через конструкторы, поля или методы.
   - Контейнер Spring позволяет легко настраивать взаимодействие между компонентами приложения, улучшая модульность и тестируемость.

   **Пример внедрения зависимостей в Spring:**
   ```java
   @Component
   public class UserService {
       private final UserRepository userRepository;

       @Autowired
       public UserService(UserRepository userRepository) {
           this.userRepository = userRepository;
       }
   }
   ```

2. **CDI (Contexts and Dependency Injection) в Java EE:**
   - CDI — это стандарт Java EE для управления зависимостями и контекстами. Он позволяет внедрять зависимости в компоненты с помощью аннотаций, таких как `@Inject` и `@Named`.
   - CDI также предоставляет возможность управлять жизненным циклом компонентов с учетом различных контекстов, таких как пользовательский сеанс или приложение.
   - CDI ориентирован на использование **контекстов**, что позволяет применять разные стратегии для создания и уничтожения объектов в зависимости от контекста.

   **Пример внедрения зависимостей в CDI:**
   ```java
   @Inject
   private UserRepository userRepository;
   ```

---

#### **Сходства между IoC в Spring и CDI в Java EE:**
1. **Внедрение зависимостей (DI):** обе технологии поддерживают принцип DI, позволяя внедрять зависимости в компоненты через аннотации, либо через конструктор, либо через поля.
2. **Контейнеры:** как Spring, так и CDI используют контейнеры для управления жизненным циклом компонентов и их зависимостями.
3. **Конфигурация:** обе технологии позволяют конфигурировать компоненты приложения с использованием аннотаций, минимизируя необходимость в XML-конфигурациях.

---

#### **Отличия между IoC в Spring и CDI в Java EE:**

1. **Подход к конфигурации:**
   - **Spring:** поддерживает множество способов конфигурации, включая XML, аннотации и Java-классы. Это позволяет разработчику выбирать более гибкий подход к настройке.
   - **CDI:** ориентирован на использование только аннотаций и автоматически настраивает компоненты на основе этих аннотаций.

2. **Контексты:**
   - **CDI:** предоставляет расширенную модель контекстов. Например, объекты могут быть привязаны к определенному контексту, такому как пользовательская сессия или приложение. Это позволяет использовать **scopes** в CDI, такие как `@RequestScoped`, `@SessionScoped`, и другие.
   - **Spring:** имеет более простую модель контекста, но поддерживает концепции скоупов через аннотации типа `@Scope`. Хотя в Spring можно использовать различные типы скоупов, они ограничены по сравнению с CDI.

3. **Реализация:**
   - **Spring:** контейнер IoC в Spring предоставляет гораздо больше возможностей для настройки и кастомизации. Он интегрируется с рядом технологий, таких как AOP (аспектно-ориентированное программирование) и интеграция с различными фреймворками.
   - **CDI:** CDI более "встроенная" в Java EE и менее гибкая, чем Spring, по сравнению с функциональностью, предоставляемой Spring.

4. **Дополнительные функции:**
   - **Spring:** поддерживает AOP, шаблоны проектирования и многочисленные расширения, такие как Spring Security, Spring Data, и другие.
   - **CDI:** в основном фокусируется на DI и контекстах, с ограниченными возможностями для добавления других фреймворков.

---

### **13. Реализация REST API в Java EE и Spring**

#### **REST API в Java EE:**
1. **JAX-RS (Java API for RESTful Web Services)** — это спецификация для создания RESTful веб-сервисов в Java EE. JAX-RS определяет как компоненты должны обрабатывать HTTP-запросы и возвращать ответы.
   
   **Основные аннотации JAX-RS:**
   - `@Path`: указывает путь к ресурсу.
   - `@GET`, `@POST`, `@PUT`, `@DELETE`: соответствуют HTTP методам.
   - `@Produces` и `@Consumes`: для указания типов контента.

   **Пример REST API с JAX-RS:**
   ```java
   @Path("/users")
   public class UserResource {
       @GET
       @Produces(MediaType.APPLICATION_JSON)
       public List<User> getUsers() {
           return userService.getAllUsers();
       }
   }
   ```

2. **JAX-RS с Java EE контейнером:**
   - Java EE поддерживает создание RESTful сервисов с использованием JAX-RS. Все компоненты JAX-RS автоматически управляются контейнером Java EE.
   - В Java EE можно настроить `web.xml` или использовать аннотации для настройки роутинга и конфигурации ресурсов.

#### **REST API в Spring:**
1. **Spring MVC (и Spring Boot) для REST:**
   - В Spring для создания REST API используется Spring MVC с аннотациями `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, и другими.
   - Spring Boot предоставляет удобный способ для автоматической конфигурации приложения для работы с REST API без необходимости в сложной настройке.

   **Пример REST API с Spring:**
   ```java
   @RestController
   @RequestMapping("/users")
   public class UserController {
       @GetMapping
       public List<User> getUsers() {
           return userService.getAllUsers();
       }
   }
   ```

2. **Spring Boot для упрощенной настройки REST:**
   - Spring Boot упрощает создание REST API, автоматически конфигурируя сервер, добавляя зависимости и настройки.
   - В Spring Boot разработчик может просто создать контроллер, и все настройки будут автоматически применяться.

3. **Отличия между JAX-RS и Spring для REST API:**
   - **JAX-RS** — более стандартное решение для работы с REST в Java EE, которое ориентировано на спецификации и стандарты.
   - **Spring** — более гибкий и модульный, позволяет работать с REST API, но имеет более обширную экосистему для интеграции с другими фреймворками и библиотеками (например, Spring Security для безопасности).

---

### **14. React JS. Архитектура и основные принципы разработки приложений**

#### **Архитектура React JS:**
React — это библиотека для создания пользовательских интерфейсов, разработанная компанией Facebook. Основная цель React — эффективно обновлять и рендерить компоненты интерфейса в зависимости от состояния данных. React использует компонентную модель, где каждый компонент является самостоятельным элементом интерфейса.

1. **Основные концепции React:**
   - **JSX (JavaScript XML):** синтаксис, который позволяет описывать интерфейс в виде HTML-подобного кода внутри JavaScript. JSX преобразуется в обычный JavaScript, который React понимает и рендерит.
   - **Компоненты:** основные строительные блоки React-приложений. Они могут быть классовыми или функциональными.
   - **Virtual DOM:** React использует виртуальное представление DOM для ускорения рендеринга. Когда состояние компонента изменяется, React обновляет виртуальный DOM и сравнивает его с реальным DOM, минимизируя количество изменений.
   - **Props (Свойства):** данные, которые передаются от родительского компонента к дочернему.
   - **State (Состояние):** данные, которые могут изменяться внутри компонента и влиять на его внешний вид и поведение.

2. **Основные принципы разработки приложений:**
   - **Компонентный подход:** приложение разбивается на небольшие, переиспользуемые компоненты.
   - **Однонаправленный поток данных:** данные передаются от родительских компонентов к дочерним через props. Дочерние компоненты не могут напрямую изменять данные родительского компонента, только через callback-функции.
   - **Декларативный стиль:** разработчики описывают, что приложение должно отображать, а React сам решает, как это сделать на основе состояния.

---

### **15. Компоненты React. State & Props. "Умные" и "глупые" компоненты**

#### **Компоненты:**
Компоненты в React — это функции или классы, которые управляют частью интерфейса приложения. Компоненты могут быть двух типов:
- **Функциональные компоненты:** используются для простых случаев и представляют собой функции, которые возвращают JSX-разметку.
- **Классовые компоненты:** более сложные компоненты, которые могут иметь собственное состояние (state) и жизненный цикл. Однако с приходом React Hooks функциональные компоненты стали более популярными.

#### **Props (Свойства):**
Props — это способ передачи данных от родительских компонентов к дочерним. Они могут быть любыми значениями, такими как строки, числа, массивы, объекты, функции и другие компоненты.

Пример передачи props:
```jsx
function Parent() {
    return <Child name="John" age={30} />;
}

function Child(props) {
    return <div>{props.name} is {props.age} years old.</div>;
}
```

#### **State (Состояние):**
State — это данные, которые хранятся внутри компонента и могут изменяться по мере взаимодействия с пользователем или другими событиями. Состояние позволяет компоненту изменять свой внешний вид или поведение.

Пример использования состояния:
```jsx
class Counter extends React.Component {
    constructor(props) {
        super(props);
        this.state = { count: 0 };
    }

    increment = () => {
        this.setState({ count: this.state.count + 1 });
    };

    render() {
        return (
            <div>
                <p>Count: {this.state.count}</p>
                <button onClick={this.increment}>Increment</button>
            </div>
        );
    }
}
```

#### **"Умные" и "глупые" компоненты:**
- **"Глупые" компоненты (Presentational Components):** отвечают только за отображение данных, которые получают через props. У них нет состояния и логики.
- **"Умные" компоненты (Container Components):** управляют состоянием, выполняют логику, обрабатывают события и передают данные в "глупые" компоненты через props.

Пример разделения на "умные" и "глупые" компоненты:
```jsx
// "Глупый" компонент
function Display({ count }) {
    return <p>Count: {count}</p>;
}

// "Умный" компонент
class Counter extends React.Component {
    constructor(props) {
        super(props);
        this.state = { count: 0 };
    }

    increment = () => {
        this.setState({ count: this.state.count + 1 });
    };

    render() {
        return (
            <div>
                <Display count={this.state.count} />
                <button onClick={this.increment}>Increment</button>
            </div>
        );
    }
}
```

---

### **16. Разметка страниц в React-приложениях. JSX**

#### **JSX:**
JSX — это синтаксический сахар, который позволяет писать разметку, похожую на HTML, прямо в JavaScript. React использует JSX для создания компонентов и их структуры. JSX позволяет легко интегрировать логику с разметкой, а также создавать компоненты, которые могут быть динамически обновлены на основе состояния.

Пример JSX:
```jsx
const element = <h1>Hello, world!</h1>;
```

JSX также поддерживает выражения JavaScript:
```jsx
const name = 'John';
const element = <h1>Hello, {name}!</h1>;
```

JSX код трансформируется в обычный JavaScript, например:
```jsx
const element = <h1>Hello, world!</h1>;
```
будет преобразован в:
```javascript
const element = React.createElement('h1', null, 'Hello, world!');
```

---

### **17. Навигация в React-приложениях. ReactRouter**

#### **React Router:**
React Router — это библиотека для добавления маршрутизации в React-приложения, которая позволяет создавать многокраничные приложения с управлением переходами между страницами. Это особенно важно для SPA (Single Page Applications), где переходы между страницами не требуют перезагрузки страницы.

Основные компоненты React Router:
- **BrowserRouter:** компонент, который предоставляет контекст для маршрутизации.
- **Route:** компонент, который определяет путь и компонент, который должен быть рендерен для этого пути.
- **Link:** компонент, который заменяет обычные ссылки, обеспечивая навигацию без перезагрузки страницы.

Пример использования:
```jsx
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

function App() {
    return (
        <Router>
            <nav>
                <Link to="/">Home</Link>
                <Link to="/about">About</Link>
            </nav>
            <Route path="/" exact component={Home} />
            <Route path="/about" component={About} />
        </Router>
    );
}

function Home() {
    return <h2>Home Page</h2>;
}

function About() {
    return <h2>About Page</h2>;
}
```

---

### **18. Управление состоянием интерфейса. Redux**

#### **Redux:**
Redux — это библиотека для управления состоянием приложения. Она используется для централизованного хранения состояния в одном месте, что позволяет компонентам эффективно обмениваться данными и управлять состоянием.

1. **Основные принципы Redux:**
   - **Store (Хранилище):** единственное место для хранения состояния.
   - **Actions (Действия):** описывают, что должно произойти в приложении (например, изменение состояния).
   - **Reducers (Редьюсеры):** функции, которые определяют, как изменяется состояние на основе actions.
   - **Dispatch (Диспатч):** функция, которая отправляет action в хранилище, чтобы изменить состояние.

Пример использования Redux:
```javascript
// Action
const increment = { type: 'INCREMENT' };

// Reducer
function counter(state = 0, action) {
    switch (action.type) {
        case 'INCREMENT':
            return state + 1;
        default:
            return state;
    }
}

// Store
const store = Redux.createStore(counter);

// Dispatch Action
store.dispatch(increment);
```

---

### **19. Angular: архитектура и основные принципы разработки приложений**

#### **Архитектура Angular:**
Angular — это фреймворк для создания динамичных, одностраничных веб-приложений (SPA). Основой Angular является компонентная архитектура, которая использует декларативный подход для построения пользовательского интерфейса и облегчает управление состоянием и взаимодействием компонентов.

1. **Компоненты:**
   - В Angular компоненты представляют собой элементы пользовательского интерфейса, которые включают шаблон (HTML), логику (TypeScript) и стиль (CSS).
   - Каждый компонент управляет частью UI и может взаимодействовать с другими компонентами.
   
2. **Модули:**
   - Angular приложения организованы в модули. Каждый модуль представляет собой контейнер для компонентов, сервисов, директив и других сущностей, которые объединяются по функциональному назначению.
   - Основной модуль приложения — это `AppModule`, который инициализирует приложение.

3. **Сервисы:**
   - Сервисы — это классы, которые предоставляют логику для обработки данных и могут быть инжектированы в компоненты или другие сервисы. Сервисы отвечают за обработку данных, взаимодействие с сервером, работу с состоянием и другие бизнес-операции.

4. **Директивы:**
   - Директивы позволяют добавлять поведение в элементы DOM. Они могут быть структурными (например, `ngIf`, `ngFor`) или аттрибутными (например, `ngClass`, `ngStyle`).

---

### **20. Angular: модули, компоненты, сервисы и DI (Dependency Injection)**

#### **Модули (Modules):**
Модули являются основной единицей организации кода в Angular-приложении. Каждый Angular-модуль (представленный с помощью `@NgModule`) состоит из:
- **Компонентов**
- **Директив**
- **Пайпов**
- **Сервисов**

Основной модуль приложения — `AppModule`. Модуль может быть функционально разделён на различные части, что помогает в организации и повторном использовании кода.

```typescript
@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

#### **Сервисы и Dependency Injection (DI):**
Angular использует паттерн Dependency Injection для управления зависимостями между компонентами и сервисами. Сервис может быть предоставлен в компонент, модуль или глобально.

Пример создания и использования сервиса:
```typescript
@Injectable({
  providedIn: 'root', // доступен во всем приложении
})
export class DataService {
  getData() {
    return ['item1', 'item2', 'item3'];
  }
}

@Component({
  selector: 'app-root',
  template: '<ul><li *ngFor="let item of items">{{ item }}</li></ul>',
})
export class AppComponent {
  items: string[];

  constructor(private dataService: DataService) {
    this.items = this.dataService.getData();
  }
}
```

DI в Angular позволяет легко управлять зависимостями и инжектировать сервисы в компоненты.

---

### **21. Angular: шаблоны страниц, жизненный цикл компонентов, подключение CSS**

#### **Шаблоны страниц (Templates):**
Шаблон в Angular — это HTML-код с директивами и привязками данных, который отображает состояние компонента. В Angular существует несколько видов привязки:
- **Interpolation ({{}}):** привязка данных внутри текста.
- **Property Binding ([property]):** привязка данных к аттрибутам элементов.
- **Event Binding ( (event)):** привязка событий к методам компонента.
- **Two-way Binding ([()].):** двусторонняя привязка данных между компонентом и элементом.

Пример шаблона с привязкой:
```html
<input [(ngModel)]="username">
<p>{{ username }}</p>
```

#### **Жизненный цикл компонентов:**
Angular компоненты имеют ряд хуков жизненного цикла, которые позволяют отслеживать этапы их создания, обновления и уничтожения:
- **ngOnInit:** вызывается один раз при инициализации компонента.
- **ngOnChanges:** вызывается при изменении входных данных компонента.
- **ngDoCheck:** позволяет самостоятельно отслеживать изменения.
- **ngOnDestroy:** вызывается перед уничтожением компонента.

Пример использования жизненного цикла:
```typescript
@Component({
  selector: 'app-root',
  template: '<p>{{ message }}</p>',
})
export class AppComponent implements OnInit {
  message: string;

  ngOnInit() {
    this.message = 'Component Initialized';
  }
}
```

#### **Подключение CSS:**
В Angular можно использовать локальные стили для компонента, указывая их в декораторе `@Component`. Стили могут быть как инлайн (включены прямо в компонент), так и в виде внешних файлов CSS.

Пример:
```typescript
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {}
```

---

### **22. Angular: клиент-серверное взаимодействие, создание, отправка и валидация данных форм**

#### **Клиент-серверное взаимодействие:**
Angular предоставляет модуль `HttpClient` для выполнения HTTP-запросов. Этот сервис поддерживает все стандартные методы (GET, POST, PUT, DELETE) и позволяет взаимодействовать с REST API.

Пример отправки GET-запроса:
```typescript
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private http: HttpClient) {}

  getData() {
    return this.http.get('https://api.example.com/data');
  }
}
```

#### **Создание и отправка данных форм:**
В Angular для работы с формами используются два подхода:
- **Template-driven forms:** формы, которые управляются с помощью директив Angular.
- **Reactive forms:** формы, которые управляются через код компонента.

Пример использования реактивной формы:
```typescript
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  template: `
    <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
      <input formControlName="username" />
      <input formControlName="password" type="password" />
      <button type="submit">Login</button>
    </form>
  `
})
export class LoginComponent {
  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  onSubmit() {
    console.log(this.loginForm.value);
  }
}
```

#### **Валидация данных форм:**
Angular поддерживает встроенную валидацию с помощью `Validators` и позволяет добавлять кастомные валидаторы.

Пример валидации:
```typescript
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  template: `
    <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
      <input formControlName="username" />
      <input formControlName="password" type="password" />
      <button type="submit" [disabled]="!loginForm.valid">Login</button>
    </form>
  `
})
export class LoginComponent {
  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3)]),
    password: new FormControl('', [Validators.required])
  });

  onSubmit() {
    if (this.loginForm.valid) {
      console.log(this.loginForm.value);
    }
  }
}
```
