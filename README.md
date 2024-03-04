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
[Monitor telegram bot](https://github.com/alexeyrand/MonitorTelegramBot/blob/main/README.md) - представляет из себя spring boot приложение, отвечающее за функционирование телеграм бота.  
Monitor - spring boot приложение, в котором сосредаточена основная бизнес логика парсинга и мониторинга страниц.  
![Structure schema](/images/schema.png)

### **Технологии**
+ Java 17
+ Spring Boot 3
+ Selenium framework
+ Telegram api
+ Discord api
+ REST
+ Multithreading
### **Как использовать**
```
sudo java -jar
```
### **Deploy и CI/CD**
### **TODO**
+ Create a black list of sellers
+ :white_check_mark: Fix NoSuchElementException for image
+ :white_check_mark: Fix Out of Memery Chrome driver
+ Add Datebase H2
+ Add autotest
