# Hibra Libra
Java проект написанный в IDE Eclipse с использованием Hibernate и JavaFx, собираемый Maven.

## Архитектура
Проект разделён на логические пакеты для удобства навигации:
- com.pofigist.hibraLibra2.**entities**
	Описывает таблицы базы данных
- com.pofigist.hibraLibra2.**controllers**
	Описывает модель поведения страниц приложения.
	Графическое представление страниц описывается в одноимённом с классом контроллера .fxml файле расположенном в src/main/resources/com/pofigist/hibraLibra2/ с измененным постфиксом View вместо Controller (для контроллера *BooksController.java* связным граф. представлением является *BooksView.fxml*)
- com.pofigist.hibraLibra2.**model**
	Реализует базисные функциональные задачи программы такие как подключение к базе данных, извлечение и запись элементов в БД, а также промежуточное хранение даных.
- com.pofigist.**hibraLibra2**
	Сюда вынесено всё то, что не нашло место во внутренних пакетах: главный класс приложения, классы обеспечивающие генерацию таблиц и диалоговых окон.

## Скриншоты
### Структура базы данных
![Структура базы данных](wikipics/Screenshot_7.png)
### Демонстрация программы
![Пример работы программы](wikipics/Screenshot_1.png)
![Пример работы программы](wikipics/Screenshot_2.png)
![Пример работы программы](wikipics/Screenshot_3.png)
![Пример работы программы](wikipics/Screenshot_4.png)
![Пример работы программы](wikipics/Screenshot_5.png)
![Пример работы программы](wikipics/Screenshot_6.png)

## Рефлексия
Таблицы данных а также диологовые окна HibraLibra создаются автоматически за счёт механизма рефлексии Java.
Поля сущностей базы данных учавствующие в таблицах/диалоговых окнах должны использовать аннотацию **UIField** (com.pofigist.hibraLibra2). Данная аннотация позволяет задать следующие мета-данные:
- title - название столбца в таблице / поля в диалоге
- useInCreateDialog - отображается ли поле в диалоге создания объекта
- showInTable - отображается ли поле в таблице
- source - имя переменной-источника данных для выбора значения (ComboBox в диалогах). Источник должен реализовывать List<>.


## Достоинства и недостатки
### Достоинства
- Простота в добавлении новых таблиц в базу данных
- MVC-подобная структура приложения с автоматической привязкой контроллеров к представлениям.
### Недостатки
- Данные в страницу загружаются все разом (проблемы при больших объёмах данных)
- Проверка введенных в диалоговое окно данных полностью отводится на пользовательский делегат
- При ошибках Hibernate конечный пользователь не будет уведомлён о них
- Таблица Books хранит издателя в виде текста в себе (лишняя трата памяти)
- Отсутствует столбец "автор" в таблице Books (забыл)
- База данных хранит текущее состояние системы, отсутствует возможность, например, посмотреть историю перемещения конкретной книги между читателями; откатить систему.
#### Работа над ошибками (может быть когда-нибудь исправлю)
- Загружать данные не разом, а по страницам. Hibernate предоставляет такие функции как setFirstResult() и setMaxResults() которые реализуют работу LIMIT (LIMIT, OFFSET). Необходимо дополнительно получать общее число строк и добавить в интерфейс кнопки перемещения по страницам. Реализовать контроллер, который будет эти перемещения обрабатывать.
- Обернуть Hibernate запросы в try catch и реализовать визуал для вывода ошибок. На данный момент спасает параметр конфигурации *CURRENT_SESSION_CONTEXT_CLASS* = *thread*  - он обеспечивает выполнение запросов в отдельном потоке, за счёт чего поток приложения при ошибках не останавливается и программа не прекращает работы
- Для таблицы Books создать две дополнительные таблицы Authors и Publishers, где хранить текстовые названия, а в Books сделать id-ссылку на них.
- Переработать архитектуру базы данных. Создать таблицу описывающую физическое присутствие книги (на данный момент книга как таковая может существовать только в рамках книжного шкафа или у читателя). Добавить событийные таблицы поступления книги, её утери. И МНОГО ДРУГОЕ.
