## **Monitor**  
### **Описание проекта**  
Монитор для парсинга веб страниц и отслеживания объявлений о продаже товаров с таких площадок, как Avito, TheMarket, Ebay.
+ Общение бота с сервисом монитора происходит по REST API
+ Возможность отслеживать несколько ссылок в многопоточном режиме
+ Минимальная задержка
+ Возможность парсить защищенные сайты
+ Интеграция с телеграм ботом (https://github.com/alexeyrand/MonitorItemsBot)
+ Интеграция с Дискрод сервером
### **Общая структурная схема архитектуры** 
Monitor telegram bot service - spring boot приложение, отвечающее за функционирование телеграм бота.  
Monitor service (публичный репозиторй https://github.com/alexeyrand/MonitorItems) - spring boot приложение, отвечающее за парсинг веб сайтов.  
![Structure schema](/images/schema.png)
### **Содержание**

### **Технологии**
+ Java 17
+ Spring Boot 3
+ Selenium framework
+ Telegram api
+ Discord api
+ REST
### **Как использовать**
```
sudo java -jar
```
### **Deploy и CI/CD**

